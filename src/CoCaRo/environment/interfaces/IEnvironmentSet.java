package CoCaRo.environment.interfaces;

import CoCaRo.Element;
import CoCaRo.Position;

public interface IEnvironmentSet {

	public void updatePosition(Position oldPosition, Position newPosition);

	public Element removeBox(Position position);
	
	public void removeRobot(Position position);
}
