package chubyqc.gaeDistributed.client;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client.createInstance(args[0], args[1]).start();
	}

}
