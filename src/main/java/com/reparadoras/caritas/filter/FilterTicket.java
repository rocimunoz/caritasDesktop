package com.reparadoras.caritas.filter;

public class FilterTicket {

	private Long idPeople;
	private String namePeople;
	private String dniPeople;
	private Integer yearTicket;
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
	
	
	
	
}
