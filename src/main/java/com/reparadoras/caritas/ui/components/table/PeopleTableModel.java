package com.reparadoras.caritas.ui.components.table;

import java.util.List;

import com.reparadoras.caritas.model.People;

public class PeopleTableModel extends GenericDomainTableModel<People>{

	public PeopleTableModel(List identifiers){
		super(identifiers);
		
	}
	
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
        case 0: return String.class;
        case 1: return String.class;
        case 2: return String.class;
        case 3: return String.class;
        case 4: return String.class;
    }
    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		People people = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: return people.getDni();
            case 1: return people.getPassport();
            case 2: return people.getName();
            case 3: return people.getFirstSurname();
            case 4: return people.getSecondSurname();
            
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
        }

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		People people = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: people.setDni((String)aValue); break;
            case 1: people.setPassport((String)aValue); break;
            case 2: people.setName((String)aValue); break;
            case 3: people.setFirstSurname((String)aValue); break;
            case 4: people.setSecondSurname((String)aValue); break;
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
		
	}

}
