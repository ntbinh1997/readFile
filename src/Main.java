/*
 *
 *
 *
 *
 *
 *
 */


import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		FactoryFile factoryFile = new FactoryFile();
		FileInput file = factoryFile.creatFile(FILE.FILECSV);
		Observable observable = new Observable();
		observable.attach(file);
		Path pathInput = Paths.get("D:\\test\\ex.csv");
		file.setPath(pathInput);
		file.process();
		observable.createWatchService();

	}
}
