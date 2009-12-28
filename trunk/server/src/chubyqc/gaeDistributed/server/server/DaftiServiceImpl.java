package chubyqc.gaeDistributed.server.server;

import java.util.Map;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.client.ClientException;
import chubyqc.gaeDistributed.server.client.DaftiService;
import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.client.widgets.console.Message;
import chubyqc.gaeDistributed.server.users.Manager;
import chubyqc.gaeDistributed.server.users.User;

import com.google.apphosting.api.DeadlineExceededException;
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
	public void isBooted() throws ClientException {
		try {
			Manager.getInstance().isBooted(getSession().getUsername());
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}

	@Override
	public Commands getCommands() throws ClientException {
		try {
			return Manager.getInstance().getCommands(getSession().getUsername());
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}
	
	@Override
	public void invoke(String commandName, Map<String, String> paramValues) throws ClientException {
		try {
			Manager.getInstance().invoke(getSession().getUsername(), commandName, paramValues);
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}
	
	@Override
	public Message[] flushMessages() throws ClientException {
		try {
			return Manager.getInstance().flushMessages(getSession().getUsername());
		} catch (DeadlineExceededException e) {
			return new Message[0];
		} catch (Exception e) {
			Logger.getInstance().fatal(e);
			throw new ClientException(e);
		}
	}
	
	private Session getSession() {
		return new Session(getThreadLocalRequest().getSession());
	}
}
