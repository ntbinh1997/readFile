import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<FileInput> observers = new ArrayList<>();

    public void attach(FileInput observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (FileInput observer : observers) {
            observer.update();
        }
    }

    public void createWatchService() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            WatchKey watchKey;
            observers.forEach(x -> {
                try {
                    x.getPath().getParent().register(
                            watchService,
                            StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
                            StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.OVERFLOW
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
//            Path pathNeedWatch = observers.get(0).getPath().getParent();
//            pathNeedWatch.register(watchService,
//                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
//                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.OVERFLOW);
            while ((watchKey = watchService.take()) != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + " Import new file");
                    if (event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)){
                        notifyAllObservers();
                    }
                }
                watchKey.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
