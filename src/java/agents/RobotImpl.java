package java.agents;

import java.CustomColor;

import speadl.agents.RobotsEcosystem.Robot;

public class RobotImpl extends Robot{
	private String identifier;
	private CustomColor color;
	
	public RobotImpl(String identifier, CustomColor color) {
		super();
		this.identifier = identifier;
		this.color = color;
	}
}
