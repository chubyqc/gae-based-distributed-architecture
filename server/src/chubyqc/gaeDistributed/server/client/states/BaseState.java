package chubyqc.gaeDistributed.server.client.states;

import chubyqc.gaeDistributed.server.client.Dafti;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;


public class BaseState extends AbstractState {
	
	private static final String UI_HOME = "Home";
	private static final String UI_REGISTER = "Register";
	private static final String UI_LOGIN = "Login";
	private static final String UI_SHOWCOMMANDS = "Show commands";
	private static final String UI_ISBOOTED = "Is booted?";
	private static final String UI_SEP = "|";

	@Override
	protected void init(Panel container) {
		HorizontalPanel panel = new HorizontalPanel();
		
		String target = new String();
		Hyperlink home = new Hyperlink(UI_HOME, target);
		Hyperlink register = new Hyperlink(UI_REGISTER, target);
		Hyperlink login = new Hyperlink(UI_LOGIN, target);
		Hyperlink showCommands = new Hyperlink(UI_SHOWCOMMANDS, target);
		Hyperlink isBooted = new Hyperlink(UI_ISBOOTED, target);
		
		panel.add(home);
		panel.add(new Label(UI_SEP));
		panel.add(register);
		panel.add(new Label(UI_SEP));
		panel.add(login);
		panel.add(new Label(UI_SEP));
		panel.add(showCommands);
		panel.add(new Label(UI_SEP));
		panel.add(isBooted);
		
		container.add(panel);

		home.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Dafti.getInstance().showHome();
			}
		});
		register.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Dafti.getInstance().showRegister();
			}
		});
		login.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Dafti.getInstance().showLogin();
			}
		});
		showCommands.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Dafti.getInstance().showCommands();
			}
		});
		isBooted.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Dafti.getInstance().showIsBooted();
			}
		});
	}

}
