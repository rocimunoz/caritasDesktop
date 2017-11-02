package com.reparadoras.caritas.ui.utils;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RelativeVerifier extends InputVerifier {

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
		
		if (name.equals("surname")) {
			String text = ((JTextField) input).getText().trim();
			if (text.isEmpty()) {
				message = "Revisar los campos introducidos. El primer apellido no puede estar vacio";
				state = false;
			} else
				state = true;
		}
		
		
		if (name.equals("relationShip")) {
			String text = ((JTextField) input).getText().trim();
			if (text.isEmpty()) {
				message = "Revisar los campos introducidos. El parentesco no puede estar vacio";
				state = false;
			} else
				state = true;
		}
		if (name.equals("dateBorn")) {
			String text = ((JTextField) input).getText().trim();
			if (text.isEmpty()) {
				message = "Revisar los campos introducidos. La fecha de nacimiento no puede estar vacia";
				state = false;
			} else
				state = true;
		}
		
		
		
		if (state == false){
			JOptionPane.showMessageDialog(input, message, "Error Dialog", JOptionPane.ERROR_MESSAGE);
			
		}
		
		return state;
		

	}

}
