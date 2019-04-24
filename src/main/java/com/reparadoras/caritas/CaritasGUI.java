/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import com.reparadoras.caritas.ui.JMainWindow;
import com.reparadoras.caritas.ui.JManagePeople;
import com.reparadoras.caritas.ui.JManageTicket;
import com.reparadoras.caritas.ui.utils.Constants;

import javax.swing.Icon;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

/**
 *
 * @author rociomunoz
 */
public class CaritasGUI  extends JFrame{
    
	private JDesktopPane jDesktopPane = null;
	

	
    public CaritasGUI() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(CaritasGUI.class.getResource("/img/caritas.png")));

    	initLookAndFeel();
        initUI();
        readProperties();
        
    }
    
    private void readProperties() {
    	
    	
    	Properties properties = new Properties();
    	InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
    	if (inputStream!=null) {
    		try {
    			properties.load(inputStream);
    			Constants.MYSQL_DUMP_PATH = (String) properties.get("mysql.dump");
    			Constants.MYSQL_LOAD_PATH = (String) properties.get("mysql.load");
    			Constants.MYSQL_USER = (String) properties.get("caritas.username");
    			Constants.MYSQL_PASS = (String) properties.get("caritas.password");
    			
    		}catch(Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	}
    	
    }
    
    private void initLookAndFeel() {
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            	System.out.println(info);
                
            	if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    //break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CaritasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CaritasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CaritasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CaritasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
	}

	
    
    private void initUI() {
        
    	
    	
       // createMenuBar();

        setTitle("Caritas Parroquial");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        this.setVisible(true);
        
        
        jDesktopPane = new JDesktopPane();
        ImageIcon icon = new ImageIcon("logo.PNG");
        JLabel l = new JLabel(icon);
        jDesktopPane.add(l, new Integer(Integer.MIN_VALUE));
        getContentPane().add(jDesktopPane);
        
        try {
        	JMainWindow jMainWindow = new JMainWindow(jDesktopPane);
        	jDesktopPane.add(jMainWindow);
        	
        	jMainWindow.setMaximum(true);
        	//jMainWindow.setMaximizable(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
      
        
        
    }
    
   
    
   
    
    
    
   
    
   
    
   
    
    public static void main(String args[]) {
      

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CaritasGUI();
        });
    }
    
}
