package robot.hardware.chassis;

public interface Chassis {
	
	public default void drive(double speed) {
		if (speed > 0) this.forwardDrive(speed);
		else this.backwardDrive(-speed);
	}
	
	public void forwardDrive(double speed);
	
	public void backwardDrive(double speed);
	
	public void forwardDriveDegrees(double speed, int degrees, boolean brake);
	
	public void backwardDriveDegrees(double speed, int degrees, boolean brake);
	
	public void forwardDriveSeconds(double speed, double seconds, boolean brake);
	
	public void backwardDriveSeconds(double speed, double seconds, boolean brake);
	
	public void tankDrive(double leftSpeed, double rightSpeed);
	
	public void tankDriveDegrees(double leftSpeed, double rightSpeed, int degrees, boolean brake);
	
	public void tankDriveSeconds(double leftSpeed, double rightSpeed, double seconds, boolean brake);
	
	public void brake();
	
	public void coast();
}
