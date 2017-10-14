package com.reparadoras.caritas.ui.components.table;

import java.util.Date;
import java.util.List;

import com.reparadoras.caritas.model.Expense;
import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;

public class ExpensesTableModel extends GenericDomainTableModel<Expense>{

	public ExpensesTableModel(List identifiers){
		super(identifiers);
		
	}
	
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
        case 0: return String.class;
        case 1: return Double.class;
        case 2: return String.class;
        case 3: return Date.class;
        
    }
    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Expense expense = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: return expense.getConcept() ;
            case 1: return expense.getAmount();
            case 2: return expense.getRegularity();
            case 3: return expense.getEndDate();
            
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
        }

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Expense expense = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: expense.setConcept((String)aValue); break;
            case 1: expense.setAmount((Double)aValue);break;
            case 2: expense.setRegularity((String)aValue); break;
            case 3: expense.setEndDate((Date)aValue); break;
           
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
		
	}

}
