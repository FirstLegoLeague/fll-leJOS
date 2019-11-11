package user;

import robot.RobotMap;
import robot.runs.RobotRun;

public class Run1 extends RobotRun{

	public Run1(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runInstructions() {
		RobotMap.getMotor("leftWheel").rotateDegrees(1.0, 720, true);
	}

}
