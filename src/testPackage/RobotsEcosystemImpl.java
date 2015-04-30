package testPackage;

import testPackage.test.RobotsEcosystem;

public class RobotsEcosystemImpl extends RobotsEcosystem{

	@Override
	protected IRobotCreator make_createRobot() {
		// TODO Auto-generated method stub
		return new RobotCreatorImpl();
	}
}
