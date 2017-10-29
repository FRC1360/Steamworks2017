package org.usfirst.frc.team1360.new_auto;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

public abstract class AutonRoutine extends Thread {
	private final String name;
	protected static final RobotOutput robotOutput = RobotOutput.getInstance();
	protected static final SensorInput sensorInput = SensorInput.getInstance();
	
	public AutonRoutine(String name)
	{
		this.name = name;
	}
	
	protected abstract void runCore() throws InterruptedException;
	
	@Override
	public final void run()
	{
		try {
			runCore();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public final String toString()
	{
		return name;
	}
}
