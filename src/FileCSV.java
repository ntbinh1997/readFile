import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Comparator;

public class FileCSV  extends ImportFile <String> {
    public static final String SPLITSTRINGCSV = ",";

    @Override
    protected Company createCompany(String[] metadata) {
        int day = 1;
        int month = 1;
        int year;

        if (metadata.length != 5 && metadata.length != 6) {
            return null;
        }
        int id = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        if (metadata[2].contains(SPLITSTRINGCSV)) {
            String[] daytime = metadata[2].split("\\" + SPLITSTRINGCSV);
            day = Integer.parseInt(daytime[0]);
            month = Integer.parseInt(daytime[1]);
            year = Integer.parseInt(daytime[2]);
        } else {
            year = Integer.parseInt(metadata[2]);
        }
        LocalDate date = LocalDate.of(year, month, day);
        int capital = Integer.parseInt(metadata[3]);
        String country = metadata[4];
        int headQuarterID = 0;
        if (metadata.length == 6) {
            headQuarterID = Integer.parseInt(metadata[5]);
        }
        return new Company(id, name, date, capital, country, headQuarterID);
    }

    @Override
    protected Company createCompanyLite(String[] metadata) {
        if (metadata.length != 5 && metadata.length != 6) {
            return null;
        }
        String name = metadata[1];
        int capital = Integer.parseInt(metadata[3]);
        String country = metadata[4];
        return new Company(capital, country, name);
    }

    @Override
    public void readFile() {
        try (BufferedReader buf = Files.newBufferedReader(path.toAbsolutePath())) {
            String line = buf.readLine();
            while (line.contains("ID") || line.contains("sep=")) {
                line = buf.readLine();
            }
            while (line != null) {
                String[] companyDetail = line.split(SPLITSTRINGCSV);
                Company company = createCompanyLite(companyDetail);
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
    public void showResult() {
        System.out.println("Country = CH");
        listCompany.stream()
                .filter(x -> x.getCountry().equals("CH"))
                .map(Company::getCapital)
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        System.out.println("Country = ZH");
        listCompany.stream()
                .filter(x -> x.getCountry().equals("ZH"))
                .map(Company::getName)
                .forEach(System.out::println);
    }

}
