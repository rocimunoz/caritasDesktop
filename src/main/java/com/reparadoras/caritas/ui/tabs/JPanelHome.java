package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.reparadoras.caritas.ui.components.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.RelativesTableModel;

public class JPanelHome extends JPanel{
	
	/*VIEW HOUSE*/
	private JLabel lblTypeHouse;
	private JTextField tfTypeHouse;
	private JLabel lblRegHolding;
	private JTextField tfRegHolding;
	private JLabel lblNumberRooms;
	private JComboBox<Integer> cbNumberRooms;
	private JLabel lblNumberPeople;
	private JComboBox<Integer> cbNumberPeople;
	private JLabel lblNumberFamilies;
	private JComboBox<Integer> cbNumberFamilies;
	private JLabel lblOtherInfo;
	private JTextArea taOtherInfo;
	
	
	public JPanelHome() {
		
		createGUIPanel();
		
		initCombos();
		
		
	}
	
public void initCombos(){
		
		for (int i =1; i<11;i++){
		this.getJComboNumberFamilies().addItem(i);
		this.getJComboNumberPeople().addItem(i);
		this.getJComboNumberRooms().addItem(i);
		}
		
	}
	
	private void createGUIPanel(){
		
		
		this.setLayout(getGridLayoutHouse());
		
		//Añado elementos del Tab Vivienda
		this.add(getJLabelTypeHouse(), getGridJLabelTypeHouse());
		this.add(getJTextFieldTypeHouse(), getGridJTextFieldTypeHouse());
		this.add(getJLabelRegHolding(), getGridJLabelRegHolding());
		this.add(getJTextFieldRegHolding(), getGridJTextFieldRegHolding());
		this.add(getJLabelNumberRooms(), getGridJLabelNumberRooms());
		this.add(getJComboNumberRooms(), getGridJComboNumberRooms());
		this.add(getJLabelNumberPeople(), getGridJLabelNumberPeople());
		this.add(getJComboNumberPeople(), getGridJComboNumberPeople());
		this.add(getJLabelNumberFamilies(), getGridJLabelNumberFamilies());
		this.add(getJComboNumberFamilies(), getGridJComboNumberFamilies());
		this.add(getJLabelOtherInfo(), getGridJLabelOtherInfo());
				
				JScrollPane scrollJTextArea = new JScrollPane (getJTextAreaOtherInfo(), 
						   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				this.add(scrollJTextArea, getGridJTextAreaOtherInfo());
		
	}
	
	private GridBagLayout getGridLayoutHouse() {
		GridBagLayout gbl_LayoutHouse = new GridBagLayout();
		gbl_LayoutHouse.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0};
		gbl_LayoutHouse.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0};

