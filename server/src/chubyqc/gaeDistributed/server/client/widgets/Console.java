package chubyqc.gaeDistributed.server.client.widgets;

import java.util.Date;

import chubyqc.gaeDistributed.server.client.BaseCallback;
import chubyqc.gaeDistributed.server.client.ClientException;
import chubyqc.gaeDistributed.server.client.widgets.console.Message;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ToggleButton;

public class Console extends AbstractWidget {
	
	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
	private static final int WAIT_TIME = 1000;
	private static final String UI_POLLING = "Polling";
	private static final String UI_NOTPOLLING = "Not polling";
	private static final String UI_CLEAR = "Clear";
	
	private Grid _console;
	private ToggleButton _toggler;
	private boolean _isPolling;

	@Override
	protected void init(Panel container) {
		_isPolling = false;
		Panel panel = new AbsolutePanel();
		panel.add(_console = new Grid(0, 2));
		container.add(panel);
		container.add(_toggler = new ToggleButton(UI_NOTPOLLING, UI_POLLING));
		
		_toggler.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (_toggler.isDown()) {
					start();
				} else {
					stop();
				}
			}
		});

		container.add(new Button(UI_CLEAR, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_console.resizeRows(0);
			}
		}));
	}

	private void append(Message message) {
		int rowIndex = _console.insertRow(_console.getRowCount());
		_console.setText(rowIndex, 0, DATE_FORMAT.format(
				new Date(message.getTime())));
		_console.setText(rowIndex, 1, message.getText());
	}
	
	public void append(String message) {
		append(new Message(message));
	}
	
	private void append(Message[] messages) {
		for (Message message : messages) {
			append(message);
		}
	}
	
	public void start() {
		show();
		_toggler.setDown(_isPolling = true);
		new Timer() {
			@Override
			public void run() {
				getService().flushMessages(new BaseCallback<Message[]>() {
					@Override
					public void onSuccess(Message[] result) {
						append(result);
						if (_isPolling) {
							schedule(WAIT_TIME);
						}
					}
					@Override
					public void onFailure(Throwable caught) {
						append(new Message(caught.getMessage()));
						if (caught instanceof ClientException) {
							stop();
						}
					}
				});
			}
		}.schedule(WAIT_TIME);
	}
	
	public void stop() {
		_toggler.setDown(_isPolling = false);
	}
}
