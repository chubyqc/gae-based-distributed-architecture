package chubyqc.gaeDistributed.server.server;

import chubyqc.gaeDistributed.server.client.GreetingService;
import chubyqc.gaeDistributed.server.network.ComManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) {
		ComManager.getInstance().alertClient(input);
		return "Hello";
	}
}
