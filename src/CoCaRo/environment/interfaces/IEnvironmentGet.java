package CoCaRo.environment.interfaces;

import java.util.List;
import java.util.Map;

import speadl.environment.BoxEnv.Box;
import speadl.environment.Environment.RobotGrid;
import speadl.environment.NestEnv.Nest;
import CoCaRo.Element;
import CoCaRo.Position;

public interface IEnvironmentGet {
	
	public Map<Nest.Component, Position> getNestList();
	public List<Box.Component> getBoxList();
	public void addRobot(RobotGrid.Component robotGrid);
	public Element[][] getPartialGrid(Position pos);
	public void addGUI(EnvChangeListener listener);
	public void removeGUI(EnvChangeListener listener);

}
