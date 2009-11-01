package chubyqc.gaeDistributed.client.exceptions;

public class TaskFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TaskFailedException(Throwable throwable) {
		super(throwable);
	}

	public TaskFailedException(String message) {
		super(message);
	}
}
