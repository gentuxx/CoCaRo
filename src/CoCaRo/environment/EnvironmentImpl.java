package CoCaRo.environment;

import java.util.List;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;
import CoCaRo.CustomColor;
import CoCaRo.agents.RobotsEcosystemImpl;

public class EnvironmentImpl extends Environment{

	//TODO Y a t'il besoin de ce genre de variables??
	private Grid globalGrid;
	private RobotsEcosystem robotEcosystem;
	private static List<RobotGrid> robots;
	
	public EnvironmentImpl(){
		/*globalGrid = make_globalGrid();
		robotEcosystem = make_robotEcosystem();*/
	}
	
	@Override
	protected RobotsEcosystem make_robotEcosystem() {
		System.out.println("make RobotsEcosystemImpl");
		return new RobotsEcosystemImpl();
	}

	@Override
	protected Grid make_globalGrid() {
		System.out.println("make global GridImpl");
		return new GridImpl();
	}

	@Override
	protected RobotGrid make_RobotGrid(String identifier, CustomColor color) {
		System.out.println("make Robot Grid " + identifier + " : "+color);
		return new RobotGrid() {

			@Override
			protected Grid make_grid() {
				//TODO A check
				System.out.println("make robotGrid grid");
				return globalGrid;
			}
			
		};
	}
	
}


