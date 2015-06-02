package CoCaRo.environment.interfaces;

import java.util.EventObject;

public interface EnvChangeListener {
	
	public class EnvChangeEvent extends EventObject {
	  public EnvChangeEvent(Object source) {
	    super(source);
	  }
	}
	
	public void changeEventReceived(EnvChangeEvent evt);
}
