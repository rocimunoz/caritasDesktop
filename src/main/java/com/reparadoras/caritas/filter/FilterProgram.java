package com.reparadoras.caritas.filter;

public class FilterProgram {
	private Long id;
	private Long idFamily;
	private Long idPeople;
	private String namePeople;
	private String firstSurname;
	private String dni;
	private Boolean active;
	
	
	public FilterProgram(){
		
	}
	
	
	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getIdFamily() {
		return idFamily;
	}
	public void setIdFamily(Long idFamily) {
		this.idFamily = idFamily;
	}
	public Long getIdPeople() {
		return idPeople;
	}
	public void setIdPeople(Long idPeople) {
		this.idPeople = idPeople;
	}
	public String getNamePeople() {
		return namePeople;
	}
	public void setNamePeople(String namePeople) {
		this.namePeople = namePeople;
	}
	public String getFirstSurname() {
		return firstSurname;
	}
	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
}
