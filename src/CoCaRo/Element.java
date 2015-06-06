package CoCaRo;

public enum Element {
	AGENT, BLUE_AGENT, RED_AGENT, GREEN_AGENT, AGENT_WITH_BOX, RED_BOX, GREEN_BOX, BLUE_BOX, 
	RED_NEST, GREEN_NEST, BLUE_NEST, OUTLINE;
	
	public String toString() {
		switch(this) {
		case AGENT : 
		case AGENT_WITH_BOX:
			return "Robot";
		case RED_BOX:
			return "Red Box";
		case GREEN_BOX:
			return "Green Box";
		case BLUE_BOX:
			return "Blue Box";
		case RED_NEST:
			return "Red Nest";
		case GREEN_NEST:
			return "Green Nest";
		case BLUE_NEST:
			return "Blue Nest";
		case OUTLINE:
			return "Outline";
		default:
			return "";
		}
	}
	
	public CustomColor getColor() {
		switch(this) {
		case RED_NEST:
		case RED_BOX:
			return CustomColor.Red;
		case GREEN_NEST:
		case GREEN_BOX:
			return CustomColor.Green;
		case BLUE_NEST:
		case BLUE_BOX:
			return CustomColor.Blue;
		default:
			return null;
		}
	}
	
	public static Element getBox(CustomColor color) {
		if(color!=null) {
			if(color.equals(CustomColor.Red)) {
				return RED_BOX;
			}
			else if(color.equals(CustomColor.Green)) {
				
			}
			else if(color.equals(CustomColor.Blue)) {
				
			}
		}
		
		return null;
	}
	
}
