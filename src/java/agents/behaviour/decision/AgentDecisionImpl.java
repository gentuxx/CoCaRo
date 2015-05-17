package java.agents.behaviour.decision;

import java.agents.behaviour.decision.interfaces.IAgentDecisionCreator;

import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision {
	
	public AgentDecisionImpl() {
		
	}
	
	@Override
	protected IAgentDecisionCreator make_creator() {
		return new IAgentDecisionCreator() {

			@Override
			public DecisionCore createAgentDecisionCore(boolean cooperative) {
				if(cooperative) {
					return new ConcurrentDecisionCore();
				}
				else {
					return new CooperativeDecisionCore(); 
				}
							
			}
			
		};
	}

	//TODO Résoudre l'origine du paramètre cooperative
	
	@Override
 	protected DecisionCore make_DecisionCore() {
		// TODO Auto-generated method stub
		return new DecisionCore();
 	}
}
