package CoCaRo;

import speadl.environment.Environment;
import CoCaRo.environment.EnvironmentImpl;

public class Application {

	public static void main(String[] args) {
		Environment.Component env = new EnvironmentImpl(true).newComponent();
//		env.envInit().init();
	}

}
