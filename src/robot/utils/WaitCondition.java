package robot.utils;

@FunctionalInterface
public interface WaitCondition {
	public boolean evaluate();
}
