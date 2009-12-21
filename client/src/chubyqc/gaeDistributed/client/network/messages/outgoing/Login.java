package chubyqc.gaeDistributed.client.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;
import chubyqc.gaeDistributed.server.network.messages.specs.ILogin;

public class Login extends OutgoingMessage implements ILogin {

	private String _username;
	private String _password;
	
	public Login(String username, String password) {
		_username = username;
		_password = password;
	}
	
	@Override
	public String getUsername() {
		return _username;
	}

	@Override
	public String getPassword() {
		return _password;
	}
}
