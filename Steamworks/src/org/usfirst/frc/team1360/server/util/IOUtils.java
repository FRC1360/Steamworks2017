package org.usfirst.frc.team1360.server.util;

import java.io.IOException;
import java.io.InputStream;

public final class IOUtils {
	private IOUtils() {	}
	
	public static byte[] String1360(String value) {
		byte[] r = new byte[value.length() + 4];
		System.arraycopy(Int32Big(value.length()), 0, r, 0, 4);
		System.arraycopy(value.getBytes(), 0, r, 4, value.length());
		return r;
	}
	
	public static byte[] Float1360(float value) {
		return Int32Big(Float.floatToRawIntBits(value));
	}

	public static byte[] UInt16Big(int value) {
		return new byte[] { (byte) (value >> 8), (byte) (value & 0xFF) };
	}

	public static int UInt16Big(byte[] data, int off) {
		return (data[off] << 8) + data[off + 1];
	}

	public static int UInt16Big(InputStream s) throws IOException {
		return UInt16Big(ReadBytes(s, 2), 0);
	}

	public static byte[] Int32Big(int value) {
		return new byte[] { (byte) (value >> 24), (byte) (value >> 16 & 0xFF), (byte) (value >> 8 & 0xFF), (byte) (value & 0xFF) };
	}
	
	public static int Int32Big(byte[] data, int off) {
		return (data[off] << 24) + (data[off + 1] << 16) + (data[off + 2] << 8) + data[off + 3];
	}
	
	public static int Int32Big(InputStream s) throws IOException {
		return Int32Big(ReadBytes(s, 4), 0);
	}

	public static byte[] ReadBytes(InputStream s, int count) throws IOException {
		byte[] a = new byte[count];
		int r = 0;
		while (r < count)
			r += s.read(a, r, count - r);
		return a;
	}
}
