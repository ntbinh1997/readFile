import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;


import java.util.zip.ZipInputStream;

public class FileCSV extends ImportFile implements FileInput , Runnable {
    List<Company> listCompany = new ArrayList<>();
    private Path path = null;

    @Override
    public List<Company> getListCompany() {
        return this.listCompany;
    }

    public static final String COMMA = ",";


    public FileCSV() {
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void update() {
        listCompany.clear();
        readFile();
        Main.menu();
    }

    @Override
    public boolean checkZipFile() {
        File fileInput = new File(path.toString());
        if (fileInput.exists() && fileInput.getName().endsWith(".zip")) {
            try (ZipInputStream zipFile = new ZipInputStream(new FileInputStream(fileInput))) {
                ZipEntry entry = zipFile.getNextEntry();
                final Path toPath = path.getParent().resolve(entry.getName());
                this.path = toPath;
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
    public void createWatchService() {
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
    public void getInfoFromFile() {
    }

    @Override
    public void run() {
        createWatchService();
    }
}
