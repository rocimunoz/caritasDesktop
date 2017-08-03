/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas.model;

import java.io.Serializable;
import java.util.Date;



public class Program implements Serializable{

    private int id;
    
    private People people;
    private Date dateGBD;
    
    

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public Date getDateGBD() {
		return dateGBD;
	}

	public void setDateGBD(Date dateGBD) {
		this.dateGBD = dateGBD;
	}
	
	
    
    

	

   

   
    
    
    
}
