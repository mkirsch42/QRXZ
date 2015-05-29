package org.amityregion5.qxrz.client.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.BroadcastDiscoveryQuery;
import org.amityregion5.qxrz.common.net.DisconnectNotification;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.ServerInfo;

public class ClientNetworkManager extends AbstractNetworkManager
{
	private NetworkNode server;
	private Logger l = Logger.getGlobal();
	public static InetAddress getBroadcast() throws SocketException,
			UnknownHostException
	{
		List<InterfaceAddress> addresses = NetworkInterface.getByInetAddress(
				InetAddress.getLocalHost()).getInterfaceAddresses();
		for (Iterator<InterfaceAddress> it = addresses.iterator(); it.hasNext();)
		{
			InterfaceAddress addr = it.next();
			if (addr.getBroadcast() != null)
				return addr.getBroadcast();
		}
		return null; // won't happen on a working computer
	}

	public ClientNetworkManager() throws Exception
	{
		super();
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				sendObject(new DisconnectNotification());
			}
		});
	}

	public void broadcastQuery() throws Exception
	{
		// do fancy broadcast stuff here
		// then wait on the callback
		// MAKE SURE TO ATTACH CALLBACK BEFORE LISTENING duh
		NetworkNode broadcast = new NetworkNode(outStream, new InetSocketAddress(getBroadcast(), 8000));
		broadcast.send(new BroadcastDiscoveryQuery());
	}

	// Once server responds to ping, we can connect
	public void connect(InetSocketAddress addr) throws SocketException
	{
		server = new NetworkNode(outStream, addr);
	}

	// this is if user wants to manually connect
	public void connect(String host, int port) throws SocketException,
			UnknownHostException
	{
		connect(new InetSocketAddress(InetAddress.getByName(host), port));
	}

	public void sendObject(Serializable s)
	{
		try
		{
			server.send(s);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				NetworkObject netObj = (NetworkObject) inStream.recvObject();
				if(netObj.getPayload() instanceof ServerInfo)
				{
					l.info(netObj.getPayload().toString());
				}
				if(server == null)
				{
					callback.dataReceived(null, netObj.getPayload());
					continue;
				}
				runHelper(server, netObj);
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}