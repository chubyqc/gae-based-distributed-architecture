package chubyqc.gaeDistributed.client.task;

import java.io.IOException;

import chubyqc.gaeDistributed.client.exceptions.TaskFailedException;

public class SystemCommand extends AbstractTask {

	private String _command;

	public SystemCommand(int id, String command) {
		super(id);
		_command = command;
	}

	public void execute() throws TaskFailedException {
		try {
			Runtime.getRuntime().exec(_command);
		} catch (IOException e) {
			throw new TaskFailedException(e);
		}
	}
}
