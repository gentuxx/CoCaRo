package CoCaRo.agents.behaviour.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import speadl.agents.AgentDecision.DecisionCore;
import speadl.environment.NestEnv.Nest;
import CoCaRo.Position;
import CoCaRo.Element;
import CoCaRo.agents.behaviour.decision.interfaces.IDecisionMaker;

public class ConcurrentDecisionCore extends DecisionCore {

	@Override
	public IDecisionMaker make_decisionMaker() {
		System.out.println("make DecisionMaker");
		return new IDecisionMaker() {
			
			@Override
			public void interact() {
				Element[][] partialGrid = requires().perception().getPartialGrid();

				if(requires().core().getEnergy()==0) {
					//TODO SUICIDE
				}
				
				Position positionToGo = null;
				Position myPosition = requires().core().getPosition();
						
				int selfPositionX = myPosition.getX();
				int selfPositionY = myPosition.getY();
				
				// si j'ai une boite
				if (! requires().core().hasBox()) {
					
					List<Position> positions = new ArrayList<Position>();
								
					// regarder aux alentours et si Nord - Est - Sud - Ouest d'un pas, alors prioritaire
					
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							Element e = partialGrid[i+1][j+1];
							
							// Si la position est NESO et qu'il n'y a pas de boite, alors on peut se deplacer sur cette case
							if ((i == -1 && j == -1 || (i == -1 && j == 1) || (i == 1 && j == -1) || (i == 1 && j == 1))
									&& e == null) {
								positions.add(new Position(selfPositionX + i, selfPositionY + j));
								// Si la position est NESO et qu'il y a une boite, alors on prend la boite
							} else if ( (i == -1 && j == -1 || (i == -1 && j == 1) || (i == 1 && j == -1) || (i == 1 && j == 1) )
									&& (e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX)) {
								
								requires().actions().takeBox(requires().core(), new Position(selfPositionX + i, selfPositionY + j));
								
								// Sortir de la fonction
								return;
								// Si il y a une boite dans les angles
							} else if (e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX) {
								positionToGo = new Position(selfPositionX + i, selfPositionY + j);
							}
						}
					}
					
					// S'il n'a pas de position où aller
					if(positionToGo != null){
						Random rand = new Random();
						positionToGo = positions.get(rand.nextInt(positions.size()));
					}
					
				}else{
					// Si le robot a une boite alors retourner au nid
					Map<Nest.Component, Position> nestsList = requires().core().getEnvironmentGet().getNestList();
					
					for(Nest.Component nest : nestsList.keySet()){
						//TODO Si la couleur du nid correspond à la boite
						if(true){
//						if(nest == requires().core().getColorBox()){
							
							positionToGo = nestsList.get(nest);
						}
						
					}
				}
				
				// Aller à la position
				
				if(positionToGo.getX() < selfPositionX){
					requires().actions().goUp(requires().core());
				}else if(positionToGo.getX() > selfPositionX){
					requires().actions().goDown(requires().core());
				}else if(positionToGo.getY() < selfPositionY){
					requires().actions().goLeft(requires().core());
				}else if(positionToGo.getY() > selfPositionY){
					requires().actions().goRight(requires().core());
				}
			}
		};
	}
}
