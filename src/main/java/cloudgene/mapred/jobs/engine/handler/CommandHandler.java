package cloudgene.mapred.jobs.engine.handler;

import cloudgene.mapred.jobs.AbstractJob;
import cloudgene.mapred.jobs.WorkflowEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CommandHandler implements IJobErrorHandler {

    private String name;

    private String command;

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    public CommandHandler(String name, String command) {
        this.name = name;
        this.command = command;
    }

    @Override
    public void handle(WorkflowEngine engine, AbstractJob job) {
        log.info("Running command handler '" +  name + "' after job '" + job.getId() + "' failed...");
        Process process = null;
        try {
            String[] commandArray = command.split(" ");
            process = new ProcessBuilder(commandArray).start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                engine.block();
                log.info("Queue is blocked by command handler '" +  name + "' after job '" + job.getId() + "' failed.");
            } else {
                log.info("Command handler '" +  name + "' after job '" + job.getId() + "' returns exit code 0.");
            }

        } catch (IOException | InterruptedException e) {
           log.error("Error running command handler '" +  name + "' after job '" + job.getId() + "' failed.", e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
