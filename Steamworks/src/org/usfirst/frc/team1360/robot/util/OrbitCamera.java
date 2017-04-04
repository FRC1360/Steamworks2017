package org.usfirst.frc.team1360.robot.util;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;

//Legit, all this class does is make a Camera.  Why the hell did I make this...?

public class OrbitCamera {
	
	private static CvSource camera;
	private CvSink src;
	private Mat frame = new Mat();
	private Mat filteredFrame = new Mat();
	
	//IP would be set as 10.13.60.3 (example)
	public OrbitCamera(String ip, String streamName)
	{
		CameraServer.getInstance().addAxisCamera("10.13.60.3");
		camera = CameraServer.getInstance().putVideo("Cool Camera", 320, 400);
	}

	public void updateCamera()
	{
		src = CameraServer.getInstance().getVideo();
		src.grabFrame(frame);
		Imgproc.cvtColor(frame, filteredFrame, Imgproc.COLOR_BGR2GRAY);
		camera.putFrame(frame);
	}
}
