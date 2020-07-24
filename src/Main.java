public class Main {
    public enum FILE {
        FILECSV,
        FILEXML,
        FILEXLSX
    }
    public static void main(String[] args) {
        FactoryFile factoryFile = new FactoryFile();
        factoryFile.creatFile(FILE.FILEXLSX);
    }
}
