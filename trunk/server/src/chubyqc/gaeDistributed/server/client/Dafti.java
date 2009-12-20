package chubyqc.gaeDistributed.server.client;

import chubyqc.gaeDistributed.server.client.widgets.AbstractWidget;
import chubyqc.gaeDistributed.server.client.widgets.Console;
import chubyqc.gaeDistributed.server.client.widgets.IsBooted;
import chubyqc.gaeDistributed.server.client.widgets.Login;
import chubyqc.gaeDistributed.server.client.widgets.Menu;
import chubyqc.gaeDistributed.server.client.widgets.Register;
import chubyqc.gaeDistributed.server.client.widgets.ShowCommands;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
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

    private DaftiServiceAsync _service;
    private AbstractWidget _register;
    private AbstractWidget _login;
    private AbstractWidget _showCommands;
    private AbstractWidget _isBooted;
    
    private AbstractWidget _currentContent;
    private Console _console;

	public void onModuleLoad()
    {
		_instance = this;
		_service = GWT.create(DaftiService.class);
		Panel root = RootPanel.get();
		Panel content = new AbsolutePanel();
		
        root.add(content);

        Menu menu = new Menu();
        menu.show();
        menu.addTo(content);
        
        (_currentContent = _register = new Register()).addTo(content);
        (_login = new Login()).addTo(content);
        (_showCommands = new ShowCommands()).addTo(content);
        (_isBooted = new IsBooted()).addTo(content);
        
        (_console = new Console()).addTo(root);
        
        showHome();
    }

	public void showHome() { _currentContent.hide(); }
	public void showRegister() { setState(_register); }
	public void showLogin() { setState(_login); }
	public void showCommands() { setState(_showCommands); }
	public void showIsBooted() { setState(_isBooted); }
	
	private void setState(AbstractWidget state) {
		_currentContent.hide();
		state.show();
		_currentContent = state;
	}
	
	public DaftiServiceAsync getService() {
		return _service;
	}
	
	public void informOfSuccess() {
		_console.append(UI_SUCCESS);
	}
	
	public void inform(String message) {
		_console.append(message);
	}
	
	public void startConsole() {
		_console.start();
	}
	
	public void stopConsole() {
		_console.stop();
	}
}
