package org.usfirst.frc.team1360.server.util;

import java.util.Optional;

public final class EventWaitHandle {
	private Object notifier = new Object();
	private boolean state, autoReset;
	
	public EventWaitHandle(boolean state, boolean autoReset)
	{
		this.state = state;
		this.autoReset = autoReset;
	}
	
	public void waitOne() throws InterruptedException
	{
		synchronized (notifier)
		{
			while (!state)
				notifier.wait();
		}
	}
	
	public void waitOne(long timeout) throws InterruptedException
	{
		if (timeout == 0)
			waitOne();
		else
		{
			Container<InterruptedException> ex = new Container<>();
			Thread t = new Thread(() -> {
				Thread.yield();
				synchronized (notifier)
				{
					try
					{
						while (!state)
							notifier.wait();
					}
					catch (InterruptedException e)
					{
						ex.put(e);
					}
					if (autoReset)
						state = false;
				}
			});
			t.start();
			long target = System.currentTimeMillis() + timeout;
			while (true)
			{
				long cur = System.currentTimeMillis();
				if (cur >= target)
				{
					t.interrupt();
					if (ex.get() != null)
						throw ex.get();
				}
				t.join(target - cur);
			}
		}
	}
	
	public void set()
	{
		synchronized (notifier)
		{
			state = true;
			if (autoReset)
				notifier.notify();
			else
				notifier.notifyAll();
		}
	}
	
	public void reset()
	{
		synchronized (notifier)
		{
			state = false;
		}
	}
}
