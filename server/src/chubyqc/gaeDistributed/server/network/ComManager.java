package chubyqc.gaeDistributed.server.network;

public class ComManager {
	private static ComManager _instance = new ComManager();
	public static ComManager getInstance() {
		return _instance;
	}
	
	private ComManager() {
	}
	
	public void alertClient(String message) {
//		try {
//			URLConnection connection = new URL("").openConnection();
//			connection.setDoOutput(true);
//			connection.getOutputStream().write(new LaunchTorrent(message).toString().getBytes());
//			connection.getOutputStream().flush();
//			connection.getInputStream().read();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
	}
}
