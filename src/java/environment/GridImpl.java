package java.environment;

import java.CustomColor;
import java.environment.interfaces.IBoxGenerator;
import java.environment.interfaces.IEnvironment;
import java.environment.interfaces.INestCreator;
import java.util.List;

import speadl.environment.BoxEnv;
import speadl.environment.BoxEnv.Box;
import speadl.environment.Grid;
import speadl.environment.NestEnv;
import speadl.environment.NestEnv.Nest;

public class GridImpl extends Grid{

	private List<Nest.Component> nestList;
	private List<Box.Component> boxList;
	
	
	@Override
	protected IEnvironment make_env() {
		
		return new IEnvironment() {

			@Override
			public List<speadl.environment.NestEnv.Nest.Component> getNest() {
				return nestList;
			}

			@Override
			public List<speadl.environment.BoxEnv.Box.Component> getBox() {
				return boxList;
			}
		};
	}

	@Override
	protected BoxEnv make_boxEnv() {
		return new BoxEnv() {
			
			@Override
			protected IBoxGenerator make_createBox() {
				return new IBoxGenerator() {
					
					@Override
					public void generateBox(CustomColor color) {
						boxList.add(newBox(color));
					}
				};
			}
		};
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
