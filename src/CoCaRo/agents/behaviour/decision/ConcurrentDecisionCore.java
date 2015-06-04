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

			@Override
			public void interact() {
				System.out.println("Action!");
				
				
				Element[][] partialGrid = requires().perception().getPartialGrid();

				if(requires().core().getEnergy()==0) {
					//TODO SUICIDE
				}

				Position positionToGo = null;
				Position currentPosition = requires().core().getPosition();

				int selfPositionX = currentPosition.getX();
				int selfPositionY = currentPosition.getY();

				
				
				//Si le robot a déjà une boite
				if (requires().core().hasBox()){
					System.out.println("J'ai une boite!!!!!!!!!!!!!!");
					
					//On récupère la position du nid
					Position nestPosition = requires().core().getEnvironmentGet().
							getNest(requires().core().getBoxColor());
					
					if(nestPosition.equals(currentPosition)) {
						requires().core().dropBox();
					}
					else {
						int xDiff = currentPosition.getX()-nestPosition.getX();
						int yDiff = currentPosition.getY()-nestPosition.getY();
					
						List<Position> positions = new ArrayList<Position>();
						
						//Algorithme pour trouver le plus court chemin vers le nid
						
						//Loop over each NESO case
						for(int i=-1;i<1;i++) {
							if(partialGrid[i][i+1]==null) {
								positions.add(new Position(i,i+1));
							}
							
							if(partialGrid[i+1][i]==null) {
								positions.add(new Position(i+1,i));
							}
						}						
					}
					
					
					/*for(Nest.Component nest : nestsList.keySet()){
						
						//TODO Si la couleur du nid correspond à la boite
						if(true){
							//						if(nest == requires().core().getColorBox()){
							//							if(getGrid[nestsList.get(nest).getX()][nestsList.get(nest).getY()] == requires().core().getColorBox())	
							positionToGo = nestsList.get(nest);
						}

					}*/
				}
				else {

					List<Position> positions = new ArrayList<Position>();

					// regarder aux alentours et si Nord - Est - Sud - Ouest d'un pas, alors prioritaire

					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							Element e = partialGrid[i+1][j+1];

							//Si on est à la même position que le robot, on passe à la suivante
							if(i==0 && j==0) {
								continue;
							} 
							// Si la position est NESO
							else if(i == 0 || j==0) {
								
								//S'il y a une boite, on la récupère
								if(e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX) {
									requires().actions().takeBox(requires().core(), new Position(selfPositionX + i, selfPositionY + j));
									return;
								}
								//S'il n'y a pas de boite, alors on peut se deplacer sur cette case
								else if(e==null) {
									positions.add(new Position(selfPositionX + i, selfPositionY + j));							
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
								
								//Si on a trouvé une position où aller, on sort des boucles
								if(positionToGo!=null) {
									break;
								}
							}
						}
						
						//Si on a trouvé une position où aller, on sort des boucles
						if(positionToGo!=null) {
							break;
						}
					}

					// Si le robot n'a pas déjà repéré de boite, 
					//alors il se déplace aléatoirement vers les positions possibles
					if(positionToGo == null){
						if(positions.isEmpty()) {
							return;
						}
						else {
							Random rand = new Random();
							int index = rand.nextInt(positions.size());
							positionToGo = positions.get(index);
							System.out.println(positionToGo);
							System.out.println("=");
						}
					}

				}

				// Aller à la position

				if(positionToGo.getX() < selfPositionX){
					System.out.println("left");
					requires().actions().goLeft(requires().core());
				}else if(positionToGo.getX() > selfPositionX){
					System.out.println("right");
					requires().actions().goRight(requires().core());
				}else if(positionToGo.getY() < selfPositionY){
					System.out.println("up");
					requires().actions().goUp(requires().core());
				}else if(positionToGo.getY() > selfPositionY){
					System.out.println("down");
					requires().actions().goDown(requires().core());
				}
				else {
					System.out.println("J'ai fait de la merde!!!!!!!!!!");
				}
			}
		};
	}
}
