package robot.utils.parallel;

import robot.utils.Action;

public class Parallel {

	public static void doAction(Action action) {
		new ParallelAction(action).start();
	}
	
}
