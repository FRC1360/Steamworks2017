package org.usfirst.frc.team1360.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechExtremeJoystick extends Joystick {

	public LogitechExtremeJoystick(int port) {
		super(port);
	}
	
	public double getXAxis()
	{
		return this.getRawAxis(0);
	}
	
	public double getYAxis()
	{
		return this.getRawAxis(1);
	}
	
	public double getZRotate()
	{
		return this.getRawAxis(2);
	}
	
	public double getSlider()
	{
		return this.getRawAxis(3);
	}
	
	public boolean getMainTrigger()
	{
		return this.getRawButton(1);
	}
	
	public boolean getSideButton()
	{
		return this.getRawButton(2);
	}
	
	public boolean getButton3()
	{
		return this.getRawButton(3);
	}
	
	public boolean getButton4()
	{
		return this.getRawButton(4);
	}
	
	public boolean getButton5()
	{
		return this.getRawButton(5);
	}
	
	public boolean getButton6()
	{
		return this.getRawButton(6);
	}
	
	public boolean getButton7()
	{
		return this.getRawButton(7);
	}
	
	public boolean getButton8()
	{
		return this.getRawButton(8);
	}
	
	public boolean getButton9()
	{
		return this.getRawButton(9);
	}
	
	public boolean getButton10()
	{
		return this.getRawButton(10);
	}
	
	public boolean getButton11()
	{
		return this.getRawButton(11);
	}
	
	public boolean getButton12()
	{
		return this.getRawButton(12);
	}
	
	public int getThumbpad()
	{
		return this.getPOV(0);
	}
	
}
