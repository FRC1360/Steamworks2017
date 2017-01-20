package org.usfirst.frc.team1360.server.util;

import java.io.IOException;
import java.io.InputStream;

public final class IOUtils {
	private IOUtils() {
	}

	public static byte[] UInt16Big(int value) {
		return new byte[] { (byte) (value >> 8), (byte) (value & 0xFF) };
	}

	public static int UInt16Big(byte[] data, int off) {
		return data[0] << 8 + data[1];
	}

	public static int UInt16Big(InputStream s) throws IOException {
		byte[] data = new byte[2];
		s.read(data);
		return UInt16Big(data, 0);
	}

	public static byte[] Int32Big(int value) {
		return new byte[] { (byte) (value >> 24), (byte) (value >> 16 & 0xFF), (byte) (value >> 8 & 0xFF), (byte) (value & 0xFF) };
	}
	
	public static int Int32Big(byte[] data, int off) {
		return data[0] << 24 + data[1] << 16 + data[2] << 8 + data[3];
	}
	
	public static int Int32Big(InputStream s) throws IOException {
		byte[] data = new byte[4];
		s.read(data);
		return Int32Big(data, 0);
	}
}
