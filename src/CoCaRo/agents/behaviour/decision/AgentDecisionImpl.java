package CoCaRo.agents.behaviour.decision;

import CoCaRo.agents.behaviour.decision.interfaces.IAgentDecisionCreator;
import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision {
	
	public AgentDecisionImpl() {
		
	}
	
	@Override
	protected IAgentDecisionCreator make_creator() {
		System.out.println("make IAgentDecisionCreator");
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
