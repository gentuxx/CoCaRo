package CoCaRo.agents.behaviour.perception;

import java.util.List;

import CoCaRo.agents.behaviour.perception.interfaces.IAgentPerception;
import speadl.agents.AgentPerception;
import speadl.environment.BoxEnv.Box;
import speadl.environment.NestEnv.Nest;

public class AgentPerceptionImpl extends AgentPerception {
	
	public AgentPerceptionImpl() {
		
	}

	@Override
	protected PerceptionCore make_PerceptionCore() {
		return new PerceptionCore() {

			@Override
			protected IAgentPerception make_perception() {
				
				return new IAgentPerception() {
					
					@Override
					public void perceive() {
						
						List<Nest.Component> nestList = requires().gridP().getNest();
						List<Box.Component> boxList = requires().gridP().getBox();
					}
				};
			}
			
		};
	}
}
