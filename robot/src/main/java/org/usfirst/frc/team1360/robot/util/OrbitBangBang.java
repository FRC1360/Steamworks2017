package org.usfirst.frc.team1360.robot.util;

public class OrbitBangBang {
	
	private double highOutput;
	private double lowOutput;
	private double target;
	
	public OrbitBangBang(double high, double low)
	{
		this.highOutput = high;
		this.lowOutput = low;
	}
	
	public void setTarget(double target)
	{	
		this.target = target;
	}
	
	public void setHighLow (double high, double low)
	{
		this.highOutput = high;
		this.lowOutput = low;
	}
	
	public double calculate(double currentSpeed)
	{
		if(currentSpeed < target)
		{
			return this.highOutput;
		} 
		else
		{
			return this.lowOutput;
		}
	}
}
