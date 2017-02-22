package org.usfirst.frc.team1360.server.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.server.Component;
import org.usfirst.frc.team1360.server.util.IOUtils;

public class ClimberCurrentDisplayComponent implements Component {
	private OutputStream out;
	private SensorInput sensorInput;
	private boolean initialized = false;

	@Override
	public void initialize(InputStream i, OutputStream o)
	{
		out = o;
		sensorInput = SensorInput.getInstance();
		initialized = true;
	}
	
	public void update()
	{
		if (initialized) {
			float current = (float)(sensorInput.getClimberFrontCurrent() + sensorInput.getClimberBackCurrent()) / 2.0f;
			try {
				out.write(IOUtils.Int32Big(Float.floatToRawIntBits(current)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
