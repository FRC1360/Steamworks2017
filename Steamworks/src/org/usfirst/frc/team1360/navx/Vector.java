package org.usfirst.frc.team1360.navx;

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
}
