package CoCaRo.environment.interfaces;

import java.util.List;

import CoCaRo.Position;
import CoCaRo.environment.GridImpl.Element;
import speadl.environment.BoxEnv.Box;
import speadl.environment.Environment.RobotGrid;
import speadl.environment.NestEnv.Nest;


public interface IEnvironmentGet {
	
	public List<Nest.Component> getNestList();
	public List<Box.Component> getBoxList();
	public void addRobot(RobotGrid.Component robotGrid);
	public Element[][] getPartialGrid(Position pos);

}
