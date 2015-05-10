package java.agents;

import speadl.agents.AgentDecision.DecisionCore;

public class ConcurrentDecisionCore extends DecisionCore {
	
	public void decide(){
		requires().actions().goDown();
		requires().actions().goUp();
		requires().actions().goLeft();
		requires().actions().goRight();
		
		requires().env().getNest();
		requires().env().getBox();
		
		
		
		requires().perception().perceive();
	}
	
}
