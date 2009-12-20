package chubyqc.gaeDistributed.client.commands;

import chubyqc.gaeDistributed.client.Client;
import chubyqc.gaeDistributed.client.network.messages.outgoing.Inform;

public class ProcessListener {
	
	private static final String ENDED = "Process ended";
	
	private StreamListener _std;
	private StreamListener _err;
	private boolean _ended;
	
	ProcessListener(Process process) {
		_ended = false;
		_std = new StreamListener(this, process.getInputStream());
		_err = new StreamListener(this, process.getErrorStream());
	}
	
	void start() {
		_std.start();
		_err.start();
	}
	
	void inform(String message) {
		Client.getInstance().send(new Inform(message));
	}
	
	void ends() {
		if (!_ended) {
			_ended = true;
			inform(ENDED);
		}
	}
}
