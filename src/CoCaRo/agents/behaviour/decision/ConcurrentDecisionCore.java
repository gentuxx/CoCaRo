package CoCaRo.agents.behaviour.decision;

import java.util.ArrayList;
import java.util.List;

import speadl.agents.AgentDecision.DecisionCore;
import CoCaRo.Position;
import CoCaRo.environment.GridImpl.Element;

public class ConcurrentDecisionCore extends DecisionCore {

	public void decide(){

		//Random rand = new Random();

		// Regarde aux alentours s'il n'y a pas de boîte(s)
		Element[][] partialGrid = requires().perception().getPartialGrid();

		// TODO : Voir si j'ai une 
		if (! requires().core().hasBox()) {

			Position myPosition = requires().core().getPosition();
			
			int X = myPosition.getX();
			int Y = myPosition.getY();
			
			List<Position> positions = new ArrayList<Position>();
			
			// regarder aux alentours et si Nord - Est - Sud - Ouest d'un pas, alors prioritaire
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					Element e = partialGrid[i+1][j+1];
					
					if (e == null) {
						positions.add(new Position(myPosition.getX() + i, myPosition.getY() + j));
					} else if ( (i == -1 && j == -1 || (i == -1 && j == 1) || (i == 1 && j == -1) || (i == 1 && j == 1) )
							&& (e == Element.BLUE_BOX || e == Element.GREEN_BOX || e == Element.RED_BOX)) {
						
						requires().actions().takeBox(requires().core(), new Position(myPosition.getX() + i, myPosition.getY() + j);
						return;
					}
				}
			}
		}




		/*requires().env().getNestList();
		requires().env().getBoxList();*/



		requires().perception().perceive();
	}

}
