package chubyqc.gaeDistributed.server.client.states;


import chubyqc.gaeDistributed.server.client.BaseCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class IsBooted extends BaseState {
	
	private static final String UI_ISBOOTED_BUTTON = "Is booted?";
	
	private TextBox _clientAddress;

	@Override
	protected void init(Panel container) {
		super.init(container);
		
		Button isBootedButton = new Button(UI_ISBOOTED_BUTTON);
		container.add(isBootedButton);
		
		_clientAddress = new TextBox();
		container.add(_clientAddress);
		
		isBootedButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				isBooted();
			}
		});
	}

	void isBooted() {
		getService().isBooted(_clientAddress.getText(), new BaseCallback<Void>());
	}
}
