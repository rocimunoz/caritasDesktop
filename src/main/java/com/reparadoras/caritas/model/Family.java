/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;



public class Family implements Serializable{

	
	private Set<Relative> relatives;
	private TypeFamily typeFamily;
	private String otherInfo;
	public Set<Relative> getRelatives() {
		return relatives;
	}
	public void setRelatives(Set<Relative> relatives) {
		this.relatives = relatives;
	}
	public TypeFamily getTypeFamily() {
		return typeFamily;
	}
	public void setTypeFamily(TypeFamily typeFamily) {
		this.typeFamily = typeFamily;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	
	
	
    
   
    
    
    
}
