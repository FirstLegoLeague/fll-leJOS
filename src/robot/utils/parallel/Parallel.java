package robot.utils.parallel;

public class Parallel {

	public static void doAction(Action action) {
		new ParallelAction(action).start();
	}
	
}
