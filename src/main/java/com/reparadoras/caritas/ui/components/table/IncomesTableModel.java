package com.reparadoras.caritas.ui.components.table;

import java.util.Date;
import java.util.List;

import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;

public class IncomesTableModel extends GenericDomainTableModel<Income>{

	public IncomesTableModel(List identifiers){
		super(identifiers);
		
	}
	
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
        case 0: return String.class;
        case 1: return String.class;
        case 2: return Integer.class;
        case 3: return Date.class;
        
    }
    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Income income = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: return "";
            case 1: return income.getConcept();
            case 2: return income.getAmount();
            case 3: return income.getEndDate();
            
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
        }

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Income income = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: ; break;
            case 1: income.setConcept((String)aValue);break;
            case 2: income.setAmount((Integer)aValue); break;
            case 3: income.setEndDate((Date)aValue); break;
           
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
		
	}

}
