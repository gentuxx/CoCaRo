package CoCaRo;

public enum Element {
	AGENT, AGENT_WITH_BOX, RED_BOX, GREEN_BOX, BLUE_BOX, 
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
}
