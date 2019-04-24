package com.reparadoras.caritas.ui.components.table;

import java.util.Date;
import java.util.List;

import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;

public class RelativesTableModel extends GenericDomainTableModel<Relative>{

	public RelativesTableModel(List identifiers){
		super(identifiers);
		
	}
	
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
		
        case 0: return String.class;
        case 1: return Date.class;
        case 2: return String.class;
        case 3: return String.class;
        case 4: return String.class;
    }
    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Relative relative = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: return relative.getName();
            case 1: return relative.getDateBorn();
            case 2: return relative.getRelationShip();
            case 3: return relative.getLiveWork();
            case 4: return relative.getSituation();
            
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
        }

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Relative relative = getDomainObject(rowIndex);
        switch(columnIndex) {
            case 0: relative.setName((String)aValue); break;
            case 1: relative.setDateBorn((Date)aValue);break;
            case 2: relative.setRelationShip((String)aValue); break;
            case 3: relative.setLiveWork((String)aValue); break;
            case 4: relative.setSituation((String) aValue); break;
                default: throw new ArrayIndexOutOfBoundsException(columnIndex);
        }
        notifyTableCellUpdated(rowIndex, columnIndex);
		
	}

}
