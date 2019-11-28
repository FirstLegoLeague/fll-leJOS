package main;

import lejos.hardware.Button;
import robot.RobotUtils;
import robot.runs.StopListener;
import user.Startup;

public class MainClass {
	
	public static void main(String[] args) {
		System.out.println("Started");
		
		System.out.println("Init hardware");
		Startup.createHardware();
		RobotUtils.resetAllMotorEncoders();
		RobotUtils.calibrateAllSensors();
		Button.ESCAPE.addKeyListener(new StopListener());
		
		System.out.println("Init runs");
		Startup.createRuns();
		
		System.out.println("Test Hardware");
		RobotUtils.testHardware();
		
		System.out.println("Run Custom Init");
		Startup.init();
		
		System.out.println("Ready");
		
		RobotUtils.floatAllMotors();
		MainMenu.init();
		
		System.out.println("Over");
	}
	
}
