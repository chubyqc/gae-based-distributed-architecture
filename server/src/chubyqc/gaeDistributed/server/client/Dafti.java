package chubyqc.gaeDistributed.server.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
    private static final String UI_SUCCESS = "Call was successful.";
    private static final String UI_ISBOOTED_BUTTON = "Is booted?";

    private TextBox _nameTextBox;
    private TextBox _emailTextBox;
    private TextBox _passwordTextBox;
    private Label _messageLabel;

    private TextBox _clientAddress;
    
    private DaftiServiceAsync _service = GWT.create(DaftiService.class);

	public void onModuleLoad()
    {
        _messageLabel = new Label();
        RootPanel.get().add(_messageLabel);
        
        addRegisterPanel();
        addIsBootedPanel();
    }
	
	private void addIsBootedPanel() {
		Button isBootedButton = new Button(UI_ISBOOTED_BUTTON);
		RootPanel.get().add(isBootedButton);
		
		_clientAddress = new TextBox();
		RootPanel.get().add(_clientAddress);
		
		ClickHandler.create(isBootedButton, IsBootedHandler.class, this, _service);
	}
	
	private void addRegisterPanel() {
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
        
        ClickHandler.create(createButton, CreateHandler.class, this, _service);
	}

    private static abstract class ClickHandlerCreator {
    	abstract ClickHandler create();
    	abstract Class<?> getType();
    }
    
	private static abstract class ClickHandler implements com.google.gwt.event.dom.client.ClickHandler {
		
		private static Map<Class<?>, ClickHandlerCreator> _creators = create(new ClickHandlerCreator[] {
				CreateHandler.creator(),
				IsBootedHandler.creator()
		});
		private static Map<Class<?>, ClickHandlerCreator> create(ClickHandlerCreator[] creators) {
			Map<Class<?>, ClickHandlerCreator> map = new HashMap<Class<?>, ClickHandlerCreator>();
			for (ClickHandlerCreator creator : creators) {
				map.put(creator.getType(), creator);
			}
			return map;
		}
        
        protected DaftiServiceAsync _service;
        protected Dafti _ui;
        
        static void create(Button button, Class<?> spec, Dafti ui, 
        		DaftiServiceAsync service) {
			try {
				ClickHandler clickHandler = _creators.get(spec).create();
	        	clickHandler._service = service;
	        	clickHandler._ui = ui;
	        	button.addClickHandler(clickHandler);
			} catch (Exception e) {
				throw new RuntimeException();
			}
        }
	}
    
    private static class CreateHandler extends ClickHandler {

        public void onClick(ClickEvent event)
        {
        	_service.register(_ui._nameTextBox.getText(), 
                _ui._passwordTextBox.getText(), _ui._emailTextBox.getText(), 
                new EmptyCallback(_ui));
        }
    	
		public static ClickHandlerCreator creator() {
			return new ClickHandlerCreator() {
				
				@Override
				public ClickHandler create() {
					return new CreateHandler();
				}

				@Override
				Class<?> getType() {
					return CreateHandler.class;
				}
			};
		}
    }
    
    private static class IsBootedHandler extends ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			_service.isBooted(_ui._clientAddress.getText(), new EmptyCallback(_ui));
		}
    	
		public static ClickHandlerCreator creator() {
			return new ClickHandlerCreator() {
				
				@Override
				public ClickHandler create() {
					return new IsBootedHandler();
				}

				@Override
				Class<?> getType() {
					return IsBootedHandler.class;
				}
			};
		}
    }
    
    private static class EmptyCallback implements AsyncCallback<Void> {
    	
    	private Dafti _ui;

		public EmptyCallback(Dafti ui) {
			_ui = ui;
		}

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
    }
}
