package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;

import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class JPanelJobSituation extends JPanel{
	
	
	private JPanel jPanelRadioButton;
	private JRadioButton jRadioUnemployee;
	private JRadioButton jRadioNormalJob;
	private JRadioButton jRadioMarginalJob;
	private JRadioButton jRadioHouseJob;
	private JRadioButton jRadioRetired;
	private JRadioButton jRadioOthers;
	
	
	
	public JPanelJobSituation() {
		
		createGUIPanel();
		
		
		
	
	}
	
	
	
	private void createGUIPanel(){
		
		
		this.setLayout(getGridJobSituation());
		
		//Añado elementos del Tab Vivienda
		this.add(getJPanelRadioButton(), getGridJPanelRadio());
		
		
	}
	
	private GridBagLayout getGridJobSituation() {
		GridBagLayout gbl_LayoutJobSituation = new GridBagLayout();
		gbl_LayoutJobSituation.columnWeights = new double[] { 0.0};
		gbl_LayoutJobSituation.rowWeights = new double[] { 0.0};

		return gbl_LayoutJobSituation;
	}
	
	
	private JPanel getJPanelRadioButton() {
		if (jPanelRadioButton == null) {
		
			jPanelRadioButton = new JPanel();
			jPanelRadioButton.setLayout(new GridLayout(0, 1, 0, 0));
			jPanelRadioButton.add(getjRadioUnemployee());
			jPanelRadioButton.add(getjRadioNormalJob());
			jPanelRadioButton.add(getjRadioHouseJob());
			jPanelRadioButton.add(getjRadioMarginalJob());
			jPanelRadioButton.add(getjRadioOthers());
			jPanelRadioButton.add(getjRadioRetired());
			
			
			ButtonGroup groupL1 = new ButtonGroup();
			groupL1.add(getjRadioUnemployee());
			groupL1.add(getjRadioNormalJob());
			groupL1.add(getjRadioHouseJob());
			groupL1.add(getjRadioMarginalJob());
			groupL1.add(getjRadioOthers());
			groupL1.add(getjRadioRetired());
			
		}
		return jPanelRadioButton;
	}
	
	private GridBagConstraints getGridJPanelRadio() {
		GridBagConstraints gbc_jPanelRadio = new GridBagConstraints();
		gbc_jPanelRadio.weighty = 1.0;
		gbc_jPanelRadio.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelRadio.weightx = 1.0;
		gbc_jPanelRadio.insets = new Insets(0, 10, 5, 0);
		gbc_jPanelRadio.gridx = 0;
		gbc_jPanelRadio.gridy = 0;

		return gbc_jPanelRadio;
	}
	
	
	
	public JRadioButton getjRadioUnemployee() {
		if (jRadioUnemployee == null) {
			jRadioUnemployee = new JRadioButton("Parado");
			
			jRadioUnemployee.setMargin(new Insets(20, 20, 2, 20));
			jRadioUnemployee.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioUnemployee;
		
	}

	public JRadioButton getjRadioNormalJob() {
		
		if (jRadioNormalJob == null) {
			jRadioNormalJob = new JRadioButton("Con Trabajo Normalizado");
			
			jRadioNormalJob.setMargin(new Insets(20, 20, 2, 20));
			jRadioNormalJob.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioNormalJob;
	}

	public JRadioButton getjRadioMarginalJob() {
		
		if (jRadioMarginalJob == null) {
			jRadioMarginalJob = new JRadioButton("Con Trabajo Marginal o Economia Sumergida");
			jRadioMarginalJob.setMargin(new Insets(20, 20, 2, 20));
			jRadioMarginalJob.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioMarginalJob;
	}

	public JRadioButton getjRadioHouseJob() {
		
		if (jRadioHouseJob == null) {
			jRadioHouseJob = new JRadioButton("Labores del Hogar (Ama de casa)");
			
			jRadioHouseJob.setMargin(new Insets(20, 20, 2, 20));
			jRadioHouseJob.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioHouseJob;
	}

	public JRadioButton getjRadioRetired() {
		if (jRadioRetired == null) {
			jRadioRetired = new JRadioButton("Pensionista o Jubilado");
			
			jRadioRetired.setMargin(new Insets(20, 20, 2, 20));
			jRadioRetired.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioRetired;
	}

	public JRadioButton getjRadioOthers() {
		
		if (jRadioOthers == null) {
			jRadioOthers = new JRadioButton("Otros inactivos (Estudiantes, Menores)");
			
			jRadioOthers.setMargin(new Insets(20, 20, 2, 20));
			jRadioOthers.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioOthers;
	}

	

	
	
	
	

	
	
	
	
	
	
	
	
}
