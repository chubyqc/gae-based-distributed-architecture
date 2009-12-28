package chubyqc.gaeDistributed.server.client;

import chubyqc.gaeDistributed.server.client.widgets.AbstractWidget;
import chubyqc.gaeDistributed.server.client.widgets.Console;
import chubyqc.gaeDistributed.server.client.widgets.Home;
import chubyqc.gaeDistributed.server.client.widgets.IsBooted;
import chubyqc.gaeDistributed.server.client.widgets.Loading;
import chubyqc.gaeDistributed.server.client.widgets.Login;
import chubyqc.gaeDistributed.server.client.widgets.Menu;
import chubyqc.gaeDistributed.server.client.widgets.Register;
import chubyqc.gaeDistributed.server.client.widgets.ShowCommands;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Dafti implements EntryPoint {
	private static Dafti _instance;
	public static Dafti getInstance() {
		return _instance;
	}
	
    private static final String UI_SUCCESS = "Call was successful.";
    
    private static final String STYLE_CONTENT = "contentContainer";

    private DaftiServiceAsync _service;
    private AbstractWidget _home;
    private AbstractWidget _register;
    private Login _login;
    private AbstractWidget _showCommands;
    private AbstractWidget _isBooted;
    
    private AbstractWidget _currentContent;
    private Console _console;
    private AbstractWidget _loading;

	public void onModuleLoad()
    {
		_instance = this;
		_service = GWT.create(DaftiService.class);
		Panel root = RootPanel.get();
		Panel content = new VerticalPanel();
		Panel contentScroll = new ScrollPanel(content);
		contentScroll.addStyleName(STYLE_CONTENT);

        Menu menu = new Menu();
        menu.show();
        root.add(menu);
        
        Grid layout = new Grid(1, 2);
        root.add(layout);
        layout.setWidget(0, 0, contentScroll);
        layout.setWidget(0, 1, _console = new Console());
        _console.show();
        
        content.add(_currentContent = _home = new Home());
        content.add(_login = new Login());
        content.add(_register = new Register(_login));
        content.add(_showCommands = new ShowCommands());
        content.add(_isBooted = new IsBooted());
        root.add(_loading = new Loading());
        
        showHome();
    }

	public void showHome() { setState(_home); }
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
	
	void startLoading() {
		_loading.show();
	}
	
	void stopLoading() {
		_loading.hide();
	}
}
