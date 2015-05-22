package CoCaRo.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import CoCaRo.CustomColor;
import CoCaRo.environment.interfaces.IBoxGenerator;
import CoCaRo.environment.interfaces.IEnvironment;
import CoCaRo.environment.interfaces.INestCreator;
import speadl.environment.BoxEnv;
import speadl.environment.BoxEnv.Box;
import speadl.environment.Grid;
import speadl.environment.NestEnv;
import speadl.environment.NestEnv.Nest;

public class GridImpl extends Grid implements IEnvironment{

	private List<Nest.Component> nestList;
	private List<Box.Component> boxList;
	private Element[][] grid = new Element[20][20];
	
	public GridImpl() {
		nestList = new ArrayList<NestEnv.Nest.Component>();
		boxList = new ArrayList<Box.Component>();
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
			protected IBoxGenerator make_createBox() {
				System.out.println("make IBoxGenerator");
				return new IBoxGenerator() {
					
					@Override
					public void generateBox(CustomColor color) {
						boxList.add(newBox(color));
						
						Random rand = new Random();
						boolean findPosition = false;
						
						while(!findPosition){
							
							int value1 = rand.nextInt(20);
							int value2 = rand.nextInt(20);
							
							if(grid[value1][value2].equals(Element.EMPTY)){
								if(color.equals(CustomColor.Blue)){
									grid[value1][value2] = Element.BOX_BLUE;
								}else if(color.equals(CustomColor.Green)){
									grid[value1][value2] = Element.BOX_GREEN;
								}else if(color.equals(CustomColor.Red)){
									grid[value1][value2] = Element.BOX_RED;
								}
								findPosition = true;
								
								System.out.println("generate box  Color : " + color +
										" X : " + value1 + " Y : " + value2);
							}
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
			protected INestCreator make_createNests() {
				System.out.println("make INestGenerator");
				return new INestCreator() {
					
					@Override
					public void createAllNests() {
						nestList.add(newNest(CustomColor.Red));
						Random rand = new Random();
						boolean findPosition = false;
						int value1;
						int value2;
						
						while(!findPosition){
							
							value1 = rand.nextInt(20);
							value2 = rand.nextInt(20);
							
							if(grid[value1][value2].equals(Element.EMPTY)){
								grid[value1][value2] = Element.NEST_RED;
								findPosition = true;
							}
						}
						
						nestList.add(newNest(CustomColor.Green));
						findPosition = false;
						while(!findPosition){
							
							value1 = rand.nextInt(20);
							value2 = rand.nextInt(20);
							
							if(grid[value1][value2].equals(Element.EMPTY)){
								grid[value1][value2] = Element.NEST_GREEN;
								findPosition = true;
							}
						}
						
						nestList.add(newNest(CustomColor.Blue));
						findPosition = false;
						while(!findPosition){
							
							value1 = rand.nextInt(20);
							value2 = rand.nextInt(20);
							
							if(grid[value1][value2].equals(Element.EMPTY)){
								grid[value1][value2] = Element.NEST_BLUE;
								findPosition = true;
							}
						}
					}
				};
			}
		};
	}
	
	public enum Element {
		EMPTY, AGENT, AGENT_WITH_BOX, BOX_RED, BOX_GREEN, BOX_BLUE,
		NEST_RED, NEST_GREEN, NEST_BLUE
	}
	
	public Element[][] getGrid(){
		return grid;
	}
}
