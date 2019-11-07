package robot.hardware.chassis;

public interface Chassis {
	
	public default void drive(double speed) {
		if (speed > 0) this.forwardDrive(speed);
		else this.backwardDrive(-speed);
	}
	
	public void forwardDrive(double speed);
	
	public void backwardDrive(double speed);
	
	public void forwardDrive(double speed, int degrees, boolean brake);
	
	public void backwardDrive(double speed, int degrees, boolean brake);
	
	public void forwardDrive(double speed, double seconds, boolean brake);
	
	public void backwardDrive(double speed, double seconds, boolean brake);
	
	public void tankDrive(double leftSpeed, double rightSpeed);
	
	public void tankDrive(double leftSpeed, double rightSpeed, int degrees, boolean brake);
	
	public void tankDrive(double leftSpeed, double rightSpeed, double seconds, boolean brake);
	
	public void brake();
	
	public void coast();
}
