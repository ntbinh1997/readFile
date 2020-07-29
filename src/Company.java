import java.time.LocalDate;
import java.time.LocalDateTime;

class Company {
	private int id;
	private String name;
	private LocalDate date;
	private int capital;
	private String country;
	private Integer headQuarterID;

	public Company(int id, String name, LocalDate date, int capital, String country, Integer headQuarterID) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.capital = capital;
		this.country = country;
		this.headQuarterID = headQuarterID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getHeadQuarterID() {
		return headQuarterID;
	}

	public void setHeadQuarterID(Integer headQuarterID) {
		this.headQuarterID = headQuarterID;
	}
}