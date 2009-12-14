package chubyqc.gaeDistributed.server.client.states;

import chubyqc.gaeDistributed.server.client.BaseCallback;
import chubyqc.gaeDistributed.server.client.Dafti;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class Login extends BaseState {

	private final static String UI_LOGIN_BUTTON = "Login";
	
	private TextBox _username;
	private PasswordTextBox _password;

	@Override
	protected void init(Panel container) {
		super.init(container);
		
		container.add(_username = new TextBox());
		container.add(_password = new PasswordTextBox());
		
		Button login = new Button(UI_LOGIN_BUTTON);
		container.add(login);
		
		login.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});
	}

	void login() {
		getService().login(_username.getText(), _password.getText(), new BaseCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Dafti.getInstance().showCommands();
			}
		});
	}
}
