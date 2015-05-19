package CoCaRo.agents.behaviour;

import CoCaRo.CustomColor;
import speadl.agents.AgentBehaviour.AgentBehaviourPDA;

public class AgentBehaviourPDAImpl extends AgentBehaviourPDA{
	
	private String identifier;
	private CustomColor color;
	
	public AgentBehaviourPDAImpl(String identifier, CustomColor color) {
		this.identifier = identifier;
		this.color = color;
				
	}
	
}
