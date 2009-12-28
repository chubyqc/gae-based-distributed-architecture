package chubyqc.gaeDistributed.server.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

public class BaseCallback<T> implements AsyncCallback<T> {
	
	public BaseCallback() {
		Dafti.getInstance().startLoading();
	}

	@Override
    public final void onFailure(Throwable caught)
    {
        Dafti.getInstance().inform(caught.getMessage());
        doFailure(caught);
        Dafti.getInstance().stopLoading();
    }

    @Override
    public final void onSuccess(T result)
    {
        Dafti.getInstance().informOfSuccess();
        doSuccess(result);
        Dafti.getInstance().stopLoading();
    }
    
    public void doFailure(Throwable caught) {}
    public void doSuccess(T result) {}
}
