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
		double sin = Math.sin(theta);
		double cos = Math.cos(theta);
		x *= cos + sin;
		y *= cos - sin;
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
	
	public void scale(double scalar)
	{
		x *= scalar;
		y *= scalar;
	}
	
	public Vector copy()
	{
		return new Vector(x, y);
	}
}
