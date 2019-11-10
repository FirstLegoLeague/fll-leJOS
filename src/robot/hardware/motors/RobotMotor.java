package robot.hardware.motors;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import robot.RobotMap;
import robot.exceptions.HardwareCreationError;
import robot.runs.RunHandler;
import robot.utils.Wait;

public abstract class RobotMotor {
	
	private String motorName;
	private MotorType motorType;
	protected boolean inverted;
	
	private char portName;
	protected Port port;

	public RobotMotor(String motorName, MotorType motorType, char portName) {
		this(motorName, motorType, portName, false);
	}
	
	public RobotMotor(String motorName, MotorType motorType, char portName, boolean isInverted) {
		this.motorName = motorName;
		this.motorType = motorType;
		
		this.portName = portName;
		this.setPort();
		
		this.inverted = isInverted;
		RobotMap.addMotor(this);
	}
	
	private void setPort() {
		switch(this.portName) {
		case 'A':
			this.port = MotorPort.A;
			break;
		case 'B':
			this.port = MotorPort.B;
			break;
		case 'C':
			this.port = MotorPort.C;
			break;
		case 'D':
			this.port = MotorPort.D;
			break;
		default:
			throw new HardwareCreationError("Port " + portName + " is invalid!");
		}
	}

	public String getName() {
		return this.motorName;
	}
	
	public char getPortName() {
		return this.portName;
	}
	
	public MotorType getType() {
		return this.motorType;
	}
	
	public void brake() {
		this.brake(false);
	}
	
	
	public void drive(double speed) {
		if (speed > 0) this.forward(speed);
		else this.backward(-speed);
	}
	
	protected int convertSpeed(double speed) {
		if (speed > 1.0 || speed < -1.0) throw new IllegalArgumentException("Speed must be between 1 and -1!");
		return (int) Math.min(Math.max((Math.abs(speed) * this.getMaxSpeed()), 0), this.getMaxSpeed());
	}
	
	public void rotateToZero(double speed, boolean brake) {
		this.rotateToValue(speed, 0, brake);
	}
	
	public void rotateToValue(double speed, int value, boolean brake) {
		if (this.readEncoder() < value) {
			this.forward(speed);
			Wait.waitFor(() -> {
				return this.readEncoder() >= value;
			});
		} else {
			this.backward(speed);
			Wait.waitFor(() -> {
				return this.readEncoder() <= value;
			});
		}
		
		if (brake) this.brake();
		else this.coast();
	}
	
	public void rotateSeconds(double speed, double seconds, boolean brake) {
		long startTime = System.currentTimeMillis();

		if (speed >= 0)
			this.forward(speed);
		else
			this.backward(speed);

		while (System.currentTimeMillis() - startTime < seconds * 1000 && RunHandler.isRunning())
			;

		if (brake) this.brake();
		else this.coast();
	}
	
	
	public abstract void forward(double speed);
	
	public abstract void backward(double speed);
	
	public abstract void brake(boolean immediateReturn);
	
	public abstract void coast();
	
	public abstract int readEncoder();
	
	public abstract void resetEncoder();
	
	public abstract boolean isStalled();
	
	public abstract float getMaxSpeed();
	
	public abstract void rotateDegrees(double speed, int degrees, boolean brake);
	
}
