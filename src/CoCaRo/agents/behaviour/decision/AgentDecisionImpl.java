package CoCaRo.agents.behaviour.decision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import CoCaRo.Element;
import CoCaRo.Position;
import CoCaRo.agents.behaviour.decision.interfaces.ICooperativeModule;
import speadl.agents.AgentDecision;

public class AgentDecisionImpl extends AgentDecision implements ICooperativeModule{
	
	private static Map<Position,Element> boxMap = new HashMap<>();
	
	public AgentDecisionImpl() {
		
	}
	
	@Override
	public synchronized void addBox(Element element,Position pos) {
		boxMap.put(pos, element);
	}
	
	@Override
	public synchronized void removeBox(Position pos) {
		boxMap.remove(pos);
	}
	
	@Override
	public synchronized Position getRandomElement(Element element) {
		List<Position> positions = new ArrayList<>();
		
		for (Entry<Position, Element> entry : boxMap.entrySet()) {
			if(entry.getValue().equals(element)) {
				positions.add(entry.getKey());
			}
		}
		
		if(positions.isEmpty()) {
			return null;
		}
		else {
			Random rand = new Random();
			int index = rand.nextInt(positions.size());
			return positions.get(index);
		}
	}
	
	@Override
 	protected DecisionCore make_DecisionCore(boolean cooperative) {
		System.out.println("make DecisionCore");
		if(cooperative) {
			return new CooperativeDecisionCore();
		}
		else {
			return new ConcurrentDecisionCore();
		}
 	}

	@Override
	protected ICooperativeModule make_coopModule() {
		return this;
	}
}
