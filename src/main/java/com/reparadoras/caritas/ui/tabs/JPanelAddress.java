package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.reparadoras.caritas.ui.components.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.RelativesTableModel;

public class JPanelAddress extends JPanel{
	
	
	/*VIEW ADDRESS*/
	private JLabel lblTown;
	private JTextField tfTown;
	private JLabel lblStreet;
	private JTextField tfStreet;
	private JLabel lblGate;
	private JTextField tfGate;
	private JLabel lblFloor;
	private JTextField tfFloor;
	private JLabel lblTelephone;
	private JTextField tfTelephone;
	private JLabel lblTelephoneContact;
	private JTextField tfTelephoneContact;
	private JLabel lblDateCensus;
	private JXDatePicker datePickerCensus;
	private JLabel lblPlace;
	private JTextField tfPlace;
	
	public JPanelAddress() {
		
		createGUIPanel();
		
		
	}
	
	private void createGUIPanel(){
		
		this.setLayout(getGridLayoutAddress());
		
				this.add(getJLabelTown(), getGridJLabelTown());
				this.add(getJTextFieldTown(), getGridJTextFieldTown());
				this.add(getJLabelStreet(), getGridJLabelStreet());
				this.add(getJTextFieldStreet(), getGridJTextFieldStreet());
				this.add(getJLabelGate(), getGridJLabelGate());
				this.add(getJTextFieldGate(), getGridJTextFieldGate());
				this.add(getJLabelFloor(), getGridJLabelFloor());
				this.add(getJTextFieldFloor(), getGridJTextFieldFloor());
				this.add(getJLabelTelephone(), getGridJLabelTelephone());
				this.add(getJTextFieldTelephone(), getGridJTextFieldTelephone());
				this.add(getJLabelTelephoneContact(), getGridJLabelTelephoneContact());
				this.add(getJTextFieldTelephoneContact(), getGridJTextFieldTelephoneContact());
				this.add(getJLabelDatePickerCensus(), this.getGridLblDatePickerCensus());
				this.add(this.getJXDatePickerCensus(), this.getGridJXDatePickerCensus());
				this.add(this.getJLabelPlace(), this.getGridJLabelPlace());
				this.add(this.getJTextFieldPlace(), this.getGridJTextFieldPlace());
	
	}
	
	
	
	
	
	
	private JLabel getJLabelTown() {

		if (lblTown == null) {
			lblTown = new JLabel("Municipio:  ");
			lblTown.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblTown;
	}

	private GridBagConstraints getGridJLabelTown() {
		GridBagConstraints gbc_lblTown = new GridBagConstraints();
		gbc_lblTown.anchor = GridBagConstraints.WEST;
		gbc_lblTown.insets = new Insets(10, 20, 0, 5);
		gbc_lblTown.gridx = 0;
		gbc_lblTown.gridy = 0;

		return gbc_lblTown;
	}
	
	private JTextField getJTextFieldTown() {
		if (tfTown == null) {
			tfTown = new JTextField();
			tfTown.setColumns(10);
		}
		return tfTown;
	}
	
	private GridBagConstraints getGridJTextFieldTown() {

		GridBagConstraints gbc_tfTown = new GridBagConstraints();
		gbc_tfTown.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTown.weightx = 1.0;
		gbc_tfTown.anchor = GridBagConstraints.NORTH;
		gbc_tfTown.insets = new Insets(10, 0, 5, 5);
		gbc_tfTown.gridx = 1;
		gbc_tfTown.gridy = 0;

		return gbc_tfTown;
	}
	
	private JLabel getJLabelStreet() {

		if (lblStreet == null) {
			lblStreet = new JLabel("Calle:  ");
			lblStreet.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblStreet;
	}

	private GridBagConstraints getGridJLabelStreet() {
		GridBagConstraints gbc_lblStreet = new GridBagConstraints();
		gbc_lblStreet.anchor = GridBagConstraints.WEST;
		gbc_lblStreet.insets = new Insets(10, 5, 0, 5);
		gbc_lblStreet.gridx = 2;
		gbc_lblStreet.gridy = 0;

		return gbc_lblStreet;
	}
	
	private JTextField getJTextFieldStreet() {
		if (tfStreet == null) {
			tfStreet = new JTextField();
			tfStreet.setColumns(10);
		}
		return tfStreet;
	}
	
	private GridBagConstraints getGridJTextFieldStreet() {

		GridBagConstraints gbc_tfStreet = new GridBagConstraints();
		gbc_tfStreet.weightx = 1.0;
		gbc_tfStreet.anchor = GridBagConstraints.NORTH;
		gbc_tfStreet.insets = new Insets(10, 0, 5, 20);
		gbc_tfStreet.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStreet.gridx = 3;
		gbc_tfStreet.gridy = 0;

		return gbc_tfStreet;
	}
	
	private JLabel getJLabelGate() {

		if (lblGate == null) {
			lblGate = new JLabel("Portal:  ");
			lblGate.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblGate;
	}
	
	private GridBagConstraints getGridJLabelGate() {
		GridBagConstraints gbc_lblGate = new GridBagConstraints();
		gbc_lblGate.anchor = GridBagConstraints.WEST;
		gbc_lblGate.insets = new Insets(10, 20, 0, 5);
		gbc_lblGate.gridx = 0;
		gbc_lblGate.gridy = 1;

		return gbc_lblGate;
	}
	
	private JTextField getJTextFieldGate() {
		if (tfGate == null) {
			tfGate = new JTextField();
			tfGate.setColumns(10);
		}
		return tfGate;
	}
	
	private GridBagConstraints getGridJTextFieldGate() {

		GridBagConstraints gbc_tfGate = new GridBagConstraints();
		gbc_tfGate.weightx = 1.0;
		gbc_tfGate.anchor = GridBagConstraints.NORTH;
		gbc_tfGate.insets = new Insets(10, 0, 5, 5);
		gbc_tfGate.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGate.gridx = 1;
		gbc_tfGate.gridy = 1;

		return gbc_tfGate;
	}
	
	private JLabel getJLabelFloor() {

		if (lblFloor == null) {
			lblFloor = new JLabel("Piso y mano:  ");
			lblFloor.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblFloor;
	}
	
	private GridBagConstraints getGridJLabelFloor() {
		GridBagConstraints gbc_lblFloor = new GridBagConstraints();
		gbc_lblFloor.anchor = GridBagConstraints.WEST;
		gbc_lblFloor.insets = new Insets(10, 5, 0, 5);
		gbc_lblFloor.gridx = 2;
		gbc_lblFloor.gridy = 1;

		return gbc_lblFloor;
	}
	
	private JTextField getJTextFieldFloor() {
		if (tfFloor == null) {
			tfFloor = new JTextField();
			tfFloor.setColumns(10);
		}
		return tfFloor;
	}
	
	private GridBagConstraints getGridJTextFieldFloor() {

		GridBagConstraints gbc_tfFloor = new GridBagConstraints();
		gbc_tfFloor.weightx = 1.0;
		gbc_tfFloor.anchor = GridBagConstraints.NORTH;
		gbc_tfFloor.insets = new Insets(10, 0, 5, 20);
		gbc_tfFloor.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfFloor.gridx = 3;
		gbc_tfFloor.gridy = 1;

		return gbc_tfFloor;
	}
	
	private JLabel getJLabelTelephone() {

		if (lblTelephone == null) {
			lblTelephone = new JLabel("Tfno Domicilio:  ");
			lblTelephone.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblTelephone;
	}
	
	private GridBagConstraints getGridJLabelTelephone() {
		GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
		gbc_lblTelephone.anchor = GridBagConstraints.WEST;
		gbc_lblTelephone.insets = new Insets(10, 20, 0, 5);
		gbc_lblTelephone.gridx = 0;
		gbc_lblTelephone.gridy = 2;

		return gbc_lblTelephone;
	}
	
	private JTextField getJTextFieldTelephone() {
		if (tfTelephone == null) {
			tfTelephone = new JTextField();
			tfTelephone.setColumns(10);
		}
		return tfTelephone;
	}
	
	private GridBagConstraints getGridJTextFieldTelephone() {

		GridBagConstraints gbc_tfTelephone = new GridBagConstraints();
		gbc_tfTelephone.weightx = 1.0;
		gbc_tfTelephone.anchor = GridBagConstraints.NORTH;
		gbc_tfTelephone.insets = new Insets(10, 0, 5, 5);
		gbc_tfTelephone.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTelephone.gridx = 1;
		gbc_tfTelephone.gridy = 2;

		return gbc_tfTelephone;
	}
	
	private JLabel getJLabelTelephoneContact() {

		if (lblTelephoneContact == null) {
			lblTelephoneContact = new JLabel("Tfno Contacto:  ");
			lblTelephoneContact.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblTelephoneContact;
	}
	
	private GridBagConstraints getGridJLabelTelephoneContact() {
		GridBagConstraints gbc_lblTelephoneContact = new GridBagConstraints();
		gbc_lblTelephoneContact.anchor = GridBagConstraints.WEST;
		gbc_lblTelephoneContact.insets = new Insets(10, 5, 0, 5);
		gbc_lblTelephoneContact.gridx = 2;
		gbc_lblTelephoneContact.gridy = 2;

		return gbc_lblTelephoneContact;
	}
	
	private JTextField getJTextFieldTelephoneContact() {
		if (tfTelephoneContact == null) {
			tfTelephoneContact = new JTextField();
			tfTelephoneContact.setColumns(10);
		}
		return tfTelephoneContact;
	}
	
	private GridBagConstraints getGridJTextFieldTelephoneContact() {

		GridBagConstraints gbc_tfTelephoneContact = new GridBagConstraints();
		gbc_tfTelephoneContact.weightx = 1.0;
		gbc_tfTelephoneContact.anchor = GridBagConstraints.NORTH;
		gbc_tfTelephoneContact.insets = new Insets(10, 0, 5, 20);
		gbc_tfTelephoneContact.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTelephoneContact.gridx = 3;
		gbc_tfTelephoneContact.gridy = 2;

		return gbc_tfTelephoneContact;
	}
	
	private JLabel getJLabelDatePickerCensus() {

		if (lblDateCensus == null) {
			lblDateCensus = new JLabel("Fecha Padrón:  ");
			lblDateCensus.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblDateCensus;
	}
	
	private GridBagConstraints getGridLblDatePickerCensus() {
		GridBagConstraints gbc_datePickerCensus = new GridBagConstraints();
		gbc_datePickerCensus.anchor = GridBagConstraints.WEST;
		gbc_datePickerCensus.insets = new Insets(10, 20, 0, 5);
		gbc_datePickerCensus.gridx = 0;
		gbc_datePickerCensus.gridy = 3;

		return gbc_datePickerCensus;
	}
	
	private JXDatePicker getJXDatePickerCensus() {
		if (datePickerCensus == null) {
			datePickerCensus = new JXDatePicker();
			
		}
		return datePickerCensus;
	}
	
	private GridBagConstraints getGridJXDatePickerCensus() {

		GridBagConstraints gbc_datePickerCensus = new GridBagConstraints();
		gbc_datePickerCensus.weightx = 1.0;
		gbc_datePickerCensus.anchor = GridBagConstraints.NORTH;
		gbc_datePickerCensus.insets = new Insets(10, 0, 5, 20);
		gbc_datePickerCensus.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePickerCensus.gridx = 1;
		gbc_datePickerCensus.gridy = 3;

		return gbc_datePickerCensus;
	}
	
	private JLabel getJLabelPlace() {

		if (lblPlace == null) {
			lblPlace = new JLabel("Lugar:  ");
			lblPlace.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblPlace;
	}
	
	private GridBagConstraints getGridJLabelPlace() {
		GridBagConstraints gbc_lblPlace = new GridBagConstraints();
		gbc_lblPlace.anchor = GridBagConstraints.WEST;
		gbc_lblPlace.insets = new Insets(10, 5, 0, 5);
		gbc_lblPlace.gridx = 2;
		gbc_lblPlace.gridy = 3;

		return gbc_lblPlace;
	}
	
	private JTextField getJTextFieldPlace() {
		if (tfPlace == null) {
			tfPlace = new JTextField();
			tfPlace.setColumns(10);
		}
		return tfPlace;
	}
	
	private GridBagConstraints getGridJTextFieldPlace() {

		GridBagConstraints gbc_tfPlace = new GridBagConstraints();
		gbc_tfPlace.weightx = 1.0;
		gbc_tfPlace.anchor = GridBagConstraints.NORTH;
		gbc_tfPlace.insets = new Insets(10, 0, 5, 20);
		gbc_tfPlace.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPlace.gridx = 3;
		gbc_tfPlace.gridy = 3;

		return gbc_tfPlace;
	}
	
	private GridBagLayout getGridLayoutAddress() {
		GridBagLayout gbl_LaoutAddress = new GridBagLayout();
		gbl_LaoutAddress.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0};
		gbl_LaoutAddress.rowWeights = new double[] { };

		return gbl_LaoutAddress;
	}

	
	
	
	
	
	
	
	
	
}
