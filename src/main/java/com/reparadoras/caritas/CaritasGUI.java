/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reparadoras.caritas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import com.reparadoras.caritas.ui.JImportPeople;
import com.reparadoras.caritas.ui.JMainWindow;
import com.reparadoras.caritas.ui.JManagePeople;
import com.reparadoras.caritas.ui.JManageTicket;

import javax.swing.Icon;
import java.awt.event.ActionListener;

/**
 *
 * @author rociomunoz
 */
public class CaritasGUI  extends JFrame{
    
	private JDesktopPane jDesktopPane = null;
	
	private JMenuBar jMenuBar = null;
	private JMenu jMenuFile = null;
	private JMenuItem jMenuItemExit = null;
	private JMenu jMenuManage = null;
	private JMenuItem jMenuItemPeople = null;
	
    public CaritasGUI() {

    	initLookAndFeel();
        initUI();
        
    }
    
    private void initLookAndFeel() {
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
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
        
    	
    	
        createMenuBar();

        setTitle("Caritas Parroquial");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        this.setVisible(true);
        
        
        jDesktopPane = new JDesktopPane();
        ImageIcon icon = new ImageIcon("./com/reparadoras/images/logo.PNG");
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
    
    private void createMenuBar() {

    	jMenuBar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");

        jMenuFile = new JMenu("Archivo");
        jMenuFile.setMnemonic(KeyEvent.VK_F);
        
        jMenuManage = new JMenu("Gestion");
        jMenuManage.setMnemonic(KeyEvent.VK_F);
        
       

        jMenuItemExit = new JMenuItem("Salir", icon);
        jMenuItemExit.setMnemonic(KeyEvent.VK_E);
        jMenuItemExit.setToolTipText("Exit application");
        jMenuItemExit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        
        jMenuItemPeople = new JMenuItem("Fichas Persona", icon);
        jMenuItemPeople.setMnemonic(KeyEvent.VK_E);
        jMenuItemPeople.setToolTipText("Exit application");
        jMenuItemPeople.addActionListener((ActionEvent event) -> {
        	jMenuItemPeopleActionPerformed(event);
        });
        
        JMenuItem jMenuItemImport = new JMenuItem("Importar", (Icon) null);
        jMenuItemImport.addActionListener((ActionEvent event) -> {
        	jMenuItemImportActionPerformed(event);
        });
        jMenuFile.add(jMenuItemImport);
        jMenuItemImport.setToolTipText("Importar");
        jMenuItemImport.setMnemonic(KeyEvent.VK_E);

        jMenuFile.add(jMenuItemExit);
        jMenuManage.add(jMenuItemPeople);

        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuManage);
        
        JMenuItem jMenuItemPrimaryProgram = new JMenuItem("Programa de Atencion Primaria", (Icon) null);
        jMenuItemPrimaryProgram.setToolTipText("Exit application");
        jMenuItemPrimaryProgram.setMnemonic(KeyEvent.VK_E);
        jMenuManage.add(jMenuItemPrimaryProgram);
        
        JMenuItem jMenuItemPoints = new JMenuItem("Vales", (Icon) null);
        jMenuItemPoints.addActionListener((ActionEvent event) -> {
        	jMenuItemTicketsPeopleActionPerformed(event);
        });
        jMenuItemPoints.setToolTipText("Exit application");
        jMenuItemPoints.setMnemonic(KeyEvent.VK_E);
        jMenuManage.add(jMenuItemPoints);
       

        setJMenuBar(jMenuBar);
    }
    
    private void jMenuItemImportActionPerformed(java.awt.event.ActionEvent evt) {
        openImportWindow();
    }
    
    private void openImportWindow() {
        
        try {
        	JImportPeople jImportPeople = new JImportPeople();
        	//jDesktopPane.add(jImportPeople);
        	
        	
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    private void jMenuItemTicketsPeopleActionPerformed(java.awt.event.ActionEvent evt) {
        openManageTicketPeopleWindow();
    }
    
    private void openManageTicketPeopleWindow() {
        
        
        try {
        	JManageTicket jManageTicket = new JManageTicket(jDesktopPane);
        	jDesktopPane.add(jManageTicket);
        	
        	jManageTicket.setMaximum(true);
        	jManageTicket.setMaximizable(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    private void jMenuItemPeopleActionPerformed(java.awt.event.ActionEvent evt) {
        openManagePeopleWindow();
    }
    
    private void openManagePeopleWindow() {
       
       
        try {
        	JManagePeople jManagePeople = new JManagePeople(jDesktopPane);
        	jDesktopPane.add(jManagePeople);
        	
        	jManagePeople.setMaximum(true);
        	jManagePeople.setMaximizable(true);
            
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
