package cloudgene.mapred.jobs.engine.handler;

import cloudgene.mapred.jobs.AbstractJob;
import cloudgene.mapred.jobs.WorkflowEngine;

public interface IJobErrorHandler {

    public void handle(WorkflowEngine engine, AbstractJob job);

    public String getName();

}
