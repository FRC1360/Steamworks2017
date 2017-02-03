package org.usfirst.frc.team1360.server.util;

import java.io.*;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

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
						data = IOUtils.ReadBytes(input, l);
					}
					synchronized (this) {
						if (channels[c] == null)
							channels[c] = new Channel(c);
					}
					synchronized (channels[c]) {
						System.out.println("Received on channel " + c);
						for (byte b : data)
							channels[c].queue.add(b);
						channels[c].notify();
					}
				}
			} catch (Exception ignored) {
				errorNotifiers.forEach(Runnable::run);
			}
		}));
		rt.start();
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
		private int channel;
		private ChannelInputStream i;
		private ChannelOutputStream o;
		private Queue<Byte> queue;
		
		public Channel(int channel){
			this.channel = channel;
			i = new ChannelInputStream();
			o = new ChannelOutputStream();
			queue = new ArrayDeque<>();
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
				synchronized (Channel.this) {
					if (queue.size() == 0)
						try {
							Channel.this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							throw new IOException(e);
						}
					return queue.remove();
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
