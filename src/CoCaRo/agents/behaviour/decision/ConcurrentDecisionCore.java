package CoCaRo.agents.behaviour.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.behaviour.decision.DecisionCoreImpl.NoPositionException;
import CoCaRo.agents.behaviour.decision.interfaces.IDecisionMaker;

public class ConcurrentDecisionCore extends DecisionCoreImpl {

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
					System.out.println("Plus d'énergie...Adieu!");
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
				
				//Si le robot a déjà une boite
				if (requires().core().hasBox()){
					System.out.println("J'ai une boite!!!!!!!!!!!!!!");
					
					//On récupère la position du nid
					Position nestPosition = requires().core().getEnvironmentGet().
							getNest(requires().core().getBoxColor());
					
					System.out.println("Nid repéré à "+nestPosition);
					try {
						positionToGo = findPathToPosition(currentPosition, nestPosition);
						if(positionToGo==null) {
							System.out.println("Boite ramenée au nid");
							requires().core().dropBox();
							return;
						}
					} catch (NoPositionException e) {
						return;
					}
				}
				else {
					positions = new ArrayList<Position>();

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
				}
				
				// Si le robot n'a pas de déplacement optimal,
				//alors il se déplace aléatoirement vers les positions possibles
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

				//Mise à jour de l'historique des positions
				if(oldPositions.size()==5) {
					oldPositions.remove(oldPositions.get(0));
				}
				
				oldPositions.add(positionToGo);
				
				// Aller à la position

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
