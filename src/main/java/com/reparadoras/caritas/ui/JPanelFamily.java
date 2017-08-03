package com.reparadoras.caritas.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

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

import com.reparadoras.caritas.ui.components.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.PeopleTableModel;
import com.reparadoras.caritas.ui.components.RelativesTableModel;

public class JPanelFamily extends JPanel{
	
	private JPanel jPanelTable;
	private JPanel jPanelTypeFamily;
	
	private JLabel lblFamilyOtherInfo;
	private JTextArea taFamilyOtherInfo;
	private RelativesTableModel relativesTableModel = null;
	private JTable tableRelatives = null;
	private JScrollPane scrollPaneJTable = null;
	
	public JPanelFamily() {
		
		createGUIPanel();
		
		
	}
	
	private void createGUIPanel(){
		
		this.setLayout(getGridLayoutFamily());
		getJPanelTableFamily().setLayout(getGridLayoutJPanelTable());
		getJPanelTypeFamily().setLayout(getGridLayoutJPanelTypeFamily());
		
		getJPanelTableFamily().add(getScrollPaneTable(), getGridJPanelScrollTable());
		
		getJPanelTypeFamily().add(getLabelFamilyOtherInfo(), getGridJLabelFamilyOtherInfo());
		getJPanelTypeFamily().add(getJTextAreaFamilyOtherInfo(), getGridJTextAreaFamilyOtherInfo());
		
		this.add(getJPanelTableFamily(), getGridJPanelTable());
		this.add(getJPanelTypeFamily(), getGridJPanelTypeFamily());
		
		
	}

	
	
	
	private GridBagLayout getGridLayoutFamily() {
		GridBagLayout gbl_LayoutFamily = new GridBagLayout();
		gbl_LayoutFamily.columnWeights = new double[] { 0.0};
		gbl_LayoutFamily.rowWeights = new double[] { 0.0, 0.0};

		return gbl_LayoutFamily;
	}
	
	
	private GridBagLayout getGridLayoutJPanelTable() {

		GridBagLayout gbl_jPanelTable = new GridBagLayout();
		gbl_jPanelTable.columnWeights = new double[] { 0.0 };
		gbl_jPanelTable.rowWeights = new double[] { 0.0 };

		return gbl_jPanelTable;
	}

	private JPanel getJPanelTableFamily() {
		if (jPanelTable == null) {
			jPanelTable = new JPanel();
			jPanelTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Composicion Familiar",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));
		}

		return jPanelTable;
	}

	private GridBagConstraints getGridJPanelTable() {
		GridBagConstraints gbc_jPanelTable = new GridBagConstraints();
		gbc_jPanelTable.weighty = 1.0;
		gbc_jPanelTable.weightx = 1.0;
		gbc_jPanelTable.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelTable.fill = GridBagConstraints.BOTH;
		gbc_jPanelTable.gridx = 0;
		gbc_jPanelTable.gridy = 0;

		return gbc_jPanelTable;
	}
	
	
	
	
	
	private GridBagLayout getGridLayoutJPanelTypeFamily() {

		GridBagLayout gbl_jPanelTable = new GridBagLayout();
		gbl_jPanelTable.columnWeights = new double[] { 0.0, 0.0 };
		gbl_jPanelTable.rowWeights = new double[] { 0.0 };

		return gbl_jPanelTable;
	}

	private JPanel getJPanelTypeFamily() {
		if (jPanelTypeFamily == null) {
			jPanelTypeFamily = new JPanel();
			jPanelTypeFamily.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tipo de familia",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));
		}

		return jPanelTypeFamily;
	}

	private GridBagConstraints getGridJPanelTypeFamily() {
		GridBagConstraints gbc_jPanelTable = new GridBagConstraints();
		gbc_jPanelTable.weighty = 1.0;
		gbc_jPanelTable.weightx = 1.0;
		gbc_jPanelTable.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelTable.fill = GridBagConstraints.BOTH;
		gbc_jPanelTable.gridx = 0;
		gbc_jPanelTable.gridy = 1;

		return gbc_jPanelTable;
	}
	
	
	
	
	
	
	private JScrollPane getScrollPaneTable(){
		if (scrollPaneJTable == null){
			scrollPaneJTable = new JScrollPane(getJTableRelatives());
			scrollPaneJTable.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		}
		
		return scrollPaneJTable;
	}
	
	private GridBagConstraints getGridJPanelScrollTable(){
		
		GridBagConstraints gbc_jPanelScroll = new GridBagConstraints();
		gbc_jPanelScroll.weighty = 1.0;
		gbc_jPanelScroll.weightx = 1.0;
		gbc_jPanelScroll.fill = GridBagConstraints.BOTH;
		gbc_jPanelScroll.anchor = GridBagConstraints.WEST;
		gbc_jPanelScroll.gridx = 0;
		gbc_jPanelScroll.gridy = 0;
		
		return gbc_jPanelScroll; 
	}
	
	
	private JLabel getLabelFamilyOtherInfo() {

		if (lblFamilyOtherInfo == null) {
			lblFamilyOtherInfo = new JLabel("Otros datos:  ");
			lblFamilyOtherInfo.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblFamilyOtherInfo;
	}

	private GridBagConstraints getGridJLabelFamilyOtherInfo() {
		GridBagConstraints gbc_lblOtherInfo = new GridBagConstraints();
		gbc_lblOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_lblOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_lblOtherInfo.gridx = 0;
		gbc_lblOtherInfo.gridy = 0;

		return gbc_lblOtherInfo;
	}
	
	private JTextArea getJTextAreaFamilyOtherInfo() {
		if (taFamilyOtherInfo == null) {
			taFamilyOtherInfo = new JTextArea();
			taFamilyOtherInfo.setRows(5);
			taFamilyOtherInfo.setColumns(10);
		}
		return taFamilyOtherInfo;
	}

	private GridBagConstraints getGridJTextAreaFamilyOtherInfo() {
		GridBagConstraints gbc_taOtherInfo = new GridBagConstraints();
		gbc_taOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_taOtherInfo.weightx = 1.0;
		gbc_taOtherInfo.anchor = GridBagConstraints.NORTH;
		gbc_taOtherInfo.insets = new Insets(10, 0, 5, 5);
		gbc_taOtherInfo.gridx = 1;
		gbc_taOtherInfo.gridy = 0;

		return gbc_taOtherInfo;
	}
	
	private JTable getJTableRelatives(){
		if (tableRelatives == null){
			
			tableRelatives = new JTable(getRelativesTableModel());
			tableRelatives.setAutoCreateRowSorter(true);
			tableRelatives.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		
		return tableRelatives;
	}
	
private RelativesTableModel getRelativesTableModel(){
		
		if (relativesTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Parentesco", "Apellidos", "Nombre", "Fecha Nacimiento", "Situacion"};
			relativesTableModel = new RelativesTableModel(Arrays.asList(columnIdentifiers));
		}
		
		return relativesTableModel;
	}
	
	
	

	
	
	
	
	
}
