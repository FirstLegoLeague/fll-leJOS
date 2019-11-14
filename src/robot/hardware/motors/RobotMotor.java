package robot.hardware.motors;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import robot.RobotMap;
import robot.exceptions.HardwareCreationError;
import robot.runs.RunHandler;

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
		this.drive(speed, 1.0);
	}
	
	public void drive(double speed, double acceleration) {
		if (speed > 0) this.forward(speed, acceleration);
		else this.backward(-speed, acceleration);
	}
	
	protected int convertAcceleration(double acceleration) {
		if (acceleration > 1.0 || acceleration < 0) throw new IllegalArgumentException("Acceleration must be between 0 and 1!");
		return (int) acceleration * 6000;
	}
	
	protected double revertAcceleration(int acceleration) {
		return (double) acceleration / 6000;
	}
	
	protected int convertSpeed(double speed) {
		if (speed > 1.0 || speed < -1.0) throw new IllegalArgumentException("Speed must be between 1 and -1!");
		return (int) Math.min(Math.max((Math.abs(speed) * this.getMaxSpeed()), 0), this.getMaxSpeed());
	}
	
	protected double revertSpeed(int speed) {
		return (double) speed / this.getMaxSpeed();
	}
	
	public void rotateToZero(double speed, boolean brake) {
		this.rotateToValue(speed, 1.0, 0, brake);
	}
	
	public void rotateToZero(double speed, double acceleration, boolean brake) {
		this.rotateToValue(speed, acceleration, 0, brake);
	}
	
	public void rotateToValue(double speed, double acceleration, int value, boolean brake) {
		
		if (this.readEncoder() < value) {
			this.forward(speed, acceleration);
			//HOTFIX
			while(this.readEncoder() < value && RunHandler.isRunning());
//			Wait.waitFor(() -> {
//				return this.readEncoder() >= value;
//			});
		} else {
			this.backward(speed, acceleration);
			//HOTFIX
			while(this.readEncoder() > value && RunHandler.isRunning());
//			Wait.waitFor(() -> {
//				return this.readEncoder() <= value;
//			});
		}
		
		if (brake) this.brake();
		else this.coast();
	}
	
	public void rotateDegrees(double speed, int degrees, boolean brake) {
		this.rotateDegrees(speed, 1.0, degrees,  brake);
	}
	
	public void rotateDegrees(double speed, double acceleration, int degrees, boolean brake) {
		if (degrees < 0) throw new IllegalArgumentException("Degrees must be positive!");
		int startValue = this.readEncoder();

		if (speed >= 0) {
			this.forward(speed, acceleration);
			//HOTFIX
			while(this.readEncoder() < startValue + degrees && RunHandler.isRunning());
//			Wait.waitFor(() -> {
//				return this.readEncoder() > startValue + degrees;
//			});
		} else {
			this.backward(speed, acceleration);
			//HOTFIX
			while(this.readEncoder() > startValue + degrees && RunHandler.isRunning());
//			Wait.waitFor(() -> {
//				return this.readEncoder() < startValue - degrees;
//			});
		}

		if (brake) this.brake();
		else this.coast();
	}
	
	public void rotateSeconds(double speed, double seconds, boolean brake) {
		this.rotateSeconds(speed, 1.0, seconds, brake);
	}
	
	public void rotateSeconds(double speed, double acceleration, double seconds, boolean brake) {
		long startTime = System.currentTimeMillis();

		if (speed >= 0)
			this.forward(speed, acceleration);
		else
			this.backward(speed, acceleration);

		while (System.currentTimeMillis() - startTime < seconds * 1000 && RunHandler.isRunning())
			;

		if (brake) this.brake();
		else this.coast();
	}
	
	public void forward(double speed) {
		this.forward(speed, 1.0);
	}
	
	public void backward(double speed) {
		this.backward(speed, 1.0);
	}
	
	public abstract void forward(double speed, double acceleration);
	
	public abstract void backward(double speed, double acceleration);
	
	public abstract void brake(boolean immediateReturn);
	
	public abstract void setStallThreshold(int error, int time);
	
	public abstract void setSpeed(double speed);
	
	public abstract void setAcceleration(double acceleration);
	
	public abstract double getCurrentSpeed();
	
	public abstract double getTargetSpeed();
	
	public abstract double getAcceleration();
	
	public abstract boolean shouldBeMoving();
	
	public abstract void coast();
	
	public abstract int readEncoder();
	
	public abstract void resetEncoder();
	
	public abstract boolean isStalled();
	
	public abstract float getMaxSpeed();
	
}
