package chubyqc.gaeDistributed.server.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Dafti implements EntryPoint {
	
	private static final String UI_EMAIL_LABEL = "Email :";
    private static final String UI_NAME_LABEL = "User name :";
    private static final String UI_PASSWORD_LABEL = "Password :";
    private static final String UI_CREATE_BUTTON = "Create";
    private static final String UI_SUCCESS = "User was successfully created";

    private TextBox _nameTextBox;
    private TextBox _emailTextBox;
    private TextBox _passwordTextBox;
    private Label _messageLabel;
    
    private DaftiServiceAsync _service = GWT.create(DaftiService.class);

	public void onModuleLoad()
    {
        _messageLabel = new Label();
        RootPanel.get().add(_messageLabel);
        
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

        RootPanel.get().add(formTable);
        RootPanel.get().add(createButton);
        
        _emailTextBox.setFocus(true);
        
        Creator handler = new Creator(this, _service);
        createButton.addClickHandler(handler);
    }
    
    private static class Creator implements ClickHandler {
        
        private DaftiServiceAsync _service;
        private Dafti _ui;
        
        Creator(Dafti ui, DaftiServiceAsync service) {
        	_service = service;
            _ui = ui;
        }

        @Override
        public void onClick(ClickEvent event)
        {
        	_service.register(_ui._nameTextBox.getText(), 
                _ui._passwordTextBox.getText(), _ui._emailTextBox.getText(), 
                new AsyncCallback<Void>() {

                    @Override
                    public void onFailure(Throwable caught)
                    {
                        _ui._messageLabel.setText(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Void result)
                    {
                        _ui._messageLabel.setText(UI_SUCCESS);
                    }
            });
        }
    }
}
