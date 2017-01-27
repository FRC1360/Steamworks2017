package org.usfirst.frc.team1360.robot.IO;

import org.usfirst.frc.team1360.robot.util.XboxRemote;

public class HumanInput {
	
	private static HumanInput instance;
	private XboxRemote driver;
	private XboxRemote operator;
	
	private HumanInput()
	{
		this.driver = new XboxRemote(0);
		this.operator = new XboxRemote(1);
	}
	
	public static HumanInput getInstance()
	{
		if (instance == null)
		{
			instance = new HumanInput();
		}
		
		return instance;
	}
	
}
