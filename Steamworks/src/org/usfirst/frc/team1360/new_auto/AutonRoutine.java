package org.usfirst.frc.team1360.new_auto;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;

public abstract class AutonRoutine extends Thread {
	private final String name;
	protected static final RobotOutput robotOutput = RobotOutput.getInstance();
	protected static final SensorInput sensorInput = SensorInput.getInstance();
	
	private final ArrayList<AutonRoutine> queue = new ArrayList<>();
	private static final HashMap<String, AutonRoutine> map = new HashMap<>();
	private boolean done = false;
	
	public AutonRoutine(String name)
	{
		this.name = name;
	}
	
	protected abstract void runCore() throws InterruptedException;
	
	public final void runUntilFinish() throws InterruptedException
	{
		runCore();
		synchronized(this)
		{
			notifyAll();
			queue.forEach(AutonRoutine::start);
			done = true;
		}
	}
	
	public final void runNow(String name)
	{
		map.put(name, this);
		AutonControl.registerThread(this);
		start();
	}
	
	public final void runAfter(String other, String name)
	{
		AutonRoutine otherRoutine = map.get(other);
		map.put(name, this);
		synchronized (otherRoutine)
		{
			if (otherRoutine.done)
			{
				start();
			}
			else
			{
				otherRoutine.queue.add(this);
			}	
		}
	}
	
	public static void waitFor(String name) throws InterruptedException
	{
		AutonRoutine routine = map.get(name);
		synchronized (routine)
		{
			if (!routine.done)
			{
				routine.wait();
			}
		}
	}
	
	@Override
	public final void run()
	{
		try {
			runUntilFinish();
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
