package com.reparadoras.caritas.ui.utils;

import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
	private int limit;

	public JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	public JTextFieldLimit(int limit, boolean upper) {
	    super();
	    this.limit = limit;
	  }
	
}