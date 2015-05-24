package CoCaRo.agents;

import CoCaRo.CustomColor;
import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.environment.interfaces.IEnvironmentGet;
import CoCaRo.environment.interfaces.IEnvironmentSet;

public interface IRobotCore {
	public CustomColor getColor();
	
	public String getIdentifier();
	
	public Position getPosition();
	
	public void setPosition(Position position);
	
	public IEnvironmentSet getEnvironmentSet();
	
	public IEnvironmentGet getEnvironmentGet();
	
	public void takeBox(Element box);
	
	public Element dropBox();

	boolean hasBox();
	
	public CustomColor getColorBox();
}
