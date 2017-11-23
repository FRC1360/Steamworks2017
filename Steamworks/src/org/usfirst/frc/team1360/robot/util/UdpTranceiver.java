package org.usfirst.frc.team1360.robot.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.function.Consumer;

public class UdpTranceiver {
	int remotePort;
	InetAddress remoteAddress;
	DatagramSocket socket;
	
	public UdpTranceiver(int localPort, InetAddress localAddress, int remotePort, InetAddress remoteAddress) throws IOException {
		this.remotePort = remotePort;
		this.remoteAddress = remoteAddress;
		socket = new DatagramSocket(localPort, localAddress);
	}
	
	public void send(byte[] data) throws IOException {
		socket.send(new DatagramPacket(data, data.length, remoteAddress, remotePort));
	}
	
	public byte[] receive() throws IOException {
		byte[] buffer = new byte[65535];
		DatagramPacket packet = new DatagramPacket(buffer, 65535);
		socket.receive(packet);
		byte[] result = new byte[packet.getLength()];
		System.arraycopy(buffer, 0, result, 0, result.length);
		return result;
	}
	
	public byte[] receive(int length) throws IOException {
		byte[] buffer = new byte[length];
		DatagramPacket packet = new DatagramPacket(buffer, length);
		socket.receive(packet);
		return buffer;
	}
	
	public void receiveAll(Consumer<byte[]> handler) throws IOException {
		while (true) {
			handler.accept(receive());
		}
	}
}
