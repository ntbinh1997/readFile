import java.nio.file.Path;
import java.util.List;

public interface FileInput extends Runnable {
    void process();

    void setPath(Path path);

    List<Company> getListCompany();

    Path getPath();

    void update();
}