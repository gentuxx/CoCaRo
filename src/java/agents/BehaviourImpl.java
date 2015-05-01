package java.agents;

import java.Color;

import speadl.agents.AgentAction;
import speadl.agents.AgentBehaviourPDA;
import speadl.agents.AgentDecision;
import speadl.agents.AgentPerception;

public class BehaviourImpl extends AgentBehaviourPDA{

	private Color color;
	private String identifier;
	
	public BehaviourImpl(String identifier,Color color) {
		this.identifier = identifier;
		this.color = color;
	}
	
	@Override
	protected AgentPerception make_perception() {
		return new AgentPerceptionImpl();
	}

	@Override
	protected AgentAction make_actions() {
		return new AgentActionsImpl();
	}

	@Override
	protected AgentDecision make_decision() {
		return new AgentDecisionImpl();
	}
	
	

}
