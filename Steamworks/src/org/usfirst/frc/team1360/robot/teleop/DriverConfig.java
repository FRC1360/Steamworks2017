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
			
			/*boolean deadzone = Math.abs(humanInput.getRacingTurn()) < 0.2;
			if (deadzone)
			{
				double speed = humanInput.getRacingThrottle();
				
				if (!lastDeadzone || Math.abs(speed) < 0.01)
				{
					driveController.SetSetpoint(SensorInput.getInstance().getAHRSYaw());
				}
				

				driveController.SetInput(SensorInput.getInstance().getAHRSYaw());
				driveController.CalculateError();
					
				robotOutput.arcadeDrive(speed, Math.abs(speed) * driveController.GetOutput());
			}
			else
			{
				robotOutput.arcadeDrive(humanInput.getRacingThrottle(), humanInput.getRacingTurn());
			}
			lastDeadzone = deadzone;*/

			
			robotOutput.arcadeDrive(humanInput.getRacingThrottle(), humanInput.getRacingTurn());
			
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
			robotOutput.arcadeDrive(-humanInput.getHaloThrottle(), humanInput.getHaloTurn());
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
			robotOutput.tankDrive(humanInput.getTankLeft(), humanInput.getTankRight());
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
			robotOutput.arcadeDrive(humanInput.getArcadeThrottle(), humanInput.getArcadeTurn());
			boolean shift = humanInput.getArcadeShifter();
			
			if(shift != lastShift)
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
