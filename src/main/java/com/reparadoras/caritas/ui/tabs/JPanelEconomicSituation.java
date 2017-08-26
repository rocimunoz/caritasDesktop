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

import com.reparadoras.caritas.ui.components.table.ExpensesTableModel;
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
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.GridLayout;

public class JPanelEconomicSituation extends JPanel{
	
	//Ingresos
	private JPanel jPanelTableIncome;
	private IncomesTableModel incomesTableModel = null;
	private JTable tableIncomes = null;
	private JScrollPane scrollPaneJTableIncomes = null;
	
	//Acciones ingresos
	private JPanel jPanelActionsIncome;
	private JButton btnAddIncome;
	private JButton btnEditIncome;
	private JButton btnDeleteIncome;
	
	//Gastos
	private JPanel jPanelTableExpense;
	private ExpensesTableModel expensesTableModel = null;
	private JTable tableExpenses = null;
	private JScrollPane scrollPaneJTableExpenses = null;
	
	//Acciones gastos
	private JPanel jPanelActionsExpense;
	private JButton btnAddExpense;
	private JButton btnEditExpense;
	private JButton btnDeleteExpense;
	
	
	public JPanelEconomicSituation() {
		
		createGUIPanel();
		
		
	}
	
	private void createGUIPanel(){
		
		this.setLayout(getGridLayoutFamily());
		
		getJPanelTableIncome().setLayout(getGridLayoutJPanelTableIncome());
		getJPanelTableExpense().setLayout(getGridLayoutJPanelTableExpense());
		
		getJPanelTableIncome().add(getScrollPaneTableIncome(), getGridJPanelScrollTableIncome());
		
		getJPanelTableExpense().add(getScrollPaneTableExpense(), getGridJPanelScrollTableExpense());
		
		this.add(getJPanelTableIncome(), getGridJPanelTableIncome());
		this.add(getJPanelTableExpense(), getGridJPanelTableExpense());
		
		//panel Ingresos
		getJPanelTableIncome().add(this.getJPanelActionsIncome(), getGridJPanelActionsIncome());
		getJPanelActionsIncome().setLayout(new GridLayout(0, 1, 0, 0));
		getJPanelActionsIncome().add(getBtnAddIncome());
		getJPanelActionsIncome().add(getBtnEditIncome());
		getJPanelActionsIncome().add(getBtnDeleteIncome());
		
		//panel Gastos
		getJPanelTableExpense().add(this.getJPanelActionsExpense(), getGridJPanelActionsExpense());
		getJPanelActionsExpense().setLayout(new GridLayout(0, 1, 0, 0));
		getJPanelActionsExpense().add(getBtnAddExpense());
		getJPanelActionsExpense().add(getBtnEditExpense());
		getJPanelActionsExpense().add(getBtnDeleteExpense());
		
		
		
	}

	
	
	
	private GridBagLayout getGridLayoutFamily() {
		GridBagLayout gbl_LayoutFamily = new GridBagLayout();
		gbl_LayoutFamily.columnWeights = new double[] { 0.0};
		gbl_LayoutFamily.rowWeights = new double[] { 0.0, 0.0};

		return gbl_LayoutFamily;
	}
	
	/*INGRESOS*/
	private GridBagLayout getGridLayoutJPanelTableIncome() {

		GridBagLayout gbl_jPanelTableIncome = new GridBagLayout();
		gbl_jPanelTableIncome.columnWeights = new double[] { 0.0, 0.0 };
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
		if (scrollPaneJTableIncomes == null){
			scrollPaneJTableIncomes = new JScrollPane(getJTableIncomes());
			scrollPaneJTableIncomes.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		}
		
		return scrollPaneJTableIncomes;
	}
	
