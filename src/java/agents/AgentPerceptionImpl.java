package java.agents;

import java.agents.interfaces.IAgentPerception;
import java.util.List;

import speadl.agents.AgentPerception;
import speadl.environment.BoxEnv.Box;
import speadl.environment.NestEnv.Nest;

public class AgentPerceptionImpl extends AgentPerception {

	private String identifier;
	
	public AgentPerceptionImpl(String identifier) {
		this.identifier = identifier;
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
