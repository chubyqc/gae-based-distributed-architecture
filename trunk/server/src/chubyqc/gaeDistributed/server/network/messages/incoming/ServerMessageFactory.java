package chubyqc.gaeDistributed.server.network.messages.incoming;

public class ServerMessageFactory extends MessageFactory {
	private static final ServerMessageFactory _instance = new ServerMessageFactory();
	public static ServerMessageFactory getInstance() {
		return _instance;
	}
	
	public ServerMessageFactory() {
		super(new MessageCreator[] {
				new MessageCreator(SendEmail.class)
		});
	}
}
