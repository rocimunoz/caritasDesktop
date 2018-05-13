package com.reparadoras.caritas.filter;

public class FilterAnswer {

	private Long idPeople;
	private String namePeople;
	private String dniPeople;
	private String passportPeople;
	private Integer yearTicket;
	private String month;
	
	
	
	private Boolean active;
	public String getNamePeople() {
		return namePeople;
	}
	public void setNamePeople(String namePeople) {
		this.namePeople = namePeople;
	}
	public String getDniPeople() {
		return dniPeople;
	}
	public void setDniPeople(String dniPeople) {
		this.dniPeople = dniPeople;
	}
	public Integer getYearTicket() {
		return yearTicket;
	}
	public void setYearTicket(Integer yearTicket) {
		this.yearTicket = yearTicket;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Long getIdPeople() {
		return idPeople;
	}
	public void setIdPeople(Long idPeople) {
		this.idPeople = idPeople;
	}
	
	public String getPassportPeople() {
		return passportPeople;
	}
	public void setPassportPeople(String passportPeople) {
		this.passportPeople = passportPeople;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
	
	
	
	
	
	
}
