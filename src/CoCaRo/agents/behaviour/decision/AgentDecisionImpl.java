package CoCaRo.agents.behaviour.decision;

import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision {
	
	public AgentDecisionImpl() {
		
	}
	
	@Override
 	protected DecisionCore make_DecisionCore(boolean cooperative) {
		System.out.println("make DecisionCore");
		if(cooperative) {
			return new CooperativeDecisionCore();
		}
		else {
			return new ConcurrentDecisionCore();
		}
 	}
}
