package com.reparadoras.caritas.ui.components.table;

import java.util.Date;
import java.util.List;

import com.reparadoras.caritas.model.Answer;
import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;

public class AnswerPeopleTableModel extends GenericDomainTableModel<Answer>{

	public AnswerPeopleTableModel(List identifiers){
		super(identifiers);
		
	}
	
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
        case 0: return String.class;
        case 1: return String.class;
        case 2: return Date.class;
        case 3: return String.class;
        
    }
    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Answer answer = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: return answer.getPeople();
            case 1: return answer.getMonth();
            case 2: return answer.getDate();
            case 3: return answer.getAnswer();
            
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
        }

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Answer answer = getDomainObject(rowIndex);
		/*
        switch(columnIndex) {
            case 1: answer.setConcept((String)aValue);break;
            case 2: answer.setAmount((Double)aValue); break;
            case 3: income.setEndDate((Date)aValue); break;
           
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
        }*/
        notifyTableCellUpdated(rowIndex, columnIndex);
		
	}

}
