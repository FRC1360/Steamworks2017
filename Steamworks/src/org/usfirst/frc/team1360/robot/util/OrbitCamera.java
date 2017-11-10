package org.usfirst.frc.team1360.robot.util;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;


//Legit, all this class does is make a Camera.  Why the hell did I make this...?

public class OrbitCamera {
	
	private static UsbCamera camera;
	private CvSink src;
	private Mat frame = new Mat();
	private Mat filteredFrame = new Mat();
	
	//IP would be set as 10.13.60.3 (example)
	public OrbitCamera()
	{
		CameraServer.getInstance().startAutomaticCapture();
		//CameraServer.getInstance().putVideo("Cool Camera", 640, 480);
	}

	public void updateCamera()
	{
		CameraServer.getInstance().getVideo();
		//src.grabFrame(frame);
		//Imgproc.cvtColor(frame, filteredFrame, Imgproc.COLOR_BGR2GRAY);
		//camera.putFrame(frame);
	}
}
