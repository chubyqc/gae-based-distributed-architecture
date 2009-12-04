package chubyqc.gaeDistributed.server.network.messages.incoming;

public class ClientBooted extends IncomingMessage {

	@Override
	public void execute() {
		System.out.println("Client booted.");
	}

}
