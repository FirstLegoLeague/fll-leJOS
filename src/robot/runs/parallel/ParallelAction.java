package robot.runs.parallel;

public class ParallelAction extends Thread{
	
	private Action a;
	
	public ParallelAction(Action a) {
		this.a = a;
	}
	
	@Override
	public void run() {
		System.out.println("Running action");
		a.execute();
	}
	
}
