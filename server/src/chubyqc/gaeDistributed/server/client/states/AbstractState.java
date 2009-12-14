package chubyqc.gaeDistributed.server.client.states;

import chubyqc.gaeDistributed.server.client.Dafti;
import chubyqc.gaeDistributed.server.client.DaftiServiceAsync;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;

public abstract class AbstractState {

	private AbsolutePanel _panel;
	
	public AbstractState() {
		_panel = new AbsolutePanel();
		hide();
		init(_panel);
	}
	
	public void addTo(Panel container) {
		container.add(_panel);
	}
	
	public void hide() {
		_panel.setVisible(false);
	}
	
	public void show() {
		_panel.setVisible(true);
	}
	
	protected DaftiServiceAsync getService() {
		return Dafti.getInstance().getService();
	}
	
	protected abstract void init(Panel container);
}
