package speadl.agents;

import java.agents.IAgentAction;
import java.agents.IAgentDecision;
import java.agents.IAgentDecisionCreator;
import java.agents.IAgentPerception;
import speadl.agents.AgentAction;
import speadl.agents.AgentDecision;
import speadl.agents.AgentPerception;
import speadl.environment.Grid;

@SuppressWarnings("all")
public abstract class AgentBehaviour {
  public interface Requires {
  }
  
  public interface Component extends AgentBehaviour.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IAgentDecisionCreator decisionCreator();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AgentPerception.Component perception();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AgentAction.Component actions();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AgentDecision.Component decision();
  }
  
  public static class ComponentImpl implements AgentBehaviour.Component, AgentBehaviour.Parts {
    private final AgentBehaviour.Requires bridge;
    
    private final AgentBehaviour implementation;
    
    public void start() {
      assert this.perception != null: "This is a bug.";
      ((AgentPerception.ComponentImpl) this.perception).start();
      assert this.actions != null: "This is a bug.";
      ((AgentAction.ComponentImpl) this.actions).start();
      assert this.decision != null: "This is a bug.";
      ((AgentDecision.ComponentImpl) this.decision).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_perception() {
      assert this.perception == null: "This is a bug.";
      assert this.implem_perception == null: "This is a bug.";
      this.implem_perception = this.implementation.make_perception();
      if (this.implem_perception == null) {
      	throw new RuntimeException("make_perception() in speadl.agents.AgentBehaviour should not return null.");
      }
      this.perception = this.implem_perception._newComponent(new BridgeImpl_perception(), false);
      
    }
    
    private void init_actions() {
      assert this.actions == null: "This is a bug.";
      assert this.implem_actions == null: "This is a bug.";
      this.implem_actions = this.implementation.make_actions();
      if (this.implem_actions == null) {
      	throw new RuntimeException("make_actions() in speadl.agents.AgentBehaviour should not return null.");
      }
      this.actions = this.implem_actions._newComponent(new BridgeImpl_actions(), false);
      
    }
    
    private void init_decision() {
      assert this.decision == null: "This is a bug.";
      assert this.implem_decision == null: "This is a bug.";
      this.implem_decision = this.implementation.make_decision();
      if (this.implem_decision == null) {
      	throw new RuntimeException("make_decision() in speadl.agents.AgentBehaviour should not return null.");
      }
      this.decision = this.implem_decision._newComponent(new BridgeImpl_decision(), false);
      
    }
    
    protected void initParts() {
      init_perception();
      init_actions();
      init_decision();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final AgentBehaviour implem, final AgentBehaviour.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    public IAgentDecisionCreator decisionCreator() {
      return this.decision().creator();
    }
    
    private AgentPerception.Component perception;
    
    private AgentPerception implem_perception;
    
    private final class BridgeImpl_perception implements AgentPerception.Requires {
    }
    
    public final AgentPerception.Component perception() {
      return this.perception;
    }
    
    private AgentAction.Component actions;
    
    private AgentAction implem_actions;
    
    private final class BridgeImpl_actions implements AgentAction.Requires {
    }
    
    public final AgentAction.Component actions() {
      return this.actions;
    }
    
    private AgentDecision.Component decision;
    
    private AgentDecision implem_decision;
    
    private final class BridgeImpl_decision implements AgentDecision.Requires {
    }
    
    public final AgentDecision.Component decision() {
      return this.decision;
    }
  }
  
  public static class AgentBehaviourPDA {
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Grid gridB();
    }
    
    public interface Component extends AgentBehaviour.AgentBehaviourPDA.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public IAgentDecision decisions();
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AgentPerception.PerceptionCore.Component aPerc();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AgentAction.ActionCore.Component aAction();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AgentDecision.DecisionCore.Component aDecision();
    }
    
    public static class ComponentImpl implements AgentBehaviour.AgentBehaviourPDA.Component, AgentBehaviour.AgentBehaviourPDA.Parts {
      private final AgentBehaviour.AgentBehaviourPDA.Requires bridge;
      
      private final AgentBehaviour.AgentBehaviourPDA implementation;
      
      public void start() {
        assert this.aPerc != null: "This is a bug.";
        ((AgentPerception.PerceptionCore.ComponentImpl) this.aPerc).start();
        assert this.aAction != null: "This is a bug.";
        ((AgentAction.ActionCore.ComponentImpl) this.aAction).start();
        assert this.aDecision != null: "This is a bug.";
        ((AgentDecision.DecisionCore.ComponentImpl) this.aDecision).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_aPerc() {
        assert this.aPerc == null: "This is a bug.";
        assert this.implementation.use_aPerc != null: "This is a bug.";
        this.aPerc = this.implementation.use_aPerc._newComponent(new BridgeImpl_perception_aPerc(), false);
        
      }
      
      private void init_aAction() {
        assert this.aAction == null: "This is a bug.";
        assert this.implementation.use_aAction != null: "This is a bug.";
        this.aAction = this.implementation.use_aAction._newComponent(new BridgeImpl_actions_aAction(), false);
        
      }
      
      private void init_aDecision() {
        assert this.aDecision == null: "This is a bug.";
        assert this.implementation.use_aDecision != null: "This is a bug.";
        this.aDecision = this.implementation.use_aDecision._newComponent(new BridgeImpl_decision_aDecision(), false);
        
      }
      
      protected void initParts() {
        init_aPerc();
        init_aAction();
        init_aDecision();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final AgentBehaviour.AgentBehaviourPDA implem, final AgentBehaviour.AgentBehaviourPDA.Requires b, final boolean doInits) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null: "This is a bug.";
        implem.selfComponent = this;
        
        // prevent them to be called twice if we are in
        // a specialized component: only the last of the
        // hierarchy will call them after everything is initialised
        if (doInits) {
        	initParts();
        	initProvidedPorts();
        }
      }
      
      public IAgentDecision decisions() {
        return this.aDecision().decisions();
      }
      
      private AgentPerception.PerceptionCore.Component aPerc;
      
      private final class BridgeImpl_perception_aPerc implements AgentPerception.PerceptionCore.Requires {
        public final Grid gridP() {
          return AgentBehaviour.AgentBehaviourPDA.ComponentImpl.this.bridge.gridB();
        }
      }
      
      public final AgentPerception.PerceptionCore.Component aPerc() {
        return this.aPerc;
      }
      
      private AgentAction.ActionCore.Component aAction;
      
      private final class BridgeImpl_actions_aAction implements AgentAction.ActionCore.Requires {
      }
      
      public final AgentAction.ActionCore.Component aAction() {
        return this.aAction;
      }
      
      private AgentDecision.DecisionCore.Component aDecision;
      
      private final class BridgeImpl_decision_aDecision implements AgentDecision.DecisionCore.Requires {
        public final IAgentPerception perception() {
          return AgentBehaviour.AgentBehaviourPDA.ComponentImpl.this.aPerc().perception();
        }
        
        public final IAgentAction actions() {
          return AgentBehaviour.AgentBehaviourPDA.ComponentImpl.this.aAction().actions();
        }
      }
      
      public final AgentDecision.DecisionCore.Component aDecision() {
        return this.aDecision;
      }
    }
    
    /**
     * Used to check that two components are not created from the same implementation,
     * that the component has been started to call requires(), provides() and parts()
     * and that the component is not started by hand.
     * 
     */
    private boolean init = false;;
    
    /**
     * Used to check that the component is not started by hand.
     * 
     */
    private boolean started = false;;
    
    private AgentBehaviour.AgentBehaviourPDA.ComponentImpl selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called automatically after the component has been instantiated.
     * 
     */
    protected void start() {
      if (!this.init || this.started) {
      	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
      }
    }
    
    /**
     * This can be called by the implementation to access the provided ports.
     * 
     */
    protected AgentBehaviour.AgentBehaviourPDA.Provides provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected AgentBehaviour.AgentBehaviourPDA.Requires requires() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
      }
      return this.selfComponent.bridge;
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected AgentBehaviour.AgentBehaviourPDA.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private AgentPerception.PerceptionCore use_aPerc;
    
    private AgentAction.ActionCore use_aAction;
    
    private AgentDecision.DecisionCore use_aDecision;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized AgentBehaviour.AgentBehaviourPDA.Component _newComponent(final AgentBehaviour.AgentBehaviourPDA.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of AgentBehaviourPDA has already been used to create a component, use another one.");
      }
      this.init = true;
      AgentBehaviour.AgentBehaviourPDA.ComponentImpl  _comp = new AgentBehaviour.AgentBehaviourPDA.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private AgentBehaviour.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected AgentBehaviour.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected AgentBehaviour.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected AgentBehaviour.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private AgentBehaviour.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected AgentBehaviour.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected AgentBehaviour.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected AgentBehaviour.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AgentPerception make_perception();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AgentAction make_actions();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AgentDecision make_decision();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized AgentBehaviour.Component _newComponent(final AgentBehaviour.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of AgentBehaviour has already been used to create a component, use another one.");
    }
    this.init = true;
    AgentBehaviour.ComponentImpl  _comp = new AgentBehaviour.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected AgentBehaviour.AgentBehaviourPDA make_AgentBehaviourPDA() {
    return new AgentBehaviour.AgentBehaviourPDA();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public AgentBehaviour.AgentBehaviourPDA _createImplementationOfAgentBehaviourPDA() {
    AgentBehaviour.AgentBehaviourPDA implem = make_AgentBehaviourPDA();
    if (implem == null) {
    	throw new RuntimeException("make_AgentBehaviourPDA() in speadl.agents.AgentBehaviour should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_perception != null: "This is a bug.";
    assert implem.use_aPerc == null: "This is a bug.";
    implem.use_aPerc = this.selfComponent.implem_perception._createImplementationOfPerceptionCore();
    assert this.selfComponent.implem_actions != null: "This is a bug.";
    assert implem.use_aAction == null: "This is a bug.";
    implem.use_aAction = this.selfComponent.implem_actions._createImplementationOfActionCore();
    assert this.selfComponent.implem_decision != null: "This is a bug.";
    assert implem.use_aDecision == null: "This is a bug.";
    implem.use_aDecision = this.selfComponent.implem_decision._createImplementationOfDecisionCore();
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public AgentBehaviour.Component newComponent() {
    return this._newComponent(new AgentBehaviour.Requires() {}, true);
  }
}
