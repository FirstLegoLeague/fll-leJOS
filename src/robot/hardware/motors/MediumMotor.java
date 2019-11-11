package robot.hardware.motors;

import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class MediumMotor extends RobotMotor {

	private EV3MediumRegulatedMotor m;

	public MediumMotor(String motorName, char port) {
		this(motorName, port, false);
	}

	public MediumMotor(String motorName, char port, boolean isInverted) {
		super(motorName, MotorType.MEDIUM, port, isInverted);
		this.m = new EV3MediumRegulatedMotor(this.port);
	}

	@Override
	public void forward(double speed, double acceleration) {
		m.setAcceleration(convertAcceleration(acceleration));
		m.setSpeed(convertSpeed(speed));
		if(!this.inverted) m.forward();
		else m.backward();
	}

	@Override
	public void backward(double speed, double acceleration) {
		m.setAcceleration(convertAcceleration(acceleration));
		m.setSpeed(convertSpeed(speed));
		if(!this.inverted) m.backward();
		else m.forward();
	}

	@Override
	public void brake(boolean immediateReturn) {
		m.stop(immediateReturn);
	}

	@Override
	public void coast() {
		m.flt();
	}
	
	@Override
	public void setStallThreshold(int error, int time) {
		m.setStallThreshold(error, time);
	}

	@Override
	public int readEncoder() {
		if (!this.inverted) return m.getTachoCount();
		else return (-m.getTachoCount());
	}

	@Override
	public void resetEncoder() {
		m.resetTachoCount();
	}

	@Override
	public boolean isStalled() {
		return m.isStalled();
	}
	
	@Override
	public float getMaxSpeed() {
		return m.getMaxSpeed();
	}
	
	@Override
	public boolean shouldBeMoving() {
		return m.isMoving();
	}
	
	@Override
	public void setSpeed(double speed) {
		m.setSpeed(convertSpeed(speed));
	}
	
	@Override
	public void setAcceleration(double acceleration) {
		m.setAcceleration(convertAcceleration(acceleration));
	}

}
