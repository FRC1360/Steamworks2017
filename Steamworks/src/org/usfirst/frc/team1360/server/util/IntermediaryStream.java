package org.usfirst.frc.team1360.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.BiConsumer;

public class IntermediaryStream {
	private InputStream input;
	private OutputStream output;
	private BiConsumer<Throwable, BiConsumer<InputStream, OutputStream>> errorHandler;
	private HashSet<Thread> currentThreads = new HashSet<>();
	private EventWaitHandle ewh = new EventWaitHandle(true, false);
	private Object rl = new Object(), wl = new Object();
	
	public IntermediaryStream(InputStream input, OutputStream output, BiConsumer<Throwable, BiConsumer<InputStream, OutputStream>> errorHandler)
	{
		Objects.requireNonNull(input, "Must supply an input stream!");
		this.input = input;
		Objects.requireNonNull(output, "Must supply an output stream!");
		this.output = output;
		Objects.requireNonNull(errorHandler, "Must supply an error handler!");
		this.errorHandler = errorHandler;
	}
	
	private void safe(Object lock, Runnable action)
	{
		while (true)
		try
		{
			synchronized (currentThreads)
			{
				currentThreads.add(Thread.currentThread());
			}
			ewh.waitOne();
			synchronized (lock)
			{
				try
				{
					action.run();
					return;
				}
				catch (Throwable t)
				{
					ewh.reset();
					synchronized (currentThreads)
					{
						currentThreads.stream().forEach(Thread::interrupt);
						currentThreads.clear();
					}
					Thread.yield();
					while (true)
						try
						{
							errorHandler.accept(t, (InputStream i, OutputStream o) ->
							{
								input = i;
								output = o;
							});
							break;
						}
						catch (Throwable _t)
						{
							_t.printStackTrace();
							continue;
						}
					ewh.set();
				}
			}
		}
		catch (InterruptedException e)
		{
			continue;
		}
		finally
		{
			synchronized (currentThreads)
			{
				if (currentThreads.contains(Thread.currentThread()))
					currentThreads.remove(Thread.currentThread());
			}
		}
	}
	
	private class InputStreamProxy extends InputStream {
		@Override
		public int read() throws IOException
		{
			Container<Integer> result = new Container<Integer>();
			safe(rl, () ->
			{
				try
				{
					result.put(input.read());
				}
				catch (IOException e)
				{
					throw new UncheckedIOException(e);
				}
			});
			return result.get();
		}
		
		private IntermediaryStream getOuter()
		{
			return IntermediaryStream.this;
		}
		
		@Override
		public boolean equals(Object other)
		{
			return other instanceof InputStreamProxy && ((InputStreamProxy)other).getOuter() == IntermediaryStream.this;
		}
	}
	
	private class OutputStreamProxy extends OutputStream {
		@Override
		public void write(int b) throws IOException
		{
			safe(wl, () -> 
			{
				try
				{
					output.write(b);
				}
				catch (IOException e)
				{
					throw new UncheckedIOException(e);
				}
			});
		}
		
		private IntermediaryStream getOuter()
		{
			return IntermediaryStream.this;
		}
		
		@Override
		public boolean equals(Object other)
		{
			return other instanceof OutputStreamProxy && ((OutputStreamProxy)other).getOuter() == IntermediaryStream.this;
		}
	}
	
	public InputStream getInputStream()
	{
		return new InputStreamProxy();
	}
	
	public OutputStream getOutputStream()
	{
		return new OutputStreamProxy();
	}
}
