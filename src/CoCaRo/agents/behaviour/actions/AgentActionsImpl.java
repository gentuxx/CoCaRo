package CoCaRo.agents.behaviour.actions;

import speadl.agents.AgentAction;
import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.IRobotCore;
import CoCaRo.agents.behaviour.actions.interfaces.IAgentAction;
import CoCaRo.environment.interfaces.IEnvironmentSet;

public class AgentActionsImpl extends AgentAction {

	@Override
	protected ActionCore make_ActionCore() {
		System.out.println("make ActionCore: Creation de l'espece ActionCore");
		return new ActionCore() {

			@Override
			protected IAgentAction make_actions() {
				
				return new IAgentAction() {
					
					@Override
					public void goUp(IRobotCore core) {
						IEnvironmentSet env = core.getEnvironmentSet();
						final Position oldPosition = core.getPosition();
						Position newPosition = new Position(oldPosition.getX(),oldPosition.getY()-1);
						System.out.println(newPosition);
						core.setPosition(newPosition);
						core.spendEnergy();
						env.updatePosition(oldPosition,newPosition);
					}

					@Override
					public void goDown(IRobotCore core) {
						IEnvironmentSet env = core.getEnvironmentSet();
						final Position oldPosition = core.getPosition();
						Position newPosition = new Position(oldPosition.getX(),oldPosition.getY()+1);
						System.out.println(newPosition);
						core.setPosition(newPosition);
						core.spendEnergy();
						env.updatePosition(oldPosition,newPosition);
					}

					@Override
					public void goLeft(IRobotCore core) {
						IEnvironmentSet env = core.getEnvironmentSet();
						final Position oldPosition = core.getPosition();
						Position newPosition = new Position(oldPosition.getX()-1,oldPosition.getY());
						System.out.println(newPosition);
						core.setPosition(newPosition);
						core.spendEnergy();
						env.updatePosition(oldPosition,newPosition);
					}

					@Override
					public void goRight(IRobotCore core) {
						IEnvironmentSet env = core.getEnvironmentSet();
						final Position oldPosition = core.getPosition();
						Position newPosition = new Position(oldPosition.getX()+1,oldPosition.getY());
						System.out.println(newPosition);
						core.setPosition(newPosition);
						core.spendEnergy();
						env.updatePosition(oldPosition,newPosition);
					}

					@Override
					public void takeBox(IRobotCore core, Position boxPosition) {
						Element box = core.getEnvironmentSet().removeBox(boxPosition);
						core.takeBox(box);
					}

					@Override
					public void dropBox(IRobotCore core) {
						Element box = core.dropBox();
						// TODO Si la box est de la couleur du nid
//						if((box == Element.BLUE_BOX &&)
//								|| (box == Element.GREEN_BOX &&)
//								|| (box == Element.RED_BOX)){
//							core.setEnergy(20);
//						}else{
//							core.setEnergy(10);
//						}
						
					}

					@Override
					public void suicide() {
						
					}

					@Override
					public void checkEnergy(IRobotCore core) {
						if(core.getEnergy() == 0){
							suicide();
						}						
					}
				};
			}
		};
	}

}
