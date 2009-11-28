package chubyqc.gaeDistributed.server.client;

public class ClientException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String _type;
	
	public ClientException() {
	}
	
	public ClientException(Exception e) {
		super(e.getMessage());
		_type = e.getClass().getName();
	}
	
	public String getType() {
		return _type;
	}
}
