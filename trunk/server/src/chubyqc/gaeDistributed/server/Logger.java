package chubyqc.gaeDistributed.server;

import java.util.logging.Level;

public class Logger {
	private static Logger _instance = new Logger();
	public static Logger getInstance() {
		return _instance;
	}
	
	private static final String SUBSYSTEM = "APP";

	private java.util.logging.Logger _logger;
	
	private Logger() {
		_logger = java.util.logging.Logger.getLogger(SUBSYSTEM);
	}
	
	public void info(String msg) {
		_logger.log(Level.INFO, msg);
	}
	
	public void error(Throwable t) {
		_logger.log(Level.WARNING, t.getMessage(), t);
	}
	
	public void fatal(Throwable t) {
		_logger.log(Level.SEVERE, t.getMessage(), t);
	}
}
