	package org.usfirst.frc.team1360.robot.util;

public class OrbitPID {
	
	private double P;
	private double I;
	private double D;
	private double errorEps;

	private double Error;
	
	private double Input;
	private double Output;
	private double Setpoint;
	
	private long PreviousTime;

	private double ErrorSum;
	private double PError;
	private double IError;
	private double DError;
	
	private double previousSetpoint;
	
	public OrbitPID(double constantP, double constantI, double constantD, double constantEps)
	{
		this.P = constantP;
		this.I = constantI;
		this.D = constantD;
		this.errorEps = constantEps;
	}

	public void SetConstants(double constantP, double constantI, double constantD)
	{
		this.P = constantP;
		this.I = constantI;
		this.D = constantD;
	}

	public void SetP(double constantP) { this.P = constantP; }
	public double GetP() { return P; }

	public void SetI(double constantI) { this.I = constantI; }
	public double GetI() { return I; }

	public void SetD(double constantD) { this.D = constantD; }
	public double GetD() { return D; }
	
	public void SetEps(double constantEps) {this.errorEps = constantEps;}
	public double GetEps() {return errorEps;}
	
	public void SetInput(double input) { this.Input = input; }
	public double GetInput() { return Input; }
	
	public double GetOutput() { return Output; }
	
	public void SetSetpoint(double setpoint)
	{ 
		this.Setpoint = setpoint; 
		if(this.Setpoint != this.previousSetpoint)
		{
			IError = 0;
		}
		this.previousSetpoint = this.Setpoint;
	}
	public double GetSetpoint() { return Setpoint; }
	
	public double GetError() { return Error; }
	
	public void CalculateError()
	{
		// Source: http://brettbeauregard.com/blog/2011/04/improving-the-beginners-pid-introduction/
		long CurrentTime = System.currentTimeMillis();
		double TimeSinceLastCalculate = (double)(CurrentTime - PreviousTime);
		
		// Compute the error components for the P, I, and D terms.
		PError = Setpoint - Input;
		
		if((Math.abs(PError)< this.errorEps))
		{
			IError = IError * 1;
		}
		else if(PError > 0)
		{
			IError = IError + Math.min((PError * TimeSinceLastCalculate), 10);
		}
		else
		{
			IError = IError + Math.max((PError * TimeSinceLastCalculate), -10);
		}
		DError = (PError - Error) / TimeSinceLastCalculate;
		
		// Compute the output.
		Output = P * PError + I * IError + D * DError;
		
		Error = PError;
		PreviousTime = CurrentTime;
		
	}
}
 