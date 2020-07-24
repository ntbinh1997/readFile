import java.io.BufferedReader;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        return false;
    }

    @Override
    public boolean importFile() {

        return false;
    }

    @Override
    public boolean readFile() {
        try (BufferedReader buf = Files.newBufferedReader(path)) {
            buf.readLine();
            String line = buf.readLine();
            while (line != null) {
                String[] companyDetail = line.split(",");

                Company company = createCompany(companyDetail);
                listCompany.add(company);
                line = buf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean getInfoFromFile() {
        return false;
    }

}
