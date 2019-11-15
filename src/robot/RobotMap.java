package robot;

import robot.exceptions.HardwareCreationError;
import robot.exceptions.HardwareNotFoundException;
import robot.hardware.chassis.Chassis;
import robot.hardware.motors.RobotMotor;
import robot.hardware.sensors.RobotSensor;

public class RobotMap {
	private RobotMap() {
	}

	private static RobotSensor[] sensors = new RobotSensor[4];
	private static RobotMotor[] motors = new RobotMotor[4];
	
	private static Chassis chassis;

	public static RobotSensor[] getSensors() {
		return sensors;
	}

	public static RobotMotor[] getMotors() {
		return motors;
	}
	
	public static Chassis getChassis() {
		if (chassis != null) {
			return chassis;
		} else {
			throw new HardwareNotFoundException("No chassis found!");
		}
	}
	
	public static void setChassis(Chassis newChassis) {
		chassis = newChassis;
	}

	public static void addSensor(RobotSensor sensor) {
		int index = sensor.getPortName() - 1;
		if (sensorExists(index)) {
			throw new HardwareCreationError("Sensor on port " + sensor.getPortName() + " already exists!");
		}
		if(sensorNameExists(sensor.getName())) throw new HardwareCreationError("Sensor with name " + sensor.getName() + " already exists!");
		sensors[index] = sensor;
	}

	public static void addMotor(RobotMotor motor) {
		int index = motorportNameToIndex(motor.getPortName());
		if (motorExists(index)) {
			throw new HardwareCreationError("Motor on port " + motor.getPortName() + " already exists!");
		}
		if(motorNameExists(motor.getName())) throw new HardwareCreationError("Motor with name " + motor.getName() + " already exists!");
		motors[index] = motor;
	}

	public static RobotSensor getSensor(String name) {
		for (int i = 0; i < sensors.length; i++) {
			if (sensorExists(i) && sensors[i].getName() == name) {
				return sensors[i];
			}
		}
		throw new HardwareNotFoundException("Sensor " + name + " not found!");
	}

	public static RobotMotor getMotor(String name) {
		for (int i = 0; i < motors.length; i++) {
			if (motorExists(i) && motors[i].getName() == name) {
				return motors[i];
			}
		}

		throw new HardwareNotFoundException("Motor " + name + " not found!");
	}

	public static RobotSensor getSensor(int portName) {
		int index = portName - 1;
		if (sensorExists(index))
			return sensors[index];

		throw new HardwareNotFoundException("Sensor " + portName + " not found!");
	}

	public static RobotMotor getMotor(char portName) {
		int index = motorportNameToIndex(portName);
		if (motorExists(index))
			return motors[index];

		throw new HardwareNotFoundException("Motor " + portName + " not found!");
	}
	
	private static boolean sensorExists(int index) {
		if (sensors[index] != null) return true;
		return false;
	}
	
	private static boolean motorExists(int index) {
		if (motors[index] != null) return true;
		return false;
	}
	
	private static boolean motorNameExists(String name) {
		for (int i = 0; i < motors.length; i++) {
			if(motors[i].getName() == name) return true;
		}
		return false;
	}
	
	private static boolean sensorNameExists(String name) {
		for (int i = 0; i < sensors.length; i++) {
			if(sensors[i].getName() == name) return true;
		}
		return false;
	}

	private static int motorportNameToIndex(char portName) {
		switch (portName) {
		case 'A':
			return 0;
		case 'B':
			return 1;
		case 'C':
			return 2;
		case 'D':
			return 3;
		default:
			return -1;
		}
	}

}
