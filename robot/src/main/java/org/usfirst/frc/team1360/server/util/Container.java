package org.usfirst.frc.team1360.server.util;

public final class Container<T> {
	private T value;
	
	public Container()
	{
		this(null);
	}
	
	public Container(T value)
	{
		this.value = value;
	}
	
	public synchronized T get()
	{
		return value;
	}
	
	public synchronized void put(T value)
	{
		this.value = value;
	}
}
