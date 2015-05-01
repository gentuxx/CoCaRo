package java.agents;

import speadl.agents.AgentAction;

public class AgentActionsImpl extends AgentAction {

	private String identifier;
	
	public AgentActionsImpl(String identifier) {
		this.identifier = identifier;
	}

	@Override
	protected ActionCore make_ActionCore() {
		return new ActionCore() {
			
		};
	}
	
	@Override
	protected IAgentAction make_actions() {
		return new IAgentAction() {
			
		};
	}
}
