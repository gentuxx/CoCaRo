package CoCaRo.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import CoCaRo.logging.interfaces.ILog;
import speadl.logging.Logging.Logger;

public class LoggerImpl extends Logger implements ILog{

	private Path pathFile;
	private static int nbAgent = 0;
	
	@Override
	public void addLine(String line) {
				
		try {
			Files.write(pathFile, line.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected ILog make_log() {
		
		pathFile = Paths.get("./log/agent"+nbAgent+".txt");
		try {
			Files.createFile(pathFile);
			nbAgent++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Path créé");
		
		return this;
	}

}
