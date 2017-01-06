package org.usfirst.frc.team1360.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public class XboxRemote extends Joystick {
	
	public XboxRemote(int port)
	{
		super(port);
	}
	
    public double getLeftXAxis()
    {
        return this.getRawAxis(0);
    }

   
    public double getLeftYAxis()
    {
        return this.getRawAxis(1);
    }

    
    public double getLeftTrigger()
    {
        return this.getRawAxis(2);
    }

    
    public double getRightTrigger()
    {
        return this.getRawAxis(3);
    }

   
    public double getRightXAxis()
    {
        return this.getRawAxis(4);
    }

    
    public double getRightYAxis()
    {
        return this.getRawAxis(5);
    }

    public boolean getButtonA()
    {
        return this.getRawButton(1);
    }

    public boolean getButtonB()
    {
        return this.getRawButton(2);
    }

    public boolean getButtonX()
    {
        return this.getRawButton(3);
    }

    public boolean getButtonY()
    {
        return this.getRawButton(4);
    }

    public boolean getButtonLB()
    {
        return this.getRawButton(5);
    }

    public boolean getButtonRB()
    {
        return this.getRawButton(6);
    }

    public boolean getButtonBack()
    {
        return this.getRawButton(7);
    }

    public boolean getButtonStart()
    {
        return this.getRawButton(8);
    }

    public boolean getClickLeftStick()
    {
        return this.getRawButton(9);
    }

    public boolean getClickRightStick()
    {
        return this.getRawButton(10);
    } 
}