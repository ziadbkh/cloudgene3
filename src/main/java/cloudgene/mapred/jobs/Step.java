package cloudgene.mapred.jobs;

import java.util.List;
import java.util.Vector;

public class Step {

	private int id;

	private String name;

	private CloudgeneJob job;

	private List<Message> logMessages = new Vector<Message>();

	public Step() {

	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public CloudgeneJob getJob() {
		return job;
	}

	public void setJob(CloudgeneJob job) {
		this.job = job;
	}

	public List<Message> getLogMessages() {
		return logMessages;
	}

	public void setLogMessages(List<Message> logMessages) {
		this.logMessages = logMessages;
	}
}
