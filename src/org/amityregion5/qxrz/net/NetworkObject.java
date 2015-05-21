package org.amityregion5.qxrz.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

// TODO we have to write object casting methods... enums?

public class NetworkObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String type;
	public Serializable payload;
	private long timeStamp;
	public static long getNetworkTime() throws IOException
	{
		URL timeAPI = new URL("http://elibaum.com");
		
		long start = System.currentTimeMillis();
		URLConnection connection = timeAPI.openConnection();
		long ends = System.currentTimeMillis();
		
		return connection.getDate() - (ends - start) >> 1;
	}
	
	public NetworkObject()
	{
		// can't do it this way, NEEDS to be synchronized clock time
		timeStamp = System.currentTimeMillis();
	}
	
	
	// MAYBE will be implemented.
	public long getTimeStamp()
	{
		return timeStamp;
	}
	
	public void setTimeStamp(long l)
	{
		this.timeStamp = l;
	}
	
	public String toString()
	{
		return "type=" + type + ", " + "payload=" + payload;
	}
	
	
}
