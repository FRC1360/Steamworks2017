package org.usfirst.frc.team1360.auto;

public class AutonOverride extends AutonCommand {
	private RobotSubsystems overrideType;
	
	public AutonOverride(RobotSubsystems type) {
		super(type);
		this.overrideType = type;
	}
	
	public boolean checkAndRun() {
		AutonCommand.overrideComponent(this.overrideType);
		return true;
	}
	
	@Override
	public boolean calculate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub

	}
}
