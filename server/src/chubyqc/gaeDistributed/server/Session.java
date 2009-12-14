package chubyqc.gaeDistributed.server;

import javax.servlet.http.HttpSession;

public class Session {
	
	private static final String SE_USERNAME = "username";
	
	private HttpSession _session;
	
	public Session(HttpSession session) {
		_session = session;
	}
	
	public String getUsername() {
		return (String)get(SE_USERNAME);
	}
	
	public void setUsername(String username) {
		set(SE_USERNAME, username);
	}
	
	public Object get(String key) {
		return _session.getAttribute(key);
	}
	
	public void set(String key, Object value) {
		_session.setAttribute(key, value);
	}
}
