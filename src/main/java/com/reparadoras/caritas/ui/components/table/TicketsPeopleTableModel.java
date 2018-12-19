package com.reparadoras.caritas.ui.components.table;


import java.sql.Date;
import java.util.List;

import javax.swing.table.TableColumn;

import com.reparadoras.caritas.model.Ticket;


public class TicketsPeopleTableModel extends GenericDomainTableModel<Ticket>
{

    public TicketsPeopleTableModel(List identifiers)
    
    {
	super(identifiers);

    }


    @Override
    public Class<?> getColumnClass(int columnIndex)
    {

    	switch (columnIndex) {
    	case 0: return Integer.class;
    	case 1: return Date.class;
    	case 2: return Integer.class;
    	case 3: return Date.class;
    	case 4: return Integer.class;
    	case 5: return Date.class;
    	case 6: return Integer.class;
    	case 7: return Date.class;
    	case 8: return Integer.class;
    	case 9: return Date.class;
    	case 10: return Integer.class;
    	case 11: return Date.class;
    	case 12: return Integer.class;
    	case 13: return Date.class;
    	case 14: return Integer.class;
    	case 15: return Date.class;
    	case 16: return Integer.class;
    	case 17: return Date.class;
    	case 18: return Integer.class;
    	case 19: return Date.class;
    	case 20: return Integer.class;
    	case 21: return Date.class;
    	case 22: return Integer.class;
    	case 23: return Date.class;
    	case 24: return Integer.class;

    	default:
    	    throw new ArrayIndexOutOfBoundsException(columnIndex);
    	}
		}
    	
    	
    	
    	


    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {

	Ticket ticket = getDomainObject(rowIndex);
	switch (columnIndex)
	{
	case 0:
	    return ticket.getYear();
	case 1:
	    return ticket.getDateJanuary();
	case 2:
	    return ticket.getPointsJanuary();
	case 3:
	    return ticket.getDateFebruary();
	case 4:
	    return ticket.getPointsFebruary();
	case 5:
	    return ticket.getDateMarch();
	case 6:
	    return ticket.getPointsMarch();
	case 7:
	    return ticket.getDateApril();
	case 8:
	    return ticket.getPointsApril();
	case 9:
	    return ticket.getDateMay();
	case 10:
	    return ticket.getPointsMay();
	case 11:
	    return ticket.getDateJune();
	case 12:
	    return ticket.getPointsJune();
	case 13:
	    return ticket.getDateJuly();
	case 14:
	    return ticket.getPointsJuly();
	case 15:
	    return ticket.getDateAugust();
	case 16:
	    return ticket.getPointsAugust();
	case 17:
	    return ticket.getDateSeptember();
	case 18:
	    return ticket.getPointsSeptember();
	case 19:
	    return ticket.getDateOctober();
	case 20:
	    return ticket.getPointsOctober();
	case 21:
	    return ticket.getDateNovember();
	case 22:
	    return ticket.getPointsNovember();
	case 23:
	    return ticket.getDateDecember();
	case 24:
	    return ticket.getPointsDecember();

	default:
	    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {

	Ticket ticket = getDomainObject(rowIndex);
	switch (columnIndex)
	{

	case 1:
	    ticket.setDateJanuary((java.util.Date) aValue);
	    break;
	case 2:
	    ticket.setPointsJanuary((Integer) aValue);
	    break;
	case 3:
		 ticket.setDateFebruary((java.util.Date) aValue);
	    break;
	case 4:
	    ticket.setPointsFebruary((Integer) aValue);
	    break;
	case 5:
		 ticket.setDateMarch((java.util.Date) aValue);
	    break;
	case 6:
	    ticket.setPointsMarch((Integer) aValue);
	    break;
	case 7:
		 ticket.setDateApril((java.util.Date) aValue);
	    break;
	case 8:
	    ticket.setPointsApril((Integer) aValue);
	    break;
	case 9:
		 ticket.setDateMay((java.util.Date) aValue);
	    break;
	case 10:
	    ticket.setPointsMay((Integer) aValue);
	    break;
	case 11:
		 ticket.setDateJune((java.util.Date) aValue);
	    break;
	case 12:
	    ticket.setPointsJune((Integer) aValue);
	    break;
	case 13:
		 ticket.setDateJuly((java.util.Date) aValue);
	    break;
	case 14:
	    ticket.setPointsJuly((Integer) aValue);
	    break;
	case 15:
		 ticket.setDateAugust((java.util.Date) aValue);
	    break;
	case 16:
	    ticket.setPointsAugust((Integer) aValue);
	    break;
	case 17:
		 ticket.setDateSeptember((java.util.Date) aValue);
	    break;
	case 18:
	    ticket.setPointsSeptember((Integer) aValue);
	    break;
	case 19:
		 ticket.setDateOctober((java.util.Date) aValue);
	    break;
	case 20:
	    ticket.setPointsOctober((Integer) aValue);
	    break;
	case 21:
		 ticket.setDateNovember((java.util.Date) aValue);
	    break;
	case 22:
	    ticket.setPointsNovember((Integer) aValue);
	    break;
	case 23:
		 ticket.setDateDecember((java.util.Date) aValue);
	    break;
	case 24:
	    ticket.setPointsDecember((Integer) aValue);
	    break;
	default:
	    throw new ArrayIndexOutOfBoundsException(columnIndex);
	}
	notifyTableCellUpdated(rowIndex, columnIndex);

    }
    
    

}
