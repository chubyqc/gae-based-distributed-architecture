package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.ComManager;
import chubyqc.gaeDistributed.server.network.messages.outgoing.Logon;
import chubyqc.gaeDistributed.server.network.messages.specs.ILogin;
import chubyqc.gaeDistributed.server.users.Manager;
import chubyqc.gaeDistributed.server.users.User;

public class Login extends IncomingMessage<ILogin> {

	@Override
	public void execute() {
		ILogin login = getDataStore();
		Manager manager = Manager.getInstance();
		try {
			manager.clientLogin(getSession(), getAddress(), login.getUsername(), 
					login.getPassword());
		} catch (Exception e) {
			ComManager.getInstance().send(User.parseAddress(getAddress()),
					new Logon(false));
		}
	}

}
