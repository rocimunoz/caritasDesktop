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

	
	private String concept;
	private Integer amount;
	private String regularity; //quiza enumerado
	private Date endDate;
	
	
	public String getConcept() {
		return concept;
	}
	public Integer getAmount() {
		return amount;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
    
   
    
    
    
}
