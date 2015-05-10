package java.agents;

import java.CustomColor;
import java.agents.interfaces.IAgentDecisionCreator;

import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision {
	
	private CustomColor color;
	private String identifier;
	
	public AgentDecisionImpl(String identifier, CustomColor color) {
		this.identifier = identifier;
		this.color = color;
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

	@Override
	protected DecisionCore make_DecisionCore() {
		return new DecisionCore();
	}

}
