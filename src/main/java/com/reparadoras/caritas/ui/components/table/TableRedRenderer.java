package com.reparadoras.caritas.ui.components.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class TableRedRenderer extends DefaultTableCellRenderer {
	   public Component getTableCellRendererComponent(
	            JTable table, Object value, boolean isSelected,
	            boolean hasFocus, int row, int column)
	   {
	      TableModel tablemodel = (TableModel) table.getModel();
	    
	  
	      if (column == 0) {
	    	  this.setForeground(Color.red);
	        
	      }
	      
	  
	      return super.getTableCellRendererComponent(table, value, isSelected,
	                                                 hasFocus, row, column);
	   }
	}
