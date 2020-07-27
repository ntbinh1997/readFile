import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    private static final Logger LOGGER = Logger.getLogger(ZipUtils.class.getName());

    public static void unzip(final Path zipFile, final Path decryptTo) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File(zipFile.toString())))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                LOGGER.log(Level.INFO, "entry name = {0}", entry.getName());
                final Path toPath = decryptTo.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectory(toPath);
                } else {
                    Files.copy(zipInputStream, toPath);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void extractFile(Path zipFile, String fileName, Path outputFile) throws IOException {
        // Wrap the file system in a try-with-resources statement
        // to auto-close it when finished and prevent a memory leak
        try (FileSystem fileSystem = FileSystems.newFileSystem(zipFile, null)) {
            Path fileToExtract = fileSystem.getPath(fileName);
            Files.copy(fileToExtract, outputFile);
        }
    }

    public static void main(String[] args) throws IOException {
        String path = ".";
        String FileName = "text.rar";
        extractFile(Paths.get(path), "text.rar", Paths.get(path));
//        File zipFile = new File(path, FileName);
//
//        FileInputStream fin = new FileInputStream(zipFile);
//        ZipInputStream zin = new ZipInputStream(fin);
//
//        ZipEntry ze = zin.getNextEntry();
//        while (ze != null) {
//            zin.closeEntry();
//            }
//        }
    }
}

