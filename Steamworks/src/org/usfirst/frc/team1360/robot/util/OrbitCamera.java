package org.usfirst.frc.team1360.robot.util;

import java.awt.Point;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;


//Legit, all this class does is make a Camera.  Why the hell did I make this...?

public class OrbitCamera {
	
	private static UsbCamera camera;
	private CvSink src;
	private CvSource output;
	private Mat frame = new Mat();
	private Mat filteredFrame = new Mat();
	private Mat out = new Mat();
	ArrayList<MatOfPoint> contours;
	
	//IP would be set as 10.13.60.3 (example)
	public OrbitCamera()
	{
		CameraServer.getInstance().startAutomaticCapture();
		output = CameraServer.getInstance().putVideo("Vision Output", 640, 480);
	}

	public void updateCamera()
	{
		src = CameraServer.getInstance().getVideo();
		src.grabFrame(frame);
		
		if(!frame.empty()) {
		
		Scalar highBound = new Scalar(181, 255, 255);
		Scalar lowBound = new Scalar(29, 235, 170);
		
		Core.inRange(frame, lowBound, highBound, filteredFrame);
		
		contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();

		//create contours around shapes in the mask
		Imgproc.findContours(filteredFrame, contours, hierarchy, 2, 1);
		
		for(int i = 0; i < contours.size(); i++)
		{
			Imgproc.drawContours(frame, contours, i, new Scalar(255, 0, 255));
		}
		
		
		output.putFrame(frame);
		}
	}
}
