package CoCaRo.agents;

/**
 * Representation of a robot as a Thread
 * @author M
 *
 */
public abstract class RobotThread extends Thread{
	
	/**
	 * The delay to wait for after doing its action
	 */
	private long delay;
	
	/**
	 * Boolean value for stopping the thread 
	 */
	private boolean stop = true;
	
	/**
	 * Boolean value for stopping the thread 
	 */
	private boolean pause = true;
	
	/**
	 * Set the delay value
	 * @param waitTime the new delay value
	 */
	public void setDelay(long waitTime) {
		this.delay = waitTime;
	}
	
	@Override
	public void run() {
		stop = false;
		
		long time = System.currentTimeMillis();
		
		while(!stop) {
			action();
			
			try {
				// waiting
				time = System.currentTimeMillis();
				while(System.currentTimeMillis() - time < delay || pause==true) {
					Thread.sleep(1);
				} 
			}
			catch (InterruptedException e) {
				
			}
		}
	}
	
	/**
	 * Stop the execution of the thread
	 */
	public void turnOff() {
		this.stop = true;
		this.interrupt();
	}
	
	/**
	 * Stop the execution of the thread
	 * @param stop true if the thread must be stopped, false if it is just a pause
	 */
	public void setPaused(boolean paused) {
		this.pause = paused;
	}
	/**
	 * The action to implements and that will be executed inside the run loop
	 */
	public abstract void action();
}
