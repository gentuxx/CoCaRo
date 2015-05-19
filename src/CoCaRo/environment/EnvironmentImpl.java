package CoCaRo.environment;

import CoCaRo.CustomColor;
import CoCaRo.agents.RobotsEcosystemImpl;
import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;

public class EnvironmentImpl extends Environment{

	@Override
	protected RobotsEcosystem make_robotEcosystem() {
		System.out.println("make RobotsEcosystemImpl");
		return new RobotsEcosystemImpl();
	}

	@Override
	protected Grid make_globalGrid() {
		System.out.println("make GridImpl");
		return new GridImpl();
	}

	@Override
	protected RobotGrid make_RobotGrid(String identifier, CustomColor color) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


