package org.usfirst.frc.team1360.server.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.function.BiConsumer;

public class IntermediaryStream {
	InputStream input;
	OutputStream output;
	BiConsumer<Throwable, BiConsumer<InputStream, OutputStream>> errorHandler;
	
	public IntermediaryStream(InputStream input, OutputStream output, BiConsumer<Throwable, BiConsumer<InputStream, OutputStream>> errorHandler)
	{
		Objects.requireNonNull(input, "Must supply an input stream!");
		this.input = input;
		Objects.requireNonNull(output, "Must supply an output stream!");
		this.output = output;
		Objects.requireNonNull(errorHandler, "Must supply an error handler!");
		this.errorHandler = errorHandler;
	}
	
	private void handleError(Throwable error)
	{
		while (true)
			try
			{
				errorHandler.accept(error, (InputStream i, OutputStream o) ->
				{
					input = i;
					output = o;
				});
				return;
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
	}
	
	private class InputStreamProxy extends InputStream {
		@Override
		public int read() throws IOException
		{
			synchronized (IntermediaryStream.this)
			{
				while (true)
					try
					{
						return input.read();
					}
					catch (Throwable t)
					{
						handleError(t);
					}
			}
		}
	}
	
	private class OutputStreamProxy extends OutputStream {
		@Override
		public void write(int b) throws IOException
		{
			synchronized (IntermediaryStream.this)
			{
				while (true)
					try
					{
						output.write(b);
						return;
					}
					catch (Throwable t)
					{
						handleError(t);
					}
			}
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
