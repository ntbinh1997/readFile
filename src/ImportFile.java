public abstract class ImportFile  implements FileInput{

    public abstract boolean checkZipFile();

    public abstract boolean importFile();

    public abstract boolean readFile();

    public abstract boolean getInfoFromFile();

    public Company createCompany(String[] metadata) {
        int id = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        String date = metadata[2];
        int capital = Integer.parseInt(metadata[3]);
        String country = metadata[4];
        Integer headQuarterID = -1;
        if (metadata.length == 6) {
            headQuarterID = Integer.parseInt(metadata[5]);
        }
        return new Company(id, name, date, capital, country, headQuarterID);
    }

    @Override
    public void process(){
        importFile();
        checkZipFile();
        readFile();
        getInfoFromFile();
    }
}
