package chubyqc.gaeDistributed.server.client.widgets;

import chubyqc.gaeDistributed.server.client.Dafti;
import chubyqc.gaeDistributed.server.client.DaftiServiceAsync;

import com.google.gwt.user.client.ui.AbsolutePanel;

public abstract class AbstractWidget extends AbsolutePanel {
	
	protected AbstractWidget() {
		hide();
		init();
	}
	
	public void hide() {
		setVisible(false);
	}
	
	public void show() {
		setVisible(true);
	}
	
	protected DaftiServiceAsync getService() {
		return Dafti.getInstance().getService();
	}
	
	protected abstract void init();
}
