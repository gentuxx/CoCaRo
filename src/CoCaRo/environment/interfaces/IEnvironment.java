package CoCaRo.environment.interfaces;

import java.util.List;

import speadl.environment.BoxEnv.Box;
import speadl.environment.NestEnv.Nest;


public interface IEnvironment {
	
	public List<Nest.Component> getNestList();
	public List<Box.Component> getBoxList();

}
