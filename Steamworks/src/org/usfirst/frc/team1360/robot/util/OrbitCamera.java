package org.usfirst.frc.team1360.robot.util;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;

//Legit, all this class does is make a Camera.  Why the hell did I make this...?

public class OrbitCamera {
	
	private static CvSource camera;
	
	//IP would be set as 10.13.60.3 (example)
	public OrbitCamera(String ip, String streamName)
	{
		CameraServer.getInstance().addAxisCamera(ip);
		camera = CameraServer.getInstance().putVideo(streamName, 320, 400);
	}
}
