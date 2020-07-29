import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public abstract class ImportFile implements FileInput {
    protected List<Company> listCompany = new ArrayList<>();
    protected Path path = null;
    private static final int NUMFIVE = 5;
    private static final int NUMSIX = 6;


    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    private void checkZipFile() {
        File fileInput = new File(path.toString());
        if (fileInput.exists() && fileInput.getName().endsWith(".zip")) {
            try (ZipInputStream zipFile = new ZipInputStream(new FileInputStream(fileInput))) {
                ZipEntry entry = zipFile.getNextEntry();
                final Path toPath = path.getParent().resolve(entry.getName());
                this.path = toPath;
                if (entry.isDirectory()) {
                    Files.createDirectory(toPath);
                } else {
                    Files.copy(zipFile, toPath, REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        listCompany.clear();
        readFile();
        Main.showResult(listCompany);

    }

    protected abstract void readFile();

    public List<Company> getListCompany() {
        return this.listCompany;
    }

    public final static String DOT = ".";

    public Company createCompany(String[] metadata) {
        int day = 1;
        int month = 1;
        int year;
        int id;
        int headQuarterID = 0;
        LocalDate date;
        int capital;

        if (metadata.length != NUMFIVE && metadata.length != NUMSIX) {
            return null;
        }
        id = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        if (metadata[2].contains(DOT)) {
            String[] daytime = metadata[2].split("\\.");
            day = Integer.parseInt(daytime[0]);
            month = Integer.parseInt(daytime[1]);
            year = Integer.parseInt(daytime[2]);
        } else {
            year = Integer.parseInt(metadata[2]);
        }
        date = LocalDate.of(year, month, day);
        capital = Integer.parseInt(metadata[3]);
        String country = metadata[4];
        if (metadata.length == 6) {
            headQuarterID = Integer.parseInt(metadata[5]);
        }
        return new Company(id, name, date, capital, country, headQuarterID);
    }

    @Override
    public final void process() {
        checkZipFile();
        readFile();
    }
}
