package CoCaRo.agents;

import CoCaRo.CustomColor;
import CoCaRo.Position;

public interface IRobotCore {
	public CustomColor getColor();
	public String getIdentifier();
	public Position getPosition();
	public void setPosition(Position position);
}
