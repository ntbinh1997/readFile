import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileCSV extends ImportFile implements FileInput {

    public static final String COMMA = ",";

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public void readFile() {
        try (BufferedReader buf = Files.newBufferedReader(path)) {
            String line = buf.readLine();
            while (line.contains("ID") || line.contains("sep=")) {
                line = buf.readLine();
            }
            while (line != null) {
                String[] companyDetail = line.split(COMMA);
                Company company = createCompany(companyDetail);
                if (company != null) {
                    listCompany.add(company);
                }
                line = buf.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        createWatchService();
    }
}
