package robot.utils;

public class ParallelAction extends Thread{
	
	private Action a;
	
	public ParallelAction(Action a) {
		this.a = a;
	}
	
	@Override
	public void run() {
		a.execute();
	}
	
}
