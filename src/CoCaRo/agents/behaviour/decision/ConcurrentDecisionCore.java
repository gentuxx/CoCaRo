package CoCaRo.agents.behaviour.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import speadl.agents.AgentDecision.DecisionCore;
import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.behaviour.decision.interfaces.IDecisionMaker;

public class ConcurrentDecisionCore extends DecisionCore {

	@Override
	public IDecisionMaker make_decisionMaker() {
		System.out.println("make DecisionMaker");
		return new IDecisionMaker() {

			private List<Position> oldPositions = new ArrayList<>();
			
			@Override
			public void interact() {
				System.out.println("Action!");
				
				Element[][] partialGrid = requires().perception().getPartialGrid();

				if(requires().core().getEnergy()==0) {
					System.out.println("Va te pendre!!!!!!!");
					try {
						requires().core().suicide();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(-1);
					}
					return;
				}

				Position positionToGo = null;
				Position currentPosition = requires().core().getPosition();

				int selfPositionX = currentPosition.getX();
				int selfPositionY = currentPosition.getY();

				List<Position> positions = new ArrayList<Position>();
				
				//Si le robot a d�j� une boite
				if (requires().core().hasBox()){
					System.out.println("J'ai une boite!!!!!!!!!!!!!!");
					
					//On r�cup�re la position du nid
					Position nestPosition = requires().core().getEnvironmentGet().
							getNest(requires().core().getBoxColor());
					
					System.out.println("Nid rep�r� � "+nestPosition);
					
					int xDiff = currentPosition.getX()-nestPosition.getX();
					int yDiff = currentPosition.getY()-nestPosition.getY();
					
					if((xDiff==0 && (yDiff==1 || yDiff==-1)) || (yDiff==0 && (xDiff==1 || xDiff==-1))){
						System.out.println("Boite ramen�e au nid");
						requires().core().dropBox();
						return;
					}
					
					//Loop over each NESO case
					for(int i=-1;i<1;i++) {
						//If the Element is null, add it to the list
						if(partialGrid[i+1][i+2]==null) {
							positions.add(new Position(i,i+1));
						}
						
						if(partialGrid[i+2][i+1]==null) {
							positions.add(new Position(i+1,i));
						}
					}
					
					//Depending on xDiff and yDiff
					if(xDiff<0 && positions.contains(Position.EAST)) {
						positionToGo = Position.EAST;
					}
					else if(yDiff<0 && positions.contains(Position.SOUTH)){
						positionToGo = Position.SOUTH;
					}
					else if(xDiff>0 && positions.contains(Position.WEST)){
						positionToGo = Position.WEST;
					}
					else if(yDiff>0 && positions.contains(Position.NORTH)) {
						positionToGo = Position.NORTH;
					}
					
					if(positionToGo!=null) {
						positionToGo = new Position(positionToGo.getX()+currentPosition.getX(),
								positionToGo.getY()+currentPosition.getY());
					}
				}
				else {
					positions = new ArrayList<Position>();

					// regarder aux alentours et si Nord - Est - Sud - Ouest d'un pas, alors prioritaire

					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							Element e = partialGrid[i+1][j+1];

							//Si on est � la m�me position que le robot, on passe � la suivante
							if(i==0 && j==0) {
								continue;
							} 
							// Si la position est NESO
							else if(i == 0 || j==0) {
								
								//S'il y a une boite, on la r�cup�re
								if(e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX) {
									requires().actions().takeBox(requires().core(), new Position(selfPositionX + i, selfPositionY + j));
									return;
								}
								//S'il n'y a pas de boite, alors on peut se deplacer sur cette case
								else if(e==null) {
									Position newPos = new Position(selfPositionX + i, selfPositionY + j);
									if(!oldPositions.contains(newPos)) {
										positions.add(newPos);
									}
								}								
							}
							// Si il y a une boite dans les angles
							else if (e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX) {
								//Si l'une des deux cases accessibles par le robot pour se diriger vers ce coin est vide
								if(partialGrid[1][j+1]==null) {
									positionToGo = new Position(selfPositionX + i,selfPositionY);
								}
								else if(partialGrid[i+1][1]==null) {
									positionToGo = new Position(selfPositionX,selfPositionY+j);
								}
								
								//Si on a trouv� une position o� aller, on sort des boucles
								if(positionToGo!=null) {
									break;
								}
							}
						}
						
						//Si on a trouv� une position o� aller, on sort des boucles
						if(positionToGo!=null) {
							break;
						}
					}
				}
				
				// Si le robot n'a pas de d�placement optimal,
				//alors il se d�place al�atoirement vers les positions possibles
				if(positionToGo == null){
					if(positions.isEmpty()) {
						oldPositions.clear();
						return;
					}
					else {
						Random rand = new Random();
						int index = rand.nextInt(positions.size());
						positionToGo = positions.get(index);
					}
				}

				//Mise � jour de l'historique des positions
				if(oldPositions.size()==5) {
					oldPositions.remove(oldPositions.get(0));
				}
				
				oldPositions.add(positionToGo);
				
				// Aller � la position

				if(positionToGo.getX() < selfPositionX){
					requires().actions().goLeft(requires().core());
				}else if(positionToGo.getX() > selfPositionX){
					requires().actions().goRight(requires().core());
				}else if(positionToGo.getY() < selfPositionY){
					requires().actions().goUp(requires().core());
				}else if(positionToGo.getY() > selfPositionY){
					requires().actions().goDown(requires().core());
				}
				else {
					System.out.println("J'ai fait de la merde : "+currentPosition + " vers " + positionToGo);
					System.exit(-1);
				}
			}
		};
	}
}
