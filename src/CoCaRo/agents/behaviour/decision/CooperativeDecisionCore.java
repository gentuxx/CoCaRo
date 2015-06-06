package CoCaRo.agents.behaviour.decision;

import java.util.ArrayList;
import java.util.List;

import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.behaviour.decision.interfaces.IDecisionMaker;

public class CooperativeDecisionCore extends DecisionCoreImpl{
	
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
					System.out.println("Plus d'�nergie...Adieu!");
					try {
						requires().core().suicide();
					} catch (Throwable e) {
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
					
					try {
						positionToGo = findPathToPosition(currentPosition, nestPosition);
						if(positionToGo==null) {
							System.out.println("Boite ramen�e au nid");
							requires().core().dropBox();
							return;
						}
					} catch (NoPositionException e) {
						return;
					}
				}
				else {
					positions = new ArrayList<Position>();
					
					Element myColorBox = Element.getBox(requires().core().getColor());
					Position boxPosition = eco_provides().coopModule().getRandomElement(myColorBox);
					
					if(boxPosition!=null) {
						System.out.println("Je me dirige vers une boite d�couverte !!!");
						try {
							positionToGo = findPathToPosition(currentPosition, boxPosition);
							
							if(positionToGo==null) {
								eco_provides().coopModule().removeBox(boxPosition);
								requires().actions().takeBox(requires().core(), boxPosition);
								return;
							}
						}
						catch (NoPositionException e) {
							return;
						}
					}
					else {
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
									
									if(e!=null) {
										Position boxPos = new Position(selfPositionX + i, selfPositionY + j);
										//S'il y a une boite, on la r�cup�re
										if(e==myColorBox) {
											eco_provides().coopModule().removeBox(boxPosition);
											requires().actions().takeBox(requires().core(), boxPos);
											return;
										}
										else if(e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX) {
											System.out.println("J'ai d�tect� une bo�te ("+e+ ") en "+boxPos);
											eco_provides().coopModule().addBox(e, boxPos);
										}
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
								else if (e!=null) {
									if(e==myColorBox) {
									
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
									//Si la boite n'est pas de la m�me couleur
									else if(e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX) {
										Position boxPos = new Position(selfPositionX + i, selfPositionY + j);
										System.out.println("J'ai d�tect� une bo�te ("+e+ ") en "+boxPos);
										
										eco_provides().coopModule().addBox(e, new Position(selfPositionX + i, selfPositionY + j));
									}
								}
							}
							
							//Si on a trouv� une position o� aller, on sort des boucles
							if(positionToGo!=null) {
								break;
							}
						}
					}					
				}
				
				// Si le robot n'a pas de d�placement optimal,
				//alors il se d�place al�atoirement vers les positions possibles
				if(positionToGo == null){
					positionToGo = randomPosition(positions);
					if(positionToGo==null) {
						oldPositions.clear();
						return;
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
