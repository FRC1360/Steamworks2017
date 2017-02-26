package org.usfirst.frc.team1360.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.BiConsumer;

import org.usfirst.frc.team1360.server.util.IntermediaryStream;
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
				IntermediaryStream is = new IntermediaryStream(conn.getInputStream(), conn.getOutputStream(), (Throwable t, BiConsumer<InputStream, OutputStream> consumer) ->
				{
					System.out.println("Connection dropped");
					t.printStackTrace();
					try
					{
						conn = server.accept();
						System.out.println("Connection regained");
						consumer.accept(conn.getInputStream(), conn.getOutputStream());
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				});
				mcs = new MultiChannelStream(is.getInputStream(), is.getOutputStream());
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
		}).start();
	}
	
	public void addComponent(Component component, int channel)
	{
		new Thread(() -> {
			synchronized (this) {
				components[channel] = component;
				if (configured)
					component.initialize(mcs.getInputStream(channel), mcs.getOutputStream(channel));
			}
		}).start();
	}

	@Override
	public synchronized void close() throws IOException
	{
		server.close();
		server = null;
		configured = false;
	}
}
