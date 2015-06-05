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
						requires().log().addLine("Go Up " + newPosition.toString() + " -- Energy : " + core.getEnergy() + "\n");
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
						requires().log().addLine("Go Down " + newPosition.toString() + " -- Energy : " + core.getEnergy() + "\n");
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
						requires().log().addLine("Go Left " + newPosition.toString() + " -- Energy : " + core.getEnergy() + "\n");
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
						requires().log().addLine("Go Right " + newPosition.toString() + " -- Energy : " + core.getEnergy() + "\n");
						core.setPosition(newPosition);
						core.spendEnergy();
						env.updatePosition(oldPosition,newPosition);
					}

					@Override
					public void takeBox(IRobotCore core, Position boxPosition) {
						Element box = core.getEnvironmentSet().removeBox(boxPosition);
						core.takeBox(box);
						requires().log().addLine("Take box at position : " + boxPosition.toString() + "\n");
					}

					@Override
					public void dropBox(IRobotCore core) {
						core.dropBox();
						requires().log().addLine("Found nest and drop box \n");
					}

					@Override
					public void suicide() {
						try {
							this.finalize();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
