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

public class JPanelTypeAuthorization extends JPanel{
	
	
	private JPanel jPanelRadioButton;
	private JRadioButton jRadioSARegular;
	private JRadioButton jRadioSAIrregular;
	private JRadioButton jRadioResidenceWork;
	private JRadioButton jRadioResidence;
	private JRadioButton jRadioStudy;
	private JRadioButton jRadioTourism;
	private JRadioButton jRadioRefugee;
	private JRadioButton jRadioUndocumented;
	
	
	public JPanelTypeAuthorization() {
		
		createGUIPanel();
		
		addListeners();
		
	
	}
	
	private void addListeners(){
		
		getJRadioSARegular().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JRadioButton radio = (JRadioButton) e.getSource();
				if (radio.isSelected()){
					getJRadioResidence().setEnabled(true);
					getJRadioResidenceWork().setEnabled(true);
					getJRadioTourism().setEnabled(true);
					getJRadioStudy().setEnabled(true);
					getJRadioRefugee().setEnabled(true);
				}
				else{
					getJRadioResidence().setEnabled(false);
					getJRadioResidence().setSelected(false);
					getJRadioResidenceWork().setEnabled(false);
					getJRadioResidenceWork().setSelected(false);

					getJRadioTourism().setEnabled(false);
					getJRadioTourism().setSelected(false);
					getJRadioStudy().setEnabled(false);
					getJRadioStudy().setSelected(false);
					
					getJRadioRefugee().setEnabled(false);
					getJRadioRefugee().setSelected(false);
					
				}
			}
		});
		
		
		
		
		
	}
	
	private void createGUIPanel(){
		
		
		this.setLayout(getGridLayoutHouse());
		
		//Añado elementos del Tab Vivienda
		this.add(getJPanelRadioButton(), getGridJPanelRadio());
		
		
	}
	
	private GridBagLayout getGridLayoutHouse() {
		GridBagLayout gbl_LayoutHouse = new GridBagLayout();
		gbl_LayoutHouse.columnWeights = new double[] { 0.0};
		gbl_LayoutHouse.rowWeights = new double[] { 0.0};

		return gbl_LayoutHouse;
	}
	
	
	private JPanel getJPanelRadioButton() {
		if (jPanelRadioButton == null) {
		
			jPanelRadioButton = new JPanel();
			jPanelRadioButton.setLayout(new GridLayout(0, 1, 0, 0));
			jPanelRadioButton.add(getJRadioSARegular());
			jPanelRadioButton.add(getJRadioResidence());
			jPanelRadioButton.add(getJRadioResidenceWork());
			jPanelRadioButton.add(getJRadioStudy());
			jPanelRadioButton.add(getJRadioTourism());
			jPanelRadioButton.add(getJRadioRefugee());
			jPanelRadioButton.add(getJRadioUndocumented());
			jPanelRadioButton.add(getJRadioSAIrregular());
			
			ButtonGroup groupL2 = new ButtonGroup();
			groupL2.add(getJRadioResidence());
			groupL2.add(getJRadioResidenceWork());
			groupL2.add(getJRadioStudy());
			groupL2.add(getJRadioTourism());
			groupL2.add(getJRadioRefugee());
			
			ButtonGroup groupL1 = new ButtonGroup();
			groupL1.add(getJRadioSARegular());
			groupL1.add(getJRadioUndocumented());
			groupL1.add(getJRadioSAIrregular());
			
			
			
			
			
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
	
	private JRadioButton getJRadioSARegular() {
		if (jRadioSARegular == null) {
			jRadioSARegular = new JRadioButton("Situación Administrativa Regular");
			
			jRadioSARegular.setMargin(new Insets(20, 20, 2, 20));
			jRadioSARegular.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioSARegular;
	}
	
	private JRadioButton getJRadioSAIrregular() {
		if (jRadioSAIrregular == null) {
			jRadioSAIrregular = new JRadioButton("Situación Administrativa Irregular");
			jRadioSAIrregular.setMargin(new Insets(20, 20, 2, 20));
			jRadioSAIrregular.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioSAIrregular;
	}
	
	private JRadioButton getJRadioUndocumented() {
		if (jRadioUndocumented == null) {
			jRadioUndocumented = new JRadioButton("Indocumentado");
			jRadioUndocumented.setMargin(new Insets(20, 20, 2, 20));
			jRadioUndocumented.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioUndocumented;
	}
	
	private JRadioButton getJRadioResidence() {
		if (jRadioResidence == null) {
			jRadioResidence = new JRadioButton("Autorización Residencia");
			jRadioResidence.setMargin(new Insets(2, 50, 2, 20));
			jRadioResidence.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioResidence;
	}
	
	private JRadioButton getJRadioResidenceWork() {
		if (jRadioResidenceWork == null) {
			jRadioResidenceWork = new JRadioButton("Autorización Residencia y Trabajo");
			jRadioResidenceWork.setMargin(new Insets(2, 50, 2, 20));
			jRadioResidenceWork.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioResidenceWork;
	}
	
	private JRadioButton getJRadioStudy() {
		if (jRadioStudy == null) {
			jRadioStudy = new JRadioButton("Estudios");
			jRadioStudy.setMargin(new Insets(2, 50, 2, 20));
			jRadioStudy.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioStudy;
	}
	
	private JRadioButton getJRadioTourism() {
		if (jRadioTourism == null) {
			jRadioTourism = new JRadioButton("Turismo");
			jRadioTourism.setMargin(new Insets(2, 50, 2, 20));
			jRadioTourism.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioTourism;
	}
	
	private JRadioButton getJRadioRefugee() {
		if (jRadioRefugee == null) {
			jRadioRefugee = new JRadioButton("Refugiado");
			jRadioRefugee.setMargin(new Insets(2, 50, 2, 20));
			jRadioRefugee.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioRefugee;
	}
	
	
	

	
	
	
	
	
	
	
	
}