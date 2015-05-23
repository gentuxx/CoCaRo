package CoCaRo.environment;

import java.util.ArrayList;
import java.util.List;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;
import CoCaRo.CustomColor;
import CoCaRo.agents.RobotsEcosystemImpl;
import CoCaRo.environment.interfaces.IEnvInit;

public class EnvironmentImpl extends Environment{

	//TODO Y a t'il besoin de ce genre de variables??
	private Grid globalGrid;
	private RobotsEcosystem robotEcosystem;
	private static List<RobotGrid.Component> robots;
	
	public EnvironmentImpl(){
		robots = new ArrayList<>();
	}
	
	@Override
	protected RobotsEcosystem make_robotEcosystem() {
		System.out.println("make RobotsEcosystemImpl");
		return new RobotsEcosystemImpl();
	}

	@Override
	protected Grid make_globalGrid() {
		System.out.println("make global GridImpl");
		globalGrid = new GridImpl();
		return globalGrid;
	}

	@Override
	protected RobotGrid make_RobotGrid(String identifier, CustomColor color) {
		return new RobotGrid() {

			@Override
			protected Grid make_grid() {
				return globalGrid;
			}
			
		};
	}

	@Override
	protected IEnvInit make_envInit() {
		
		return new IEnvInit() {
			
			@Override
			public void init() {
				provides().nestCreator().createAllNests();
				provides().boxGenerator().generateBox(CustomColor.Red);
				parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.Red));
			}
		};
	}
	
}


