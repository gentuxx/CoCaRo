package CoCaRo.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import speadl.environment.BoxEnv;
import speadl.environment.BoxEnv.Box;
import speadl.environment.Environment.RobotGrid;
import speadl.environment.Grid;
import speadl.environment.NestEnv;
import speadl.environment.NestEnv.Nest;
import CoCaRo.CustomColor;
import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.environment.interfaces.EnvChangeListener;
import CoCaRo.environment.interfaces.EnvChangeListener.EnvChangeEvent;
import CoCaRo.environment.interfaces.IBoxGenerator;
import CoCaRo.environment.interfaces.IEnvironmentGet;
import CoCaRo.environment.interfaces.IEnvironmentSet;
import CoCaRo.environment.interfaces.INestCreator;

public class GridImpl extends Grid implements IEnvironmentGet, IEnvironmentSet{

	private final static int GRID_SIZE = 20;
	
	private Map<Nest.Component, Position> nestList;
	private List<Box.Component> boxList;
	private Map<Position,RobotGrid.Component> robotsMap;
	private Element[][] grid = new Element[GRID_SIZE][GRID_SIZE];
	
	private CopyOnWriteArrayList<EnvChangeListener> listeners;
	
	public GridImpl() {
		nestList = new HashMap<>();
		boxList = new ArrayList<>();
		robotsMap = new HashMap<>();
		listeners = new CopyOnWriteArrayList<EnvChangeListener>();
	}
	
	//TODO Rendre accessible pour l'interface graphique
	public void addGUI(EnvChangeListener listener) {
		listeners.add(listener);
	}
	
	//TODO Vérifier l'utilité de cette fonction
	//TODO Rendre accessible pour l'interface graphique
	public void removeGUI(EnvChangeListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Envoie un évènement à toutes les interfaces graphiques enregistrées
	 */
	protected void fireChangeEvent() {
	    EnvChangeEvent evt = new EnvChangeEvent("");

	    for (EnvChangeListener listener : listeners) {
	    	listener.changeEventReceived(evt);
	    }
	}
	
	@Override
	public Position getNest(CustomColor color) {
		
		for(Iterator<Nest.Component>it = nestList.keySet().iterator();it.hasNext();) {
			Nest.Component currentComp = it.next();
			if(currentComp.getColor().equals(color)) {
				return nestList.get(currentComp);
			}
		}
		
		return null;
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
						
						Position pos = putInGrid(Element.RED_NEST);
						nestList.put(newNest(CustomColor.Red), pos);
						System.out.println("Generated the "+CustomColor.Red+" nest");
						
						pos = putInGrid(Element.GREEN_NEST);	
						nestList.put(newNest(CustomColor.Green), pos);
						System.out.println("Generated the "+CustomColor.Green+" nest");
						
						pos = putInGrid(Element.BLUE_NEST);
						nestList.put(newNest(CustomColor.Blue), pos);
						System.out.println("Generated the "+CustomColor.Blue+" nest");
					}
				};
			}

			@Override
			protected Nest make_Nest(final CustomColor color) {
				return new Nest() {

					@Override
					protected CustomColor make_getColor() {
						return color;
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
				System.out.println(elem+" put at ("+xValue+";"+yValue+")");
				return new Position(xValue,yValue);
			}
		}
		fireChangeEvent();
		return null;
	}
	
	public Element[][] getGrid(){
		return grid;
	}

	@Override
	public void addRobot(
			speadl.environment.Environment.RobotGrid.Component robotGrid) {
		
		System.err.println("Test " + robotGrid.robotCore().getColor());
		
		CustomColor robotColor = robotGrid.robotCore().getColor();
		
		if(robotColor == CustomColor.Red){
			robotGrid.robotCore().setPosition(putInGrid(Element.RED_AGENT));
		}else if(robotColor == CustomColor.Blue){
			robotGrid.robotCore().setPosition(putInGrid(Element.BLUE_AGENT));
		}else if(robotColor == CustomColor.Green){
			robotGrid.robotCore().setPosition(putInGrid(Element.GREEN_AGENT));
		}else{
			robotGrid.robotCore().setPosition(putInGrid(Element.AGENT));
		}
		
		robotsMap.put(robotGrid.robotCore().getPosition(),robotGrid);
	}

	@Override
	public void updatePosition(Position oldPosition, Position newPosition) {
		System.out.println("Déplacement de "+oldPosition+" vers "+newPosition );
		grid[newPosition.getX()][newPosition.getY()] = grid[oldPosition.getX()][oldPosition.getY()];
		grid[oldPosition.getX()][oldPosition.getY()] = null;
		fireChangeEvent();
	}

	@Override
	public Element removeBox(Position position) {
		Element box = grid[position.getX()][position.getY()];
		grid[position.getX()][position.getY()] = null;
		fireChangeEvent();
		return box;
	}

	@Override
	public Element[][] getPartialGrid(Position pos) {

		int posX,posY;
		
		Element[][] partialGrid = new Element[3][3];
			
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				posX = pos.getX() + i;
				posY = pos.getY() + j;
				
				//Si la nouvelle position est hors de la grille
				if(posX < 0 ||  posY < 0 || posX >= grid.length || posY >= grid[0].length){
					partialGrid[i+1][j+1] = Element.OUTLINE;
				}else{
					partialGrid[i+1][j+1] = grid[posX][posY];
				}				
			}
		}
		return partialGrid;
	}

	public void removeRobot(Position position) {
		grid[position.getX()][position.getY()]=null;
		robotsMap.remove(position);
	}

	@Override
	public Map<Position, speadl.environment.Environment.RobotGrid.Component> getRobotsMap() {
		return robotsMap;
	}
}
