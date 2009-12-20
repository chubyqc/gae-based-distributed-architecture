package chubyqc.gaeDistributed.server.client;

import java.util.Map;

import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.client.widgets.console.Message;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface DaftiService extends RemoteService {
	
	void login(String username, String password) throws ClientException;
	
	void register(String name, String password, String email) throws ClientException;
	
	void isBooted() throws ClientException;
	
	Commands getCommands() throws ClientException;
	
	void invoke(String commandName, Map<String, String> paramValues) throws ClientException;
	
	Message[] flushMessages() throws ClientException;
}
