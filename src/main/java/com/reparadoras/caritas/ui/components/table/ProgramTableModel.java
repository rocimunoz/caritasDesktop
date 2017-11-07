package com.reparadoras.caritas.ui.components.table;

import java.util.Date;
import java.util.List;

import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;

public class ProgramTableModel extends GenericDomainTableModel<Program>{

	public ProgramTableModel(List identifiers){
		super(identifiers);
		
	}
	
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
        case 0: return String.class; //dni
        case 1: return String.class; //passport
        case 2: return String.class; //nombre
        case 3: return String.class; //APELLIDO
        case 4: return Date.class; //fecha
    }
    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Program program = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: return program.getPeople().getDni();
            case 1: return program.getPeople().getPassport();
            case 2: return program.getPeople().getName();
            case 3: return program.getPeople().getFirstSurname();
            case 4: return program.getPeople().getCreateDate();
            
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
        }

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Program program = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: program.getPeople().setDni((String)aValue); break;
            case 1: program.getPeople().setPassport((String)aValue); break;
            case 2: program.getPeople().setName((String)aValue); break;
            case 3: program.getPeople().setFirstSurname((String)aValue);break;
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
		
	}

}
