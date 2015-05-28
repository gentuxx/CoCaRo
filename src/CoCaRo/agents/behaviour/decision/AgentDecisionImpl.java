package CoCaRo.agents.behaviour.decision;

import CoCaRo.agents.behaviour.decision.interfaces.IAgentDecisionCreator;
import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision {
	
	public AgentDecisionImpl() {
		
	}

	//TODO Résoudre l'origine du paramètre cooperative
	
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
