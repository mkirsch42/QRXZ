package org.amityregion5.qxrz.server.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class UDPOutputStream extends OutputStream
{

	private ArrayList<Byte> data = new ArrayList<Byte>();
	private DatagramSocket ds;
	
	public UDPOutputStream(DatagramSocket ds)
	{
		this.ds = ds;
	}
	
	@Override
	public void write(int b) throws IOException
	{
		data.add((byte) b);
	}
	
	@Override
	public void flush() throws IOException
	{
		byte[] buf = new byte[data.size()];
		int index = 0;
		for(Byte b : data)
		{
			buf[index] = b;
			index ++;
		}
		
		ds.send(new DatagramPacket(buf, buf.length));
	}
	
	public void sendObject(Object o) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(o);
	}
	
}