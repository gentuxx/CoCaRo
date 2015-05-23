package CoCaRo.agents.behaviour.perception;

import speadl.agents.AgentPerception;
import CoCaRo.Position;
import CoCaRo.agents.behaviour.perception.interfaces.IAgentPerception;
import CoCaRo.environment.GridImpl.Element;
import CoCaRo.environment.interfaces.IEnvironmentGet;

public class AgentPerceptionImpl extends AgentPerception {
	
	public AgentPerceptionImpl() {
		System.out.println("Constructor AgentPerceptionImpl");
		
	}

	@Override
	protected PerceptionCore make_PerceptionCore() {
		System.out.println("make PerceptionCore");
		return new PerceptionCore() {

			@Override
			protected IAgentPerception make_perception() {
				
				return new IAgentPerception() {
					
					@Override
					public Element[][] getPartialGrid() {
						
						IEnvironmentGet envGet = requires().core().getEnvironmentGet();
						Position position = requires().core().getPosition();
						return envGet.getPartialGrid(position);
					}
				};
			}
		};
	}
}
