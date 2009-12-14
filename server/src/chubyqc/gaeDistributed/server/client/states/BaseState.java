package chubyqc.gaeDistributed.server.client.states;

import chubyqc.gaeDistributed.server.client.Dafti;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Panel;


public class BaseState extends AbstractState {
	
	private static final String UI_HOME = "Home";
	private static final String UI_REGISTER = "Register";
	private static final String UI_LOGIN = "Login";
	private static final String UI_SHOWCOMMANDS = "Show commands";

	@Override
	protected void init(Panel container) {
		String target = new String();
		Hyperlink home = new Hyperlink(UI_HOME, target);
		Hyperlink register = new Hyperlink(UI_REGISTER, target);
		Hyperlink login = new Hyperlink(UI_LOGIN, target);
		Hyperlink showCommands = new Hyperlink(UI_SHOWCOMMANDS, target);
		
		container.add(home);
		container.add(register);
		container.add(login);
		container.add(showCommands);

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
	}

}
