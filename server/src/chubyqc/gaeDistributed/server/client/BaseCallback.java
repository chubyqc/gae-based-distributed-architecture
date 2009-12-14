package chubyqc.gaeDistributed.server.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

public class BaseCallback<T> implements AsyncCallback<T> {

	@Override
    public void onFailure(Throwable caught)
    {
        Dafti.getInstance().inform(caught.getMessage());
    }

    @Override
    public void onSuccess(T result)
    {
        Dafti.getInstance().informOfSuccess();
    }
}