	private GridBagConstraints getGridJPanelScrollTableIncome(){
		
		GridBagConstraints gbc_jPanelScroll = new GridBagConstraints();
		gbc_jPanelScroll.weighty = 1.0;
		gbc_jPanelScroll.weightx = 1.0;
		gbc_jPanelScroll.fill = GridBagConstraints.BOTH;
		gbc_jPanelScroll.anchor = GridBagConstraints.WEST;
		gbc_jPanelScroll.gridx = 1;
		gbc_jPanelScroll.gridy = 0;
		
		return gbc_jPanelScroll; 
	}
	
	
	public JTable getJTableIncomes(){
		if (tableIncomes == null){
			
			tableIncomes = new JTable(getIncomesTableModel());
			tableIncomes.setAutoCreateRowSorter(true);
			tableIncomes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		
		return tableIncomes;
	}
	
    public IncomesTableModel getIncomesTableModel(){
		
		if (incomesTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Persona", "Concepto", "Cantidad", "Fecha Fin"};
			incomesTableModel = new IncomesTableModel(Arrays.asList(columnIdentifiers));
		}
		
		return incomesTableModel;
	}
	
	
    private JPanel getJPanelActionsIncome() {
		if (jPanelActionsIncome == null) {
			jPanelActionsIncome = new JPanel();
			
			
		}

		return jPanelActionsIncome;
	}
    
private GridBagConstraints getGridJPanelActionsIncome(){
		
		GridBagConstraints gbc_jPanelActionsIncome = new GridBagConstraints();
		gbc_jPanelActionsIncome.weighty = 1.0;
		gbc_jPanelActionsIncome.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelActionsIncome.gridx = 0;
		gbc_jPanelActionsIncome.gridy = 0;
		
		return gbc_jPanelActionsIncome; 
	}

public JButton getBtnAddIncome() {
	if (btnAddIncome == null) {
		btnAddIncome = new JButton("Nuevo");
		btnAddIncome.setIcon(new ImageIcon(JPanelEconomicSituation.class.getResource("/com/reparadoras/images/icon-add.png")));
	}
	return btnAddIncome;
}

public JButton getBtnDeleteIncome() {
	if (btnDeleteIncome == null) {
		btnDeleteIncome = new JButton("Borrar");
		btnDeleteIncome.setIcon(new ImageIcon(JPanelEconomicSituation.class.getResource("/com/reparadoras/images/icon-delete.png")));
	}
	return btnDeleteIncome;
}

public JButton getBtnEditIncome() {
	if (btnEditIncome == null) {
		btnEditIncome = new JButton("Editar");
		btnEditIncome.setIcon(new ImageIcon(JPanelEconomicSituation.class.getResource("/com/reparadoras/images/icon-update.png")));
	}
	return btnEditIncome;
}

    
    /*GASTOS*/
    
	private GridBagLayout getGridLayoutJPanelTableExpense() {

		GridBagLayout gbl_jPanelTableExpense = new GridBagLayout();
		gbl_jPanelTableExpense.columnWeights = new double[] { 0.0, 0.0 };
		gbl_jPanelTableExpense.rowWeights = new double[] { 0.0 };

		return gbl_jPanelTableExpense;
	}

	private JPanel getJPanelTableExpense() {
		if (jPanelTableExpense == null) {
			jPanelTableExpense = new JPanel();
			jPanelTableExpense.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Gastos", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));
		}

		return jPanelTableExpense;
	}

	private GridBagConstraints getGridJPanelTableExpense() {
		GridBagConstraints gbc_jPanelTableExpense = new GridBagConstraints();
		gbc_jPanelTableExpense.weighty = 1.0;
		gbc_jPanelTableExpense.weightx = 1.0;
		gbc_jPanelTableExpense.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelTableExpense.fill = GridBagConstraints.BOTH;
		gbc_jPanelTableExpense.gridx = 0;
		gbc_jPanelTableExpense.gridy = 1;

		return gbc_jPanelTableExpense;
	}
	
	private JScrollPane getScrollPaneTableExpense(){
		if (scrollPaneJTableExpenses == null){
			scrollPaneJTableExpenses = new JScrollPane(getJTableExpenses());
			scrollPaneJTableExpenses.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		}
		
		return scrollPaneJTableExpenses;
	}
	
	private GridBagConstraints getGridJPanelScrollTableExpense(){
		
		GridBagConstraints gbc_jPanelScrollExpense = new GridBagConstraints();
		gbc_jPanelScrollExpense.weighty = 1.0;
		gbc_jPanelScrollExpense.weightx = 1.0;
		gbc_jPanelScrollExpense.fill = GridBagConstraints.BOTH;
		gbc_jPanelScrollExpense.anchor = GridBagConstraints.WEST;
		gbc_jPanelScrollExpense.gridx = 1;
		gbc_jPanelScrollExpense.gridy = 0;
		
		return gbc_jPanelScrollExpense; 
	}
	
	private JTable getJTableExpenses(){
		if (tableExpenses == null){
			
			tableExpenses = new JTable(getExpensesTableModel());
			tableExpenses.setAutoCreateRowSorter(true);
			tableExpenses.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		
		return tableExpenses;
	}
	
private ExpensesTableModel getExpensesTableModel(){
		
		if (expensesTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Concepto", "Cantidad", "Periodicidad", "Fecha Fin"};
			expensesTableModel = new ExpensesTableModel(Arrays.asList(columnIdentifiers));
		}
		
		return expensesTableModel;
	}

private JPanel getJPanelActionsExpense() {
	if (jPanelActionsExpense == null) {
		jPanelActionsExpense = new JPanel();
		
		
	}

	return jPanelActionsExpense;
}

private GridBagConstraints getGridJPanelActionsExpense(){
	
	GridBagConstraints gbc_jPanelActionsExpense = new GridBagConstraints();
	gbc_jPanelActionsExpense.anchor = GridBagConstraints.NORTHWEST;
	gbc_jPanelActionsExpense.gridx = 0;
	gbc_jPanelActionsExpense.gridy = 0;
	
	return gbc_jPanelActionsExpense; 
}

private JButton getBtnAddExpense() {
	if (btnAddExpense == null) {
		btnAddExpense = new JButton("Nuevo");
		btnAddExpense.setIcon(new ImageIcon(JPanelEconomicSituation.class.getResource("/com/reparadoras/images/icon-add.png")));
	}
	return btnAddExpense;
}

private JButton getBtnDeleteExpense() {
	if (btnDeleteExpense == null) {
		btnDeleteExpense = new JButton("Borrar");
		btnDeleteExpense.setIcon(new ImageIcon(JPanelEconomicSituation.class.getResource("/com/reparadoras/images/icon-delete.png")));
	}
	return btnDeleteExpense;
}

private JButton getBtnEditExpense() {
	if (btnEditExpense == null) {
		btnEditExpense = new JButton("Editar");
		btnEditExpense.setIcon(new ImageIcon(JPanelEconomicSituation.class.getResource("/com/reparadoras/images/icon-update.png")));
	}
	return btnEditExpense;
}
	
	
}
