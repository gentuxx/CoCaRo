package CoCaRo.environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Environment;
import speadl.environment.Grid;
import speadl.graphics.GUI;
import speadl.logging.Logging;
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
import CoCaRo.logging.LoggerImpl;

public class EnvironmentImpl extends Environment{
	
	private GridImpl globalGrid;
	
	public EnvironmentImpl() {
		
	}
	
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
		
		RobotGrid robotGrid = new RobotGrid() {
			
			private RobotThread robotThread;
			
			@Override
			protected IRobotCore make_robotCore() {
				return new IRobotCore() {

					private final static long MAX_ENERGY = 100;
					
					private Position position;
					
					private Element box;
					
					private long energy = MAX_ENERGY;
					
					@Override
					public CustomColor getColor() {
						return color;
					}

					@Override
					public String getIdentifier() {
						return identifier;
					}
					
					public void suicide(){
						eco_provides().controller().removeThread(robotThread);
						globalGrid.removeRobot(getPosition());
						
						//TODO Voir a quoi sert l'identifier
						globalGrid.addRobot(newRobotGrid("", color, cooperative));
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
						eco_provides().boxGenerator().generateBox(CustomColor.randomColor());
					}

					@Override
					public boolean hasBox() {
						return (this.box != null);
					}
					
					@Override
					public void dropBox() {
						if(box.getColor().equals(getColor())) {
							energy+=(2 * MAX_ENERGY)/3;
						}
						else {
							energy+=MAX_ENERGY/3;
						}
						
						box = null;
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
				robotThread = new RobotThread() {
					@Override
					public void action() {
						parts().aRobot().decisionMaker().interact();
					}
				};
				eco_provides().controller().addThread(robotThread);
			}
		};
		
		return robotGrid;
	}

	@Override
	protected IEnvInit make_envInit() {
		
		return new IEnvInit() {
			
			@Override
			public void init(int nbRobots, int nbBoxes, int vitesseExec, boolean cooperative) {
				System.out.println("\n\n\n Debut de l'initialisation \n\n\n");
				
				try {
					Files.createDirectory(Paths.get("./log"));
				} catch (FileAlreadyExistsException e2) {
					
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("Failed to create log folder");
					System.exit(-3);
				} 
				
				File folder = new File("./log/");
				
				if(folder!=null && folder.isDirectory() && folder.exists()) {
					System.out.println(folder.listFiles().length);
					for (final File fileEntry : folder.listFiles()) {
				        try {
							Files.deleteIfExists(fileEntry.toPath());
						} catch (IOException e) {
							e.printStackTrace();
						}
				    }
				}
				
				
				provides().controller().removeAllThread();
	
				parts().globalGrid().env().initGrid();
				
				provides().nestCreator().createAllNests();
				
				for(int i = 0; i<nbBoxes; i++){
					provides().boxGenerator().generateBox(CustomColor.randomColor());
				}
				
				System.out.println("\n===================\n");
				for(int i = 0; i<nbRobots; i++){
					parts().globalGrid().env().addRobot(newRobotGrid("test", CustomColor.randomColor(),cooperative));
				}
				System.out.println("\n===================\n");
				provides().controller().start(vitesseExec*2);
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

	@Override
	protected Logging make_log1() {
		System.out.println("make LoggingImpl");
		return new Logging(){

			@Override
			protected Logger make_Logger() {
				return new LoggerImpl();
			}
			
		};
	}
	
}
