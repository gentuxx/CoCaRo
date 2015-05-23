package CoCaRo.environment;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;
import CoCaRo.CustomColor;
import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.IRobotCore;
import CoCaRo.agents.RobotsEcosystemImpl;
import CoCaRo.environment.interfaces.IEnvInit;
import CoCaRo.environment.interfaces.IEnvironmentGet;
import CoCaRo.environment.interfaces.IEnvironmentSet;

public class EnvironmentImpl extends Environment{
	
	private GridImpl globalGrid;
	
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
	protected RobotGrid make_RobotGrid(final String identifier, final CustomColor color) {
		System.out.println("make RobotGrid ("+identifier+";"+color+")");
		return new RobotGrid() {

			@Override
			protected IRobotCore make_robotCore() {
				return new IRobotCore() {

					private Position position;
					
					private Element box;
					
					@Override
					public CustomColor getColor() {
						return color;
					}

					@Override
					public String getIdentifier() {
						return identifier;
					}

					@Override
					public IEnvironmentSet getEnvironmentSet() {
						return globalGrid;
					}
					
					@Override
					public Position getPosition() {
						return position;
					}

					@Override
					public void setPosition(Position newPosition) {
						position = newPosition;
					}

					@Override
					public IEnvironmentGet getEnvironmentGet() {
						return globalGrid;
					}

					@Override
					public void takeBox(Element box) {
						this.box = box;
					}

					@Override
					public boolean hasBox() {
						return (this.box == null);
					}
					
					@Override
					public Element dropBox() {
						Element oldBox = box;
						box = null;
						return oldBox;
					}
				};
			}
		};
	}

	@Override
	protected IEnvInit make_envInit() {
		
		return new IEnvInit() {
			
			@Override
			public void init() {
				System.out.println("\n\n\n Début de l'initialisation \n\n\n");
				
				provides().nestCreator().createAllNests();
				provides().boxGenerator().generateBox(CustomColor.Red);
				provides().boxGenerator().generateBox(CustomColor.Blue);
				provides().boxGenerator().generateBox(CustomColor.Green);
				parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.Red));
				parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.Green));
				parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.Blue));
			}
		};
	}
	
}


