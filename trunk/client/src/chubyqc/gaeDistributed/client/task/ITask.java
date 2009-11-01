package chubyqc.gaeDistributed.client.task;

import chubyqc.gaeDistributed.client.exceptions.TaskFailedException;

public interface ITask {
	void execute() throws TaskFailedException;
}
