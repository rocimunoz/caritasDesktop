package com.reparadoras.caritas.ui.components.datepicker;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.table.DatePickerCellEditor;

/*
public class CaritasDatePickerCellEditor extends DatePickerCellEditor{
	
	
	public CaritasDatePickerCellEditor() {
        super();
    }

    public CaritasDatePickerCellEditor(DateFormat dateFormat) {
        super(dateFormat);
    }

    public void setLowerBound(Date date) {
        datePicker.getMonthView().setLowerBound(date);          
    }

    public void setUpperBound(Date date) {
        datePicker.getMonthView().setLowerBound(date);          
    }
    
   */

public class CaritasDatePickerCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener { 
    private JXDatePicker datePicker = new JXDatePicker(); 

    /** Creates a new instance of DateCellEditor */ 
    public CaritasDatePickerCellEditor() { 
        datePicker.addActionListener(this); 
        
        datePicker.setFormats(new SimpleDateFormat("dd-MMM-yyyy")); 
    } 

    public Object getCellEditorValue() { 
        return datePicker.getDate(); 
    } 

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 
        java.util.Date date = (java.util.Date) value; 

        datePicker.setDate(date); 

        return datePicker; 
    } 
    
    public void setLowerBound(Date date) {
        datePicker.getMonthView().setLowerBound(date);          
    }

    public void setUpperBound(Date date) {
        datePicker.getMonthView().setLowerBound(date);          
    }

    public void actionPerformed(ActionEvent e) { 
        fireEditingStopped(); 
    } 
} 


