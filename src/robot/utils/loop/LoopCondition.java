package robot.utils.loop;

import robot.runs.RunHandler;
import robot.utils.wait.Condition;

public class LoopCondition {
	private Condition condition;
	
	public LoopCondition(Condition condition) {
		this.condition = condition;
	}
	
	public boolean evaluate() {
		return (this.condition.evaluate() && RunHandler.isRunning());
	}
}
