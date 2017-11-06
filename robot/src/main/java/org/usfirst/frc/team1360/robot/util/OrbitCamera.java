package org.usfirst.frc.team1360.robot.util;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

//Legit, all this class does is make a Camera.  Why the hell did I make this...?

public class OrbitCamera {
	
	//private static CvSource camera;
	private static UsbCamera camera;
	/*private CvSink src;
	private Mat frame = new Mat();
	private Mat filteredFrame = new Mat();*/
	private CvSource output;
	private CvSink cvSink;
	private Mat source = new Mat();
	private Mat out = new Mat();
	
	//IP would be set as 10.13.60.3 (example)
	public OrbitCamera(String ip, String streamName)
	{
		//CameraServer.getInstance().addAxisCamera("10.13.60.91");
		//camera = CameraServer.getInstance().putVideo("Cool Camera", 320, 400);
		
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(320, 400);
		output = CameraServer.getInstance().putVideo(streamName, 320, 400);
		
		
	}

	public void updateCamera()
	{
		/*src = CameraServer.getInstance().getVideo();
		src.grabFrame(frame);
		Imgproc.cvtColor(frame, filteredFrame, Imgproc.COLOR_BGR2GRAY);
		camera.putFrame(frame);*/
		cvSink = CameraServer.getInstance().getVideo();
		cvSink.grabFrame(source);
		Imgproc.cvtColor(source, out, Imgproc.COLOR_BGR2GRAY);
		output.putFrame(out);
	}
}
