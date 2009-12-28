package chubyqc.gaeDistributed.server.users;

import java.util.Collections;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.client.widgets.console.Message;

public class Manager {
	private static Manager instance = new Manager();
	public static Manager getInstance() {
		return instance;
	}
	
	private static final String ERR_LOGIN_BAD = "Bad login.";
	private static final String ERR_USER_NOT_FOUND = "Bad login.";

	private static final String CACHE = "cache";
	private static final String PREFIX_USERS = "users";
	private static final String PREFIX_UPDATED = "updated";
	
	private Cache _cache;
	
	private Manager() {
		try {
			_cache = CacheManager.getInstance().getCache(CACHE);
			if (_cache == null) {
				_cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
				CacheManager.getInstance().registerCache(CACHE, _cache);
			}
		} catch (CacheException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User login(Session session, String username, String password) 
		throws UserException, Exception {
		User user = getUser(username);
		if (!user.isRightPassword(password)) {
			throw new UserException(ERR_LOGIN_BAD);
		}
		user.remember(session);
		commit(user);
		return user;
	}
	
	public void clientLogin(Session session, String address, String username, String password)
		throws Exception {
		User user = login(session, username, password);
		user.setAddress(address);
		commit(user);
		user.logon();
	}
	
	public User createUser(String name, String password, String email) throws Exception {
		return new User(name, password, email);
	}
	
	public void save(User user) throws Exception {
		user.save();
	}
	
	public void setCommands(String username, Commands commands) throws UserException {
		User user = getUser(username);
		user.setCommands(commands);
		commit(user);
	}
	
	public void isBooted(String username) throws Exception {
		getUser(username).isBooted();
	}
	
	public Commands getCommands(String username) throws Exception {
		Commands commands = getUser(username).getCommands();
		return (commands == null) ? new Commands() : commands;
	}
	
	public void invoke(String username, String commandName, Map<String, String> paramValues) throws Exception {
		getUser(username).invoke(commandName, paramValues);
	}

	public void inform(String username, String message) throws UserException {
		User user = getUser(username);
		user.inform(message);
		commit(user);
	}
	
	@SuppressWarnings("unchecked")
	private boolean isUpdated(String key) {
		Boolean updated = (Boolean)_cache.get(key);
		updated = updated != null && updated;
		if (updated) {
			_cache.put(key, false);
		}
		return updated;
	}
	
	public Message[] flushMessages(String username) throws Exception {
		String key = PREFIX_UPDATED + username;
		while (!isUpdated(key)) {
			Thread.sleep(1000);
		}
		User user = getUser(username);
		Message[] messages = user.flushMessages();
		commit(user);
		return messages;
	}
	
	private User getUser(String username) throws UserException {
		if (username == null) {
			throw new UserException(ERR_USER_NOT_FOUND);
		}
		User user = (User)_cache.get(PREFIX_USERS + username);
		if (user == null) {
			user = GAEPersistenceManager.getInstance().getUser(username);
			commit(user);
			if (user == null) {
				throw new UserException(ERR_USER_NOT_FOUND);
			}
		}
		return user;
	}
	
	private void commit(User user) {
		user.commit(_cache, PREFIX_USERS);
		user.updated(_cache, PREFIX_UPDATED);
	}
}
