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
		
	public static CustomColor randomColor() {
        return values()[(int) (Math.random() * values().length)];
    }
}
