package chubyqc.gaeDistributed.server.client.widgets;


import chubyqc.gaeDistributed.server.client.BaseCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class Register extends AbstractWidget {
	
	private static final String UI_EMAIL_LABEL = "Email :";
    private static final String UI_NAME_LABEL = "User name :";
    private static final String UI_PASSWORD_LABEL = "Password :";
    private static final String UI_CREATE_BUTTON = "Create";
    
    private TextBox _nameTextBox;
    private TextBox _emailTextBox;
    private TextBox _passwordTextBox;
    
    private Login _login;
    
    public Register(Login login) {
    	super();
    	_login = login;
    }

	@Override
	protected void init() {
		Grid formTable = new Grid(3, 2);
	    _emailTextBox = new TextBox();
	    _nameTextBox = new TextBox();
	    _passwordTextBox = new PasswordTextBox();
	    Button createButton = new Button(UI_CREATE_BUTTON);

	    formTable.setText(0, 0, UI_NAME_LABEL);
	    formTable.setText(1, 0, UI_PASSWORD_LABEL);
	    formTable.setText(2, 0, UI_EMAIL_LABEL);

	    formTable.setWidget(0, 1, _nameTextBox);
	    formTable.setWidget(1, 1, _passwordTextBox);
	    formTable.setWidget(2, 1, _emailTextBox);

	    add(formTable);
	    add(createButton);
	    
	    _emailTextBox.setFocus(true);
	    
	    createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				register();
			}
		});
	}

	void register() {
		getService().register(_nameTextBox.getText(), _passwordTextBox.getText(), 
					_emailTextBox.getText(), new BaseCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				super.onSuccess(result);
				_login.login(_nameTextBox.getText(), _passwordTextBox.getText());
			}
		});
	}
}
