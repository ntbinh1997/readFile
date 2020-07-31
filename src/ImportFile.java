import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public abstract class ImportFile <T> implements FileInput {
    protected List<Company> listCompany = new ArrayList<>();
    protected Path path = null;

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

    protected abstract Company createCompany(T[] metadata);

    protected abstract Company createCompanyLite(T[] metadata);

    public void update() {
        listCompany.clear();
        readFile();
        showResult();
    }

    protected abstract void readFile();

    protected abstract void showResult();


    @Override
    public final void process() {
        checkZipFile();
        readFile();
        showResult();
    }
}
