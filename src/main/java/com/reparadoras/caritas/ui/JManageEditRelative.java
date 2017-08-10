package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Font;

@SuppressWarnings("serial")

public class JManageEditRelative extends AbstractJInternalFrame {
	
	private JPanel jPanelContentPane;
	private JTextField txfName;
	private JComboBox cbRelationShip;

	private JPanel jPanelPersonalData;
	private JLabel jLblName;
	private JLabel jLblRelationShip;
	
	
	private JPanel jPanelActions;
	private JButton jBtnAccept;
	private JButton jBtnCancel;
	private JLabel jLblSurname;
	private JTextField txfSurname;
	private JComboBox<String> jComboBoxSex;
	private JLabel jLblDateBorn;
	private PeopleDAO peopleDAO;
	
	private int executingMode;
	

	
	private Relative selectedRelative;
	

	public JManageEditRelative(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title, Relative relative)
			throws Exception {
		super(jCicIFParent, modal);
		setVisible(true);
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setSize(800, 300);
		this.setTitle(title);
		
		this.selectedRelative = relative;
		this.executingMode = executingMode;
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		
		initComponents();
		configureModeEdition(executingMode);
		fillData(executingMode);
		
		getContentPane().setLayout(getGridLayoutContentPane());
		getContentPane().add(getJPanelContentPane(), getGridBagConstraintsJPaneContentPane());
		getJPanelContentPane().setLayout(getGridLayoutJPaneContentPane());

		getJPanelContentPane().add(getJPanelPersonalData(), getGridJPanelPersonalData());
		getJPanelPersonalData().setLayout(getGridLayoutJPanelPersonalData());
		getJPanelPersonalData().add(getJLabelSurname(), getGridJLabelSurname());
		getJPanelPersonalData().add(getJTextFieldSurname(), getGridJTextFieldSurname());
		getJPanelPersonalData().add(getJLabelName(), getGridJLabelName());
		getJPanelPersonalData().add(getJTextFieldName(), getGridJTextFieldName());
		getJPanelPersonalData().add(getJLabelRelationShip(), getGridJLabelRelationShip());
		getJPanelPersonalData().add(getJLabelRelationShip(), getJComboBoxRelationShip());
		getJPanelPersonalData().add(getComboBox(), getGridComboBoxSex());
		getJPanelPersonalData().add(getJLblDateBorn(), getGridJLabelSex());
		getJPanelContentPane().add(getJPanelActions(), getGridBagConstraintsJPanelActions());
		getJPanelActions().setLayout(getGridLayoutJPanelActions());

		getJButtonCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseWindow();
			}
		});

		
		getJButtonAccept().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//Abrir transaccion
				if (executingMode == JWindowParams.IMODE_UPDATE){
					onUpdatePeople();
				}
				else if (executingMode == JWindowParams.IMODE_INSERT){
					onCreatePeople();
				}
				
				//Cerrar Transaccion
				onCloseWindow();
				
				
				
				
			}
		});
		
		getJPanelActions().add(getJButtonAccept(), getGridBagConstraintsJButtonAccept());
		
		getJPanelActions().add(getJButtonCancel(), getGridBagConstraintsJButtonCancel());

	}
	
	private void onCloseWindow(){
		try {
			this.setClosed(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initComponents(){
	    this.getComboBox().addItem("V");
	    this.getComboBox().addItem("F");
	    
	}
	
	private void configureModeEdition(int mode){
		
		if (mode == JWindowParams.IMODE_SELECT){
			//Deshabilito campos
			//this.getJTextFieldDni().setEnabled(false);
			//this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldName().setEnabled(false);
			this.getJTextFieldName().setText(this.selectedRelative.getName());
			this.getJTextFieldSurname().setEnabled(false);
			this.getJTextFieldSurname().setText(this.selectedRelative.getSurname());
			this.getComboBox().setEnabled(false);
			//this.getComboBox().setSelectedItem(this.selectedPeople.getSex());
			this.getJButtonAccept().setVisible(false);
			this.getJButtonCancel().setText("Salir");
		}
		else{
			//Habilito campos
			//this.getJTextFieldDni().setEnabled(true);
			this.getJTextFieldName().setEnabled(true);
			this.getJTextFieldSurname().setEnabled(true);
			this.getComboBox().setEnabled(true);
			this.getJButtonAccept().setVisible(true);
			this.getJButtonCancel().setText("Cancelar");
		}
	}
	
	private void fillData(int mode){
		if (mode == JWindowParams.IMODE_SELECT || mode == JWindowParams.IMODE_UPDATE){
			//this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldName().setText(this.selectedRelative.getName());
			this.getJTextFieldSurname().setText(this.selectedRelative.getSurname());
			
			//this.getComboBox().setSelectedItem(this.selectedRelative.getSex());
			
		}
		
	}
	
	private void onUpdatePeople(){
		try{
		
		//this.selectedPeople.setDni(this.getJTextFieldDni().getText());
		this.selectedRelative.setName(this.getJTextFieldName().getText());
		this.selectedRelative.setSurname(this.getJTextFieldSurname().getText());
		//this.selectedPeople.setSex((String) this.getComboBox().getSelectedItem());
		
		
			//peopleDAO.update(selectedPeople);
		}catch(Exception e){
		    JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void onCreatePeople(){
		try{
		
			People people = new People();
			people.setName(this.getJTextFieldName().getText());
			people.setFirstSurname(this.getJTextFieldSurname().getText());
			//people.setDni(this.getJTextFieldDni().getText());
			people.setActive(true);
			
			//save people
			peopleDAO.insert(people);
		}catch(Exception e){
		    JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	
	/* FUNCIONES PANEL CONTENT PANE*/
	
	private GridBagLayout getGridLayoutContentPane(){
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		
		return gridBagLayout;
	}
	
	private JPanel getJPanelContentPane() {

		if (jPanelContentPane == null) {
			jPanelContentPane = new JPanel();
			jPanelContentPane.setBorder(null);	
		}
		return jPanelContentPane;		
}
	
	private GridBagLayout getGridLayoutJPaneContentPane() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0 };
		
		return gbl_panel;
	}
	
	private GridBagConstraints getGridBagConstraintsJPaneContentPane() {
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weightx = 1.0;
		gbc_panel.weighty = 1.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		
		return gbc_panel;
	}

	
	
	/* FUNCIONES PANEL PERSONAL DATA */
	private JPanel getJPanelPersonalData() {

		if (jPanelPersonalData == null) {
			jPanelPersonalData = new JPanel();
			jPanelPersonalData.setBorder(new TitledBorder(null, "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				
		}

		return jPanelPersonalData;
	}

	private GridBagConstraints getGridJPanelPersonalData() {

		GridBagConstraints gbc_jPanelPersonalData = new GridBagConstraints();
		gbc_jPanelPersonalData.weightx = 1.0;
		gbc_jPanelPersonalData.weighty = 1.0;
		gbc_jPanelPersonalData.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPersonalData.fill = GridBagConstraints.BOTH;
		gbc_jPanelPersonalData.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelPersonalData.gridx = 0;
		gbc_jPanelPersonalData.gridy = 0;

		return gbc_jPanelPersonalData;
	}

	private GridBagLayout getGridLayoutJPanelPersonalData() {
		GridBagLayout gbl_jPanelPersonalData = new GridBagLayout();
		gbl_jPanelPersonalData.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0 };
		gbl_jPanelPersonalData.rowWeights = new double[] { 0.0, 0.0 };
		return gbl_jPanelPersonalData;
	}

	private JLabel getJLabelName() {

		if (jLblName == null) {
			jLblName = new JLabel("Nombre");
			jLblName.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblName.setPreferredSize(new Dimension(80, 25));
		}

		return jLblName;
	}

	private GridBagConstraints getGridJLabelName() {
		GridBagConstraints gbc_jLblName = new GridBagConstraints();
		gbc_jLblName.anchor = GridBagConstraints.WEST;
		gbc_jLblName.insets = new Insets(0, 20, 5, 5);
		gbc_jLblName.gridx = 0;
		gbc_jLblName.gridy = 0;
		
				return gbc_jLblName;
	}
	
	private JTextField getJTextFieldName() {

		if (txfName == null){
			txfName = new JTextField();	
			txfName.setColumns(10);
		}
		

		return txfName;
	}

	private GridBagConstraints getGridJTextFieldName() {

		GridBagConstraints gbc_txfName = new GridBagConstraints();
		gbc_txfName.weightx = 1.0;
		gbc_txfName.insets = new Insets(0, 0, 5, 5);
		gbc_txfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfName.gridx = 1;
		gbc_txfName.gridy = 0;
		
		return gbc_txfName;
		
	}
	
	private JLabel getJLabelSurname() {

		if (jLblSurname == null) {
			jLblSurname = new JLabel("Apellidos");
			jLblSurname.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblSurname.setMinimumSize(new Dimension(20, 14));
			jLblSurname.setMaximumSize(new Dimension(20, 14));
			
		}

		return jLblSurname;
	}

	private GridBagConstraints getGridJLabelSurname() {
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.WEST;
		gbc_lblSurname.insets = new Insets(0, 15, 5, 5);
		gbc_lblSurname.gridx = 2;
		gbc_lblSurname.gridy = 0;
		
				return gbc_lblSurname;
	}
	
	private JTextField getJTextFieldSurname() {

		if (txfSurname == null){
			txfSurname = new JTextField();	
			txfSurname.setColumns(10);
		}
		

		return txfSurname;
	}

	private GridBagConstraints getGridJTextFieldSurname() {

		GridBagConstraints gbc_txfSurname = new GridBagConstraints();
		gbc_txfSurname.weightx = 1.0;
		gbc_txfSurname.insets = new Insets(0, 0, 5, 0);
		gbc_txfSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfSurname.gridx = 3;
		gbc_txfSurname.gridy = 0;
		
		return gbc_txfSurname;
		
	}
	
	private JLabel getJLabelRelationShip() {

		if (jLblRelationShip == null) {
			jLblRelationShip = new JLabel("Parentesco");
			jLblRelationShip.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblRelationShip;
	}

	private GridBagConstraints getGridJLabelRelationShip() {

		GridBagConstraints gbc_jLblRelationShip = new GridBagConstraints();
		gbc_jLblRelationShip.anchor = GridBagConstraints.WEST;
		gbc_jLblRelationShip.insets = new Insets(0, 20, 5, 5);
		gbc_jLblRelationShip.gridx = 0;
		gbc_jLblRelationShip.gridy = 1;
		
		

		return gbc_jLblRelationShip;
	}
	
	private JComboBox<String> getJComboBoxRelationShip() {

		if (cbRelationShip == null){
			cbRelationShip = new JComboBox();	
		}
		
		return cbRelationShip;
	}

	private GridBagConstraints getGridJComboBoxRelationShip() {

		GridBagConstraints gbc_txfDni = new GridBagConstraints();
		gbc_txfDni.weightx = 1.0;
		gbc_txfDni.insets = new Insets(0, 0, 5, 5);
		gbc_txfDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfDni.gridx = 1;
		gbc_txfDni.gridy = 1;
		
		return gbc_txfDni;
		
	}
	
	private JLabel getJLblDateBorn() {
		if (jLblDateBorn == null) {
			jLblDateBorn = new JLabel("Fecha Nacimiento");
			jLblDateBorn.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jLblDateBorn;
	}
	
	private GridBagConstraints getGridJLabelSex() {

		GridBagConstraints gbc_jLblDateBorn = new GridBagConstraints();
		gbc_jLblDateBorn.anchor = GridBagConstraints.WEST;
		gbc_jLblDateBorn.weightx = 1.0;
		gbc_jLblDateBorn.insets = new Insets(0, 15, 5, 5);
		gbc_jLblDateBorn.gridx = 2;
		gbc_jLblDateBorn.gridy = 1;
		
		return gbc_jLblDateBorn;
		
	}
	
	
	private JComboBox getComboBox() {
		if (jComboBoxSex == null) {
			jComboBoxSex = new JComboBox();
		}
		return jComboBoxSex;
	}
	
private  GridBagConstraints getGridComboBoxSex() {
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.weightx = 1.0;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
		
		return gbc_comboBox;
	}


	
	/*  FUNCIONES PANEL ACCIONES*/
	
	private JPanel getJPanelActions() {

		if (jPanelActions == null) {
			jPanelActions = new JPanel();
		}

		return jPanelActions;
	}
	
	private GridBagConstraints getGridBagConstraintsJPanelActions() {
		GridBagConstraints gbc_jPanelActions = new GridBagConstraints();
		gbc_jPanelActions.anchor = GridBagConstraints.SOUTH;
		gbc_jPanelActions.weightx = 1.0;
		gbc_jPanelActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelActions.gridx = 0;
		gbc_jPanelActions.gridy = 1;
		
		return gbc_jPanelActions;
	}
	
	private GridBagLayout getGridLayoutJPanelActions() {
		GridBagLayout gbl_jPanelActions = new GridBagLayout();
		gbl_jPanelActions.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_jPanelActions.rowWeights = new double[] { 0.0 };
		
		return gbl_jPanelActions;
	}
	
	private JButton getJButtonAccept(){
		if (jBtnAccept == null){
			jBtnAccept = new JButton("Aceptar");
		}
		
		return jBtnAccept;
	}
	
	private GridBagConstraints getGridBagConstraintsJButtonAccept() {
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAccept.insets = new Insets(0, 0, 0, 5);
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 0;
		
		return gbc_btnAccept;
	}
	
	private JButton getJButtonCancel(){
		if (jBtnCancel == null){
			jBtnCancel = new JButton("Cancelar");
		}
		
		return jBtnCancel;
	}
	
	private GridBagConstraints getGridBagConstraintsJButtonCancel() {
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 0;
		
		return gbc_btnCancel;
	}

	
	
	
	
}
