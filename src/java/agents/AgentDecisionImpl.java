package java.agents;

import java.Color;

import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision {
	
	private Color color;
	private String identifier;
	
	public AgentDecisionImpl(String identifier, Color color) {
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

}
