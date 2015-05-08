package speadl.agents;

import java.agents.interfaces.IAgentAction;
import java.agents.interfaces.IAgentDecisionCreator;
import java.agents.interfaces.IAgentPerception;
import java.environment.interfaces.IEnvironment;

@SuppressWarnings("all")
public abstract class AgentDecision {
  public interface Requires {
  }
  
  public interface Component extends AgentDecision.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IAgentDecisionCreator creator();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements AgentDecision.Component, AgentDecision.Parts {
    private final AgentDecision.Requires bridge;
    
    private final AgentDecision implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_creator() {
      assert this.creator == null: "This is a bug.";
      this.creator = this.implementation.make_creator();
      if (this.creator == null) {
      	throw new RuntimeException("make_creator() in speadl.agents.AgentDecision should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_creator();
    }
    
    public ComponentImpl(final AgentDecision implem, final AgentDecision.Requires b, final boolean doInits) {
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
    
    private IAgentDecisionCreator creator;
    
    public IAgentDecisionCreator creator() {
      return this.creator;
    }
  }
  
  public static class DecisionCore {
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public IAgentPerception perception();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public IAgentAction actions();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public IEnvironment env();
    }
    
    public interface Component extends AgentDecision.DecisionCore.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
    }
    
    public static class ComponentImpl implements AgentDecision.DecisionCore.Component, AgentDecision.DecisionCore.Parts {
      private final AgentDecision.DecisionCore.Requires bridge;
      
      private final AgentDecision.DecisionCore implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final AgentDecision.DecisionCore implem, final AgentDecision.DecisionCore.Requires b, final boolean doInits) {
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
    
    private AgentDecision.DecisionCore.ComponentImpl selfComponent;
    
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
    protected AgentDecision.DecisionCore.Provides provides() {
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
    protected AgentDecision.DecisionCore.Requires requires() {
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
    protected AgentDecision.DecisionCore.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized AgentDecision.DecisionCore.Component _newComponent(final AgentDecision.DecisionCore.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of DecisionCore has already been used to create a component, use another one.");
      }
      this.init = true;
      AgentDecision.DecisionCore.ComponentImpl  _comp = new AgentDecision.DecisionCore.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private AgentDecision.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected AgentDecision.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected AgentDecision.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected AgentDecision.Parts eco_parts() {
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
  
  private AgentDecision.ComponentImpl selfComponent;
  
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
  protected AgentDecision.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IAgentDecisionCreator make_creator();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected AgentDecision.Requires requires() {
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
  protected AgentDecision.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized AgentDecision.Component _newComponent(final AgentDecision.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of AgentDecision has already been used to create a component, use another one.");
    }
    this.init = true;
    AgentDecision.ComponentImpl  _comp = new AgentDecision.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected AgentDecision.DecisionCore make_DecisionCore() {
    return new AgentDecision.DecisionCore();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public AgentDecision.DecisionCore _createImplementationOfDecisionCore() {
    AgentDecision.DecisionCore implem = make_DecisionCore();
    if (implem == null) {
    	throw new RuntimeException("make_DecisionCore() in speadl.agents.AgentDecision should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public AgentDecision.Component newComponent() {
    return this._newComponent(new AgentDecision.Requires() {}, true);
  }
}
