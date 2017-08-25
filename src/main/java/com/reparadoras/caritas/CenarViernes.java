package com.reparadoras.caritas;

import java.util.Random;

public class CenarViernes {

	
	 public static void main(String[] args) {
		 
		int numeroCena;
		 int minimo = 1;
		 int maximo = 100;

		 numeroCena = ((int) (Math.random()*(maximo - minimo))) + minimo;
		 
		 System.out.println("El numero elegido es:    " + numeroCena);
		 System.out.println("Por lo tanto .......");
		 
		 if (numeroCena <50){
			 System.out.println("Cenamos en el Japones");
		 }
		 else if (numeroCena >50){
			 System.out.println("Cenamos en casa comida india");
		 }else{
			 System.out.println("Da igual donde cenamos, paga David");
		 }
		 
		 
	 }
	
	
}
