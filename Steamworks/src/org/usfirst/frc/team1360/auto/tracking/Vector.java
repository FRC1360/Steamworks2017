package org.usfirst.frc.team1360.auto.tracking;

public class Vector {
	private double x, y;
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	public double getPhase()
	{
		return Math.atan2(y, x);
	}
	
	public void rotate(double theta)
	{
		double m = getMagnitude();
		double p = getPhase();
		p += theta;
		x = m * Math.cos(p);
		y = m * Math.sin(p);
	}
	
	public void update(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void addFrom(Vector other)
	{
		x += other.x;
		y += other.y;
	}
	
	public void addFrom(Vector other, double scalar)
	{
		x += other.x * scalar;
		y += other.y * scalar;
	}
	
	public void addFrom(double x, double y)
	{
		this.x += x;
		this.y += y;
	}
	
	public Vector copy()
	{
		return new Vector(x, y);
	}
}
