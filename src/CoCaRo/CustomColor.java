package CoCaRo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
	
	private static final List<CustomColor> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	
	private static final int SIZE = VALUES.size();
	
	private static final Random RANDOM = new Random();
	
	public static CustomColor randomColor()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	  }
}