		return gbl_LayoutHouse;
	}
	
	
	private JLabel getJLabelTypeHouse() {

		if (lblTypeHouse == null) {
			lblTypeHouse = new JLabel("Tipo:  ");
			lblTypeHouse.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblTypeHouse;
	}

	private GridBagConstraints getGridJLabelTypeHouse() {
		GridBagConstraints gbc_lblTypeHouse = new GridBagConstraints();
		gbc_lblTypeHouse.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTypeHouse.anchor = GridBagConstraints.WEST;
		gbc_lblTypeHouse.insets = new Insets(10, 20, 0, 5);
		gbc_lblTypeHouse.gridx = 0;
		gbc_lblTypeHouse.gridy = 0;

		return gbc_lblTypeHouse;
	}
	
	private JTextField getJTextFieldTypeHouse() {
		if (tfTypeHouse == null) {
			tfTypeHouse = new JTextField();
			tfTypeHouse.setColumns(10);
		}
		return tfTypeHouse;
	}

	private GridBagConstraints getGridJTextFieldTypeHouse() {
		GridBagConstraints gbc_tfTypeHouse = new GridBagConstraints();
		gbc_tfTypeHouse.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTypeHouse.weightx = 1.0;
		gbc_tfTypeHouse.anchor = GridBagConstraints.NORTH;
		gbc_tfTypeHouse.insets = new Insets(10, 0, 5, 5);
		gbc_tfTypeHouse.gridx = 1;
		gbc_tfTypeHouse.gridy = 0;

		return gbc_tfTypeHouse;
	}
	
	private JLabel getJLabelRegHolding() {

		if (lblRegHolding == null) {
			lblRegHolding = new JLabel("Régimen tenencia:  ");
			lblRegHolding.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblRegHolding;
	}

	private GridBagConstraints getGridJLabelRegHolding() {
		GridBagConstraints gbc_lblRegHolding = new GridBagConstraints();
		gbc_lblRegHolding.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRegHolding.anchor = GridBagConstraints.WEST;
		gbc_lblRegHolding.insets = new Insets(10, 20, 0, 5);
		gbc_lblRegHolding.gridx = 2;
		gbc_lblRegHolding.gridy = 0;

		return gbc_lblRegHolding;
	}
	
	private JTextField getJTextFieldRegHolding() {
		if (tfRegHolding == null) {
			tfRegHolding = new JTextField();
			tfRegHolding.setColumns(10);
		}
		return tfRegHolding;
	}

	private GridBagConstraints getGridJTextFieldRegHolding() {
		GridBagConstraints gbc_tfTypeRegHolding = new GridBagConstraints();
		gbc_tfTypeRegHolding.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTypeRegHolding.weightx = 1.0;
		gbc_tfTypeRegHolding.anchor = GridBagConstraints.NORTH;
		gbc_tfTypeRegHolding.insets = new Insets(10, 0, 5, 5);
		gbc_tfTypeRegHolding.gridx = 3;
		gbc_tfTypeRegHolding.gridy = 0;

		return gbc_tfTypeRegHolding;
	}
	
	private JLabel getJLabelNumberRooms() {

		if (lblNumberRooms == null) {
			lblNumberRooms = new JLabel("Número Habitaciones:  ");
			lblNumberRooms.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblNumberRooms;
	}
	
	private GridBagConstraints getGridJLabelNumberRooms() {
		GridBagConstraints gbc_lblNumberRooms = new GridBagConstraints();
		gbc_lblNumberRooms.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumberRooms.anchor = GridBagConstraints.WEST;
		gbc_lblNumberRooms.insets = new Insets(10, 20, 0, 5);
		gbc_lblNumberRooms.gridx = 0;
		gbc_lblNumberRooms.gridy = 1;
		return gbc_lblNumberRooms;
	}
	
	
	private JComboBox<Integer> getJComboNumberRooms() {

		if ( cbNumberRooms== null) {
			cbNumberRooms = new JComboBox<Integer>();
			
		}
		return cbNumberRooms;
	}
	
	private GridBagConstraints getGridJComboNumberRooms() {
		GridBagConstraints gbc_cbNumberRooms = new GridBagConstraints();
		gbc_cbNumberRooms.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbNumberRooms.weightx = 1.0;
		gbc_cbNumberRooms.anchor = GridBagConstraints.WEST;
		gbc_cbNumberRooms.insets = new Insets(10, 20, 0, 5);
		gbc_cbNumberRooms.gridx = 1;
		gbc_cbNumberRooms.gridy = 1;
		return gbc_cbNumberRooms;
	}
	
	private JLabel getJLabelNumberPeople() {

		if (lblNumberPeople == null) {
			lblNumberPeople = new JLabel("Número Personas que residen:  ");
			lblNumberPeople.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblNumberPeople;
	}
	
	private GridBagConstraints getGridJLabelNumberPeople() {
		GridBagConstraints gbc_lblNumberPeople = new GridBagConstraints();
		gbc_lblNumberPeople.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumberPeople.anchor = GridBagConstraints.WEST;
		gbc_lblNumberPeople.insets = new Insets(10, 20, 0, 5);
		gbc_lblNumberPeople.gridx = 2;
		gbc_lblNumberPeople.gridy = 1;
		return gbc_lblNumberPeople;
	}
	
	private JComboBox<Integer> getJComboNumberPeople() {

		if ( cbNumberPeople== null) {
			cbNumberPeople = new JComboBox<Integer>();
			
		}
		return cbNumberPeople;
	}
	
	private GridBagConstraints getGridJComboNumberPeople() {
		GridBagConstraints gbc_cbNumberPeople = new GridBagConstraints();
		gbc_cbNumberPeople.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbNumberPeople.weightx = 1.0;
		gbc_cbNumberPeople.anchor = GridBagConstraints.WEST;
		gbc_cbNumberPeople.insets = new Insets(10, 20, 0, 5);
		gbc_cbNumberPeople.gridx = 3;
		gbc_cbNumberPeople.gridy = 1;
		return gbc_cbNumberPeople;
	}
	
	private JLabel getJLabelNumberFamilies() {

		if (lblNumberFamilies == null) {
			lblNumberFamilies = new JLabel("Número Familias nucleares:  ");
			lblNumberFamilies.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblNumberFamilies;
	}
	
	private GridBagConstraints getGridJLabelNumberFamilies() {
		GridBagConstraints gbc_lblNumberFamilies = new GridBagConstraints();
		gbc_lblNumberFamilies.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumberFamilies.anchor = GridBagConstraints.WEST;
		gbc_lblNumberFamilies.insets = new Insets(10, 20, 0, 5);
		gbc_lblNumberFamilies.gridx = 0;
		gbc_lblNumberFamilies.gridy = 2;
		return gbc_lblNumberFamilies;
	}
	
	private JComboBox<Integer> getJComboNumberFamilies() {

		if ( cbNumberFamilies== null) {
			cbNumberFamilies = new JComboBox<Integer>();
			
		}
		return cbNumberFamilies;
	}
	
	private GridBagConstraints getGridJComboNumberFamilies() {
		GridBagConstraints gbc_cbNumberFamilies = new GridBagConstraints();
		gbc_cbNumberFamilies.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbNumberFamilies.weightx = 1.0;
		gbc_cbNumberFamilies.anchor = GridBagConstraints.WEST;
		gbc_cbNumberFamilies.insets = new Insets(10, 20, 0, 5);
		gbc_cbNumberFamilies.gridx = 1;
		gbc_cbNumberFamilies.gridy = 2;
		return gbc_cbNumberFamilies;
	}
	
	private JLabel getJLabelOtherInfo() {

		if (lblOtherInfo == null) {
			lblOtherInfo = new JLabel("Otros datos:  ");
			lblOtherInfo.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblOtherInfo;
	}
	
	private GridBagConstraints getGridJLabelOtherInfo() {
		GridBagConstraints gbc_lblOtherInfo = new GridBagConstraints();
		gbc_lblOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_lblOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_lblOtherInfo.gridx = 0;
		gbc_lblOtherInfo.gridy = 3;
		return gbc_lblOtherInfo;
	}
	
	
	
	private JTextArea getJTextAreaOtherInfo() {
		
		if ( taOtherInfo== null) {
			taOtherInfo = new JTextArea();
			taOtherInfo.setRows(5);
			taOtherInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
			taOtherInfo.setWrapStyleWord(true);
			taOtherInfo.setLineWrap(true);
			
		}
		return taOtherInfo;
	}
	
	private GridBagConstraints getGridJTextAreaOtherInfo() {
		GridBagConstraints gbc_taOtherInfo = new GridBagConstraints();
		gbc_taOtherInfo.gridwidth = 4;
		gbc_taOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_taOtherInfo.weightx = 1.0;
		gbc_taOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_taOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_taOtherInfo.gridx = 0;
		gbc_taOtherInfo.gridy = 4;
		return gbc_taOtherInfo;
	}

	
	
	
	
	
	
	
	
}
