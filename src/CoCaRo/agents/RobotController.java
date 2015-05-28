package CoCaRo.agents;

import java.util.ArrayList;
import java.util.List;

public class RobotController {
	private List<RobotThread> robots;
	private int speed;
	
	public RobotController() {
		robots = new ArrayList<RobotThread>();
	}
	
	public void start(int speed) {
		this.speed = speed;
		if(speed <= 0) {
			this.speed = 1;
		}
		long waitTime = calculateDelay();
		 
		for(final RobotThread robot : robots) {
			robot.setDelay(waitTime);
			robot.run();
		}
	}
	
	public void pause() {
		for(final RobotThread robot : robots) {
			robot.turnOff();
			try {
				robot.join();
			} catch (InterruptedException e) {
			}
		}
	}
	
	private long calculateDelay() {
		long waitTime = 3000 - (250 * speed);
		if(waitTime <= 0) {
			waitTime = 1;
		}
		return waitTime;
	}
	
	public void increaseSpeed() {
		this.speed++;
		long waitTime = calculateDelay();
		for(final RobotThread robot : robots) {
			robot.setDelay(waitTime);
		}				
	}
	
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

	public void addThread(RobotThread robot) {
		robots.add(robot);
		robot.run();
	}
}
