package chubyqc.gaeDistributed.client;

public class Start {
	
	private static final String ERR_USAGE = "Usage: <username> <password> <passphrase>";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println(ERR_USAGE);
			return;
		}
		Client.createInstance(args[0], args[1], args[2]).start();
	}

}
