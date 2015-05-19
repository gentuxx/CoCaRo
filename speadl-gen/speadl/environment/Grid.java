package speadl.environment;

import CoCaRo.environment.interfaces.IBoxGenerator;
import CoCaRo.environment.interfaces.IEnvironment;
import CoCaRo.environment.interfaces.INestCreator;
import speadl.environment.BoxEnv;
import speadl.environment.NestEnv;

@SuppressWarnings("all")
public abstract class Grid {
  public interface Requires {
  }
  
  public interface Component extends Grid.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public INestCreator createNests();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IBoxGenerator createBox();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEnvironment env();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public BoxEnv.Component boxEnv();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public NestEnv.Component nestEnv();
  }
  
  public static class ComponentImpl implements Grid.Component, Grid.Parts {
    private final Grid.Requires bridge;
    
    private final Grid implementation;
    
    public void start() {
      assert this.boxEnv != null: "This is a bug.";
      ((BoxEnv.ComponentImpl) this.boxEnv).start();
      assert this.nestEnv != null: "This is a bug.";
      ((NestEnv.ComponentImpl) this.nestEnv).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_boxEnv() {
      assert this.boxEnv == null: "This is a bug.";
      assert this.implem_boxEnv == null: "This is a bug.";
      this.implem_boxEnv = this.implementation.make_boxEnv();
      if (this.implem_boxEnv == null) {
      	throw new RuntimeException("make_boxEnv() in speadl.environment.Grid should not return null.");
      }
      this.boxEnv = this.implem_boxEnv._newComponent(new BridgeImpl_boxEnv(), false);
      
    }
    
    private void init_nestEnv() {
      assert this.nestEnv == null: "This is a bug.";
      assert this.implem_nestEnv == null: "This is a bug.";
      this.implem_nestEnv = this.implementation.make_nestEnv();
      if (this.implem_nestEnv == null) {
      	throw new RuntimeException("make_nestEnv() in speadl.environment.Grid should not return null.");
      }
      this.nestEnv = this.implem_nestEnv._newComponent(new BridgeImpl_nestEnv(), false);
      
    }
    
    protected void initParts() {
      init_boxEnv();
      init_nestEnv();
    }
    
    private void init_env() {
      assert this.env == null: "This is a bug.";
      this.env = this.implementation.make_env();
      if (this.env == null) {
      	throw new RuntimeException("make_env() in speadl.environment.Grid should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_env();
    }
    
    public ComponentImpl(final Grid implem, final Grid.Requires b, final boolean doInits) {
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
    
    public INestCreator createNests() {
      return this.nestEnv().createNests();
    }
    
    public IBoxGenerator createBox() {
      return this.boxEnv().createBox();
    }
    
    private IEnvironment env;
    
    public IEnvironment env() {
      return this.env;
    }
    
    private BoxEnv.Component boxEnv;
    
    private BoxEnv implem_boxEnv;
    
    private final class BridgeImpl_boxEnv implements BoxEnv.Requires {
    }
    
    public final BoxEnv.Component boxEnv() {
      return this.boxEnv;
    }
    
    private NestEnv.Component nestEnv;
    
    private NestEnv implem_nestEnv;
    
    private final class BridgeImpl_nestEnv implements NestEnv.Requires {
    }
    
    public final NestEnv.Component nestEnv() {
      return this.nestEnv;
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
  
  private Grid.ComponentImpl selfComponent;
  
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
  protected Grid.Provides provides() {
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
  protected abstract IEnvironment make_env();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Grid.Requires requires() {
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
  protected Grid.Parts parts() {
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
  protected abstract BoxEnv make_boxEnv();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract NestEnv make_nestEnv();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Grid.Component _newComponent(final Grid.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Grid has already been used to create a component, use another one.");
    }
    this.init = true;
    Grid.ComponentImpl  _comp = new Grid.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Grid.Component newComponent() {
    return this._newComponent(new Grid.Requires() {}, true);
  }
}
