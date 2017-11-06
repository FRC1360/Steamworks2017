package org.usfirst.frc.team1360.server.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.usfirst.frc.team1360.server.Component;
import org.usfirst.frc.team1360.server.util.IOUtils;

public class AnalogDisplayComponent implements Component {
	private OutputStream out;
	
	@Override
	public synchronized void initialize(InputStream i, OutputStream o)
	{
		out = o;
	}
	
	public synchronized void update(float value)
	{
		if (out != null)
			try {
				out.write(IOUtils.Float1360(value));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
