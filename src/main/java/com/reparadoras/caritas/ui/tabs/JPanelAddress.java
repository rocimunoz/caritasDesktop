package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
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

import com.reparadoras.caritas.model.Address;
import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;
import java.awt.Dimension;

public class JPanelAddress extends JPanel {

	private JPanel jPanelAddress;
	/* VIEW ADDRESS */
	private JLabel lblTown;
	private JTextField tfTown;
	private JLabel lblStreet;
	private JTextField tfStreet;
	private JLabel lblGate;
	private JTextField tfGate;
	private JLabel lblFloor;
	private JTextField tfFloor;
	private JLabel lblTelephone;
	private JFormattedTextField tfTelephone;
	private JLabel lblTelephoneContact;
	private JFormattedTextField tfTelephoneContact;
	private JLabel lblDateCensus;
	private JXDatePicker datePickerCensus;
	private JLabel lblPostalCode;
	private JFormattedTextField tfPostalCode;

	public JPanelAddress() {

		createGUIPanel();

	}

	public void createGUIPanel() {

		this.setLayout(getGridLayoutAddress());

		// Añado elementos del Tab Vivienda
		getJPanelAddress().setLayout(getGridLayoutPanelAddress());
		this.add(getJPanelAddress(), getGridJPanelAddress());

		getJPanelAddress().add(getJLabelTown(), getGridJLabelTown());
		getJPanelAddress().add(getJTextFieldTown(), getGridJTextFieldTown());
		getJPanelAddress().add(getJLabelStreet(), getGridJLabelStreet());
		getJPanelAddress().add(getJTextFieldStreet(), getGridJTextFieldStreet());
		getJPanelAddress().add(getJLabelGate(), getGridJLabelGate());
		getJPanelAddress().add(getJTextFieldGate(), getGridJTextFieldGate());
		getJPanelAddress().add(getJLabelFloor(), getGridJLabelFloor());
		getJPanelAddress().add(getJTextFieldFloor(), getGridJTextFieldFloor());
		getJPanelAddress().add(getJLabelTelephone(), getGridJLabelTelephone());
		getJPanelAddress().add(getJTextFieldTelephone(), getGridJTextFieldTelephone());
		getJPanelAddress().add(getJLabelTelephoneContact(), getGridJLabelTelephoneContact());
		getJPanelAddress().add(getJTextFieldTelephoneContact(), getGridJTextFieldTelephoneContact());
		getJPanelAddress().add(getJLabelDatePickerCensus(), this.getGridLblDatePickerCensus());
		getJPanelAddress().add(this.getJXDatePickerCensus(), this.getGridJXDatePickerCensus());
		
		getJPanelAddress().add(this.getJLabelPostalCode(), this.getGridJLabelPostalCode());
		getJPanelAddress().add(this.getJTextFieldPostalCode(), this.getGridJTextFieldPostalCode());

	}

	public JPanel getJPanelAddress() {
		if (jPanelAddress == null) {
			jPanelAddress = new JPanel();
		}
		return jPanelAddress;
	}

	public GridBagConstraints getGridJPanelAddress() {
		GridBagConstraints gbc_jPanelRadio = new GridBagConstraints();
		gbc_jPanelRadio.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelRadio.weighty = 1.0;
		gbc_jPanelRadio.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelRadio.weightx = 1.0;
		gbc_jPanelRadio.insets = new Insets(0, 10, 5, 0);
		gbc_jPanelRadio.gridx = 0;
		gbc_jPanelRadio.gridy = 0;

		return gbc_jPanelRadio;
	}

