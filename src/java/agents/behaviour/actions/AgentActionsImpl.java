package java.agents.behaviour.actions;

import java.agents.behaviour.actions.interfaces.IAgentAction;

import speadl.agents.AgentAction;

public class AgentActionsImpl extends AgentAction {
	
	public AgentActionsImpl() {
		
	}

	@Override
	protected ActionCore make_ActionCore() {
		return new ActionCore() {

			@Override
			protected IAgentAction make_actions() {
				
				return new IAgentAction() {
					
					@Override
					public void goUp() {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void goRight() {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void goLeft() {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void goDown() {
						// TODO Auto-generated method stub
					}

					@Override
					public void takeBox() {
						// TODO Auto-generated method stub
					}

					@Override
					public void dropBox() {
						// TODO Auto-generated method stub
					}
				};
			}
			
		};
	}
}
