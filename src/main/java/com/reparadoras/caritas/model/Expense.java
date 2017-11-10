/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;



public class Expense implements Serializable{

	private Long id;
	private String concept;
	private Double amount;
	private String regularity; //quiza enumerado
	private Date endDate;
	private Program program;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConcept() {
		return concept;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRegularity() {
		return regularity;
	}
	public void setRegularity(String regularity) {
		this.regularity = regularity;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
   
    
    
    
}
