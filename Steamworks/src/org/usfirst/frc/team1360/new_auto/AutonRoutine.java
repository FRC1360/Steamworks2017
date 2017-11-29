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
	private boolean done;
	
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
			if (isAlive())
			{
				try
				{
					interrupt();
				}
				finally
				{
					override("timeout");
				}
			}
		}
		else
		{
			runCore();
		}
		done = true;
		synchronized(this)
		{
			queue.forEach(AutonRoutine::start);
		}
	}
	
	public final void runNow(String name)
	{
		map.put(name.toLowerCase(), this);
		AutonControl.registerThread(this);
		start();
		if (timeout != 0)
		{
			AutonControl.run(() ->
			{
				Thread.sleep(timeout);
				if (isAlive())
				{
					try
					{
						kill();
					}
					finally
					{
						override("timeout");
					}
				}
			});
		}
	}
	
	public final void runAfter(String other, String name)
	{
		AutonRoutine otherRoutine = map.get(other.toLowerCase());
		synchronized (otherRoutine)
		{
			if (otherRoutine.done)
			{
				runNow(name);
			}
			else
			{
				map.put(name.toLowerCase(), this);
				otherRoutine.queue.add(this);
			}	
		}
	}
	
	public synchronized final void kill()
	{
		while (isAlive())
		{
			interrupt();
			try
			{
				join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		override("kill");
		notifyAll();
		queue.forEach(AutonRoutine::start);
	}
	
	public static void kill(String name)
	{
		map.get(name.toLowerCase()).kill();
	}
	
	public static void waitFor(String name, long timeout) throws InterruptedException
	{
		System.out.println("Waiting for " + name);
		AutonRoutine routine = map.get(name.toLowerCase());
		synchronized (routine)
		{
			if (routine.done)
				return;
			if (routine.isAlive())
			{
				if (timeout == 0)
					routine.join();
				else
				{
					routine.join(timeout);
					if (routine.isAlive())
						routine.kill();
				}
			}
			else if (timeout == 0)
				routine.wait(timeout);
			else
				routine.wait();
		}
	}
	
	@Override
	public final void run()
	{
		try
		{
			runCore();
			synchronized(this)
			{
				notifyAll();
				queue.forEach(AutonRoutine::start);
				done = true;
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public final String toString()
	{
		return name;
	}
	
	protected final void override(String reason)
	{
		System.out.printf("%s overriden: %s!\n", getClass().getSimpleName(), reason);
		overrideCore();
	}
	
	protected void overrideCore()
	{
	}
}
