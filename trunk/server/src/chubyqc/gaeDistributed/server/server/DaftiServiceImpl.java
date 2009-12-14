package chubyqc.gaeDistributed.server.server;

import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.client.ClientException;
import chubyqc.gaeDistributed.server.client.DaftiService;
import chubyqc.gaeDistributed.server.client.states.commands.Commands;
import chubyqc.gaeDistributed.server.network.messages.outgoing.IsClientBooted;
import chubyqc.gaeDistributed.server.users.Manager;
import chubyqc.gaeDistributed.server.users.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DaftiServiceImpl extends RemoteServiceServlet implements
		DaftiService {
	
	public void login(String username, String password) throws ClientException {
		try {
			Manager.getInstance().login(getSession(), username, password);
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}

	public void register(String name, String password, String email) throws ClientException {
		Manager manager = Manager.getInstance();
		try {
			User user = manager.createUser(name, password, email);
			manager.save(user);
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}

	@Override
	public void isBooted(String username) {
		Manager.getInstance().sendMessage(username, new IsClientBooted());
	}

	@Override
	public Commands getCommands() {
		return Manager.getInstance().getCommands(getSession());
	}
	
	private Session getSession() {
		return new Session(getThreadLocalRequest().getSession());
	}
}
