package CoCaRo.agents.behaviour.perception;

import speadl.agents.AgentPerception;
import CoCaRo.agents.behaviour.perception.interfaces.IAgentPerception;

public class AgentPerceptionImpl extends AgentPerception {

	@Override
	protected PerceptionCore make_PerceptionCore() {
		System.out.println("make PerceptionCore");
		return new PerceptionCore() {

			@Override
			protected IAgentPerception make_perception() {
				
				return new IAgentPerception() {
					
					@Override
					public void perceive() {
						
						/*List<Nest.Component> nestList = requires().gridP().getNestList();
						List<Box.Component> boxList = requires().gridP().getBoxList();*/
					}
				};
			}
			
		};
	}
}
