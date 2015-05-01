package java.environment;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;

public class EnvironmentImpl extends Environment{


	@Override
	protected Grid make_grid() {
		return new GridImpl();
	}

	@Override
	protected RobotsEcosystem make_robotEcosystem() {
		// TODO Auto-generated method stub
		return null;
	}
}
