package org.usfirst.frc.team1360.server.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.server.Component;
import org.usfirst.frc.team1360.server.util.IOUtils;

public class ClimberCurrentDisplayComponent implements Component {
	private OutputStream out;
	private SensorInput sensorInput = SensorInput.getInstance();

	@Override
	public void initialize(InputStream i, OutputStream o)
	{
		out = o;
	}
	
	public void update()
	{
		try {
			out.write(IOUtils.Int32Big(Float.floatToRawIntBits((float)(sensorInput.getClimberFrontCurrent() + sensorInput.getClimberBackCurrent()) / 2)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
