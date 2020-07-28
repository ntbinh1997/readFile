import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static java.nio.file.StandardCopyOption.*;

public abstract class ImportFile implements FileInput, Runnable  {
    protected List<Company> listCompany = new ArrayList<>();
    protected Path path = null;

    public void checkZipFile() {
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
        Main.menu();
    }

    public void createWatchService(){
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            WatchKey watchKey;
            Path pathNeedWatch = path.getParent();
            pathNeedWatch.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.OVERFLOW);
            while ((watchKey = watchService.take()) != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + " Import new file");
                    update();
                }
                watchKey.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    };

    public abstract void readFile();

    public List<Company> getListCompany() {
        return this.listCompany;
    }

    public final static String DOT = ".";

    public Company createCompany(String[] metadata) {
        int day = 1;
        int month = 1;
        int year = 0;

        if (metadata.length != 6 && metadata.length != 5){
            return null;
        }
        int id = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        if (metadata[2].contains(DOT)) {
            String[] daytime = metadata[2].split("\\.");
            day = Integer.parseInt(daytime[0]);
            month = Integer.parseInt(daytime[1]);
            year = Integer.parseInt(daytime[2]);
        } else {
            year = Integer.parseInt(metadata[2]);
        }
        LocalDate date = LocalDate.of(year, month, day);
        int capital = Integer.parseInt(metadata[3]);
        String country = metadata[4];
        int headQuarterID = -1;
        if (metadata.length == 6) {
            headQuarterID = Integer.parseInt(metadata[5]);
        }
        return new Company(id, name, date, capital, country, headQuarterID);
    }

    @Override
    public final void process() {
        checkZipFile();
        createWatchService();;
        readFile();
    }
}
