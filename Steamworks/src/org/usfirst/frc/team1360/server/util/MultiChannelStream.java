package org.usfirst.frc.team1360.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import com.Ostermiller.util.CircularByteBuffer;

public class MultiChannelStream {
	private static final int MAX_BLOCK = 65535;
	
	InputStream input;
	OutputStream output;
	public Channel[] channels = new Channel[256];
	LinkedList<Runnable> errorNotifiers = new LinkedList<>();
	boolean work = true;
	Thread rt;
	
	public MultiChannelStream(InputStream i, OutputStream o) {
		input = i;
		output = o;
		rt = new Thread((Runnable)(() -> {
			try {
				while (work) {
					int c, l;
					byte[] data;
					synchronized (input) {
						c = input.read();
						l = IOUtils.UInt16Big(input);
						data = new byte[l];
						input.read(data);
					}
					synchronized (this) {
						if (channels[c] == null)
							channels[c] = new Channel(c);
					}
					synchronized (channels[c]) {
						channels[c].buffer.getOutputStream().write(data);
					}
				}
			} catch (Exception ignored) {
				for (Runnable r : errorNotifiers)
					r.run();
			}
		}));
	}
	
	public synchronized InputStream getInputStream(int channel) {
		if (channels[channel] == null)
			channels[channel] = new Channel(channel);
		return channels[channel].getInputStream();
	}
	
	public synchronized OutputStream getOutputStream(int channel) {
		if (channels[channel] == null)
			channels[channel] = new Channel(channel);
		return channels[channel].getOutputStream();
	}
	
	private class Channel {
		public CircularByteBuffer buffer = new CircularByteBuffer();
		public Runnable notifier;
		private int channel;
		private ChannelInputStream i;
		private ChannelOutputStream o;
		
		public Channel(int channel){
			this.channel = channel;
			i = new ChannelInputStream();
			o = new ChannelOutputStream();
		}
		
		public InputStream getInputStream() {
			return i;
		}
		
		public OutputStream getOutputStream() {
			return o;
		}
		
		private class ChannelInputStream extends InputStream {			
			@Override
			public synchronized int read() throws IOException {
				if (buffer.getSize() == 0){
					notifier = this::notify;
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						throw new IOException(e);
					}
				}
				synchronized(Channel.this) {
					return buffer.getInputStream().read();
				}
			}
		}
		
		private class ChannelOutputStream extends OutputStream {
			@Override
			public void write(int b) throws IOException {
				write(new byte[] { (byte)b }, 0, 1);
			}
			
			@Override
			public synchronized void write(byte[] b, int off, int len) throws IOException {
				while (len != 0) {
					int n = Math.min(len, MAX_BLOCK);
					synchronized(output){
						output.write(channel);
						output.write(IOUtils.UInt16Big(n));
						output.flush();
						output.write(b, off, n);
						output.flush();
					}
					off += n;
					len -= n;
				}
			}
		}
	}
}
