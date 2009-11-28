package chubyqc.gaeDistributed.server.server;

import chubyqc.gaeDistributed.server.client.ClientException;
import chubyqc.gaeDistributed.server.client.DaftiService;
import chubyqc.gaeDistributed.server.users.Manager;
import chubyqc.gaeDistributed.server.users.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DaftiServiceImpl extends RemoteServiceServlet implements
		DaftiService {

	public void register(String name, String password, String email) throws ClientException {
		Manager manager = Manager.getInstance();
		try {
			User user = manager.createUser(name, password, email);
			manager.save(user);
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}
}
