package CoCaRo;

public enum CustomColor {
	Red, Green, Blue;
	
	public CustomColor next() {
		if(this == Red) {
			return Green;
		}
		else if(this == Green) {
			return Blue;
		}
		else {
			return Red;
		}
	}
	
	@Override
	public String toString() {
		if(this == Red) {
			return "Red";
		}
		else if(this == Green) {
			return "Green";
		}
		else {
			return "Blue";
		}
	}
}
