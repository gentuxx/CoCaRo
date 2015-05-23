package CoCaRo.agents.behaviour.decision;

import java.util.Random;

import speadl.agents.AgentDecision.DecisionCore;

public class ConcurrentDecisionCore extends DecisionCore {
	
	public void decide(){
		
		Random rand = new Random();
		
		switch(rand.nextInt(4)) {
		case 0:
			requires().actions().goDown();
			break;
		case 1:
			requires().actions().goUp();
			break;
		case 2:
			requires().actions().goLeft();
			break;
		case 3:
			requires().actions().goRight();
			break;
		}
		
		
		
		
		
		/*requires().env().getNestList();
		requires().env().getBoxList();*/
		
		
		
		requires().perception().perceive();
	}
	
}
