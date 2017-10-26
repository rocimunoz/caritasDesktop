/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas.model;

import java.io.Serializable;
import java.util.Date;



public class Program implements Serializable{

    private Long id;
    
    private People people;
    private Family family;
    private AuthorizationType authorizationType;
    private JobSituation jobSituation;
    private Studies studies;
    
    
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public People getPeople() {
		return people;
	}
	public void setPeople(People people) {
		this.people = people;
	}
	public Family getFamily() {
		return family;
	}
	public void setFamily(Family family) {
		this.family = family;
	}
	public AuthorizationType getAuthorizationType() {
		return authorizationType;
	}
	public void setAuthorizationType(AuthorizationType authorizationType) {
		this.authorizationType = authorizationType;
	}
	public JobSituation getJobSituation() {
		return jobSituation;
	}
	public void setJobSituation(JobSituation jobSituation) {
		this.jobSituation = jobSituation;
	}
	public Studies getStudies() {
		return studies;
	}
	public void setStudies(Studies studies) {
		this.studies = studies;
	}
	
	
	
	
	
    
    

	

	
	
	
    
    

	

   

   
    
    
    
}
