import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public enum FILE {
        FILECSV,
        FILEXML,
        FILEXLSX
    }
    public static void showResult(FileInput file){
        file.getListCompany().stream().filter(x -> x.getCountry().equals("CH"))
                .map(Company::getCapital).sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        file.getListCompany().stream().filter(x ->x.getCountry().equals("ZH"))
                .map(Company::getName).forEach(System.out::println);
    }

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
        int choice = 0;
        FactoryFile factoryFile = new FactoryFile();
        FileInput file = factoryFile.creatFile(FILE.FILECSV);
        Thread t1 = new Thread(file);
        while ( choice != 3 ){
            choice = menu();
            switch (choice){
                case 1: {
                    Path pathInput = Paths.get("D:\\test\\ex.csv");
                    file.setPath(pathInput);
                    file.process();
                    if (!t1.isAlive()){
                        t1.start();
                    }

                    break;
                }

                case 2: {
                    if (file.getPath() == null ){
                        System.out.println("Please import file first");
                    } else {
                        showResult(file);
                    }
                    break;
                }
                case 3: {
                    break;
                }
                default:
                    System.out.println("Please choice right");
            }
        }
    }
}
