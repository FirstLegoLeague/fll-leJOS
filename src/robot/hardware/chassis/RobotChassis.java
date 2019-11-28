package robot.hardware.chassis;

import robot.RobotMap;
import robot.hardware.motors.LargeMotor;
import robot.runs.RunHandler;
import robot.utils.wait.Wait;

public class RobotChassis implements Chassis{

	private LargeMotor leftMotor;
	private LargeMotor rightMotor;
	
	public RobotChassis(char leftPort, char rightPort) {
		this(leftPort, rightPort, false);
	}
	
	public RobotChassis(char leftPort, char rightPort, boolean isInverted) {
		this("leftWheel", leftPort, "rightWheel", rightPort, isInverted);
	}
	
	public RobotChassis(String leftMotorName, char leftPort, String rightMotorName, char rightPort) {
		this(leftMotorName, leftPort, rightMotorName, rightPort, false);
	}
	
	public RobotChassis(String leftMotorName, char leftPort, String rightMotorName, char rightPort, boolean isInverted) {
		LargeMotor lm = new LargeMotor(leftMotorName, leftPort, isInverted);
		LargeMotor rm = new LargeMotor(rightMotorName, rightPort, isInverted);
		
		this.leftMotor = lm;
		this.rightMotor = rm;
		
		lm.syncWithMotor(rm);
		RobotMap.setChassis(this);
	}

	@Override
	public void forwardDrive(double speed) {
		syncDrive(speed, speed);
	}

	@Override
	public void backwardDrive(double speed) {
		syncDrive(-speed, -speed);
	}

	@Override
	public void forwardDriveDegrees(double speed, int degrees, boolean brake) {
		if (degrees < 0) throw new IllegalArgumentException("Degrees must be positive!");
		int startValue = leftMotor.readEncoder();
		
		syncDrive(speed, speed);

		while(leftMotor.readEncoder() < startValue + degrees && RunHandler.isRunning());
		
		if (brake) this.brake();
		else this.coast();
	}

	@Override
	public void backwardDriveDegrees(double speed, int degrees, boolean brake) {
		if (degrees < 0) throw new IllegalArgumentException("Degrees must be positive!");
		int startValue = leftMotor.readEncoder();
		
		syncDrive(-speed, -speed);
		
		while(leftMotor.readEncoder() > startValue - degrees && RunHandler.isRunning());
		
		if (brake) this.brake();
		else this.coast();
	}

	@Override
	public void forwardDriveSeconds(double speed, double seconds, boolean brake) {
		syncDrive(speed, speed);
		
		Wait.waitForSeconds(seconds);
		
		if (brake) this.brake();
		else this.coast();
	}

	@Override
	public void backwardDriveSeconds(double speed, double seconds, boolean brake) {
		syncDrive(-speed, -speed);
		
		Wait.waitForSeconds(seconds);
		
		if (brake) this.brake();
		else this.coast();
	}

	@Override
	public void tankDrive(double leftSpeed, double rightSpeed) {
		syncDrive(leftSpeed, rightSpeed);
	}

	@Override
	public void tankDriveDegrees(double leftSpeed, double rightSpeed, int degrees, boolean brake) {
		if (degrees < 0) throw new IllegalArgumentException("Degrees must be positive!");
		int leftStartValue = leftMotor.readEncoder();
		int rightStartValue = rightMotor.readEncoder();
		
		syncDrive(leftSpeed, rightSpeed);
		
		while(Math.abs(leftMotor.readEncoder()) < Math.abs(leftStartValue) + degrees 
				&& Math.abs(rightMotor.readEncoder()) < Math.abs(rightStartValue) + degrees 
				&& RunHandler.isRunning());
		
		if (brake) this.brake();
		else this.coast();
		
	}

	@Override
	public void tankDriveSeconds(double leftSpeed, double rightSpeed, double seconds, boolean brake) {
		syncDrive(leftSpeed, rightSpeed);
		
		Wait.waitForSeconds(seconds);
		
		if (brake) this.brake();
		else this.coast();
	}
	
	private void syncDrive(double leftSpeed, double rightSpeed) {
		leftMotor.startSync();
		if(leftMotor.isInverted()) leftSpeed = -leftSpeed;
		if(rightMotor.isInverted()) rightSpeed = -rightSpeed;
		
		if(leftSpeed >= 0) {
			leftMotor.forward(leftSpeed);
		} else {
			leftMotor.backward(leftSpeed);
		}
		
		if(rightSpeed >= 0) {
			rightMotor.forward(rightSpeed);
		} else {
			rightMotor.backward(rightSpeed);
		}
		leftMotor.endSync();
	}

	@Override
	public void brake() {
		leftMotor.startSync();
		leftMotor.brake(true);
		rightMotor.brake(true);
		leftMotor.endSync();
	}

	@Override
	public void coast() {
		leftMotor.startSync();
		leftMotor.coast();
		rightMotor.coast();
		leftMotor.endSync();
	}
		
}
