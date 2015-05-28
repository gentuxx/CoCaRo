package CoCaRo.agents;

public abstract class RobotThread extends Thread{
	
	private long delay;
	private boolean stop;
	
	public void setDelay(long waitTime) {
		this.delay = waitTime;
	}
	
	public void run() {
		long time = System.currentTimeMillis();
		while(!stop) {
			action();
			
			// waiting
			time = System.currentTimeMillis();
			while(System.currentTimeMillis() - time < delay) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	public void turnOff() {
		this.stop = true;
		this.interrupt();
	}
	
	public abstract void action();
}
