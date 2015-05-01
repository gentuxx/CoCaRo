package speadl.environment;

import speadl.agents.RobotsEcosystem;
import speadl.environment.Grid;

@SuppressWarnings("all")
public abstract class Environment {
  public interface Requires {
  }
  
  public interface Component extends Environment.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Grid.Component grid();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public RobotsEcosystem.Component robotEcosystem();
  }
  
  public static class ComponentImpl implements Environment.Component, Environment.Parts {
    private final Environment.Requires bridge;
    
    private final Environment implementation;
    
    public void start() {
      assert this.grid != null: "This is a bug.";
      ((Grid.ComponentImpl) this.grid).start();
      assert this.robotEcosystem != null: "This is a bug.";
      ((RobotsEcosystem.ComponentImpl) this.robotEcosystem).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_grid() {
      assert this.grid == null: "This is a bug.";
      assert this.implem_grid == null: "This is a bug.";
      this.implem_grid = this.implementation.make_grid();
      if (this.implem_grid == null) {
      	throw new RuntimeException("make_grid() in speadl.environment.Environment should not return null.");
      }
      this.grid = this.implem_grid._newComponent(new BridgeImpl_grid(), false);
      
    }
    
    private void init_robotEcosystem() {
      assert this.robotEcosystem == null: "This is a bug.";
      assert this.implem_robotEcosystem == null: "This is a bug.";
      this.implem_robotEcosystem = this.implementation.make_robotEcosystem();
      if (this.implem_robotEcosystem == null) {
      	throw new RuntimeException("make_robotEcosystem() in speadl.environment.Environment should not return null.");
      }
      this.robotEcosystem = this.implem_robotEcosystem._newComponent(new BridgeImpl_robotEcosystem(), false);
      
    }
    
    protected void initParts() {
      init_grid();
      init_robotEcosystem();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Environment implem, final Environment.Requires b, final boolean doInits) {
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
    
    private Grid.Component grid;
    
    private Grid implem_grid;
    
    private final class BridgeImpl_grid implements Grid.Requires {
    }
    
    public final Grid.Component grid() {
      return this.grid;
    }
    
    private RobotsEcosystem.Component robotEcosystem;
    
    private RobotsEcosystem implem_robotEcosystem;
    
    private final class BridgeImpl_robotEcosystem implements RobotsEcosystem.Requires {
    }
    
    public final RobotsEcosystem.Component robotEcosystem() {
      return this.robotEcosystem;
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
  
  private Environment.ComponentImpl selfComponent;
  
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
  protected Environment.Provides provides() {
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
  protected Environment.Requires requires() {
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
  protected Environment.Parts parts() {
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
  protected abstract Grid make_grid();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract RobotsEcosystem make_robotEcosystem();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Environment.Component _newComponent(final Environment.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Environment has already been used to create a component, use another one.");
    }
    this.init = true;
    Environment.ComponentImpl  _comp = new Environment.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Environment.Component newComponent() {
    return this._newComponent(new Environment.Requires() {}, true);
  }
}
