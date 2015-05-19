package CoCaRo.agents;

import CoCaRo.CustomColor;
import CoCaRo.agents.behaviour.AgentBehaviourPDAImpl;
import CoCaRo.agents.behaviour.actions.AgentActionsImpl;
import CoCaRo.agents.behaviour.decision.AgentDecisionImpl;
import CoCaRo.agents.behaviour.perception.AgentPerceptionImpl;
import speadl.agents.AgentAction;
import speadl.agents.AgentBehaviour;
import speadl.agents.AgentDecision;
import speadl.agents.AgentPerception;
import speadl.agents.RobotsEcosystem;

public class RobotsEcosystemImpl extends RobotsEcosystem{
	
	@Override
	protected Robot make_Robot(final String identifier, final CustomColor color) {
		System.out.println("make RobotImpl");
		return new RobotImpl(identifier,color);
	}

	@Override
	protected AgentBehaviour make_behaviour() {
		System.out.println("make AgentBehaviour");
		return new AgentBehaviour() {
			
			@Override
			protected AgentBehaviourPDA make_AgentBehaviourPDA(String identifier, CustomColor color) {
				System.out.println("make AgentBehaviourPDAImpl");
				return new AgentBehaviourPDAImpl(identifier,color);				
			}
			
			@Override
			protected AgentPerception make_perception() {
				System.out.println("make AgentPerceptionImpl");
				return new AgentPerceptionImpl();
			}
			
			@Override
			protected AgentDecision make_decision() {
				System.out.println("make AgentDecisionImpl");
				return new AgentDecisionImpl();
			}
			
			@Override
			protected AgentAction make_actions() {
				System.out.println("make AgentActionsImpl");
				return new AgentActionsImpl();
			}
		};
	}

}
