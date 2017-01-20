package org.usfirst.frc.team1360.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.BiFunction;

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
	
	public <T extends Component> T AddComponent(BiFunction<InputStream, OutputStream, T> generator, int channel) {
		T component = generator.apply(mcs.getInputStream(channel), mcs.getOutputStream(channel));
		components[channel] = component;
		return component;
	}

	@Override
	public void close() throws IOException {
		server.close();
		server = null;
	}
}
