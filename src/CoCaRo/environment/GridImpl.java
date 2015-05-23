package CoCaRo.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import speadl.environment.BoxEnv;
import speadl.environment.BoxEnv.Box;
import speadl.environment.Environment.RobotGrid;
import speadl.environment.Grid;
import speadl.environment.NestEnv;
import speadl.environment.NestEnv.Nest;
import CoCaRo.CustomColor;
import CoCaRo.Position;
import CoCaRo.environment.interfaces.IBoxGenerator;
import CoCaRo.environment.interfaces.IEnvironment;
import CoCaRo.environment.interfaces.INestCreator;

public class GridImpl extends Grid implements IEnvironment{

	private List<Nest.Component> nestList;
	private List<Box.Component> boxList;
	private List<RobotGrid.Component> robotsList;
	private Element[][] grid = new Element[20][20];
	
	public GridImpl() {
		nestList = new ArrayList<>();
		boxList = new ArrayList<>();
		robotsList = new ArrayList<>();
	}
	
	@Override
	public List<Nest.Component> getNestList() {
		//TODO Vérifier que cette méthode est bien utiles
		return nestList;
	}


	@Override
	public List<Box.Component> getBoxList() {
		//TODO Vérifier que cette méthode est bien utile
		return boxList;
	}
	
	
	@Override
	protected IEnvironment make_env() {
		//Return the current object to avoid data duplication
		return this;
	}

	@Override
	protected BoxEnv make_boxEnv() {
		System.out.println("make BoxEnv");
		return new BoxEnv() {
			
			@Override
			protected IBoxGenerator make_boxGenerator() {
				System.out.println("make IBoxGenerator");
				return new IBoxGenerator() {
					
					@Override
					public void generateBox(CustomColor color) {
						boxList.add(newBox(color));
						
						if(color.equals(CustomColor.Blue)){
							putInGrid(Element.BLUE_BOX);
						}else if(color.equals(CustomColor.Green)){
							putInGrid(Element.GREEN_BOX);
						}else if(color.equals(CustomColor.Red)){
							putInGrid(Element.RED_BOX);
						}
						
						System.out.println("Generated a "+color+" box");
					}
				};
			}
		};
	}

	@Override
	protected NestEnv make_nestEnv() {
		System.out.println("make NestEnv");
		return new NestEnv() {
			
			@Override
			protected INestCreator make_nestCreator() {
				System.out.println("make INestGenerator");
				return new INestCreator() {
					
					@Override
					public void createAllNests() {
						nestList.add(newNest(CustomColor.Red));
						System.out.println("Generated the "+CustomColor.Red+" nest");
						putInGrid(Element.RED_NEST);
						
						nestList.add(newNest(CustomColor.Green));
						System.out.println("Generated the "+CustomColor.Green+" nest");
						putInGrid(Element.GREEN_NEST);						
						
						nestList.add(newNest(CustomColor.Blue));
						System.out.println("Generated the "+CustomColor.Blue+" nest");
						putInGrid(Element.BLUE_NEST);
					}
				};
			}
		};
	}
	
	//TODO Voir si on peut pas s'arranger pour positionner directement
	private Position putInGrid(Element elem) {
		boolean findPosition = false;
		
		while(!findPosition){
			Random rand = new Random();
			
			int xValue = rand.nextInt(20);
			int yValue = rand.nextInt(20);
			
			if(grid[xValue][yValue]==null || grid[xValue][yValue].equals(Element.EMPTY) || (grid[xValue][yValue]==null)){ 
				grid[xValue][yValue] = elem;
				findPosition = true;
				System.out.println("Element put at ("+xValue+";"+yValue+")");
				return new Position(xValue,yValue);
			}
		}
		
		return null;
	}
	
	public enum Element {
		EMPTY, AGENT, AGENT_WITH_BOX, RED_BOX, GREEN_BOX, BLUE_BOX,
		RED_NEST, GREEN_NEST, BLUE_NEST
	}
	
	public Element[][] getGrid(){
		return grid;
	}

	@Override
	public void addRobot(
			speadl.environment.Environment.RobotGrid.Component robotGrid) {
		robotGrid.robotCore().setPosition(putInGrid(Element.AGENT));
		robotsList.add(robotGrid);
	}
}
