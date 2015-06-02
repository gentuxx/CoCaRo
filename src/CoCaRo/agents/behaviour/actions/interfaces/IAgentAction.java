package CoCaRo.agents.behaviour.actions.interfaces;

import CoCaRo.Position;
import CoCaRo.agents.IRobotCore;

public interface IAgentAction {
	
	public void goUp(IRobotCore core);
	
	public void goDown(IRobotCore core);
	
	public void goLeft(IRobotCore core);
	
	public void goRight(IRobotCore core);

	public void dropBox(IRobotCore core);

	public void takeBox(IRobotCore core, Position boxPosition);
	
	public void suicide();
	
	public void checkEnergy(IRobotCore core);
}
