package chubyqc.gaeDistributed.server.client.widgets;

import chubyqc.gaeDistributed.server.client.BaseCallback;
import chubyqc.gaeDistributed.server.client.Dafti;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class Login extends AbstractWidget {

	private final static String UI_LOGIN_BUTTON = "Login";
	
	private TextBox _username;
	private PasswordTextBox _password;

	@Override
	protected void init() {
		add(_username = new TextBox());
		add(_password = new PasswordTextBox());
		
		Button login = new Button(UI_LOGIN_BUTTON);
		add(login);
		
		login.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});
	}

	void login() {
		login(_username.getText(), _password.getText());
	}
	
	void login(String username, String password) {
		getService().login(username, password, new BaseCallback<Void>() {
			@Override
			public void doSuccess(Void result) {
				Dafti.getInstance().startConsole();
				Dafti.getInstance().showCommands();
			}
		});
	}
}
