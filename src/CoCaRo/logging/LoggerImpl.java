package CoCaRo.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import CoCaRo.logging.interfaces.ILog;
import speadl.logging.Logging.Logger;

public class LoggerImpl extends Logger implements ILog{

	private Path fileFile;
	private int nbAgent = 0;
	
	@Override
	public void addLine(String line) {
//		System.out.println(line);
//		
//		try {
//			Files.write(fileFile, line.getBytes());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}

	@Override
	protected ILog make_log() {
		
//		fileFile = Paths.get("C:/Users/Willems/Documents/Cours/M2/SMA/CoCaRo/log/agent"+nbAgent+".txt");
//		try {
//			Files.createFile(fileFile);
//			nbAgent++;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return this;
	}

}
