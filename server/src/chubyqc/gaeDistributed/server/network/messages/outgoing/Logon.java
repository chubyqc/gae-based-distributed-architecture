package chubyqc.gaeDistributed.server.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.specs.ILogon;

public class Logon extends OutgoingMessage implements ILogon {
	
	private boolean _isSuccessful;
	
	public Logon(boolean isSuccessful) {
		_isSuccessful = isSuccessful;
	}

	@Override
	public boolean isSuccessful() {
		return _isSuccessful;
	}

}
