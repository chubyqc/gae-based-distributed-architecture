package chubyqc.gaeDistributed.server.client;

import chubyqc.gaeDistributed.server.client.commands.Commands;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DaftiServiceAsync {
	void register(String name, String password, String email, AsyncCallback<Void> callback);
	
	void isBooted(String clientAddress, AsyncCallback<Void> callback);
	
	void getCommands(String username, AsyncCallback<Commands> callback);
}
