package com.reparadoras.caritas.ui.components;

import java.awt.Component;
import java.text.Format;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FormattedCellRenderer extends DefaultTableCellRenderer { 
    
    protected Format format; 


    public FormattedCellRenderer(Format format) { 
        this.format = format; 
    } 

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, 
            int nRow, int nCol) { 
        return super.getTableCellRendererComponent(table, (value == null) 
                ? null 
                : format.format(value), isSelected, hasFocus, nRow, nCol); 

    } 
}
