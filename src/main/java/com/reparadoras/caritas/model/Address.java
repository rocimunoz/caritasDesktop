/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas.model;

import java.io.Serializable;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlRootElement;

import org.jdesktop.swingx.JXDatePicker;



public class Address implements Serializable{

	
	private String town;
	private String street;
	private String gate;
	private String floor;
	private String telephone;
	private String telephoneContact;
	private Date census;
	private String place;
	
	
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getTelephoneContact() {
		return telephoneContact;
	}
	public void setTelephoneContact(String telephoneContact) {
		this.telephoneContact = telephoneContact;
	}
	public Date getCensus() {
		return census;
	}
	public void setCensus(Date census) {
		this.census = census;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	
	

   
    
    
    
    
}
