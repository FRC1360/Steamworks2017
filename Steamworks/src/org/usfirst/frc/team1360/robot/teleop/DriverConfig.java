package org.usfirst.frc.team1360.robot.teleop;

import org.usfirst.frc.team1360.robot.IO.HumanInput;
import org.usfirst.frc.team1360.robot.IO.RobotOutput;

public enum DriverConfig {
	RACING {		
		@Override
		public void calculate(RobotOutput robotOutput, HumanInput humanInput)
		{
			robotOutput.arcadeDrive(humanInput.getRacingThrottle(), humanInput.getRacingTurn());
			boolean shift = humanInput.getRacingShifter();
			
			if (shift && !lastShift)
				robotOutput.shiftSpeed(currentState = !currentState);
			
			lastShift = shift;
		}
	},
	
	HALO {
		@Override
		public void calculate(RobotOutput robotOutput, HumanInput humanInput)
		{
			robotOutput.arcadeDrive(humanInput.getHaloThrottle(), humanInput.getHaloTurn());
			boolean shift = humanInput.getHaloShifter();
			
			if (shift && !lastShift)
				robotOutput.shiftSpeed(currentState = !currentState);
			
			lastShift = shift;
		}
	};
	
	private static boolean lastShift = false;
	private static boolean currentState = false;
	
	public abstract void calculate(RobotOutput robotOutput, HumanInput humanInput);
}
