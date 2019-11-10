package robot.hardware.motors;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import robot.runs.RunHandler;
import robot.utils.Wait;

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
	public void forward(double speed) {
		m.setSpeed(convertSpeed(speed));
		if(!this.inverted) m.forward();
		else m.backward();
	}

	@Override
	public void backward(double speed) {
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
	public int readEncoder() {
		if (!this.inverted) return m.getTachoCount();
		else return -m.getTachoCount();
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
	public void rotateDegrees(double speed, int degrees, boolean brake) {
		if (degrees < 0) throw new IllegalArgumentException("Degrees must be positive!");
		m.resetTachoCount();

		if (speed >= 0) {
			this.forward(speed);
			Wait.waitFor(() -> {
				return Math.abs(m.getTachoCount()) < degrees;
			});
		} else {
			this.backward(speed);
			Wait.waitFor(() -> {
				return -Math.abs(m.getTachoCount()) > degrees;
			});
		}

		if (brake) this.brake();
		else this.coast();
	}

}
