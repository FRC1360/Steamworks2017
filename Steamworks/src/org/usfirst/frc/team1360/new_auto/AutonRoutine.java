package org.usfirst.frc.team1360.new_auto;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team1360.new_auto.providers.RobotOutputProvider;
import org.usfirst.frc.team1360.new_auto.providers.SensorInputProvider;

public abstract class AutonRoutine extends Thread {
	private final String name;
	private final long timeout;
	protected static RobotOutputProvider robotOutput;
	protected static SensorInputProvider sensorInput;
	
	private final ArrayList<AutonRoutine> queue = new ArrayList<>();
	private static final HashMap<String, AutonRoutine> map = new HashMap<>();
	private boolean done = false;
	
	public static void configure(RobotOutputProvider robotOutput, SensorInputProvider sensorInput)
	{
		AutonRoutine.robotOutput = robotOutput;
		AutonRoutine.sensorInput = sensorInput;
	}
	
	public AutonRoutine(String name, long timeout)
	{
		this.name = name;
		this.timeout = timeout;
	}
	
	protected abstract void runCore() throws InterruptedException;
	
	public final void runUntilFinish() throws InterruptedException
	{
		if (timeout != 0)
		{
			start();
			Thread.sleep(timeout);
			interrupt();
		}
		else
		{
			runCore();
		}
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
		if (timeout != 0)
		{
			AutonControl.run(() ->
			{
				Thread.sleep(timeout);
				if (!done)
				{
					kill();
				}
			});
		}
	}
	
	public final void runAfter(String other, String name)
	{
		AutonRoutine otherRoutine = map.get(other);
		synchronized (otherRoutine)
		{
			if (otherRoutine.done)
			{
				runNow(name);
			}
			else
			{
				map.put(name, this);
				otherRoutine.queue.add(this);
			}	
		}
	}
	
	public synchronized final void kill()
	{
		interrupt();
		notifyAll();
		queue.forEach(AutonRoutine::start);
		done = true;
	}
	
	public static void waitFor(String name, long timeout) throws InterruptedException
	{
		AutonRoutine routine = map.get(name);
		synchronized (routine)
		{
			if (!routine.done)
			{
				routine.wait(timeout);
				if (!routine.done)
				{
					routine.kill();
				}
			}
		}
	}
	
	@Override
	public final void run()
	{
		try {
			runCore();
			synchronized(this)
			{
				notifyAll();
				queue.forEach(AutonRoutine::start);
				done = true;
			}
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
