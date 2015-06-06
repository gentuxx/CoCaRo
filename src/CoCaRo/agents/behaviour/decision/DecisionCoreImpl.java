package CoCaRo.agents.behaviour.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.behaviour.decision.interfaces.IDecisionMaker;
import speadl.agents.AgentDecision.DecisionCore;

public class DecisionCoreImpl extends DecisionCore{

	protected class NoPositionException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8701812250832572509L;
		
	}
	
	protected Position findPathToPosition(Position currentPosition, Position targetPosition) throws NoPositionException{
		Element[][] partialGrid = requires().perception().getPartialGrid();
		Position positionToGo = null;
		
		List<Position> positions = new ArrayList<>();
		List<Position> orientedPositions = new ArrayList<>();
		
		int xDiff = currentPosition.getX()-targetPosition.getX();
		int yDiff = currentPosition.getY()-targetPosition.getY();
		
		if((xDiff==0 && (yDiff==1 || yDiff==-1)) || (yDiff==0 && (xDiff==1 || xDiff==-1))){
			System.out.println("Target Acquired!!!");
			return null;
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
			orientedPositions.add(Position.EAST);
		}
		
		if(yDiff<0 && positions.contains(Position.SOUTH)){
			orientedPositions.add(Position.SOUTH);
		}
		
		if(xDiff>0 && positions.contains(Position.WEST)){
			orientedPositions.add(Position.WEST);
		}
		
		if(yDiff>0 && positions.contains(Position.NORTH)) {
			orientedPositions.add(Position.NORTH);
		}
		
		if(!orientedPositions.isEmpty()) {
			positionToGo = randomPosition(orientedPositions);
			
			return new Position(positionToGo.getX()+currentPosition.getX(),
					positionToGo.getY()+currentPosition.getY());
		}
		else {
			Position result =  randomPosition(positions);
			
			if(result == null) {
				throw new NoPositionException();
			}
			else {
				return result;
			}
		}
	}
	
	protected Position randomPosition(List<Position> positions) {
		if(!positions.isEmpty()) {
			Random rand = new Random();
			int index = rand.nextInt(positions.size());
			return positions.get(index);
		}
		
		return null;
	}
	
	@Override
	protected IDecisionMaker make_decisionMaker() {
		// TODO Auto-generated method stub
		return null;
	}

}
