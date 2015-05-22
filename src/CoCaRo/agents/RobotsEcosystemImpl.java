package CoCaRo.agents;

import java.util.List;

import speadl.agents.AgentAction;
import speadl.agents.AgentBehaviour;
import speadl.agents.AgentDecision;
import speadl.agents.AgentPerception;
import speadl.agents.RobotsEcosystem;
import CoCaRo.CustomColor;
import CoCaRo.agents.behaviour.AgentBehaviourPDAImpl;
import CoCaRo.agents.behaviour.actions.AgentActionsImpl;
import CoCaRo.agents.behaviour.decision.AgentDecisionImpl;
import CoCaRo.agents.behaviour.perception.AgentPerceptionImpl;

public class RobotsEcosystemImpl extends RobotsEcosystem{
	
	private final static int NB_ROBOTS_INIT = 3;
	
	private static long nextRobotIdentifier = 0l;
	
	private static CustomColor nextRobotColor = CustomColor.Red; 
	
	private List<Robot> robots;
	
	//TODO Identifier où il faut mettre ce genre d'initialisation
	public RobotsEcosystemImpl() {
		//Initialize Robot list
		/*robots = new ArrayList<Robot>();
		
		//Create NB_ROBOTS_INIT with different identifiers and colors
		for(int i=0;i<NB_ROBOTS_INIT;i++) {
			System.out.println("Création du robot "+String.valueOf(nextRobotIdentifier)
					+" de couleur "+nextRobotColor);
			
			//Add the result of make_Robot to the list
			robots.add(make_Robot(String.valueOf(nextRobotIdentifier),nextRobotColor));
			nextRobotIdentifier++;
			nextRobotColor = nextRobotColor.next();
		}*/
	}
	
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
