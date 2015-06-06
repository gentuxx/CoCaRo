package CoCaRo.agents;

import java.util.ArrayList;
import java.util.List;


/**
 * Control the start/stop and speed of the robots
 * @author M
 *
 */
public class RobotController {
	
	/**
	 * The list of RobotThread
	 */
	private List<RobotThread> robots;
	
	/**
	 * The speed of the robots (minimum 1)
	 */
	private int speed = -1;
	
	/**
	 * Boolean value to know when robots run or not
	 */
	private boolean stop;
	
	/**
	 * Ctor
	 */
	public RobotController() {
		robots = new ArrayList<RobotThread>();
		stop = true;
	}
	
	/**
	 * Start all the RobotThread in the list
	 */
	public void start() {
		if(speed>0) {
			start(speed);
		}
	}
	
	/**
	 * Start all the RobotThread in the list
	 * @param speed the initial speed of the program
	 */
	public void start(int speed) {
		this.speed = speed;
		
		if(speed <= 0) {
			this.speed = 1;
		}
		
		this.stop = false;
		long waitTime = calculateDelay();
		 
		for(final RobotThread robot : robots) {
			robot.setDelay(waitTime);
			robot.start();
		}
	}
	
	/**
	 * Pause the execution of the program
	 */
	public void pause() {
		for(final RobotThread robot : robots) {
			robot.setPaused(true);
		}
	}
	
	/**
	 * Pause the execution of the program
	 */
	public void restart() {
		for(final RobotThread robot : robots) {
			robot.setPaused(false);
		}
	}
	
	/**
	 * Stop the execution of the program
	 */
	public void stop() {
		for(final RobotThread robot : robots) {
			robot.turnOff();
			
			try {
				robot.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Calculate the delay to wait for in the threads
	 * @return the delay as a long
	 */
	private long calculateDelay() {
		long waitTime = 3000 - (250 * speed);
		if(waitTime <= 0) {
			waitTime = 1;
		}
		return waitTime;
	}
	
	/**
	 * Increase the speed of the program
	 */
	public void increaseSpeed() {
		this.speed++;
		long waitTime = calculateDelay();
		for(final RobotThread robot : robots) {
			robot.setDelay(waitTime);
		}				
	}
	
	/**
	 * Decrease the speed of the program
	 */
	public void decreaseSpeed() {
		this.speed--;
		
		if(this.speed <= 0) {
			this.speed = 1;
		}
		
		long waitTime = calculateDelay();
		for(final RobotThread robot : robots) {
			robot.setDelay(waitTime);
		}			
	}

	
	/**
	 * Add and start the RobotThread in parameter
	 * @param robot the RobotThread 
	 */
	public void addThread(RobotThread robot) {
		robots.add(robot);
		if(!stop) {
			robot.setDelay(calculateDelay());
			robot.start();
		}
	}

	public void removeThread(RobotThread robotThread) {
		robotThread.turnOff();
		robots.remove(robotThread);
	}
	
	public int getSpeed() {
		return speed;
	}

	public void removeAllThread() {
		for(final RobotThread robot : robots) {
			robot.interrupt();
			removeThread(robot);
		}
		
	}
}
