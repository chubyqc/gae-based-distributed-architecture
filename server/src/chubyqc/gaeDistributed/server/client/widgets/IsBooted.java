package chubyqc.gaeDistributed.server.client.widgets;


import chubyqc.gaeDistributed.server.client.BaseCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

public class IsBooted extends AbstractWidget {
	
	private static final String UI_ISBOOTED_BUTTON = "Is booted?";

	@Override
	protected void init() {
		Button isBootedButton = new Button(UI_ISBOOTED_BUTTON);
		add(isBootedButton);
		
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
