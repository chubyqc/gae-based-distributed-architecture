package chubyqc.gaeDistributed.server.network.messages.incoming;

public class ServerMessageFactory extends MessageFactory {
	private static final ServerMessageFactory _instance = new ServerMessageFactory();
	public static ServerMessageFactory getInstance() {
		return _instance;
	}
	
	@SuppressWarnings("unchecked")
	public ServerMessageFactory() {
		super(
				SendEmail.class, 
				ClientBooted.class,
				SendCommands.class);
	}
}
