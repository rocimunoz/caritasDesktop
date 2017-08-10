package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.People;
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
import javax.swing.ImageIcon;

@SuppressWarnings("serial")

public class JManageEditPeople extends AbstractJInternalFrame {
	
	private JPanel jPanelContentPane;
	private JTextField txfName;
	private JTextField txfDni;

	private JPanel jPanelPersonalData;
	private JLabel jLblName;
	private JLabel jLblDni;
	private JLabel jLblActive;
	
	private JPanel jPanelActions;
	private JButton jBtnAccept;
	private JButton jBtnCancel;
	private JLabel jLblSurname;
	private JTextField txfSurname;
	private JComboBox<String> jComboBoxSex;
	private JLabel jLblSex;
	private PeopleDAO peopleDAO;
	
	private int executingMode;
	

	
	private People selectedPeople;
	private JCheckBox jckActive;
	

	public JManageEditPeople(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title, People people)
			throws Exception {
		super(jCicIFParent, modal);
		setVisible(true);
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setSize(800, 300);
		this.setTitle(title);
		
		this.selectedPeople = people;
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
		getJPanelPersonalData().add(getJLabelDni(), getGridJLabelDni());
		getJPanelPersonalData().add(getJTextFieldDni(), getGridJTextFieldDni());
		getJPanelPersonalData().add(getComboBox(), getGridComboBoxSex());
		getJPanelPersonalData().add(getJLblSex(), getGridJLabelSex());
		
		getJPanelPersonalData().add(getJckActive(), getGridJCheckActive());
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
			this.getJTextFieldDni().setEditable(false);
			this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldName().setEditable(false);
			this.getJTextFieldName().setText(this.selectedPeople.getName());
			this.getJTextFieldSurname().setEditable(false);
			this.getJTextFieldSurname().setText(this.selectedPeople.getFirstSurname());
			this.getComboBox().setEnabled(false);
			this.getComboBox().setSelectedItem(this.selectedPeople.getSex());
			
			this.getJckActive().setEnabled(false);
			
			this.getJButtonAccept().setVisible(false);
			this.getJButtonCancel().setText("Salir");
		}
		else{
			//Habilito campos
			this.getJTextFieldDni().setEditable(true);
		
			this.getJTextFieldName().setEditable(true);
			this.getJTextFieldSurname().setEditable(true);
			this.getComboBox().setEditable(true);
			this.getJckActive().setEnabled(true);
			
			
			this.getJButtonAccept().setVisible(true);
			this.getJButtonCancel().setText("Cancelar");
		}
	}
	
	private void fillData(int mode){
		if (mode == JWindowParams.IMODE_SELECT || mode == JWindowParams.IMODE_UPDATE){
			this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldName().setText(this.selectedPeople.getName());
			this.getJTextFieldSurname().setText(this.selectedPeople.getFirstSurname());
			this.getJckActive().setSelected(this.selectedPeople.isActive());
			this.getComboBox().setSelectedItem(this.selectedPeople.getSex());
			
		}
		
	}
	
	private void onUpdatePeople(){
		try{
		
		this.selectedPeople.setDni(this.getJTextFieldDni().getText());
		this.selectedPeople.setName(this.getJTextFieldName().getText());
		this.selectedPeople.setFirstSurname(this.getJTextFieldSurname().getText());
		this.selectedPeople.setSex((String) this.getComboBox().getSelectedItem());
		if (this.getJckActive().isSelected()){
			this.selectedPeople.setActive(true);
		}
		else{
			this.selectedPeople.setActive(false);
		}
		
			peopleDAO.update(selectedPeople);
			
			JOptionPane.showMessageDialog(this, "Actualizado correctamente.", "Error", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(Exception e){
		    JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro", "Actualización Persona", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void onCreatePeople(){
		try{
		
			People people = new People();
			people.setName(this.getJTextFieldName().getText());
			people.setFirstSurname(this.getJTextFieldSurname().getText());
			people.setDni(this.getJTextFieldDni().getText());
			people.setSex((String) this.getComboBox().getSelectedItem());
			people.setActive(true);
			
			//save people
			peopleDAO.insert(people);
			
			JOptionPane.showMessageDialog(this, "Se ha dado de alta correctamente a " + people.getName(), "Inserción Persona", JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e){
		    JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	
	/* FUNCIONES PANEL CONTENT PANE*/
	
	private GridBagLayout getGridLayoutContentPane(){
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		
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
		gbl_panel.rowWeights = new double[] { 0.0, 0.0 };
		
		return gbl_panel;
	}
	
	private GridBagConstraints getGridBagConstraintsJPaneContentPane() {
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.weightx = 1.0;
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
		gbc_jPanelPersonalData.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPersonalData.weightx = 1.0;
		gbc_jPanelPersonalData.weighty = 1.0;
		gbc_jPanelPersonalData.fill = GridBagConstraints.BOTH;
		gbc_jPanelPersonalData.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelPersonalData.gridx = 0;
		gbc_jPanelPersonalData.gridy = 0;

		return gbc_jPanelPersonalData;
	}

	private GridBagLayout getGridLayoutJPanelPersonalData() {
		GridBagLayout gbl_jPanelPersonalData = new GridBagLayout();
		gbl_jPanelPersonalData.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0 };
		gbl_jPanelPersonalData.rowWeights = new double[] { 0.0, 0.0, 0.0 };
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
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 20, 5, 5);
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
	
	private JLabel getJLabelDni() {

		if (jLblDni == null) {
			jLblDni = new JLabel("Dni");
			jLblDni.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblDni;
	}

	private GridBagConstraints getGridJLabelDni() {

		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.WEST;
		gbc_lblDni.insets = new Insets(0, 20, 5, 5);
		gbc_lblDni.gridx = 0;
		gbc_lblDni.gridy = 1;
		
		

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
		gbc_txfDni.weightx = 1.0;
		gbc_txfDni.insets = new Insets(0, 0, 5, 5);
		gbc_txfDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfDni.gridx = 1;
		gbc_txfDni.gridy = 1;
		
		return gbc_txfDni;
		
	}
	
	private JLabel getJLblSex() {
		if (jLblSex == null) {
			jLblSex = new JLabel("Sexo");
			jLblSex.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jLblSex;
	}
	
	private GridBagConstraints getGridJLabelSex() {

		GridBagConstraints gbc_jLblSex = new GridBagConstraints();
		gbc_jLblSex.anchor = GridBagConstraints.WEST;
		gbc_jLblSex.weightx = 1.0;
		gbc_jLblSex.insets = new Insets(0, 15, 5, 5);
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
		gbc_comboBox.weightx = 1.0;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
		
		return gbc_comboBox;
	}



private JCheckBox getJckActive() {
	if (jckActive == null) {
		jckActive = new JCheckBox("Activo");
		jckActive.setFont(new Font("Verdana", Font.PLAIN, 14));
	}
	return jckActive;
}

private GridBagConstraints getGridJCheckActive() {
	GridBagConstraints gbcCheckActive = new GridBagConstraints();
	gbcCheckActive.anchor = GridBagConstraints.WEST;
	gbcCheckActive.insets = new Insets(0, 15, 5, 5);
	gbcCheckActive.gridx = 0;
	gbcCheckActive.gridy = 2;
	return gbcCheckActive;
}
	
	/*  FUNCIONES PANEL ACCIONES*/
	
	private JPanel getJPanelActions() {

		if (jPanelActions == null) {
			jPanelActions = new JPanel();
			jPanelActions.setMinimumSize(new Dimension(100, 100));
			jPanelActions.setMaximumSize(new Dimension(1000, 1000));
		}

		return jPanelActions;
	}
	
	private GridBagConstraints getGridBagConstraintsJPanelActions() {
		GridBagConstraints gbc_jPanelActions = new GridBagConstraints();
		gbc_jPanelActions.weighty = 1.0;
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
			jBtnAccept.setIcon(new ImageIcon(JManageEditPeople.class.getResource("/com/reparadoras/images/icon-check.png")));
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
			jBtnCancel.setIcon(new ImageIcon(JManageEditPeople.class.getResource("/com/reparadoras/images/icon-cancel.png")));
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
