package robot.utils.wait;

@FunctionalInterface
public interface Condition {
	public boolean evaluate();
}
