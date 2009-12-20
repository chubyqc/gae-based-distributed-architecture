package chubyqc.gaeDistributed.server.client.widgets.console;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private long _time;
	private String _message;
	
	Message() {}
	
	public Message(String message) {
		_time = System.currentTimeMillis();
		_message = message;
	}
	
	public long getTime() {
		return _time;
	}
	
	public String getText() {
		return _message;
	}
}
