package org.amityregion5.qxrz.server.net;

import org.amityregion5.qxrz.common.net.NetworkObject;

//TODO dataReceived handler will have to check for disconnected-notification
public interface ServerEventListener
{
	public void dataReceived(Client c, NetworkObject netObj);
	
	public void newClient(Client c);
}
