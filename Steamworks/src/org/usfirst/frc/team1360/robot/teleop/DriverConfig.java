package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;
import org.usfirst.frc.team1360.robot.IO.SensorInput;
import org.usfirst.frc.team1360.robot.util.OrbitPID;

public enum DriverConfig {
	RACING 
	{
		@Override
		public void calculate(RobotOutput robotOutput, HumanInput humanInput)
		{
			boolean shift = humanInput.getRacingShifter();
			
			boolean deadzone = Math.abs(humanInput.getRacingTurn()) < 0.2;
			if (deadzone)
			{
				double speed = humanInput.getRacingThrottle();
				
				if (!lastDeadzone || Math.abs(speed) < 0.01)
				{
					driveController.SetSetpoint(SensorInput.getInstance().getAHRSYaw());
				}
				

				driveController.SetInput(SensorInput.getInstance().getAHRSYaw());
				driveController.CalculateError();
					
				robotOutput.arcadeDrivePID(speed, Math.abs(speed) * driveController.GetOutput());
			}
			else
			{
				robotOutput.arcadeDrive(humanInput.getRacingThrottle(), humanInput.getRacingTurn());
			}
			lastDeadzone = deadzone;
			
			double turn = humanInput.getRacingTurn();
			boolean change = humanInput.getRacingDampen();
			
			if(Math.abs(turn) < 0.2)
				turn = 0;
			
			if(change)
			{
				robotOutput.arcadeDrive(humanInput.getRacingThrottle() / 2, turn / 2);	
			}
			else
			{
				robotOutput.arcadeDrive(humanInput.getRacingThrottle(), turn);
			}
			
			if (shift != lastShift)
				robotOutput.shiftSpeed(currentState = !currentState);
			
			lastShift = shift;
		}
	},
	
	HALO 
	{
		@Override
		public void calculate(RobotOutput robotOutput, HumanInput humanInput)
		{
			double turn = humanInput.getHaloTurn();
			
			if(Math.abs(turn) < 0.2)
				turn = 0;
			
			robotOutput.arcadeDrive(-humanInput.getHaloThrottle(), turn);
			boolean shift = humanInput.getHaloShifter();
			
			if (shift != lastShift)
				robotOutput.shiftSpeed(currentState = !currentState);
			
			lastShift = shift;
		}
	},
	
	TANK 
	{

		@Override
		public void calculate(RobotOutput robotOutput, HumanInput humanInput)
		{
			double left = humanInput.getTankLeft();
			double right = humanInput.getTankRight();
			
			if(Math.abs(left) < 0.2)
				left = 0;
			
			if(Math.abs(right) < 0.2)
				right = 0;
			
			robotOutput.tankDrive(left, right);
			boolean shift = humanInput.getTankShifter();
			
			if(shift != lastShift)
				robotOutput.shiftSpeed(currentState = !currentState);
			
			lastShift = shift;
		}
		
	},
	
	ARCADE
	{

		@Override
		public void calculate(RobotOutput robotOutput, HumanInput humanInput) 
		{
			double turn = humanInput.getArcadeTurn();
			
			if(Math.abs(turn) < 0.2)
				turn = 0;
			
			robotOutput.arcadeDrive(humanInput.getArcadeThrottle(), turn);
			boolean shift = humanInput.getArcadeShifter();
			
			if(shift != lastShift)
				robotOutput.shiftSpeed(currentState = !currentState);
			
			lastShift = shift;
		}
		
	},
	
	JOYSTICKTANK
	{
		@Override
		public void calculate(RobotOutput robotOutput, HumanInput humanInput)
		{
			double left = humanInput.getLeftJoystickThrottle();
			
			double right = humanInput.getRightJoystickThrottle();
			boolean shift = humanInput.getJoystickShift();
			
			robotOutput.tankDrive(left, right);
			
			if (shift != lastShift)
				robotOutput.shiftSpeed(currentState = !currentState);
			
			lastShift = shift;
		}
	};
	
	private static boolean lastShift = false;
	private static boolean currentState = false;
	private static OrbitPID driveController = new OrbitPID(0.1, 0.00005, 0.01, 0.5);
	private static boolean lastDeadzone = false;
	
	public abstract void calculate(RobotOutput robotOutput, HumanInput humanInput);
}
