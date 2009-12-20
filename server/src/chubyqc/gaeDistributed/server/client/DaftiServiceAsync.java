package chubyqc.gaeDistributed.server.client;

import java.util.Map;

import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.client.widgets.console.Message;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DaftiServiceAsync {

	void login(String username, String password, AsyncCallback<Void> callback);
	
	void register(String name, String password, String email, AsyncCallback<Void> callback);
	
	void isBooted(AsyncCallback<Void> callback);
	
	void getCommands(AsyncCallback<Commands> callback);
	
	void invoke(String commandName, Map<String, String> paramValues, AsyncCallback<Void> callback);
	
	void flushMessages(AsyncCallback<Message[]> callback);
}
