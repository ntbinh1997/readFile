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
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static int menu() {
		System.out.println("MENU");
		System.out.println("1: ImportFile");
		System.out.println("2: Show result");
		System.out.println("3: Exit");
		System.out.print("Your choice: ");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		return input;
	}

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
