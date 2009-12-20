package chubyqc.gaeDistributed.client.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;
import chubyqc.gaeDistributed.server.network.messages.specs.IInform;

public class Inform extends OutgoingMessage implements IInform {
	
	private String _message;

	public Inform(String message) {
		_message = message;
	}
	
	public String getMessage() {
		return _message;
	}
}
