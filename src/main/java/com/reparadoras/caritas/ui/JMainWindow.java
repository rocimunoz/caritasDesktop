package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.table.PeopleTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class JMainWindow extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	private JDesktopPane desktop = null;
	private PeopleTableModel peopleTableModel = null;
	
	private PeopleDAO peopleDAO;
	private ProgramDAO programDAO;
	private TicketDAO ticketDAO;
	private JLabel lblLogo;
	private JPanel jPanelMenu;
	private JLabel lblRegPeople;
	private JLabel lblProgram;
	private JLabel lblTickets;
	private JPanel panel;
	private JLabel lblExit;


	public JMainWindow(JDesktopPane desktop) {
		super(desktop);
		getContentPane().setBackground(Color.WHITE);
		this.desktop = desktop;
		this.setTitle("Gestion Personas");
		this.setVisible(true);

		this.pack();
		this.setResizable(true);
		this.setClosable(true);
		
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		ticketDAO = new TicketDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		
		createGUIComponents();
		initComponents();

		
		addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
               
            }
        });

		

	}

	
	public void createGUIComponents(){
		getContentPane().setLayout(getGridContentPane());
		GridBagConstraints gbc_jPanelMenu = new GridBagConstraints();
		gbc_jPanelMenu.insets = new Insets(0, 0, 5, 5);
		gbc_jPanelMenu.weightx = 1.0;
		gbc_jPanelMenu.weighty = 1.0;
		gbc_jPanelMenu.fill = GridBagConstraints.BOTH;
		gbc_jPanelMenu.gridx = 0;
		gbc_jPanelMenu.gridy = 0;
		getContentPane().add(getJPanelMenu(), gbc_jPanelMenu);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weighty = 1.0;
		gbc_panel.weightx = 1.0;
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(0, 0, 5, 40);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(getPanel(), gbc_panel);
	}
	
	public void initComponents(){
	}
	
	
	/* FUNCIONES DEL GETCONTENTPANE */

	private GridBagLayout getGridContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0 };

		return gridBagLayout;
	}
	
	
	
	
	
	

	
	/* EVENTOS */

	
	
	
	
	
		
	
	
	private JLabel getLblLogo() {
		if (lblLogo == null) {
			lblLogo = new JLabel("");
			lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
			lblLogo.setIcon(new ImageIcon(JMainWindow.class.getResource("/com/reparadoras/images/logo2.PNG")));
		}
		return lblLogo;
	}
	private JPanel getJPanelMenu() {
		if (jPanelMenu == null) {
			jPanelMenu = new JPanel();
			jPanelMenu.setBackground(Color.WHITE);
			GridBagLayout gbl_jPanelMenu = new GridBagLayout();
			gbl_jPanelMenu.columnWeights = new double[]{1.0};
			gbl_jPanelMenu.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			jPanelMenu.setLayout(gbl_jPanelMenu);
			GridBagConstraints gbc_lblRegPeople = new GridBagConstraints();
			gbc_lblRegPeople.insets = new Insets(20, 0, 20, 0);
			gbc_lblRegPeople.weightx = 1.0;
			gbc_lblRegPeople.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblRegPeople.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblRegPeople.gridx = 0;
			gbc_lblRegPeople.gridy = 1;
			jPanelMenu.add(getLblRegPeople(), gbc_lblRegPeople);
			GridBagConstraints gbc_lblTickets = new GridBagConstraints();
			gbc_lblTickets.insets = new Insets(20, 0, 20, 0);
			gbc_lblTickets.weightx = 1.0;
			gbc_lblTickets.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblTickets.gridx = 0;
			gbc_lblTickets.gridy = 2;
			jPanelMenu.add(getLblTickets(), gbc_lblTickets);
			GridBagConstraints gbc_lblLogo = new GridBagConstraints();
			gbc_lblLogo.insets = new Insets(0, 0, 5, 0);
			gbc_lblLogo.weighty = 1.0;
			gbc_lblLogo.fill = GridBagConstraints.BOTH;
			gbc_lblLogo.weightx = 1.0;
			gbc_lblLogo.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblLogo.gridx = 0;
			gbc_lblLogo.gridy = 0;
			jPanelMenu.add(getLblLogo(), gbc_lblLogo);
			GridBagConstraints gbc_lblProgram = new GridBagConstraints();
			gbc_lblProgram.insets = new Insets(20, 0, 5, 20);
			gbc_lblProgram.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblProgram.weightx = 1.0;
			gbc_lblProgram.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblProgram.gridx = 0;
			gbc_lblProgram.gridy = 3;
			jPanelMenu.add(getLblProgram(), gbc_lblProgram);
		}
		return jPanelMenu;
	}
	private JLabel getLblRegPeople() {
		if (lblRegPeople == null) {
			lblRegPeople = new JLabel("  Fichero Registro Personas ");
			lblRegPeople.setHorizontalTextPosition(SwingConstants.RIGHT);
			lblRegPeople.setFont(new Font("Verdana", Font.BOLD, 30));
			lblRegPeople.setForeground(Color.BLACK);
			lblRegPeople.setBackground(Color.WHITE);
			lblRegPeople.setBorder(null);
			lblRegPeople.setHorizontalAlignment(SwingConstants.CENTER);
			lblRegPeople.setIcon(new ImageIcon(JMainWindow.class.getResource("/com/reparadoras/images/icon-program-64.png")));
		}
		return lblRegPeople;
	}
	private JLabel getLblProgram() {
		if (lblProgram == null) {
			lblProgram = new JLabel("  Programa de Atención Primaria");
			lblProgram.setHorizontalAlignment(SwingConstants.CENTER);
			lblProgram.setIcon(new ImageIcon(JMainWindow.class.getResource("/com/reparadoras/images/icon-program-64.png")));
			lblProgram.setForeground(Color.BLACK);
			lblProgram.setFont(new Font("Verdana", Font.BOLD, 30));
			lblProgram.setHorizontalTextPosition(SwingConstants.RIGHT);
			lblProgram.setBackground(Color.WHITE);
			lblProgram.setBorder(null);
		}
		return lblProgram;
	}
	private JLabel getLblTickets() {
		if (lblTickets == null) {
			lblTickets = new JLabel("  Gestión de Vales");
			lblTickets.setIcon(new ImageIcon(JMainWindow.class.getResource("/com/reparadoras/images/icon-program-64.png")));
			lblTickets.setForeground(Color.BLACK);
			lblTickets.setFont(new Font("Verdana", Font.BOLD, 30));
			lblTickets.setHorizontalTextPosition(SwingConstants.RIGHT);
			lblTickets.setHorizontalAlignment(SwingConstants.CENTER);
			lblTickets.setBorder(null);
		}
		return lblTickets;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setVgap(0);
			flowLayout.setHgap(0);
			panel.add(getLblExit());
		}
		return panel;
	}
	private JLabel getLblExit() {
		if (lblExit == null) {
			lblExit = new JLabel("Salir");
			lblExit.setFont(new Font("Verdana", Font.PLAIN, 18));
			lblExit.setHorizontalTextPosition(SwingConstants.RIGHT);
			lblExit.setHorizontalAlignment(SwingConstants.RIGHT);
			lblExit.setIcon(new ImageIcon(JMainWindow.class.getResource("/com/reparadoras/images/icon-exit-64.png")));
		}
		return lblExit;
	}
}
	
	
	

