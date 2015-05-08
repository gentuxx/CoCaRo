package java.agents;

import java.Color;

import speadl.agents.AgentAction;
import speadl.agents.AgentBehaviour.AgentBehaviourPDA;
import speadl.agents.AgentDecision;
import speadl.agents.AgentPerception;

public class BehaviourImpl extends AgentBehaviourPDA{

	private Color color;
	private String identifier;
	
	public BehaviourImpl(String identifier,Color color) {
		this.identifier = identifier;
		this.color = color;
	}
	
}
