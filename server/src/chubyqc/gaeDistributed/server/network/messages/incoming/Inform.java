package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.messages.specs.IInform;
import chubyqc.gaeDistributed.server.users.Manager;

public class Inform extends IncomingMessage<IInform> {

	@Override
	public void execute() throws Exception {
		Manager.getInstance().inform(getUsername(), getDataStore().getMessage());
	}
}
