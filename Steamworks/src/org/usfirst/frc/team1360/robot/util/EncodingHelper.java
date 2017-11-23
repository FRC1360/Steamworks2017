package org.usfirst.frc.team1360.robot.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EncodingHelper {
	private EncodingHelper() {}
	
	public static Stream<Byte> encodeSingle(Object obj) {
		if (obj == null) {
			return Stream.of((byte)0);
		} else if (obj instanceof Byte) {
			return Stream.of((Byte)obj);
		} else if (obj instanceof Short) {
			short v = (short)obj;
			return Stream.of((byte)(v >> 8), (byte)(v & 0xFF));
		} else if (obj instanceof Integer) {
			int v = (int)obj;
			return Stream.of((byte)(v >> 24), (byte)(v >> 16 & 0xFF), (byte)(v >> 8 & 0xFF), (byte)(v & 0xFF));
		} else if (obj instanceof Long) {
			long v = (long)obj;
			return Stream.of((byte)(v >> 56), (byte)(v >> 48 & 0xFF), (byte)(v >> 40 & 0xFF), (byte)(v >> 32 & 0xFF), (byte)(v >> 24 & 0xFF), (byte)(v >> 16 & 0xFF), (byte)(v >> 8 & 0xFF), (byte)(v & 0xFF));
		} else if (obj instanceof Float) {
			return encodeSingle(Float.floatToRawIntBits((float)obj));
		} else if (obj instanceof Double) {
			return encodeSingle(Double.doubleToRawLongBits((double)obj));
		} else if (obj instanceof byte[]) {
			return Stream.generate(new Supplier<Byte>() {
				int index = 0;
				
				@Override
				public Byte get() {
					return ((byte[])obj)[index++];
				}
			}).limit(((byte[])obj).length);
		} else if (obj instanceof String) {
			try {
				return encodeSingle(((String)obj).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		} else {
			return Stream.empty();
		}
	}
	
	public static byte[] encode(Object ...objects) {
		List<Byte> bytes = Arrays.stream(objects).flatMap(EncodingHelper::encodeSingle).collect(Collectors.toList());
		byte[] result = new byte[bytes.size()];
		for (int i = 0; i < result.length; ++i)
			result[i] = bytes.get(i);
		return result;
	}
	
	private static Object decodeSingle(String format, int formatOffset, byte[] data, int dataOffset, IntConsumer updateFormatOffset, IntConsumer updateDataOffet) throws Exception {
		Object result = null;
		switch(format.charAt(formatOffset++)) {
		case 'x':
			++dataOffset;
			break;
		case 'b':
			result = data[dataOffset++];
			break;
		case 'w':
			result = (short)(data[dataOffset] << 8 | data[dataOffset + 1]);
			dataOffset += 2;
			break;
		case 'i':
			result = (int)(data[dataOffset] << 24 | data[dataOffset + 1] << 16 | data[dataOffset + 2] << 8 | data[dataOffset + 3]);
			dataOffset += 4;
			break;
		case 'q':
			result = (int)(data[dataOffset] << 56 | data[dataOffset + 1] << 48 | data[dataOffset + 2] << 40 | data[dataOffset + 3] << 32 | data[dataOffset + 4] << 24 | data[dataOffset + 5] << 16 | data[dataOffset + 6] << 8 | data[dataOffset + 7]);
			dataOffset += 8;
			break;
		case 'f':
			updateFormatOffset.accept(formatOffset);
			return Float.intBitsToFloat((int)decodeSingle("i", 0, data, dataOffset, off -> {}, updateDataOffet));
		case 'd':
			updateFormatOffset.accept(formatOffset);
			return Double.longBitsToDouble((long)decodeSingle("l", 0, data, dataOffset, off -> {}, updateDataOffet));
		case 'a': {
			int len = 0;
			while (formatOffset < format.length()) {
				char c = format.charAt(formatOffset++);
				if (c < '0' || c > '9')
					break;
				len *= 10;
				len += c - '0';
			}
			byte[] array = new byte[len];
			System.arraycopy(data, dataOffset, array, 0, len);
			result = array;
			dataOffset += len;
			break;
		}
		case 's': {
			int len = 0;
			while (formatOffset < format.length()) {
				char c = format.charAt(formatOffset++);
				if (c < '0' || c > '9')
					break;
				len *= 10;
				len += c - '0';
			}
			result = new String(data, dataOffset, len);
			dataOffset += len;
			break;
		}
		case 'A': {
			int len = (int)decodeSingle("i", 0, data, dataOffset, off -> {}, off -> {});
			byte[] array = new byte[len];
			System.arraycopy(data, dataOffset += 4, array, 0, len);
			result = array;
			dataOffset += len;
			break;
		}
		case 'S': {
			int len = (int)decodeSingle("i", 0, data, dataOffset, off -> {}, off -> {});
			result = new String(data, dataOffset += 4, len);
			dataOffset += len;
			break;
		}
		default:
			throw new Exception("Invalid format string at character " + (formatOffset - 1));
		}
		updateFormatOffset.accept(formatOffset);
		updateDataOffet.accept(dataOffset);
		return result;
	}
	
	public static Object[] decode(String format, byte[] data) throws Exception {
		List<Object> objects = new ArrayList<>();
		for (OrbitContainer<Integer> i = new OrbitContainer<Integer>(0), j = new OrbitContainer<Integer>(0); i.getValue() < format.length(); ) {
			if (j.getValue() >= data.length)
				throw new Exception("Data too small");
			objects.add(decodeSingle(format, i.getValue(), data, j.getValue(), i::setValue, j::setValue));
		}
		Object[] result = new Object[objects.size()];
		for (int i = 0; i < result.length; ++i)
			result[i] = objects.get(i);
		return result;
	}
}
