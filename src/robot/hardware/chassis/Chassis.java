package robot.hardware.chassis;

public interface Chassis {
	
	public default void drive(double speed) {
		if (speed > 0) this.forwardDrive(speed);
		else this.backwardDrive(-speed);
	}
	
	public default void forwardDrive(double speed) {
		this.forwardDrive(speed, 1.0);
	}
	
	public default void backwardDrive(double speed) {
		this.backwardDrive(speed, 1.0);
	}
	
	public void forwardDrive(double speed, double acceleration);
	
	public void backwardDrive(double speed, double acceleration);
	
	public default void forwardDriveDegrees(double speed, int degrees, boolean brake) {
		this.forwardDriveDegrees(speed, 1.0, degrees, brake);
	}
	
	public default void backwardDriveDegrees(double speed, int degrees, boolean brake) {
		this.backwardDriveDegrees(speed, 1.0, degrees, brake);
	}
	
	public void forwardDriveDegrees(double speed, double acceleration, int degrees, boolean brake);
	
	public void backwardDriveDegrees(double speed, double acceleration, int degrees, boolean brake);
	
	public default void forwardDriveSeconds(double speed, double seconds, boolean brake) {
		this.forwardDriveSeconds(speed, 1.0, seconds, brake);
	}
	
	public default void backwardDriveSeconds(double speed, double seconds, boolean brake) {
		this.backwardDriveSeconds(speed, 1.0, seconds, brake);
	}
	
	public void forwardDriveSeconds(double speed, double acceleration, double seconds, boolean brake);
	
	public void backwardDriveSeconds(double speed, double acceleration, double seconds, boolean brake);
	
	public default void tankDrive(double leftSpeed, double rightSpeed) {
		this.tankDrive(leftSpeed, rightSpeed, 1.0);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed, double acceleration);
	
	public default void tankDriveDegrees(double leftSpeed, double rightSpeed, int degrees, boolean brake) {
		this.tankDriveDegrees(leftSpeed, rightSpeed, 1.0, degrees, brake);
	}
	
	public void tankDriveDegrees(double leftSpeed, double rightSpeed, double acceleration, int degrees, boolean brake);
	
	public default void tankDriveSeconds(double leftSpeed, double rightSpeed, double seconds, boolean brake) {
		this.tankDriveSeconds(leftSpeed, rightSpeed, 1.0, seconds, brake);
	}
	
	public void tankDriveSeconds(double leftSpeed, double rightSpeed, double acceleration, double seconds, boolean brake);
	
	public void setAcceleration(double acceleration);
	
	public void brake();
	
	public void coast();
}
