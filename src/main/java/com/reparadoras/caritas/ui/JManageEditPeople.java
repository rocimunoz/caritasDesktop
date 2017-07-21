package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
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

@SuppressWarnings("serial")

public class JManageEditPeople extends AbstractJInternalFrame {
	
	private JPanel jPanelContentPane;
	private JTextField txfName;
	private JTextField txfDni;

	private JPanel jPanelPersonalData;
	private JLabel jLblName;
	private JLabel jLblDni;
	
	private JPanel jPanelActions;
	private JButton jBtnAccept;
	private JButton jBtnCancel;
	private JLabel jLblSurname;
	private JTextField txfSurname;
	private JComboBox<String> jComboBoxSex;
	private JLabel jLblSex;
	private PeopleDAO peopleDAO;
	

	
	private People selectedPeople;

	public JManageEditPeople(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title, People people)
			throws Exception {
		super(jCicIFParent, modal);
		setVisible(true);
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);
		
		this.selectedPeople = people;
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
		getJPanelPersonalData().add(getJLabelDni(), getGridJLabelDni());
		getJPanelPersonalData().add(getJTextFieldDni(), getGridJTextFieldDni());
		getJPanelPersonalData().add(getComboBox(), getGridComboBoxSex());
		getJPanelPersonalData().add(getJLblSex(), this.getGridJLabelSex());
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
				onCreatePeople();
				//Cerrar Transaccion
				onCloseWindow();
				
				//filtrar resultados
				
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
			this.getJTextFieldDni().setEnabled(false);
			this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldName().setEnabled(false);
			this.getJTextFieldName().setText(this.selectedPeople.getName());
			this.getJTextFieldSurname().setEnabled(false);
			this.getJTextFieldSurname().setText(this.selectedPeople.getSurname());
			this.getComboBox().setEnabled(false);
			//this.getComboBox().setSelectedItem(this.selectedPeople.getSex());
			this.getJButtonAccept().setVisible(false);
			this.getJButtonCancel().setText("Salir");
		}
		else{
			//Deshabilito campos
			this.getJTextFieldDni().setEnabled(true);
			this.getJTextFieldName().setEnabled(true);
			this.getJTextFieldSurname().setEnabled(true);
			this.getComboBox().setEnabled(true);
			this.getJButtonAccept().setVisible(true);
			this.getJButtonCancel().setText("Cancelar");
		}
	}
	
	private void fillData(int mode){
		if (mode == JWindowParams.IMODE_SELECT || mode == JWindowParams.IMODE_UPDATE){
			this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldName().setText(this.selectedPeople.getName());
			this.getJTextFieldSurname().setText(this.selectedPeople.getSurname());
			
		}
		
	}
	
	private void onCreatePeople(){
		try{
		
			People people = new People();
			people.setName(this.getJTextFieldName().getText());
			people.setSurname(this.getJTextFieldSurname().getText());
			people.setDni(this.getJTextFieldDni().getText());
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
			jPanelContentPane.setBorder(new TitledBorder(null, "titulo", TitledBorder.LEADING, TitledBorder.TOP, null, null));	
		}
		return jPanelContentPane;		
}
	
	private GridBagLayout getGridLayoutJPaneContentPane() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0 };
		
		return gbl_panel;
	}
	
	private GridBagConstraints getGridBagConstraintsJPaneContentPane() {
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
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
		gbc_jPanelPersonalData.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelPersonalData.weighty = 1.0;
		gbc_jPanelPersonalData.weightx = 1.0;
		gbc_jPanelPersonalData.fill = GridBagConstraints.BOTH;
		gbc_jPanelPersonalData.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelPersonalData.gridx = 0;
		gbc_jPanelPersonalData.gridy = 0;

		return gbc_jPanelPersonalData;
	}

	private GridBagLayout getGridLayoutJPanelPersonalData() {
		GridBagLayout gbl_jPanelPersonalData = new GridBagLayout();
		gbl_jPanelPersonalData.columnWidths = new int[] { 0 };
		gbl_jPanelPersonalData.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_jPanelPersonalData.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0 };
		gbl_jPanelPersonalData.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		return gbl_jPanelPersonalData;
	}

	private JLabel getJLabelName() {

		if (jLblName == null) {
			jLblName = new JLabel("Nombre");
		}

		return jLblName;
	}

	private GridBagConstraints getGridJLabelName() {
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		
				return gbc_lblName;
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
		gbc_txfName.insets = new Insets(0, 0, 5, 5);
		gbc_txfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfName.gridx = 1;
		gbc_txfName.gridy = 0;
		
		return gbc_txfName;
		
	}
	
	private JLabel getJLabelSurname() {

		if (jLblSurname == null) {
			jLblSurname = new JLabel("Apellidos");
		}

		return jLblSurname;
	}

	private GridBagConstraints getGridJLabelSurname() {
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 0;
		gbc_lblSurname.gridy = 1;
		
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
		gbc_txfSurname.insets = new Insets(0, 0, 5, 5);
		gbc_txfSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfSurname.gridx = 1;
		gbc_txfSurname.gridy = 1;
		
		return gbc_txfSurname;
		
	}
	
	private JLabel getJLabelDni() {

		if (jLblDni == null) {
			jLblDni = new JLabel("Dni");
		}

		return jLblDni;
	}

	private GridBagConstraints getGridJLabelDni() {

		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.EAST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 2;
		gbc_lblDni.gridy = 0;
		
		

		return gbc_lblDni;
	}
	
	private JTextField getJTextFieldDni() {

		if (txfDni == null){
			txfDni = new JTextField();	
			txfDni.setColumns(10);
		}
		

		return txfDni;
	}

	private GridBagConstraints getGridJTextFieldDni() {

		GridBagConstraints gbc_txfDni = new GridBagConstraints();
		gbc_txfDni.insets = new Insets(0, 0, 5, 0);
		gbc_txfDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfDni.gridx = 3;
		gbc_txfDni.gridy = 0;
		
		return gbc_txfDni;
		
	}
	
	private JLabel getJLblSex() {
		if (jLblSex == null) {
			jLblSex = new JLabel("Sexo");
		}
		return jLblSex;
	}
	
	private GridBagConstraints getGridJLabelSex() {

		GridBagConstraints gbc_jLblSex = new GridBagConstraints();
		gbc_jLblSex.anchor = GridBagConstraints.WEST;
		gbc_jLblSex.insets = new Insets(0, 0, 5, 5);
		gbc_jLblSex.gridx = 2;
		gbc_jLblSex.gridy = 1;
		
		return gbc_jLblSex;
		
	}
	
	
	private JComboBox getComboBox() {
		if (jComboBoxSex == null) {
			jComboBoxSex = new JComboBox();
		}
		return jComboBoxSex;
	}
	
private  GridBagConstraints getGridComboBoxSex() {
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
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
		gbl_jPanelActions.columnWidths = new int[] { 0 };
		gbl_jPanelActions.rowHeights = new int[] { 0 };
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
