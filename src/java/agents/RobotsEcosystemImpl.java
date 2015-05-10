package java.agents;

import java.CustomColor;

import speadl.agents.AgentAction;
import speadl.agents.AgentBehaviour;
import speadl.agents.AgentDecision;
import speadl.agents.AgentPerception;
import speadl.agents.RobotsEcosystem;

public class RobotsEcosystemImpl extends RobotsEcosystem{
	
	String identifier;
	CustomColor color;

	@Override
	protected Robot make_Robot(final String identifier, final CustomColor color) {
		this.identifier = identifier;
		this.color = color;
		return new Robot();
	}

	@Override
	protected AgentBehaviour make_behaviour() {
		return new AgentBehaviour() {
			
			@Override
			protected AgentPerception make_perception() {
				return new AgentPerceptionImpl(identifier);
			}
			
			@Override
			protected AgentDecision make_decision() {
				return new AgentDecisionImpl(identifier, color);
			}
			
			@Override
			protected AgentAction make_actions() {
				return new AgentActionsImpl(identifier);
			}
		};
	}

}
