package java.agents;

import java.agents.interfaces.IAgentAction;

import speadl.agents.AgentAction;

public class AgentActionsImpl extends AgentAction {

	private String identifier;
	
	public AgentActionsImpl(String identifier) {
		this.identifier = identifier;
	}

	@Override
	protected ActionCore make_ActionCore() {
		return new ActionCore() {

			@Override
			protected IAgentAction make_actions() {
				
				return new IAgentAction() {
					
					@Override
					public void goUp() {
						
					}
					
					@Override
					public void goRight() {
						
					}
					
					@Override
					public void goLeft() {
						
					}
					
					@Override
					public void goDown() {
						
					}
				};
			}
			
		};
	}
}
