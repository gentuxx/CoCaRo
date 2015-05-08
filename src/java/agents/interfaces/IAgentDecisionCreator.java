package java.agents.interfaces;

import speadl.agents.AgentDecision.DecisionCore;

public interface IAgentDecisionCreator {
	public DecisionCore createAgentDecisionCore(boolean cooperative);
}
