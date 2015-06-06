package CoCaRo.agents.behaviour.decision.interfaces;

import java.util.HashMap;
import java.util.Map;

import CoCaRo.Element;
import CoCaRo.Position;

public interface ICooperativeModule {
	
	public static Map<Position,Element> boxMap = new HashMap<>();
	
	public void addBox(Element element,Position pos);
	
	public void removeBox(Position pos);
	
	public Position getRandomElement(Element element);
}
