package chubyqc.gaeDistributed.server.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DaftiServiceAsync {
	void register(String name, String password, String email, AsyncCallback<Void> callback);
}
