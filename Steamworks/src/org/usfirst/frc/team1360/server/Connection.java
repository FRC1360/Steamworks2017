package org.usfirst.frc.team1360.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.usfirst.frc.team1360.server.util.MultiChannelStream;

public class Connection implements Closeable {
	private ServerSocket server;
	private Socket conn;
	private MultiChannelStream mcs;
	private Component[] components = new Component[256];
	private boolean configured = false;

	public Connection(int port)
	{
		new Thread(() ->
		{
			try
			{
				System.out.println("Opening connection");
				server = new ServerSocket(port);
				conn = server.accept();
				System.out.println("Connection open");
				mcs = new MultiChannelStream(conn.getInputStream(), conn.getOutputStream());
				System.out.println("MCS set up");
				synchronized (this)
				{
					configured = true;
					for (int i = 0; i < 256; ++i)
						if (components[i] != null)
							components[i].initialize(mcs.getInputStream(i), mcs.getOutputStream(i));
				}
			}
			catch (IOException e)
			{
				System.err.println("Unable to open connection to driver station!");
				e.printStackTrace();
			}
		});
		
	}
	
	public synchronized void addComponent(Component component, int channel)
	{
		components[channel] = component;
		if (configured)
			component.initialize(mcs.getInputStream(channel), mcs.getOutputStream(channel));
	}

	@Override
	public synchronized void close() throws IOException
	{
		server.close();
		server = null;
		configured = false;
	}
}
