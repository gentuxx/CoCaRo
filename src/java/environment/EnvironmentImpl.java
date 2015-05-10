package java.environment;

import java.CustomColor;
import java.agents.RobotsEcosystemImpl;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;

public class EnvironmentImpl extends Environment{

	@Override
	protected RobotsEcosystem make_robotEcosystem() {
		return new RobotsEcosystemImpl();
	}

	@Override
	protected Grid make_globalGrid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RobotGrid make_RobotGrid(String identifier, CustomColor color) {
		// TODO Auto-generated method stub
		return null;
	}
}
