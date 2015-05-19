package CoCaRo.agents.behaviour.decision.interfaces;

import speadl.agents.AgentDecision.DecisionCore;

public interface IAgentDecisionCreator {
	public DecisionCore createAgentDecisionCore(boolean cooperative);
}
