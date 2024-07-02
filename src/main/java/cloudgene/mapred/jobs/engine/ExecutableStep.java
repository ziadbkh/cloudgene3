package cloudgene.mapred.jobs.engine;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import cloudgene.mapred.jobs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cloudgene.mapred.jobs.sdk.WorkflowStep;
import cloudgene.mapred.plugins.PluginManager;
import cloudgene.mapred.steps.ErrorStep;
import cloudgene.mapred.steps.JavaInternalStep;
import cloudgene.mapred.util.TimeUtil;
import cloudgene.mapred.wdl.WdlStep;
import genepi.io.FileUtil;

public class ExecutableStep implements Runnable {

	private WdlStep step;

	private CloudgeneContext context;

	private CloudgeneJob job;

	private CloudgeneStep instance;

	private static final Logger log = LoggerFactory.getLogger(CloudgeneJob.class);

	private boolean successful = false;

	private long time;

	private String id = "";

	public ExecutableStep(WdlStep step, CloudgeneContext context) throws Exception {
		this.step = step;
		this.context = context;
		this.job = context.getJob();
		instance();
	}

	public WdlStep getStep() {
		return step;
	}

	public void setStep(WdlStep step) {
		this.step = step;
	}

	private void instance()
			throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		id = step.getName().toLowerCase().replace(" ", "_");

		// find step implementation

		CloudgeneStepFactory factory = CloudgeneStepFactory.getInstance();
		Class myClass = factory.getClassname(step);

		// create instance
		try {


			Object object = myClass.newInstance();

			if (object instanceof CloudgeneStep) {
				instance = (CloudgeneStep) object;
			} else if (object instanceof WorkflowStep) {
				instance = new JavaInternalStep((WorkflowStep) object);
			} else {
				instance = new ErrorStep("Error during initialization: class " + step.getClassname() + " ( "
						+ object.getClass().getSuperclass().getCanonicalName() + ") "
						+ " has to extend CloudgeneStep or WorkflowStep. ");

			}

			// check requirements
			PluginManager pluginManager = PluginManager.getInstance();
			for (String plugin : instance.getRequirements()) {
				if (!pluginManager.isEnabled(plugin)) {
					instance = new ErrorStep(
							"Requirements not fullfilled. This steps needs plugin '" + plugin + "'");
				}
			}


		} catch (Exception e) {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			String s = writer.toString();
			instance = new ErrorStep("Error during initialization: " + s);

		}

		instance.setJob(job);
	}

	@Override
	public void run() {

		job.writeLog("------------------------------------------------------");
		job.writeLog(step.getName());
		job.writeLog("------------------------------------------------------");

		long start = System.currentTimeMillis();

		try {

			instance.setup(context);
			boolean successful = instance.run(step, context);

			if (!successful) {
				job.writeLog("  " + step.getName() + " [ERROR]");
				successful = false;

				context.incCounter("steps.failure." + id, 1);
				context.submitCounter("steps.failure." + id);

				return;
			} else {
				long end = System.currentTimeMillis();
				long time = end - start;

				job.writeLog("  " + step.getName() + " [" + TimeUtil.format(time) + "]");
				setTime(time);

			}
		} catch (Exception e) {
			log.error("Running extern job failed!", e);

			context.incCounter("steps.failure." + id, 1);
			context.submitCounter("steps.failure." + id);

			successful = false;
			return;
		}

		context.incCounter("steps.success." + id, 1);
		context.submitCounter("steps.success." + id);

		successful = true;

	}

	public boolean isSuccessful() {
		return successful;
	}

	public void kill() {
		if (instance != null) {
			log.info("Get kill signal for job " + job.getId());
			instance.kill();
		}
	}

	public void updateProgress() {
		if (instance != null) {
			instance.updateProgress();
		}
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getExecutionTime() {
		return time;
	}

}
