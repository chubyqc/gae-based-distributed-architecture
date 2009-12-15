package chubyqc.gaeDistributed.server.client.states;


import chubyqc.gaeDistributed.server.client.BaseCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;

public class IsBooted extends BaseState {
	
	private static final String UI_ISBOOTED_BUTTON = "Is booted?";

	@Override
	protected void init(Panel container) {
		super.init(container);
		
		Button isBootedButton = new Button(UI_ISBOOTED_BUTTON);
		container.add(isBootedButton);
		
		isBootedButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				isBooted();
			}
		});
	}

	void isBooted() {
		getService().isBooted(new BaseCallback<Void>());
	}
}
