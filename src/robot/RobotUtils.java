package robot;

import robot.hardware.motors.RobotMotor;
import robot.hardware.sensors.RobotSensor;

public class RobotUtils {
	private RobotUtils() {
	}
	
	public static void testHardware() {
		RobotMotor[] motors = RobotMap.getMotors();
		RobotSensor[] sensors = RobotMap.getSensors();
		
		for (int i = 0; i < motors.length; i++) {
			if(motors[i] == null) continue;
			motors[i].drive(0.01, 1.0);
			motors[i].brake(true);
		}
		
		for (int i = 0; i < sensors.length; i++) {
			if(sensors[i] == null) continue;
			sensors[i].read();
		}
	}

	public static void calibrateAllSensors() {
		RobotSensor[] sensors = RobotMap.getSensors();
		for (int i = 0; i < sensors.length; i++) {
			if (sensors[i] != null) {
				sensors[i].resetToAbsoluteValue();
				if (sensors[i].canCalibrate()) {
					sensors[i].calibrate();
				}
			}
		}
	}

	public static void resetAllMotorEncoders() {
		RobotMotor[] motors = RobotMap.getMotors();
		for (int i = 0; i < motors.length; i++) {
			if (motors[i] != null) {
				motors[i].resetEncoder();
			}
		}
	}

	public static void stopAllMotors() {
		RobotMotor[] motors = RobotMap.getMotors();
		for (int i = 0; i < motors.length; i++) {
			if (motors[i] != null) {
				motors[i].brake(true);
			}
		}
	}
	
	public static void floatAllMotors() {
		RobotMotor[] motors = RobotMap.getMotors();
		for (int i = 0; i < motors.length; i++) {
			if (motors[i] != null) {
				motors[i].coast();
			}
		}
	}
}
