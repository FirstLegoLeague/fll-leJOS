package robot.utils;

import robot.runs.RunHandler;

@FunctionalInterface
public interface Condition {
	public boolean evaluate();
	
	public default boolean loopEvaluate() {
		return (this.evaluate() && RunHandler.isRunning());
	}
}
