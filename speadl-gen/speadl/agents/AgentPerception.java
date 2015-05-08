package speadl.agents;

import java.agents.interfaces.IAgentPerception;
import java.environment.interfaces.IEnvironment;

@SuppressWarnings("all")
public abstract class AgentPerception {
  public interface Requires {
  }
  
  public interface Component extends AgentPerception.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements AgentPerception.Component, AgentPerception.Parts {
    private final AgentPerception.Requires bridge;
    
    private final AgentPerception implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final AgentPerception implem, final AgentPerception.Requires b, final boolean doInits) {
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
  
  public static abstract class PerceptionCore {
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public IEnvironment gridP();
    }
    
    public interface Component extends AgentPerception.PerceptionCore.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public IAgentPerception perception();
    }
    
    public interface Parts {
    }
    
    public static class ComponentImpl implements AgentPerception.PerceptionCore.Component, AgentPerception.PerceptionCore.Parts {
      private final AgentPerception.PerceptionCore.Requires bridge;
      
      private final AgentPerception.PerceptionCore implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_perception() {
        assert this.perception == null: "This is a bug.";
        this.perception = this.implementation.make_perception();
        if (this.perception == null) {
        	throw new RuntimeException("make_perception() in speadl.agents.AgentPerception$PerceptionCore should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_perception();
      }
      
      public ComponentImpl(final AgentPerception.PerceptionCore implem, final AgentPerception.PerceptionCore.Requires b, final boolean doInits) {
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
      
      private IAgentPerception perception;
      
      public IAgentPerception perception() {
        return this.perception;
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
    
    private AgentPerception.PerceptionCore.ComponentImpl selfComponent;
    
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
    protected AgentPerception.PerceptionCore.Provides provides() {
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
    protected abstract IAgentPerception make_perception();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected AgentPerception.PerceptionCore.Requires requires() {
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
    protected AgentPerception.PerceptionCore.Parts parts() {
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
    public synchronized AgentPerception.PerceptionCore.Component _newComponent(final AgentPerception.PerceptionCore.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PerceptionCore has already been used to create a component, use another one.");
      }
      this.init = true;
      AgentPerception.PerceptionCore.ComponentImpl  _comp = new AgentPerception.PerceptionCore.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private AgentPerception.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected AgentPerception.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected AgentPerception.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected AgentPerception.Parts eco_parts() {
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
  
  private AgentPerception.ComponentImpl selfComponent;
  
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
  protected AgentPerception.Provides provides() {
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
  protected AgentPerception.Requires requires() {
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
  protected AgentPerception.Parts parts() {
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
  public synchronized AgentPerception.Component _newComponent(final AgentPerception.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of AgentPerception has already been used to create a component, use another one.");
    }
    this.init = true;
    AgentPerception.ComponentImpl  _comp = new AgentPerception.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract AgentPerception.PerceptionCore make_PerceptionCore();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public AgentPerception.PerceptionCore _createImplementationOfPerceptionCore() {
    AgentPerception.PerceptionCore implem = make_PerceptionCore();
    if (implem == null) {
    	throw new RuntimeException("make_PerceptionCore() in speadl.agents.AgentPerception should not return null.");
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
  public AgentPerception.Component newComponent() {
    return this._newComponent(new AgentPerception.Requires() {}, true);
  }
}
