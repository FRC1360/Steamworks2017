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

	public Connection(int port) throws IOException {
		server = new ServerSocket(port);
		conn = server.accept();
		mcs = new MultiChannelStream(conn.getInputStream(), conn.getOutputStream());
	}
	
	public void addComponent(Component component, int channel) {
		components[channel] = component;
		component.initialize(mcs.getInputStream(channel), mcs.getOutputStream(channel));
	}

	@Override
	public void close() throws IOException {
		server.close();
		server = null;
	}
}
