package robot.utils;

@FunctionalInterface
public interface Action {
	public void execute();
	
	public default void runInParallel() {
		new ParallelAction(this).start();
	}
}
