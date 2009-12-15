package chubyqc.gaeDistributed.server.client;

import chubyqc.gaeDistributed.server.client.states.commands.Commands;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface DaftiService extends RemoteService {
	
	void login(String username, String password) throws ClientException;
	
	void register(String name, String password, String email) throws ClientException;
	
	void isBooted() throws ClientException;
	
	Commands getCommands() throws ClientException;
}
