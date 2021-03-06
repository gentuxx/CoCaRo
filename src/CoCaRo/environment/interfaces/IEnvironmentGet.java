package CoCaRo.environment.interfaces;

import java.util.List;
import java.util.Map;

import speadl.environment.BoxEnv.Box;
import speadl.environment.Environment.RobotGrid;
import CoCaRo.CustomColor;
import CoCaRo.Element;
import CoCaRo.Position;

public interface IEnvironmentGet {
	
	public Position getNest(CustomColor color);
	public List<Box.Component> getBoxList();
	public Element[][] getPartialGrid(Position pos);
	public Element[][] getGrid();
	public void addGUI(EnvChangeListener listener);
	public void removeGUI(EnvChangeListener listener);
	public void addRobot(RobotGrid.Component robotGrid);
	public Map<Position,RobotGrid.Component> getRobotsMap();
	public void initGrid();

}
