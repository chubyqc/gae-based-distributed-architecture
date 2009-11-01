package chubyqc.gaeDistributed.client;

import chubyqc.gaeDistributed.client.network.ComManager;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComManager.getInstance().start();
	}

}
