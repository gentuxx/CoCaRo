package CoCaRo.agents;

import CoCaRo.CustomColor;
import CoCaRo.Position;
import speadl.agents.RobotsEcosystem.Robot;

public class RobotImpl extends Robot{
	private String identifier;
	private CustomColor color;
	private Position position;
	
	public RobotImpl(String identifier, CustomColor color) {
		super();
		this.identifier = identifier;
		this.color = color;
	}
}
