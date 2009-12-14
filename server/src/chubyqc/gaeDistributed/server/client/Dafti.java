package chubyqc.gaeDistributed.server.client;

import chubyqc.gaeDistributed.server.client.states.AbstractState;
import chubyqc.gaeDistributed.server.client.states.BaseState;
import chubyqc.gaeDistributed.server.client.states.IsBooted;
import chubyqc.gaeDistributed.server.client.states.Login;
import chubyqc.gaeDistributed.server.client.states.Register;
import chubyqc.gaeDistributed.server.client.states.ShowCommands;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Dafti implements EntryPoint {
	private static Dafti _instance;
	public static Dafti getInstance() {
		return _instance;
	}
	
    private static final String UI_SUCCESS = "Call was successful.";

    private Label _messageLabel;
    private DaftiServiceAsync _service;
    private AbstractState _register;
    private AbstractState _login;
    private AbstractState _showCommands;
    private AbstractState _isBooted;
    private AbstractState _home;
    
    private AbstractState _currentState;

	public void onModuleLoad()
    {
		_instance = this;
		_service = GWT.create(DaftiService.class);
		
        _messageLabel = new Label();
        RootPanel.get().add(_messageLabel);

        (_currentState = _home = new BaseState()).addTo(RootPanel.get());
        (_register = new Register()).addTo(RootPanel.get());
        (_login = new Login()).addTo(RootPanel.get());
        (_showCommands = new ShowCommands()).addTo(RootPanel.get());
        (_isBooted = new IsBooted()).addTo(RootPanel.get());
        
        showHome();
    }

	public void showHome() { setState(_home); }
	public void showRegister() { setState(_register); }
	public void showLogin() { setState(_login); }
	public void showCommands() { setState(_showCommands); }
	public void showIsBooted() { setState(_isBooted); }
	
	private void setState(AbstractState state) {
		_currentState.hide();
		state.show();
		_currentState = state;
	}
	
	public DaftiServiceAsync getService() {
		return _service;
	}
	
	public void informOfSuccess() {
		_messageLabel.setText(UI_SUCCESS);
	}
	
	public void inform(String message) {
		_messageLabel.setText(message);
	}
}
