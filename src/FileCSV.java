import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;


import java.util.zip.ZipInputStream;

public class FileCSV extends ImportFile {
    List<Company> listCompany = new ArrayList<>();
    private Path path;
    private boolean status;
    public List<Company> getListCompany(){
        return this.listCompany;
    }

    public FileCSV(Path path) {
        this.path = path;
        this.status = true;
    }

    public FileCSV() {
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean checkZipFile() {
        File fileInput = new File(path.toString());
        if (fileInput.exists() && fileInput.getName().endsWith(".rar")){
            try (ZipInputStream zipFile = new ZipInputStream( new FileInputStream(fileInput))){
                ZipEntry entry = zipFile.getNextEntry();
                final Path toPath = path.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectory(toPath);
                } else {
                    Files.copy(zipFile, toPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public void importFile() {

    }

    public static String COMMA = ",";

    @Override
    public void readFile() {
        try (BufferedReader buf = Files.newBufferedReader(path)) {
            buf.readLine();
            String line = buf.readLine();
            while (line != null) {
                String[] companyDetail = line.split(COMMA);

                Company company = createCompany(companyDetail);
                listCompany.add(company);
                line = buf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getInfoFromFile() {
    }

}
