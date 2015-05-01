package speadl.agents;

import java.Color;
import speadl.agents.AgentBehaviourPDA;

@SuppressWarnings("all")
public abstract class RobotsEcosystem {
  public interface Requires {
  }
  
  public interface Component extends RobotsEcosystem.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements RobotsEcosystem.Component, RobotsEcosystem.Parts {
    private final RobotsEcosystem.Requires bridge;
    
    private final RobotsEcosystem implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final RobotsEcosystem implem, final RobotsEcosystem.Requires b, final boolean doInits) {
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
  
  public static abstract class Robot {
    public interface Requires {
    }
    
    public interface Component extends RobotsEcosystem.Robot.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AgentBehaviourPDA.Component behaviour();
    }
    
    public static class ComponentImpl implements RobotsEcosystem.Robot.Component, RobotsEcosystem.Robot.Parts {
      private final RobotsEcosystem.Robot.Requires bridge;
      
      private final RobotsEcosystem.Robot implementation;
      
      public void start() {
        assert this.behaviour != null: "This is a bug.";
        ((AgentBehaviourPDA.ComponentImpl) this.behaviour).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_behaviour() {
        assert this.behaviour == null: "This is a bug.";
        assert this.implem_behaviour == null: "This is a bug.";
        this.implem_behaviour = this.implementation.make_behaviour();
        if (this.implem_behaviour == null) {
        	throw new RuntimeException("make_behaviour() in speadl.agents.RobotsEcosystem$Robot should not return null.");
        }
        this.behaviour = this.implem_behaviour._newComponent(new BridgeImpl_behaviour(), false);
        
      }
      
      protected void initParts() {
        init_behaviour();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final RobotsEcosystem.Robot implem, final RobotsEcosystem.Robot.Requires b, final boolean doInits) {
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
      
      private AgentBehaviourPDA.Component behaviour;
      
      private AgentBehaviourPDA implem_behaviour;
      
      private final class BridgeImpl_behaviour implements AgentBehaviourPDA.Requires {
      }
      
      public final AgentBehaviourPDA.Component behaviour() {
        return this.behaviour;
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
    
    private RobotsEcosystem.Robot.ComponentImpl selfComponent;
    
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
    protected RobotsEcosystem.Robot.Provides provides() {
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
    protected RobotsEcosystem.Robot.Requires requires() {
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
    protected RobotsEcosystem.Robot.Parts parts() {
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
    protected abstract AgentBehaviourPDA make_behaviour();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized RobotsEcosystem.Robot.Component _newComponent(final RobotsEcosystem.Robot.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Robot has already been used to create a component, use another one.");
      }
      this.init = true;
      RobotsEcosystem.Robot.ComponentImpl  _comp = new RobotsEcosystem.Robot.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private RobotsEcosystem.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected RobotsEcosystem.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected RobotsEcosystem.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected RobotsEcosystem.Parts eco_parts() {
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
  
  private RobotsEcosystem.ComponentImpl selfComponent;
  
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
  protected RobotsEcosystem.Provides provides() {
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
  protected RobotsEcosystem.Requires requires() {
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
  protected RobotsEcosystem.Parts parts() {
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
  public synchronized RobotsEcosystem.Component _newComponent(final RobotsEcosystem.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RobotsEcosystem has already been used to create a component, use another one.");
    }
    this.init = true;
    RobotsEcosystem.ComponentImpl  _comp = new RobotsEcosystem.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract RobotsEcosystem.Robot make_Robot(final String identifier, final Color color);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public RobotsEcosystem.Robot _createImplementationOfRobot(final String identifier, final Color color) {
    RobotsEcosystem.Robot implem = make_Robot(identifier,color);
    if (implem == null) {
    	throw new RuntimeException("make_Robot() in speadl.agents.RobotsEcosystem should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected RobotsEcosystem.Robot.Component newRobot(final String identifier, final Color color) {
    RobotsEcosystem.Robot _implem = _createImplementationOfRobot(identifier,color);
    return _implem._newComponent(new RobotsEcosystem.Robot.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public RobotsEcosystem.Component newComponent() {
    return this._newComponent(new RobotsEcosystem.Requires() {}, true);
  }
}
