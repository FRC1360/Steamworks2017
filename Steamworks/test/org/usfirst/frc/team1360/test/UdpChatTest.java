package org.usfirst.frc.team1360.test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import org.usfirst.frc.team1360.robot.util.EncodingHelper;
import org.usfirst.frc.team1360.robot.util.UdpTranceiver;

public class UdpChatTest {
	public static void main(String[] args) throws Exception {
		UdpTranceiver net = new UdpTranceiver(1630, InetAddress.getLoopbackAddress(), 1360, InetAddress.getLoopbackAddress());
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.print(">");
			net.send(EncodingHelper.encode(s.nextLine()));
			byte[] result = net.receive();
			System.out.println(":" + EncodingHelper.decode("s" + result.length, result)[0]);
		}
	}
}
