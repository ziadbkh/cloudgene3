package cloudgene.mapred.plugins.nextflow;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import cloudgene.mapred.jobs.Step;
import cloudgene.mapred.plugins.nextflow.report.CommandOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cloudgene.mapred.jobs.CloudgeneContext;
import cloudgene.mapred.jobs.workspace.IWorkspace;
import genepi.io.FileUtil;

public class NextflowTask {

	private int id;

	private Map<String, Object> trace;

	private String logText = null;

	private CloudgeneContext context;

	private Step step;

	private static final Logger log = LoggerFactory.getLogger(NextflowTask.class);
	
	public NextflowTask(CloudgeneContext context, Map<String, Object> trace, Step step) {
		id = (Integer) trace.get("task_id");
		this.trace = trace;
		this.context = context;
		this.step = step;
	}

	public void update(Map<String, Object> trace) throws IOException {
		this.trace = trace;

		// TODO: check if CHACHED os also needed!
		String status = (String) trace.get("status");
		if (!status.equals("COMPLETED") && !status.equals("FAILED")) {
			return;
		}

		String workDir = (String) trace.get("workdir");

		String logFilename = FileUtil.path(workDir, "cloudgene.out");
		if (parseCommandOutput(logFilename)){
			return;
		}

		String outputFilename = FileUtil.path(workDir, CommandOutput.DEFAULT_FILENAME);
		parseCommandOutput(outputFilename);

	}

	private boolean parseCommandOutput(String reportFilename) throws IOException {
		IWorkspace workspace = context.getJob().getWorkspace();
		if (!workspace.exists(reportFilename)) {
			return false;
		}
		InputStream stream = context.getWorkspace().download(reportFilename);
		try {
			CommandOutput output = new CommandOutput(stream);
			output.execute(context, step);
		} catch (Exception e) {
			log.error("[Job {}] Invalid report file.", context.getJobId(), e);
			logText = "Invalid report file: \n" + FileUtil.readFileAsString(stream);
		}
		return true;
	}

	public int getId() {
		return id;
	}

	public Map<String, Object> getTrace() {
		return trace;
	}

	public String getLogText() {
		return logText;
	}

}
