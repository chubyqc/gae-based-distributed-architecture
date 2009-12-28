package chubyqc.gaeDistributed.server.client.widgets;

import java.util.Date;

import chubyqc.gaeDistributed.server.client.widgets.console.Message;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class Console extends AbstractWidget {
	
	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
	private static final int WAIT_TIME = 1000;
	private static final String UI_POLLING = "Polling";
	private static final String UI_NOTPOLLING = "Not polling";
	private static final String UI_CLEAR = "Clear";
	private static final String STYLE_CONSOLE = "consolePanel";
	private static final String STYLE_BTNS = "consoleButton";
	
	private Grid _console;
	private ToggleButton _toggler;
	private boolean _isPolling;
	private ScrollPanel _panel;
	private int _timerCount;

	protected void init() {
		_timerCount = 0;
		_isPolling = false;
		_panel = new ScrollPanel();
		_panel.addStyleName(STYLE_CONSOLE);
		_panel.add(_console = new Grid(0, 2));
		add(_panel);
		Grid buttons = new Grid(1, 2);
		add(buttons);
		buttons.setWidget(0, 0, _toggler = new ToggleButton(UI_NOTPOLLING, UI_POLLING));
		_toggler.addStyleName(STYLE_BTNS);
		
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

		Button clearBtn = new Button(UI_CLEAR, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_console.resizeRows(0);
			}
		});
		clearBtn.addStyleName(STYLE_BTNS);
		buttons.setWidget(0, 1, clearBtn);
	}

	private void append(Message message) {
		int rowIndex = _console.insertRow(_console.getRowCount());
		_console.setText(rowIndex, 0, DATE_FORMAT.format(
				new Date(message.getTime())));
		_console.setText(rowIndex, 1, message.getText());
		_panel.scrollToBottom();
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
		if (++_timerCount == 1) {
			new Timer() {
				@Override
				public void run() {
					getService().flushMessages(new AsyncCallback<Message[]>() {
						
						@Override
						public void onSuccess(Message[] result) {
							append(result);
							if (_isPolling) {
								schedule(WAIT_TIME);
							} else {
								--_timerCount;
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							stop();
							--_timerCount;
						}
					});
				}
			}.schedule(WAIT_TIME);
		}
	}
	
	public void stop() {
		_toggler.setDown(_isPolling = false);
	}
}
