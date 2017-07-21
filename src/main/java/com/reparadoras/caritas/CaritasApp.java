package com.reparadoras.caritas;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;



/**
 *
 * @author rociomunoz
 */

public class CaritasApp{
    
    public static void main(String[] args) {
    
    	SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                CaritasGUI caritas = new CaritasGUI();
               
            }
        });
		

		


    
    }
    
}
