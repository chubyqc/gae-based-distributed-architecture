package chubyqc.gaeDistributed.server.client.commands;

import java.io.Serializable;
import java.util.Map;

public class Command implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String _name;
	protected String _exec;
	protected Map<String, String> _parameters;
	
	Command() {}
	
	public Command(String name, String exec, Map<String, String> parameters) {
		_name = name;
		_exec = exec;
		_parameters = parameters;
	}
	
	public String getName() {
		return _name;
	}
	
	public Map<String, String> getParametersSpec() {
		return _parameters;
	}
	
	@Override
	public int hashCode() {
		return _name.hashCode();
	}
}
