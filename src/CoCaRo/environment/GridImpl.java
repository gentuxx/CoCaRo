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
import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.environment.interfaces.IBoxGenerator;
import CoCaRo.environment.interfaces.IEnvironmentGet;
import CoCaRo.environment.interfaces.IEnvironmentSet;
import CoCaRo.environment.interfaces.INestCreator;

public class GridImpl extends Grid implements IEnvironmentGet, IEnvironmentSet{

	private final static int GRID_SIZE = 20;
	
	private List<Nest.Component> nestList;
	private List<Box.Component> boxList;
	private List<RobotGrid.Component> robotsList;
	private Element[][] grid = new Element[GRID_SIZE][GRID_SIZE];
	
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
	protected IEnvironmentGet make_env() {
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
						
						System.out.println("Generated a "+color+" box");
						
						if(color.equals(CustomColor.Blue)){
							putInGrid(Element.BLUE_BOX);
						}else if(color.equals(CustomColor.Green)){
							putInGrid(Element.GREEN_BOX);
						}else if(color.equals(CustomColor.Red)){
							putInGrid(Element.RED_BOX);
						}
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
			
			int xValue = rand.nextInt(GRID_SIZE);
			int yValue = rand.nextInt(GRID_SIZE);
			
			if(grid[xValue][yValue]==null){ 
				grid[xValue][yValue] = elem;
				findPosition = true;
				System.out.println("Element put at ("+xValue+";"+yValue+")");
				return new Position(xValue,yValue);
			}
		}
		
		return null;
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

	@Override
	public void updatePosition(Position oldPosition, Position newPosition) {
		grid[newPosition.getX()][newPosition.getY()] = grid[oldPosition.getX()][oldPosition.getY()];
		grid[oldPosition.getX()][oldPosition.getY()] = null;
	}

	@Override
	public Element removeBox(Position position) {
		Element box = grid[position.getX()][position.getY()];
		grid[position.getX()][position.getY()] = null;
		return box;
	}
}
