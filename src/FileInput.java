import java.nio.file.Path;
import java.util.List;

public interface FileInput {
	void process();

	void update();

	void setPath(Path path);

	Path getPath();
}