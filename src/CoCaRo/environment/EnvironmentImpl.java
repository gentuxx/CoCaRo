package CoCaRo.environment;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;
import speadl.graphics.GUI;
import CoCaRo.CustomColor;
import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.IRobotCore;
import CoCaRo.agents.RobotController;
import CoCaRo.agents.RobotThread;
import CoCaRo.agents.RobotsEcosystemImpl;
import CoCaRo.environment.interfaces.IEnvInit;
import CoCaRo.environment.interfaces.IEnvironmentGet;
import CoCaRo.environment.interfaces.IEnvironmentSet;
import CoCaRo.graphics.GUIImpl;

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
	protected RobotGrid make_RobotGrid(final String identifier, final CustomColor color, final boolean cooperative) {
		System.out.println("make RobotGrid ("+identifier+";"+color+")");
		
		return new RobotGrid() {

			@Override
			protected IRobotCore make_robotCore() {
				return new IRobotCore() {

					private Position position;
					
					private Element box;
					
					private long energy;
					
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
						return (this.box != null);
					}
					
					@Override
					public Element dropBox() {
						Element oldBox = box;
						box = null;
						
						if(oldBox.getColor().equals(getColor())) {
							energy+=66;
						}
						else {
							energy+=33;
						}
						
						return oldBox;
					}

					@Override
					public CustomColor getBoxColor() {
						return box.getColor();
					}
					
					public long getEnergy() {
						return energy;
					}

					@Override
					public void spendEnergy() {
						this.energy--;
					}
				};
			}
		
			@Override
			protected void start() {
				RobotThread t = new RobotThread() {
					@Override
					public void action() {
						parts().aRobot().decisionMaker().interact();
					}
				};
				eco_provides().controller().addThread(t);
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
				System.out.println("\n===================\n");
				parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.Red,false));
				/*parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.Green,false));
				parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.Blue,false));*/
				System.out.println("\n===================\n");
				provides().controller().start(20);
			}
		};
	}

	@Override
	protected RobotController make_controller() {
		return new RobotController();
	}

	@Override
	protected GUI make_graphics() {
		return new GUIImpl();
	}
	
}
