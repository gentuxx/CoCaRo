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

	@Override
	protected IRobotCore make_robotCore() {
		return new IRobotCore() {

			@Override
			public CustomColor getColor() {
				return color;
			}

			@Override
			public String getIdentifier() {
				return identifier;
			}

			@Override
			public Position getPosition() {
				return position;
			}

			@Override
			public void setPosition(Position newPosition) {
				position = newPosition;
			}
		};
	}
}
