import java.io.IOException;
import java.time.LocalDate;

public abstract class ImportFile implements FileInput {

    public abstract boolean checkZipFile();

    public abstract void createWatchService();

    public abstract void readFile();

    public abstract void getInfoFromFile();

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
    public void process() {
        checkZipFile();
//        createWatchService();
        readFile();
        getInfoFromFile();
    }
}
