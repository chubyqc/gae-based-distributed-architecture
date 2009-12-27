package chubyqc.gaeDistributed.server.client.widgets;

import java.util.HashMap;
import java.util.Map;

import chubyqc.gaeDistributed.server.client.BaseCallback;
import chubyqc.gaeDistributed.server.client.Dafti;
import chubyqc.gaeDistributed.server.client.widgets.commands.Command;
import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.client.widgets.commands.ICommandsWriter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;

public class ShowCommands extends AbstractWidget implements ICommandsWriter {
	
	private static final String UI_COMMANDS_BUTTON = "Show";
	private static final String ERR_NO_COMMANDS = "There is no commands.";
	private static final String UI_INVOKE = "Invoke";
	
	private static final String STYLE_COMMANDS = "commandsContainer";
	
	private Grid _commandsGrid;

	@Override
	protected void init() {
		Button getCommands = new Button(UI_COMMANDS_BUTTON);
		add(getCommands);
		
		add(_commandsGrid = new Grid(0, 3));
		ScrollPanel container = new ScrollPanel(_commandsGrid);
		container.addStyleName(STYLE_COMMANDS);
		add(container);
		
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
				_commandsGrid.resizeRows(0);
				result.printCommands(ShowCommands.this);
				if (_commandsGrid.getRowCount() == 0) {
					Dafti.getInstance().inform(ERR_NO_COMMANDS);
				}
			}
		});
	}

	@Override
	public void print(final Command command) {
		final Map<String, TextBox> values = new HashMap<String, TextBox>();
		int row = _commandsGrid.insertRow(_commandsGrid.getRowCount());
		_commandsGrid.setText(row, 0, command.getName());
		
		for (Map.Entry<String, String> entry : command.getParametersSpec().entrySet()) {
			TextBox textBox = new TextBox();
			textBox.setText(entry.getValue());
			values.put(entry.getKey(), textBox);

			_commandsGrid.setText(row, 1, entry.getKey());
			_commandsGrid.setWidget(row, 2, textBox);
			
			row = _commandsGrid.insertRow(_commandsGrid.getRowCount());
		}
		
		_commandsGrid.setWidget(row, 2, new Button(UI_INVOKE, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> paramValues = new HashMap<String, String>();
				for (Map.Entry<String, TextBox> value : values.entrySet()) {
					paramValues.put(value.getKey(), value.getValue().getText());
				}
				getService().invoke(command.getName(), paramValues, new BaseCallback<Void>());
			}
		}));
	}
}
