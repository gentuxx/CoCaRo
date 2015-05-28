package CoCaRo.agents.behaviour.decision;

import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision {
	
	public AgentDecisionImpl() {
		
	}
	
	@Override
 	protected DecisionCore make_DecisionCore(boolean cooperative) {
		// TODO Auto-generated method stub
		System.out.println("make DecisionCore");
		if(cooperative) {
			return new ConcurrentDecisionCore();
		}
		else {
			return new CooperativeDecisionCore(); 
		}
 	}
}
