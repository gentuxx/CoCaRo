package java.agents;

import speadl.agents.AgentPerception;

public class AgentPerceptionImpl extends AgentPerception {

	private String identifier;
	
	public AgentPerceptionImpl(String identifier) {
		this.identifier = identifier;
	}

	@Override
	protected PerceptionCore make_PerceptionCore() {
		return new PerceptionCore() {
			
		};
	}
	
	@Override
	protected IAgentPerception make_perception() {
		return new IAgentPerception() {
	
		};
	}

}