	public JLabel getJLabelTown() {

		if (lblTown == null) {
			lblTown = new JLabel("Municipio:  ");
			lblTown.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblTown;
	}

	public GridBagConstraints getGridJLabelTown() {
		GridBagConstraints gbc_lblTown = new GridBagConstraints();
		gbc_lblTown.anchor = GridBagConstraints.WEST;
		gbc_lblTown.insets = new Insets(10, 20, 0, 5);
		gbc_lblTown.gridx = 0;
		gbc_lblTown.gridy = 0;

		return gbc_lblTown;
	}

	public JTextField getJTextFieldTown() {
		if (tfTown == null) {
			tfTown = new JTextField();
			tfTown.setColumns(10);
		}
		return tfTown;
	}

	public GridBagConstraints getGridJTextFieldTown() {

		GridBagConstraints gbc_tfTown = new GridBagConstraints();
		gbc_tfTown.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTown.weightx = 1.0;
		gbc_tfTown.anchor = GridBagConstraints.NORTH;
		gbc_tfTown.insets = new Insets(10, 0, 5, 5);
		gbc_tfTown.gridx = 1;
		gbc_tfTown.gridy = 0;

		return gbc_tfTown;
	}

	public JLabel getJLabelStreet() {

		if (lblStreet == null) {
			lblStreet = new JLabel("Calle:  ");
			lblStreet.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblStreet;
	}

	public GridBagConstraints getGridJLabelStreet() {
		GridBagConstraints gbc_lblStreet = new GridBagConstraints();
		gbc_lblStreet.anchor = GridBagConstraints.WEST;
		gbc_lblStreet.insets = new Insets(10, 5, 0, 5);
		gbc_lblStreet.gridx = 2;
		gbc_lblStreet.gridy = 0;

		return gbc_lblStreet;
	}

	public JTextField getJTextFieldStreet() {
		if (tfStreet == null) {
			tfStreet = new JTextField();
			tfStreet.setColumns(10);
		}
		return tfStreet;
	}

	public GridBagConstraints getGridJTextFieldStreet() {

		GridBagConstraints gbc_tfStreet = new GridBagConstraints();
		gbc_tfStreet.weightx = 1.0;
		gbc_tfStreet.anchor = GridBagConstraints.NORTH;
		gbc_tfStreet.insets = new Insets(10, 0, 5, 20);
		gbc_tfStreet.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStreet.gridx = 3;
		gbc_tfStreet.gridy = 0;

		return gbc_tfStreet;
	}

	public JLabel getJLabelGate() {

		if (lblGate == null) {
			lblGate = new JLabel("Portal:  ");
			lblGate.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblGate;
	}

	public GridBagConstraints getGridJLabelGate() {
		GridBagConstraints gbc_lblGate = new GridBagConstraints();
		gbc_lblGate.anchor = GridBagConstraints.WEST;
		gbc_lblGate.insets = new Insets(10, 20, 0, 5);
		gbc_lblGate.gridx = 0;
		gbc_lblGate.gridy = 1;

		return gbc_lblGate;
	}

	public JTextField getJTextFieldGate() {
		if (tfGate == null) {
			tfGate = new JTextField();
			tfGate.setColumns(10);
		}
		return tfGate;
	}

	public GridBagConstraints getGridJTextFieldGate() {

		GridBagConstraints gbc_tfGate = new GridBagConstraints();
		gbc_tfGate.weightx = 1.0;
		gbc_tfGate.anchor = GridBagConstraints.NORTH;
		gbc_tfGate.insets = new Insets(10, 0, 5, 5);
		gbc_tfGate.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGate.gridx = 1;
		gbc_tfGate.gridy = 1;

		return gbc_tfGate;
	}

	public JLabel getJLabelFloor() {

		if (lblFloor == null) {
			lblFloor = new JLabel("Piso y mano:  ");
			lblFloor.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblFloor;
	}

	public GridBagConstraints getGridJLabelFloor() {
		GridBagConstraints gbc_lblFloor = new GridBagConstraints();
		gbc_lblFloor.anchor = GridBagConstraints.WEST;
		gbc_lblFloor.insets = new Insets(10, 5, 0, 5);
		gbc_lblFloor.gridx = 2;
		gbc_lblFloor.gridy = 1;

		return gbc_lblFloor;
	}

	public JTextField getJTextFieldFloor() {
		if (tfFloor == null) {
			tfFloor = new JTextField();
			tfFloor.setColumns(10);
		}
		return tfFloor;
	}

	public GridBagConstraints getGridJTextFieldFloor() {

		GridBagConstraints gbc_tfFloor = new GridBagConstraints();
		gbc_tfFloor.weightx = 1.0;
		gbc_tfFloor.anchor = GridBagConstraints.NORTH;
		gbc_tfFloor.insets = new Insets(10, 0, 5, 20);
		gbc_tfFloor.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfFloor.gridx = 3;
		gbc_tfFloor.gridy = 1;

		return gbc_tfFloor;
	}

	public JLabel getJLabelTelephone() {

		if (lblTelephone == null) {
			lblTelephone = new JLabel("Tfno Domicilio:  ");
			lblTelephone.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblTelephone;
	}

	public GridBagConstraints getGridJLabelTelephone() {
		GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
		gbc_lblTelephone.anchor = GridBagConstraints.WEST;
		gbc_lblTelephone.insets = new Insets(10, 20, 0, 5);
		gbc_lblTelephone.gridx = 0;
		gbc_lblTelephone.gridy = 2;

		return gbc_lblTelephone;
	}

	public JFormattedTextField getJTextFieldTelephone() {
		if (tfTelephone == null) {
			NumberFormat numberFormat = NumberFormat.getNumberInstance();
			numberFormat.setGroupingUsed(false);
			
			tfTelephone = new JFormattedTextField(numberFormat);
			
			tfTelephone.setColumns(10);
		}
		return tfTelephone;
	}

	public GridBagConstraints getGridJTextFieldTelephone() {

		GridBagConstraints gbc_tfTelephone = new GridBagConstraints();
		gbc_tfTelephone.weightx = 1.0;
		gbc_tfTelephone.anchor = GridBagConstraints.NORTH;
		gbc_tfTelephone.insets = new Insets(10, 0, 5, 5);
		gbc_tfTelephone.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTelephone.gridx = 1;
		gbc_tfTelephone.gridy = 2;

		return gbc_tfTelephone;
	}

	public JLabel getJLabelTelephoneContact() {

		if (lblTelephoneContact == null) {
			lblTelephoneContact = new JLabel("Tfno Contacto:  ");
			lblTelephoneContact.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblTelephoneContact;
	}

	public GridBagConstraints getGridJLabelTelephoneContact() {
		GridBagConstraints gbc_lblTelephoneContact = new GridBagConstraints();
		gbc_lblTelephoneContact.anchor = GridBagConstraints.WEST;
		gbc_lblTelephoneContact.insets = new Insets(10, 5, 0, 5);
		gbc_lblTelephoneContact.gridx = 2;
		gbc_lblTelephoneContact.gridy = 2;

		return gbc_lblTelephoneContact;
	}

	public JFormattedTextField getJTextFieldTelephoneContact() {
		if (tfTelephoneContact == null) {
			NumberFormat numberFormat = NumberFormat.getNumberInstance();
			numberFormat.setGroupingUsed(false);
			tfTelephoneContact = new JFormattedTextField(numberFormat);
			tfTelephoneContact.setColumns(10);
		}
		return tfTelephoneContact;
	}

	public GridBagConstraints getGridJTextFieldTelephoneContact() {

		GridBagConstraints gbc_tfTelephoneContact = new GridBagConstraints();
		gbc_tfTelephoneContact.weightx = 1.0;
		gbc_tfTelephoneContact.anchor = GridBagConstraints.NORTH;
		gbc_tfTelephoneContact.insets = new Insets(10, 0, 5, 20);
		gbc_tfTelephoneContact.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTelephoneContact.gridx = 3;
		gbc_tfTelephoneContact.gridy = 2;

		return gbc_tfTelephoneContact;
	}

	public JLabel getJLabelDatePickerCensus() {

		if (lblDateCensus == null) {
			lblDateCensus = new JLabel("Fecha Padrón:  ");
			lblDateCensus.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblDateCensus;
	}

	public GridBagConstraints getGridLblDatePickerCensus() {
		GridBagConstraints gbc_datePickerCensus = new GridBagConstraints();
		gbc_datePickerCensus.anchor = GridBagConstraints.WEST;
		gbc_datePickerCensus.insets = new Insets(10, 20, 0, 5);
		gbc_datePickerCensus.gridx = 0;
		gbc_datePickerCensus.gridy = 3;

		return gbc_datePickerCensus;
	}

	public JXDatePicker getJXDatePickerCensus() {
		if (datePickerCensus == null) {
			datePickerCensus = new JXDatePicker();

		}
		return datePickerCensus;
	}

	public GridBagConstraints getGridJXDatePickerCensus() {

		GridBagConstraints gbc_datePickerCensus = new GridBagConstraints();
		gbc_datePickerCensus.weightx = 1.0;
		gbc_datePickerCensus.anchor = GridBagConstraints.NORTH;
		gbc_datePickerCensus.insets = new Insets(10, 0, 5, 20);
		gbc_datePickerCensus.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePickerCensus.gridx = 1;
		gbc_datePickerCensus.gridy = 3;

		return gbc_datePickerCensus;
	}

	
	public JLabel getJLabelPostalCode() {

		if (lblPostalCode == null) {
			lblPostalCode = new JLabel("Codigo Postal:  ");
			lblPostalCode.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblPostalCode;
	}

	public GridBagConstraints getGridJLabelPostalCode() {
		GridBagConstraints gbc_lblPlace = new GridBagConstraints();
		gbc_lblPlace.anchor = GridBagConstraints.WEST;
		gbc_lblPlace.insets = new Insets(10, 5, 0, 5);
		gbc_lblPlace.gridx = 2;
		gbc_lblPlace.gridy = 3;

		return gbc_lblPlace;
	}

	public JFormattedTextField getJTextFieldPostalCode() {
		if (tfPostalCode == null) {
			NumberFormat numberFormat = NumberFormat.getNumberInstance();
			numberFormat.setMinimumIntegerDigits(0);
			numberFormat.setGroupingUsed(false);

			tfPostalCode = new JFormattedTextField(numberFormat);
			tfPostalCode.setColumns(10);
		}
		return tfPostalCode;
	}

	public GridBagConstraints getGridJTextFieldPostalCode() {

		GridBagConstraints gbc_tfPlace = new GridBagConstraints();
		gbc_tfPlace.weightx = 1.0;
		gbc_tfPlace.anchor = GridBagConstraints.NORTH;
		gbc_tfPlace.insets = new Insets(10, 0, 5, 20);
		gbc_tfPlace.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPlace.gridx = 3;
		gbc_tfPlace.gridy = 3;

		return gbc_tfPlace;
	}

	public GridBagLayout getGridLayoutAddress() {
		GridBagLayout gbl_LaoutAddress = new GridBagLayout();
		gbl_LaoutAddress.columnWeights = new double[] { 0.0 };
		gbl_LaoutAddress.rowWeights = new double[] {};

		return gbl_LaoutAddress;
	}

	public GridBagLayout getGridLayoutPanelAddress() {
		GridBagLayout gbl_LaoutAddress = new GridBagLayout();
		gbl_LaoutAddress.columnWeights = new double[] { 0.0 };
		gbl_LaoutAddress.rowWeights = new double[] {};

		return gbl_LaoutAddress;
	}

	public void cleanAddress() {
		// address
		this.getJTextFieldFloor().setText("");
		this.getJTextFieldGate().setText("");
		
		this.getJTextFieldStreet().setText("");
		this.getJTextFieldTelephone().setText("");
		this.getJTextFieldTelephoneContact().setText("");
		this.getJTextFieldTown().setText("");
		this.getJTextFieldPostalCode().setText("");
		this.getJXDatePickerCensus().setDate(null);

	}

	public void fillData(Address address) {

		
		this.getJTextFieldFloor().setText(address.getFloor());
		this.getJTextFieldGate().setText(address.getGate());
		this.getJTextFieldStreet().setText(address.getStreet());
		this.getJTextFieldTelephone().setText(address.getTelephone());
		this.getJTextFieldTelephoneContact().setText(address.getTelephoneContact());
		this.getJTextFieldTown().setText(address.getTown());
		this.getJTextFieldPostalCode().setText(address.getPostalCode());
		
		this.getJXDatePickerCensus().setDate(address.getCensus());

	}

}
