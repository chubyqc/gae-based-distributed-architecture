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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class ShowCommands extends AbstractWidget implements ICommandsWriter {
	
	private static final String UI_COMMANDS_BUTTON = "Show";
	private static final String ERR_NO_COMMANDS = "There is no commands.";
	private static final String UI_INVOKE = "Invoke";
	
	private Grid _commandsGrid;

	@Override
	protected void init(Panel container) {
		new Menu().addTo(container);
		
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
		final Panel panel = new HorizontalPanel();
		int row = _commandsGrid.insertRow(_commandsGrid.getRowCount());
		_commandsGrid.setWidget(row, 0, panel);
		
		panel.add(new Label(command.getName()));
		
		for (Map.Entry<String, String> entry : command.getParametersSpec().entrySet()) {
			TextBox textBox = new TextBox();
			textBox.setText(entry.getValue());

			panel.add(new Label(entry.getKey()));
			panel.add(textBox);
			values.put(entry.getKey(), textBox);
		}
		
		panel.add(new Button(UI_INVOKE, new ClickHandler() {
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
