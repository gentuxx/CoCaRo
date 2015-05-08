package java.environment;

import java.CustomColor;
import java.environment.interfaces.IEnvironment;
import java.environment.interfaces.INestCreator;
import java.util.List;

import speadl.environment.BoxEnv;
import speadl.environment.Grid;
import speadl.environment.NestEnv;
import speadl.environment.NestEnv.Nest;

public class GridImpl extends Grid{

	public static List<Nest.Component> nestList;
	
	
	@Override
	protected IEnvironment make_env() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BoxEnv make_boxEnv() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected NestEnv make_nestEnv() {
		return new NestEnv() {
			
			@Override
			protected INestCreator make_createNests() {
				return new INestCreator() {
					
					@Override
					public void createAllNests() {
						nestList.add(newNest(CustomColor.Red));
						nestList.add(newNest(CustomColor.Green));
						nestList.add(newNest(CustomColor.Blue));
					}
				};
			}
		};
	}

}
