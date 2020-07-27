import java.nio.file.Path;
import java.util.List;

public class FileXLSX extends ImportFile implements FileInput {

    @Override
    public boolean checkZipFile() {
        return false;
    }

    @Override
    public void createWatchService() {
    }

    @Override
    public void readFile() {
    }

    @Override
    public void getInfoFromFile() {
    }

    @Override
    public void setPath(Path path) {

    }

    @Override
    public List<Company> getListCompany() {
        return null;
    }

    @Override
    public Path getPath() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void run() {

    }
}
