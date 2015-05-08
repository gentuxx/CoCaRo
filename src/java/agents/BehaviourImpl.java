package java.agents;

import java.CustomColor;

import speadl.agents.AgentBehaviour.AgentBehaviourPDA;

public class BehaviourImpl extends AgentBehaviourPDA{

	private CustomColor color;
	private String identifier;
	
	public BehaviourImpl(String identifier,CustomColor color) {
		this.identifier = identifier;
		this.color = color;
	}
	
}
