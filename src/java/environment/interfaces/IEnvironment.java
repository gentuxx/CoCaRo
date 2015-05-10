package java.environment.interfaces;

import java.util.List;

import speadl.environment.BoxEnv.Box;
import speadl.environment.NestEnv.Nest;


public interface IEnvironment {
	
	public List<Nest.Component> getNest();
	public List<Box.Component> getBox();

}
