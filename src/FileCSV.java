import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCSV extends ImportFile {

    public static final String COMMA = ",";

    @Override
    public void readFile() {
        try (BufferedReader buf = Files.newBufferedReader(path.toAbsolutePath())) {
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

}
