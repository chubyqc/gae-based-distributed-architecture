package chubyqc.gaeDistributed.client.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class StreamListener {
	
	private ProcessListener _processListener;
	private InputStreamReader _reader;
	
	StreamListener(ProcessListener listener, InputStream is) {
		_processListener = listener;
		_reader = new InputStreamReader(is);
	}
	
	void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					CharBuffer buffer = CharBuffer.allocate(1024);
					int read = _reader.read(buffer);
					while (read > -1) {
						buffer.clear();
						_processListener.inform(buffer.toString());
						read = _reader.read(buffer);
					}
					_processListener.ends();
				} catch (IOException e) {
					_processListener.inform(e.getMessage());
				}
			}
		}).start();
	}
}
