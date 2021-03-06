package com.reparadoras.caritas.ui.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PeopleVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {
		String name = input.getName();
		boolean state =true;
		String message = "";
		if (name.equals("name")) {
			String text = ((JTextField) input).getText().trim();
			if (text.isEmpty()) {
				message = "Revisar los campos introducidos. El nombre no puede estar vacio";
				state = false;
			} else
				state = true;
		}
		
		if (name.equals("firstSurname")) {
			String text = ((JTextField) input).getText().trim();
			if (text.isEmpty()) {
				message = "Revisar los campos introducidos. El primer apellido no puede estar vacio";
				state = false;
			} else
				state = true;
		}
		
		if (name.equals("secondSurname")) {
			String text = ((JTextField) input).getText().trim();
			if (text.isEmpty()) {
				message = "Revisar los campos introducidos. El segundo apellido no puede estar vacio";
				state = false;
			} else
				state = true;
		}
		
		if (name.equals("createDate")) {
			String date = ((JTextField) input).getText().trim();
		
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			//convert String to LocalDate
			LocalDateTime localDate = LocalDateTime.parse(date, formatter);
			if (localDate.isAfter(LocalDateTime.now())){
				message = "Revisar los campos introducidos. La fecha de alta no puede ser superior a la fecha actual";
				state = false;
			}
			
		}
		
		
		
		if (state == false){
			JOptionPane.showMessageDialog(input, message, "Error Dialog", JOptionPane.ERROR_MESSAGE);
			
		}
		
		return state;
		

	}

}
