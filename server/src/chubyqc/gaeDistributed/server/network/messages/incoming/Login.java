package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.messages.specs.ILogin;
import chubyqc.gaeDistributed.server.users.Manager;
import chubyqc.gaeDistributed.server.users.UserException;

public class Login extends IncomingMessage<ILogin> {

	@Override
	public void execute() throws UserException, Exception {
		ILogin login = getDataStore();
		Manager.getInstance().login(getSession(), login.getUsername(), 
				login.getPassword());
		Manager.getInstance().setAddress(getUsername(), getAddress());
	}

}
