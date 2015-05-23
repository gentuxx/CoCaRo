package speadl.environment;

import CoCaRo.CustomColor;
import CoCaRo.environment.interfaces.IBoxGenerator;
import CoCaRo.environment.interfaces.IEnvironment;
import CoCaRo.environment.interfaces.INestCreator;
import speadl.agents.RobotsEcosystem;
import speadl.environment.Grid;

@SuppressWarnings("all")
public abstract class Environment {
  public interface Requires {
  }
  
  public interface Component extends Environment.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IBoxGenerator boxGenerator();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public INestCreator nestCreator();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public RobotsEcosystem.Component robotEcosystem();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Grid.Component globalGrid();
  }
  
  public static class ComponentImpl implements Environment.Component, Environment.Parts {
    private final Environment.Requires bridge;
    
    private final Environment implementation;
    
    public void start() {
      assert this.robotEcosystem != null: "This is a bug.";
      ((RobotsEcosystem.ComponentImpl) this.robotEcosystem).start();
      assert this.globalGrid != null: "This is a bug.";
      ((Grid.ComponentImpl) this.globalGrid).start();
      this.implementation.start();
      this.implementation.started = true;
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
    
    private void init_globalGrid() {
      assert this.globalGrid == null: "This is a bug.";
      assert this.implem_globalGrid == null: "This is a bug.";
      this.implem_globalGrid = this.implementation.make_globalGrid();
      if (this.implem_globalGrid == null) {
      	throw new RuntimeException("make_globalGrid() in speadl.environment.Environment should not return null.");
      }
      this.globalGrid = this.implem_globalGrid._newComponent(new BridgeImpl_globalGrid(), false);
      
    }
    
    protected void initParts() {
      init_robotEcosystem();
      init_globalGrid();
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
    
    public IBoxGenerator boxGenerator() {
      return this.globalGrid().boxGenerator();
    }
    
    public INestCreator nestCreator() {
      return this.globalGrid().nestCreator();
    }
    
    private RobotsEcosystem.Component robotEcosystem;
    
    private RobotsEcosystem implem_robotEcosystem;
    
    private final class BridgeImpl_robotEcosystem implements RobotsEcosystem.Requires {
    }
    
    public final RobotsEcosystem.Component robotEcosystem() {
      return this.robotEcosystem;
    }
    
    private Grid.Component globalGrid;
    
    private Grid implem_globalGrid;
    
    private final class BridgeImpl_globalGrid implements Grid.Requires {
    }
    
    public final Grid.Component globalGrid() {
      return this.globalGrid;
    }
  }
  
  public static abstract class RobotGrid {
    public interface Requires {
    }
    
    public interface Component extends Environment.RobotGrid.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public RobotsEcosystem.Robot.Component aRobot();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Grid.Component grid();
    }
    
    public static class ComponentImpl implements Environment.RobotGrid.Component, Environment.RobotGrid.Parts {
      private final Environment.RobotGrid.Requires bridge;
      
      private final Environment.RobotGrid implementation;
      
      public void start() {
        assert this.aRobot != null: "This is a bug.";
        ((RobotsEcosystem.Robot.ComponentImpl) this.aRobot).start();
        assert this.grid != null: "This is a bug.";
        ((Grid.ComponentImpl) this.grid).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_aRobot() {
        assert this.aRobot == null: "This is a bug.";
        assert this.implementation.use_aRobot != null: "This is a bug.";
        this.aRobot = this.implementation.use_aRobot._newComponent(new BridgeImpl_robotEcosystem_aRobot(), false);
        
      }
      
      private void init_grid() {
        assert this.grid == null: "This is a bug.";
        assert this.implem_grid == null: "This is a bug.";
        this.implem_grid = this.implementation.make_grid();
        if (this.implem_grid == null) {
        	throw new RuntimeException("make_grid() in speadl.environment.Environment$RobotGrid should not return null.");
        }
        this.grid = this.implem_grid._newComponent(new BridgeImpl_grid(), false);
        
      }
      
      protected void initParts() {
        init_aRobot();
        init_grid();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final Environment.RobotGrid implem, final Environment.RobotGrid.Requires b, final boolean doInits) {
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
      
      private RobotsEcosystem.Robot.Component aRobot;
      
      private final class BridgeImpl_robotEcosystem_aRobot implements RobotsEcosystem.Robot.Requires {
        public final IEnvironment gridR() {
          return Environment.RobotGrid.ComponentImpl.this.grid().env();
        }
      }
      
      public final RobotsEcosystem.Robot.Component aRobot() {
        return this.aRobot;
      }
      
      private Grid.Component grid;
      
      private Grid implem_grid;
      
      private final class BridgeImpl_grid implements Grid.Requires {
      }
      
      public final Grid.Component grid() {
        return this.grid;
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
    
    private Environment.RobotGrid.ComponentImpl selfComponent;
    
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
    protected Environment.RobotGrid.Provides provides() {
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
    protected Environment.RobotGrid.Requires requires() {
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
    protected Environment.RobotGrid.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private RobotsEcosystem.Robot use_aRobot;
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract Grid make_grid();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized Environment.RobotGrid.Component _newComponent(final Environment.RobotGrid.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of RobotGrid has already been used to create a component, use another one.");
      }
      this.init = true;
      Environment.RobotGrid.ComponentImpl  _comp = new Environment.RobotGrid.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Environment.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Environment.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Environment.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Environment.Parts eco_parts() {
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
  protected abstract RobotsEcosystem make_robotEcosystem();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Grid make_globalGrid();
  
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
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Environment.RobotGrid make_RobotGrid(final String identifier, final CustomColor color);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Environment.RobotGrid _createImplementationOfRobotGrid(final String identifier, final CustomColor color) {
    Environment.RobotGrid implem = make_RobotGrid(identifier,color);
    if (implem == null) {
    	throw new RuntimeException("make_RobotGrid() in speadl.environment.Environment should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_robotEcosystem != null: "This is a bug.";
    assert implem.use_aRobot == null: "This is a bug.";
    implem.use_aRobot = this.selfComponent.implem_robotEcosystem._createImplementationOfRobot(identifier,color);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected Environment.RobotGrid.Component newRobotGrid(final String identifier, final CustomColor color) {
    Environment.RobotGrid _implem = _createImplementationOfRobotGrid(identifier,color);
    return _implem._newComponent(new Environment.RobotGrid.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Environment.Component newComponent() {
    return this._newComponent(new Environment.Requires() {}, true);
  }
}
