public class FactoryFile {
	public FactoryFile() {
	}

	public FileInput creatFile(FILE filestype) {
		switch (filestype) {
			case FILECSV:
				return new FileCSV();
			case FILEXML:
				return new FileXML();
			case FILEXLSX:
				return new FileXLSX();
			default:
				return null;
		}

	}
}
