/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas.model;

import java.io.Serializable;





public class Home implements Serializable{

	
private static final long serialVersionUID = 1L;
	

private String regHolding; //entidad type regHolding
private int numberRooms;
private int numberPeople;
private int numberFamilies;
private String otherInfo;

private HomeType homeType;
private Address address;



public String getRegHolding() {
	return regHolding;
}

public void setRegHolding(String regHolding) {
	this.regHolding = regHolding;
}

public int getNumberRooms() {
	return numberRooms;
}

public void setNumberRooms(int numberRooms) {
	this.numberRooms = numberRooms;
}

public int getNumberPeople() {
	return numberPeople;
}

public void setNumberPeople(int numberPeople) {
	this.numberPeople = numberPeople;
}

public int getNumberFamilies() {
	return numberFamilies;
}

public void setNumberFamilies(int numberFamilies) {
	this.numberFamilies = numberFamilies;
}

public String getOtherInfo() {
	return otherInfo;
}

public void setOtherInfo(String otherInfo) {
	this.otherInfo = otherInfo;
}

public Address getAddress() {
	return address;
}

public void setAddress(Address address) {
	this.address = address;
}

public HomeType getHomeType() {
	return homeType;
}

public void setHomeType(HomeType homeType) {
	this.homeType = homeType;
}



   
    
    

     
 
    
    
    
    
}
