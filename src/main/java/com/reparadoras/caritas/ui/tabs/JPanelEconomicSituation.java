package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.IncomesTableModel;
import com.reparadoras.caritas.ui.components.table.PeopleTableModel;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;

import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Component;

public class JPanelEconomicSituation extends JPanel{
	
	private JPanel jPanelTableIncome;
	private IncomesTableModel incomesTableModel = null;
	private JTable tableIncomes = null;
	private JScrollPane scrollPaneJTable = null;
	
	public JPanelEconomicSituation() {
		
		createGUIPanel();
		
		
	}
	
	private void createGUIPanel(){
		
		this.setLayout(getGridLayoutFamily());
		getJPanelTableIncome().setLayout(getGridLayoutJPanelTableIncome());
		
		getJPanelTableIncome().add(getScrollPaneTableIncome(), getGridJPanelScrollTableIncome());
		this.add(getJPanelTableIncome(), getGridJPanelTableIncome());
		
		
	}

	
	
	
	private GridBagLayout getGridLayoutFamily() {
		GridBagLayout gbl_LayoutFamily = new GridBagLayout();
		gbl_LayoutFamily.columnWeights = new double[] { 0.0};
		gbl_LayoutFamily.rowWeights = new double[] { 0.0};

		return gbl_LayoutFamily;
	}
	
	
	private GridBagLayout getGridLayoutJPanelTableIncome() {

		GridBagLayout gbl_jPanelTableIncome = new GridBagLayout();
		gbl_jPanelTableIncome.columnWeights = new double[] { 0.0 };
		gbl_jPanelTableIncome.rowWeights = new double[] { 0.0 };

		return gbl_jPanelTableIncome;
	}

	private JPanel getJPanelTableIncome() {
		if (jPanelTableIncome == null) {
			jPanelTableIncome = new JPanel();
			jPanelTableIncome.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ingresos", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));
		}

		return jPanelTableIncome;
	}

	private GridBagConstraints getGridJPanelTableIncome() {
		GridBagConstraints gbc_jPanelTableIncome = new GridBagConstraints();
		gbc_jPanelTableIncome.weighty = 1.0;
		gbc_jPanelTableIncome.weightx = 1.0;
		gbc_jPanelTableIncome.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelTableIncome.fill = GridBagConstraints.BOTH;
		gbc_jPanelTableIncome.gridx = 0;
		gbc_jPanelTableIncome.gridy = 0;

		return gbc_jPanelTableIncome;
	}
	
	
	
	private JScrollPane getScrollPaneTableIncome(){
		if (scrollPaneJTable == null){
			scrollPaneJTable = new JScrollPane(getJTableIncomes());
			scrollPaneJTable.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		}
		
		return scrollPaneJTable;
	}
	
	private GridBagConstraints getGridJPanelScrollTableIncome(){
		
		GridBagConstraints gbc_jPanelScroll = new GridBagConstraints();
		gbc_jPanelScroll.weighty = 1.0;
		gbc_jPanelScroll.weightx = 1.0;
		gbc_jPanelScroll.fill = GridBagConstraints.BOTH;
		gbc_jPanelScroll.anchor = GridBagConstraints.WEST;
		gbc_jPanelScroll.gridx = 0;
		gbc_jPanelScroll.gridy = 0;
		
		return gbc_jPanelScroll; 
	}
	
	
	
	
	
	private JTable getJTableIncomes(){
		if (tableIncomes == null){
			
			tableIncomes = new JTable(getIncomesTableModel());
			tableIncomes.setAutoCreateRowSorter(true);
			tableIncomes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		
		return tableIncomes;
	}
	
private IncomesTableModel getIncomesTableModel(){
		
		if (incomesTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Persona", "Concepto", "Cantidad", "Fecha Fin"};
			incomesTableModel = new IncomesTableModel(Arrays.asList(columnIdentifiers));
		}
		
		return incomesTableModel;
	}
	
	
	
}
