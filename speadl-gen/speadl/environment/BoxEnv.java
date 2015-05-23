package speadl.environment;

import CoCaRo.CustomColor;
import CoCaRo.environment.interfaces.IBoxGenerator;

@SuppressWarnings("all")
public abstract class BoxEnv {
  public interface Requires {
  }
  
  public interface Component extends BoxEnv.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IBoxGenerator boxGenerator();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements BoxEnv.Component, BoxEnv.Parts {
    private final BoxEnv.Requires bridge;
    
    private final BoxEnv implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_boxGenerator() {
      assert this.boxGenerator == null: "This is a bug.";
      this.boxGenerator = this.implementation.make_boxGenerator();
      if (this.boxGenerator == null) {
      	throw new RuntimeException("make_boxGenerator() in speadl.environment.BoxEnv should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_boxGenerator();
    }
    
    public ComponentImpl(final BoxEnv implem, final BoxEnv.Requires b, final boolean doInits) {
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
    
    private IBoxGenerator boxGenerator;
    
    public IBoxGenerator boxGenerator() {
      return this.boxGenerator;
    }
  }
  
  public static class Box {
    public interface Requires {
    }
    
    public interface Component extends BoxEnv.Box.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
    }
    
    public static class ComponentImpl implements BoxEnv.Box.Component, BoxEnv.Box.Parts {
      private final BoxEnv.Box.Requires bridge;
      
      private final BoxEnv.Box implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final BoxEnv.Box implem, final BoxEnv.Box.Requires b, final boolean doInits) {
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
    
    private BoxEnv.Box.ComponentImpl selfComponent;
    
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
    protected BoxEnv.Box.Provides provides() {
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
    protected BoxEnv.Box.Requires requires() {
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
    protected BoxEnv.Box.Parts parts() {
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
    public synchronized BoxEnv.Box.Component _newComponent(final BoxEnv.Box.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Box has already been used to create a component, use another one.");
      }
      this.init = true;
      BoxEnv.Box.ComponentImpl  _comp = new BoxEnv.Box.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private BoxEnv.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected BoxEnv.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected BoxEnv.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected BoxEnv.Parts eco_parts() {
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
  
  private BoxEnv.ComponentImpl selfComponent;
  
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
  protected BoxEnv.Provides provides() {
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
  protected abstract IBoxGenerator make_boxGenerator();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected BoxEnv.Requires requires() {
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
  protected BoxEnv.Parts parts() {
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
  public synchronized BoxEnv.Component _newComponent(final BoxEnv.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of BoxEnv has already been used to create a component, use another one.");
    }
    this.init = true;
    BoxEnv.ComponentImpl  _comp = new BoxEnv.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected BoxEnv.Box make_Box(final CustomColor color) {
    return new BoxEnv.Box();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public BoxEnv.Box _createImplementationOfBox(final CustomColor color) {
    BoxEnv.Box implem = make_Box(color);
    if (implem == null) {
    	throw new RuntimeException("make_Box() in speadl.environment.BoxEnv should not return null.");
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
  protected BoxEnv.Box.Component newBox(final CustomColor color) {
    BoxEnv.Box _implem = _createImplementationOfBox(color);
    return _implem._newComponent(new BoxEnv.Box.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BoxEnv.Component newComponent() {
    return this._newComponent(new BoxEnv.Requires() {}, true);
  }
}
