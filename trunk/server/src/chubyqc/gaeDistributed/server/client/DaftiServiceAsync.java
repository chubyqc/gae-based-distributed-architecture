package chubyqc.gaeDistributed.server.client;

import chubyqc.gaeDistributed.server.client.states.commands.Commands;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DaftiServiceAsync {

	void login(String username, String password, AsyncCallback<Void> callback);
	
	void register(String name, String password, String email, AsyncCallback<Void> callback);
	
	void isBooted(String clientAddress, AsyncCallback<Void> callback);
	
	void getCommands(AsyncCallback<Commands> callback);
}
