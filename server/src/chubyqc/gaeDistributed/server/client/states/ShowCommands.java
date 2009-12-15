package chubyqc.gaeDistributed.server.client.states;

import chubyqc.gaeDistributed.server.client.BaseCallback;
import chubyqc.gaeDistributed.server.client.Dafti;
import chubyqc.gaeDistributed.server.client.states.commands.Command;
import chubyqc.gaeDistributed.server.client.states.commands.Commands;
import chubyqc.gaeDistributed.server.client.states.commands.ICommandsWriter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;

public class ShowCommands extends BaseState implements ICommandsWriter {
	
	private static final String UI_COMMANDS_BUTTON = "Show";
	private static final String ERR_NO_COMMANDS = "There is no commands.";
	
	private Grid _commandsGrid;

	@Override
	protected void init(Panel container) {
		super.init(container);
		
		new BaseState().addTo(container);
		
		Button getCommands = new Button(UI_COMMANDS_BUTTON);
		container.add(getCommands);
		
		container.add(_commandsGrid = new Grid(0, 1));
		
		getCommands.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showCommands();
			}
		});
	}

	void showCommands() {
		getService().getCommands(new BaseCallback<Commands>() {
			@Override
			public void onSuccess(Commands result) {
				result.printCommands(ShowCommands.this);
				if (_commandsGrid.getRowCount() == 0) {
					Dafti.getInstance().inform(ERR_NO_COMMANDS);
				}
			}
		});
	}

	@Override
	public void print(Command command) {
		int row = _commandsGrid.insertRow(_commandsGrid.getRowCount());
		_commandsGrid.setText(row, 0, command.getName());
	}
}
