package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.messages.specs.IInform;
import chubyqc.gaeDistributed.server.users.Manager;
import chubyqc.gaeDistributed.server.users.UserException;

public class Inform extends IncomingMessage<IInform> {

	@Override
	public void execute() throws UserException {
		Manager.getInstance().inform(getUsername(), getDataStore().getMessage());
	}
}
