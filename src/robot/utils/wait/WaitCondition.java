package robot.utils.wait;

@FunctionalInterface
public interface WaitCondition {
	public boolean evaluate();
}
