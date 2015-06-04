package CoCaRo.logging;

import CoCaRo.logging.interfaces.ILog;
import speadl.logging.Logging.Logger;

public class LoggerImpl extends Logger implements ILog{

	@Override
	public void addLine(String line) {
		System.err.println("TEST : " + line);
		
	}

	@Override
	protected ILog make_log() {
		return this;
	}

}
