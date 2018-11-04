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



public class MonthlyReport implements Serializable{

	private Long id;
	private String atencion;
	private String apellidos;
	private String nombre;
	private String documentacion;
	private String fechaNacimiento;
	private String sexo;
	private String domicilio;
	private String numPersonas;
	private String tipoFamilia;
	private String estadoCivil;
	private String hijosMenor18;
	private String hijosMayor18;
	private String anyoLlegada;
	private String nacionalidad;
	private String tipoAutorizacion;
	private String situacionLaboral;
	private String estudios;
	//private String demandas;
	
	private Double respuestaImporte;
	private Integer valorTicket;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAtencion() {
		return atencion;
	}
	public void setAtencion(String atencion) {
		this.atencion = atencion;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDocumentacion() {
		return documentacion;
	}
	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getNumPersonas() {
		return numPersonas;
	}
	public void setNumPersonas(String numPersonas) {
		this.numPersonas = numPersonas;
	}
	public String getTipoFamilia() {
		return tipoFamilia;
	}
	public void setTipoFamilia(String tipoFamilia) {
		this.tipoFamilia = tipoFamilia;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getHijosMenor18() {
		return hijosMenor18;
	}
	public void setHijosMenor18(String hijosMenor18) {
		this.hijosMenor18 = hijosMenor18;
	}
	public String getHijosMayor18() {
		return hijosMayor18;
	}
	public void setHijosMayor18(String hijosMayor18) {
		this.hijosMayor18 = hijosMayor18;
	}
	public String getAnyoLlegada() {
		return anyoLlegada;
	}
	public void setAnyoLlegada(String anyoLlegada) {
		this.anyoLlegada = anyoLlegada;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getTipoAutorizacion() {
		return tipoAutorizacion;
	}
	public void setTipoAutorizacion(String tipoAutorizacion) {
		this.tipoAutorizacion = tipoAutorizacion;
	}
	public String getSituacionLaboral() {
		return situacionLaboral;
	}
	public void setSituacionLaboral(String situacionLaboral) {
		this.situacionLaboral = situacionLaboral;
	}
	public String getEstudios() {
		return estudios;
	}
	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}
//	public String getDemandas() {
//		return demandas;
//	}
//	public void setDemandas(String demandas) {
//		this.demandas = demandas;
//	}
	
	public Integer getValorTicket() {
		return valorTicket;
	}
	public void setValorTicket(Integer valorTicket) {
		this.valorTicket = valorTicket;
	}
	public Double getRespuestaImporte() {
		return respuestaImporte;
	}
	public void setRespuestaImporte(Double respuestaImporte) {
		this.respuestaImporte = respuestaImporte;
	}
	
	
	
	
	
	
	
	
	

   
    
    
    
    
}
