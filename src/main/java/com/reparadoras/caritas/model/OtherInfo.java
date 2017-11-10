/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas.model;

import java.io.Serializable;





public class OtherInfo implements Serializable{

	
private static final long serialVersionUID = 1L;
	
private int id;
private String institutions; 
private String demand; 
private String description; 
private String actuations; 








public String getInstitutions() {
	return institutions;
}

public void setInstitutions(String institutions) {
	this.institutions = institutions;
}

public String getDemand() {
	return demand;
}

public void setDemand(String demand) {
	this.demand = demand;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getActuations() {
	return actuations;
}

public void setActuations(String actuations) {
	this.actuations = actuations;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}



   
    
    

     
 
    
    
    
    
}
