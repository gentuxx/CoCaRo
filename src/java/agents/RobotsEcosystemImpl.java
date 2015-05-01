package java.agents;

import java.Color;

import speadl.agents.AgentBehaviourPDA;
import speadl.agents.RobotsEcosystem;

public class RobotsEcosystemImpl extends RobotsEcosystem{

	@Override
	protected Robot make_Robot(String identifier, Color color) {
		Robot robot = new Robot(){

			@Override
			protected AgentBehaviourPDA make_behaviour() {
				return new BehaviourImpl(identifier,color);
			}
			
		};
		return null;
	}

}
