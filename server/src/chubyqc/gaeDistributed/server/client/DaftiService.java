package chubyqc.gaeDistributed.server.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface DaftiService extends RemoteService {
	void register(String name, String password, String email) throws ClientException;
	
	void isBooted(String clientAddress);
}
