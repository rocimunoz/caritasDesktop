package com.reparadoras.caritas.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.reparadoras.caritas.ui.components.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.RelativesTableModel;

public class JPanelFamily extends JPanel{
	
	private JLabel lblFamilyOtherInfo;
	private JTextArea taFamilyOtherInfo;
	private RelativesTableModel relativesTableModel = null;
	private JTable tableRelatives = null;
	private JScrollPane scrollPaneJTable = null;
	//private JPanel jPanelFamily;
	public JPanelFamily() {
		
		createGUIPanel();
		
		
	}
	
	private void createGUIPanel(){
		
		this.setLayout(getGridLayoutFamily());
		this.add(getScrollPaneTable(), getGridJPanelScrollTable());
	}

	
	/*
	private JPanel getJPanelFamily() {
		if (jPanelFamily == null) {
			jPanelFamily = new JPanel();
			jPanelFamily.setBorder(new LineBorder(new Color(0, 0, 0)));
			
		}
		return jPanelFamily;

	}*/
	
	private GridBagLayout getGridLayoutFamily() {
		GridBagLayout gbl_LayoutFamily = new GridBagLayout();
		gbl_LayoutFamily.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0};
		gbl_LayoutFamily.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0};

		return gbl_LayoutFamily;
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
	
	private JTable getJTableRelatives(){
		if (tableRelatives == null){
			TableModel tableModel = getRelativesTableModel();
			tableRelatives = new JTable(tableModel){

				protected JTableHeader createDefaultTableHeader() {
			          return new GroupableTableHeader(columnModel);
			      }
			};
			
			tableRelatives.setAutoCreateRowSorter(true);
			tableRelatives.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tableRelatives.setShowGrid(true);
			tableRelatives.setShowVerticalLines(true);
			
		}
		
		return tableRelatives;
	}
	
private RelativesTableModel getRelativesTableModel(){
		
		if (relativesTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Parentesco", "Apellidos", "Nombre", "F.Nacimiento", "Situación"};
			relativesTableModel = new RelativesTableModel(Arrays.asList(columnIdentifiers));
			}
		
		return relativesTableModel;
	}
	
	
	
	
	
}
