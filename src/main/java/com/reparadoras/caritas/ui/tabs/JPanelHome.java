package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

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

import com.reparadoras.caritas.dao.HomeTypeDAO;
import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.HomeType;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;

public class JPanelHome extends JPanel{
	
	private JPanel jPanelHome;
	/*VIEW HOUSE*/
	private JLabel lblTypeHouse;
	private JComboBox<HomeType> cbHomeType;
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
	
	private HomeTypeDAO homeTypeDAO;
	
	
	public JPanelHome() {
		
		
		homeTypeDAO = new HomeTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		createGUIPanel();
		
		initCombos();
		
		
	}
	
public void initCombos(){
		
		for (int i =1; i<11;i++){
		this.getJComboNumberFamilies().addItem(i);
		this.getJComboNumberPeople().addItem(i);
		this.getJComboNumberRooms().addItem(i);
		}
		
		List<HomeType> listHomeType = homeTypeDAO.findAll();
		for (HomeType homeType : listHomeType) {
			cbHomeType.addItem(homeType);
		}
	
		
		
		
	}
	
	private void createGUIPanel(){
		
		
		this.setLayout(getGridLayoutHouse());
		
		getJPanelHome().setLayout(getGridLayoutPanelHome());
		this.add(getJPanelHome(), getGridJPanelHome());
		
		//Añado elementos del Tab Vivienda
		getJPanelHome().add(getJLabelTypeHouse(), getGridJLabelTypeHouse());
		getJPanelHome().add(getJTextFieldTypeHouse(), getGridJTextFieldTypeHouse());
		getJPanelHome().add(getJLabelRegHolding(), getGridJLabelRegHolding());
		getJPanelHome().add(getJTextFieldRegHolding(), getGridJTextFieldRegHolding());
		getJPanelHome().add(getJLabelNumberRooms(), getGridJLabelNumberRooms());
		getJPanelHome().add(getJComboNumberRooms(), getGridJComboNumberRooms());
		getJPanelHome().add(getJLabelNumberPeople(), getGridJLabelNumberPeople());
		getJPanelHome().add(getJComboNumberPeople(), getGridJComboNumberPeople());
		getJPanelHome().add(getJLabelNumberFamilies(), getGridJLabelNumberFamilies());
		getJPanelHome().add(getJComboNumberFamilies(), getGridJComboNumberFamilies());
		getJPanelHome().add(getJLabelOtherInfo(), getGridJLabelOtherInfo());
				
				JScrollPane scrollJTextArea = new JScrollPane (getJTextAreaOtherInfo(), 
						   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				getJPanelHome().add(scrollJTextArea, getGridJTextAreaOtherInfo());
		
	}
	
	private GridBagLayout getGridLayoutHouse() {
		GridBagLayout gbl_LayoutHouse = new GridBagLayout();
		gbl_LayoutHouse.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0};
		gbl_LayoutHouse.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0};

		return gbl_LayoutHouse;
	}
	private GridBagLayout getGridLayoutPanelHome() {
		GridBagLayout gbl_LaoutHome = new GridBagLayout();
		gbl_LaoutHome.columnWeights = new double[] { 0.0};
		gbl_LaoutHome.rowWeights = new double[] { };

		return gbl_LaoutHome;
	}

	
	private JPanel getJPanelHome() {
		if (jPanelHome == null) {
			jPanelHome = new JPanel();
		}
		return jPanelHome;
	}
	
	private GridBagConstraints getGridJPanelHome() {
		GridBagConstraints gbc_jPanelHome = new GridBagConstraints();
		gbc_jPanelHome.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelHome.weighty = 1.0;
		gbc_jPanelHome.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelHome.weightx = 1.0;
		gbc_jPanelHome.insets = new Insets(0, 10, 5, 0);
		gbc_jPanelHome.gridx = 0;
		gbc_jPanelHome.gridy = 0;

		return gbc_jPanelHome;
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
	
	public JComboBox getJTextFieldTypeHouse() {
		if (cbHomeType == null) {
			cbHomeType = new JComboBox<HomeType>();
			
		}
		return cbHomeType;
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
	
	public JTextField getJTextFieldRegHolding() {
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
	
	
	public JComboBox<Integer> getJComboNumberRooms() {

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
	
	public JComboBox<Integer> getJComboNumberPeople() {

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
	
	public JComboBox<Integer> getJComboNumberFamilies() {

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
	
	
	
	public JTextArea getJTextAreaOtherInfo() {
		
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
