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
		
		/*try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		while(!stop) {
			System.out.println("Action!");
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
	
	/**
	 * Stop the execution of the thread
	 */
	public void turnOff() {
		this.stop = true;
		this.interrupt();
	}
	
	/**
	 * The action to implements and that will be executed inside the run loop
	 */
	public abstract void action();
}
