package java.agents;

import java.CustomColor;
import java.agents.behaviour.AgentBehaviourPDAImpl;
import java.agents.behaviour.actions.AgentActionsImpl;
import java.agents.behaviour.decision.AgentDecisionImpl;
import java.agents.behaviour.perception.AgentPerceptionImpl;

import speadl.agents.AgentAction;
import speadl.agents.AgentBehaviour;
import speadl.agents.AgentDecision;
import speadl.agents.AgentPerception;
import speadl.agents.RobotsEcosystem;

public class RobotsEcosystemImpl extends RobotsEcosystem{
	
	@Override
	protected Robot make_Robot(final String identifier, final CustomColor color) {
		return new RobotImpl(identifier,color);
	}

	@Override
	protected AgentBehaviour make_behaviour() {
		return new AgentBehaviour() {
			
			@Override
			protected AgentBehaviourPDA make_AgentBehaviourPDA(String identifier, CustomColor color) {
				return new AgentBehaviourPDAImpl(identifier,color);				
			}
			
			@Override
			protected AgentPerception make_perception() {
				return new AgentPerceptionImpl();
			}
			
			@Override
			protected AgentDecision make_decision() {
				return new AgentDecisionImpl();
			}
			
			@Override
			protected AgentAction make_actions() {
				return new AgentActionsImpl();
			}
		};
	}

}
