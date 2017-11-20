package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.utils.PeopleVerifier;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")

public class JManageEditPeople extends AbstractJInternalFrame {

	static final Logger logger = Logger.getLogger(JManageEditPeople.class);
	
	private JPanel jPanelContentPane;

	private JPanel jPanelPersonalData;
	private JLabel jLblName;
	private JTextField txfName;
	private JLabel jLblDni;
	private JTextField txfDni;
	private JLabel jLblPassport;
	private JTextField txfPassport;
	private JTextField txfFirstSurname;
	private JTextField txfSecondSurname;
	private JComboBox<String> jComboBoxSex;
	private JLabel jLblSex;

	private JLabel jLblCountry;
	private JTextField txfCountry;
	private JLabel jLblNationality;
	private JTextField txfNationality;
	private JLabel jLblYearToSpain;
	private JFormattedTextField txfYearToSpain;
	private JLabel jLblCreateDate;
	private JXDatePicker jxCreateDate;
	private JLabel jLblDateBorn;
	private JXDatePicker jxDateBorn;
	private JLabel jLblReactivateDate;
	private JXDatePicker jxReactivateDate;

	private JPanel jPanelActions;
	private JButton jBtnAccept;
	private JButton jBtnCancel;

	private PeopleDAO peopleDAO;
	private PeopleVerifier peopleVerifier = new PeopleVerifier();

	private int executingMode;

	private People selectedPeople;
	private JCheckBox jckActive;

	private AbstractJInternalFrame jCicIFParent;
	private JLabel jLblFirstSurname;
	private JLabel jLblSecondSurname;

	public JManageEditPeople(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title,
			People people) throws Exception {
		super(jCicIFParent, modal);
		setVisible(true);
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setSize(935, 301);
		this.setTitle(title);

		this.selectedPeople = people;
		this.executingMode = executingMode;
		this.jCicIFParent = jCicIFParent;
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		initComponents();
		configureModeEdition(executingMode);
		fillData(executingMode);

		getContentPane().setLayout(getGridLayoutContentPane());
		getContentPane().add(getJPanelContentPane(), getGridBagConstraintsJPaneContentPane());
		getJPanelContentPane().setLayout(getGridLayoutJPaneContentPane());

		getJPanelContentPane().add(getJPanelPersonalData(), getGridJPanelPersonalData());
		getJPanelPersonalData().setLayout(getGridLayoutJPanelPersonalData());

		getJPanelPersonalData().add(getJLabelName(), getGridJLabelName());
		getJPanelPersonalData().add(getJTextFieldName(), getGridJTextFieldName());
		getJPanelPersonalData().add(getJLabelFirstSurname(), getGridJLabelFirstSurname());
		getJPanelPersonalData().add(getJTextFieldFirstSurname(), getGridJTextFieldFirstSurname());
		getJPanelPersonalData().add(getJLabelSecondSurname(), getGridJLabelSecondSurname());
		getJPanelPersonalData().add(getJTextFieldSecondSurname(), getGridJTextFieldSecondSurname());
		getJPanelPersonalData().add(getJLabelDni(), getGridJLabelDni());
		getJPanelPersonalData().add(getJTextFieldDni(), getGridJTextFieldDni());
		getJPanelPersonalData().add(getJLabelPassport(), getGridJLabelPassport());
		getJPanelPersonalData().add(getJTextFieldPassport(), getGridJTextFieldPassport());
		getJPanelPersonalData().add(getComboBox(), getGridComboBoxSex());
		getJPanelPersonalData().add(getJLblSex(), getGridJLabelSex());
		getJPanelPersonalData().add(getJLabelCountry(), getGridJLabelCountry());
		getJPanelPersonalData().add(getJTextFieldCountry(), getGridJTextFieldCountry());
		getJPanelPersonalData().add(getJLabelNationality(), getGridJLabelNationality());
		getJPanelPersonalData().add(getJTextFieldNationality(), getGridJTextFieldNationality());
		getJPanelPersonalData().add(getJLabelYearToSpain(), getGridJLabelYearToSpain());
		getJPanelPersonalData().add(getJTextFieldYearToSpain(), getGridJTextFieldYearToSpain());
		getJPanelPersonalData().add(getJLabelCreateDate(), getGridJLabelCreateDate());
		getJPanelPersonalData().add(getJXCreateDate(), getGridJXCreateDate());
		getJPanelPersonalData().add(getJLabelReactivateDate(), getGridJLabelReactivateDate());
		getJPanelPersonalData().add(getJXReactivateDate(), getGridJXReactivateDate());
		getJPanelPersonalData().add(getJckActive(), getGridJCheckActive());
		getJPanelPersonalData().add(getJLabelDateBorn(), getGridJLabelDateBorn());
		getJPanelPersonalData().add(getJXDateBorn(), getGridJXDateBorn());
		getJPanelContentPane().add(getJPanelActions(), getGridBagConstraintsJPanelActions());
		getJPanelActions().setLayout(getGridLayoutJPanelActions());

		getJButtonCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseWindow();
			}
		});

		getJButtonAccept().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkRequiredFields()) {
					// Abrir transaccion
					if (executingMode == JWindowParams.IMODE_UPDATE) {
						onUpdatePeople();
					} else if (executingMode == JWindowParams.IMODE_INSERT) {
						onCreatePeople();
					}

					// Cerrar Transaccion
					onCloseWindow();
				}

			}
		});

		getJPanelActions().add(getJButtonAccept(), getGridBagConstraintsJButtonAccept());

		getJPanelActions().add(getJButtonCancel(), getGridBagConstraintsJButtonCancel());

	}

	private void onCloseWindow() {
		try {
			this.setClosed(true);

		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error onCloseWindow " + e.getMessage());
		}
	}

	private void initComponents() {
		this.getComboBox().addItem("V");
		this.getComboBox().addItem("F");

	}

	private void configureModeEdition(int mode) {

		if (mode == JWindowParams.IMODE_SELECT) {
			// Deshabilito campos

			this.getJTextFieldName().setEditable(false);
			this.getJTextFieldName().setText(this.selectedPeople.getName());
			this.getJTextFieldFirstSurname().setEditable(false);
			this.getJTextFieldFirstSurname().setText(this.selectedPeople.getFirstSurname());
			this.getJTextFieldSecondSurname().setEditable(false);
			this.getJTextFieldSecondSurname().setText(this.selectedPeople.getSecondSurname());
			this.getJTextFieldDni().setEditable(false);
			this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldPassport().setEditable(false);
			this.getJTextFieldPassport().setText(this.selectedPeople.getPassport());
			this.getComboBox().setEnabled(false);
			this.getComboBox().setSelectedItem(this.selectedPeople.getSex());
			this.getJTextFieldCountry().setEditable(false);
			this.getJTextFieldCountry().setText(this.selectedPeople.getCountry());
			this.getJTextFieldNationality().setEditable(false);
			this.getJTextFieldNationality().setText(this.selectedPeople.getNationality());
			this.getJTextFieldYearToSpain().setEditable(false);
			this.getJTextFieldYearToSpain().setText(this.selectedPeople.getYearToSpain() + ""); // revisar
			this.getJXCreateDate().setEditable(false);
			this.getJXCreateDate().setDate(this.selectedPeople.getCreateDate());
			this.getJXReactivateDate().setEditable(false);
			this.getJXReactivateDate().setDate(this.selectedPeople.getReactivateDate());
			this.getJckActive().setEnabled(false);
			this.getJXDateBorn().setDate(this.selectedPeople.getDateBorn());

			this.getJButtonAccept().setVisible(false);
			this.getJButtonCancel().setText("Salir");
		} else {
			// Habilito campos
			this.getJTextFieldDni().setEditable(true);

			this.getJTextFieldName().setEditable(true);
			this.getJTextFieldFirstSurname().setEditable(true);
			this.getJTextFieldSecondSurname().setEditable(true);
			this.getJTextFieldDni().setEditable(true);
			this.getJTextFieldPassport().setEditable(true);
			this.getComboBox().setEditable(true);
			this.getJTextFieldCountry().setEditable(true);
			this.getJTextFieldNationality().setEditable(true);
			this.getJTextFieldYearToSpain().setEditable(true);
			this.getJXCreateDate().setEditable(true);
			this.getJXReactivateDate().setEditable(true);
			this.getJckActive().setEnabled(true);

			this.getJButtonAccept().setVisible(true);
			this.getJButtonCancel().setText("Cancelar");
		}
	}

	private void fillData(int mode) {
		if (mode == JWindowParams.IMODE_SELECT || mode == JWindowParams.IMODE_UPDATE) {
			this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldName().setText(this.selectedPeople.getName());
			this.getJTextFieldFirstSurname().setText(this.selectedPeople.getFirstSurname());
			this.getJTextFieldSecondSurname().setText(this.selectedPeople.getSecondSurname());
			this.getJTextFieldDni().setText(this.selectedPeople.getDni());
			this.getJTextFieldPassport().setText(this.selectedPeople.getPassport());
			this.getComboBox().setSelectedItem(this.selectedPeople.getSex());
			this.getJTextFieldCountry().setText(this.selectedPeople.getCountry());
			this.getJTextFieldNationality().setText(this.selectedPeople.getNationality());
			if (this.selectedPeople.getYearToSpain() != null) {
				this.getJTextFieldYearToSpain().setText(String.valueOf(this.selectedPeople.getYearToSpain()));
			}else{
				this.getJTextFieldYearToSpain().setText(null);
			}

			this.getJXCreateDate().setDate(this.selectedPeople.getCreateDate());
			this.getJXReactivateDate().setDate(this.selectedPeople.getReactivateDate());
			this.getJckActive().setSelected(this.selectedPeople.isActive());
			this.getJXDateBorn().setDate(this.selectedPeople.getDateBorn());

		} else if (mode == JWindowParams.IMODE_INSERT) {
			this.getJckActive().setSelected(true);
		}

	}

	private boolean manageReactivateDate() {
		// Compruebo si en BBDD no estaba activo
		People peopleBBDD = peopleDAO.findPeopleById(this.selectedPeople);
		if (peopleBBDD != null) {
			if (!peopleBBDD.isActive()) {
				return true;
			} else
				return false;
		} else
			return false;

	}

	private void onUpdatePeople() {
		try {

			if (this.getJckActive().isSelected()) {
				if (manageReactivateDate()) {
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

					this.getJXReactivateDate().setDate(sf.parse(sf.format(new Date())));
				}
				this.selectedPeople.setActive(true);

			} else {
				this.selectedPeople.setActive(false);
			}

			this.selectedPeople.setName(this.getJTextFieldName().getText());
			this.selectedPeople.setFirstSurname(this.getJTextFieldFirstSurname().getText());
			this.selectedPeople.setSecondSurname(this.getJTextFieldSecondSurname().getText());
			this.selectedPeople.setDni(this.getJTextFieldDni().getText());
			this.selectedPeople.setPassport(this.getJTextFieldPassport().getText());
			this.selectedPeople.setSex((String) this.getComboBox().getSelectedItem());
			this.selectedPeople.setCountry(this.getJTextFieldCountry().getText());
			this.selectedPeople.setNationality(this.getJTextFieldNationality().getText());
			if (this.getJTextFieldYearToSpain().getText() != null && !this.getJTextFieldYearToSpain().getText().equals("")) {
				this.selectedPeople.setYearToSpain(Integer.parseInt(this.getJTextFieldYearToSpain().getText().replace(".", "")));
			} else {
				this.selectedPeople.setYearToSpain(null);
			}

			this.selectedPeople.setCreateDate(this.getJXCreateDate().getDate());
			this.selectedPeople.setReactivateDate(this.getJXReactivateDate().getDate());
			this.selectedPeople.setDateBorn(this.getJXDateBorn().getDate());

			peopleDAO.update(selectedPeople);

			JOptionPane.showMessageDialog(this, "Actualizado correctamente.", "Error", JOptionPane.INFORMATION_MESSAGE);
			((JManagePeople) jCicIFParent).filterPeople();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro",
					"Actualización Persona", JOptionPane.ERROR_MESSAGE);
			logger.error("Error actualizando persona" + e);
			
		}

	}

	private void onCreatePeople() {
		try {

			People people = new People();
			people.setName(this.getJTextFieldName().getText());
			people.setFirstSurname(this.getJTextFieldFirstSurname().getText());
			people.setSecondSurname(this.getJTextFieldSecondSurname().getText());

			people.setDni(this.getJTextFieldDni().getText());
			people.setPassport(this.getJTextFieldPassport().getText());
			people.setSex((String) this.getComboBox().getSelectedItem());
			people.setCountry(this.getJTextFieldCountry().getText());
			people.setNationality(this.getJTextFieldNationality().getText());
			if (this.getJTextFieldYearToSpain().getText() != null
					&& !this.getJTextFieldYearToSpain().getText().equals("")) {
				people.setYearToSpain(Integer.parseInt(this.getJTextFieldYearToSpain().getText().replace(".", "")));
			}

			people.setCreateDate(this.getJXCreateDate().getDate());
			people.setReactivateDate(this.getJXReactivateDate().getDate());
			people.setActive(this.getJckActive().isSelected());
			people.setDateBorn(this.getJXCreateDate().getDate());

			// save people
			peopleDAO.insert(people);

			JOptionPane.showMessageDialog(this, "Se ha dado de alta correctamente a " + people.getName(),
					"Inserción Persona", JOptionPane.INFORMATION_MESSAGE);
			((JManagePeople) jCicIFParent).filterPeople();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
			logger.error("Error guardando persona" + e);
		}

	}

	private boolean checkRequiredFields() {
		String message = "";
		boolean state = true;
		if (this.getJTextFieldName().getText().equals("")) {
			message = "El nombre es obligatorio";
			state = false;
		} else if (this.getJTextFieldFirstSurname().getText().equals("")) {
			message = "El primer apellido es obligatorio";
			state = false;
		}

		if (state == false) {
			JOptionPane.showMessageDialog(this, "Revise los datos introducidos. " + message, "Error Dialog",
					JOptionPane.ERROR_MESSAGE);
		}

		return state;

	}

	/* FUNCIONES PANEL CONTENT PANE */

	private GridBagLayout getGridLayoutContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };

		return gridBagLayout;
	}

	private JPanel getJPanelContentPane() {

		if (jPanelContentPane == null) {
			jPanelContentPane = new JPanel();
			jPanelContentPane.setMaximumSize(new Dimension(10, 10));
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
			jPanelPersonalData.setBorder(
					new TitledBorder(null, "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			

			((javax.swing.border.TitledBorder) jPanelPersonalData.getBorder())
					.setTitleFont(new Font("Verdana", Font.ITALIC, 18));

		}

		return jPanelPersonalData;
	}

	private GridBagConstraints getGridJPanelPersonalData() {

		GridBagConstraints gbc_jPanelPersonalData = new GridBagConstraints();
		gbc_jPanelPersonalData.weighty = 1.0;
		gbc_jPanelPersonalData.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelPersonalData.anchor = GridBagConstraints.NORTH;
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

		if (txfName == null) {
			txfName = new JTextField(100);
			txfName.setColumns(10);
			txfName.setName("name");
			// txfName.setInputVerifier(peopleVerifier);

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


	private JLabel getJLabelFirstSurname() {

		if (jLblFirstSurname == null) {
			jLblFirstSurname = new JLabel("Primer Apellido");
			jLblFirstSurname.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblFirstSurname.setPreferredSize(new Dimension(80, 25));

		}

		return jLblFirstSurname;
	}

	private GridBagConstraints getGridJLabelFirstSurname() {
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 20, 5, 5);
		gbc_lblName.gridx = 2;
		gbc_lblName.gridy = 0;

		return gbc_lblName;
	}

	private JTextField getJTextFieldFirstSurname() {

		if (txfFirstSurname == null) {
			txfFirstSurname = new JTextField(100);
			//txfFirstSurname.setDocument(new JTextFieldLimit(100));
			txfFirstSurname.setColumns(10);
			txfFirstSurname.setName("firstSurname");
			// txfFirstSurname.setInputVerifier(peopleVerifier);
		}

		return txfFirstSurname;
	}

	private GridBagConstraints getGridJTextFieldFirstSurname() {

		GridBagConstraints gbc_txfFirstSurname = new GridBagConstraints();
		gbc_txfFirstSurname.weightx = 1.0;
		gbc_txfFirstSurname.insets = new Insets(0, 0, 5, 5);
		gbc_txfFirstSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfFirstSurname.gridx = 3;
		gbc_txfFirstSurname.gridy = 0;

		return gbc_txfFirstSurname;

	}

	private JLabel getJLabelSecondSurname() {

		if (jLblSecondSurname == null) {
			jLblSecondSurname = new JLabel("Segundo Apellido");
			jLblSecondSurname.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblSecondSurname.setPreferredSize(new Dimension(80, 25));

		}

		return jLblSecondSurname;
	}

	private GridBagConstraints getGridJLabelSecondSurname() {
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 20, 5, 5);
		gbc_lblName.gridx = 4;
		gbc_lblName.gridy = 0;

		return gbc_lblName;
	}
	

	private JTextField getJTextFieldSecondSurname() {

		if (txfSecondSurname == null) {
			txfSecondSurname = new JTextField(100);
			txfSecondSurname.setColumns(10);
			txfSecondSurname.setName("secondSurname");
			// txfSecondSurname.setInputVerifier(peopleVerifier);
		}

		return txfSecondSurname;
	}

	private GridBagConstraints getGridJTextFieldSecondSurname() {

		GridBagConstraints gbc_txfSecondSurname = new GridBagConstraints();
		gbc_txfSecondSurname.weightx = 1.0;
		gbc_txfSecondSurname.insets = new Insets(0, 0, 5, 0);
		gbc_txfSecondSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfSecondSurname.gridx = 5;
		gbc_txfSecondSurname.gridy = 0;

		return gbc_txfSecondSurname;

	}

	private JLabel getJLabelDni() {

		if (jLblDni == null) {
			jLblDni = new JLabel("Dni");
			jLblDni.setMaximumSize(new Dimension(30, 14));
			jLblDni.setHorizontalAlignment(SwingConstants.LEFT);
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

		if (txfDni == null) {
			txfDni = new JTextField(9);
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

	private JLabel getJLabelPassport() {

		if (jLblPassport == null) {
			jLblPassport = new JLabel("Pasaporte");
			jLblPassport.setMaximumSize(new Dimension(30, 14));
			jLblPassport.setHorizontalAlignment(SwingConstants.LEFT);
			jLblPassport.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblPassport;
	}

	private GridBagConstraints getGridJLabelPassport() {

		GridBagConstraints gbc_lblPassport = new GridBagConstraints();
		gbc_lblPassport.anchor = GridBagConstraints.WEST;
		gbc_lblPassport.insets = new Insets(0, 20, 5, 5);
		gbc_lblPassport.gridx = 2;
		gbc_lblPassport.gridy = 1;

		return gbc_lblPassport;
	}

	private JTextField getJTextFieldPassport() {

		if (txfPassport == null) {
			txfPassport = new JTextField(20);

		}

		return txfPassport;
	}

	private GridBagConstraints getGridJTextFieldPassport() {

		GridBagConstraints gbc_txfPassport = new GridBagConstraints();
		gbc_txfPassport.weightx = 1.0;
		gbc_txfPassport.insets = new Insets(0, 0, 5, 5);
		gbc_txfPassport.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfPassport.gridx = 3;
		gbc_txfPassport.gridy = 1;

		return gbc_txfPassport;

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
		gbc_jLblSex.gridx = 4;
		gbc_jLblSex.gridy = 1;

		return gbc_jLblSex;

	}

	private JComboBox getComboBox() {
		if (jComboBoxSex == null) {
			jComboBoxSex = new JComboBox();
		}
		return jComboBoxSex;
	}

	private JLabel getJLabelCountry() {

		if (jLblCountry == null) {
			jLblCountry = new JLabel("Pais");
			jLblCountry.setMaximumSize(new Dimension(30, 14));
			jLblCountry.setHorizontalAlignment(SwingConstants.LEFT);
			jLblCountry.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblCountry;
	}

	private GridBagConstraints getGridJLabelCountry() {

		GridBagConstraints gbc_lblCountry = new GridBagConstraints();
		gbc_lblCountry.anchor = GridBagConstraints.WEST;
		gbc_lblCountry.insets = new Insets(0, 20, 5, 5);
		gbc_lblCountry.gridx = 0;
		gbc_lblCountry.gridy = 2;

		return gbc_lblCountry;
	}

	private JTextField getJTextFieldCountry() {

		if (txfCountry == null) {
			txfCountry = new JTextField(100);

		}

		return txfCountry;
	}

	private GridBagConstraints getGridJTextFieldCountry() {

		GridBagConstraints gbc_txfCountry = new GridBagConstraints();
		gbc_txfCountry.weightx = 1.0;
		gbc_txfCountry.insets = new Insets(0, 0, 5, 5);
		gbc_txfCountry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfCountry.gridx = 1;
		gbc_txfCountry.gridy = 2;

		return gbc_txfCountry;

	}

	private JLabel getJLabelNationality() {

		if (jLblNationality == null) {
			jLblNationality = new JLabel("Nacionalidad");
			jLblNationality.setMaximumSize(new Dimension(30, 14));
			jLblNationality.setHorizontalAlignment(SwingConstants.LEFT);
			jLblNationality.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblNationality;
	}

	private GridBagConstraints getGridJLabelNationality() {

		GridBagConstraints gbc_lblNationality = new GridBagConstraints();
		gbc_lblNationality.anchor = GridBagConstraints.WEST;
		gbc_lblNationality.insets = new Insets(0, 20, 5, 5);
		gbc_lblNationality.gridx = 2;
		gbc_lblNationality.gridy = 2;

		return gbc_lblNationality;
	}

	private JTextField getJTextFieldNationality() {

		if (txfNationality == null) {
			txfNationality = new JTextField(100);

		}

		return txfNationality;
	}

	private GridBagConstraints getGridJTextFieldNationality() {

		GridBagConstraints gbc_txfNationality = new GridBagConstraints();
		gbc_txfNationality.weightx = 1.0;
		gbc_txfNationality.insets = new Insets(0, 0, 5, 5);
		gbc_txfNationality.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfNationality.gridx = 3;
		gbc_txfNationality.gridy = 2;

		return gbc_txfNationality;

	}

	private JLabel getJLabelYearToSpain() {

		if (jLblYearToSpain == null) {
			jLblYearToSpain = new JLabel("Año llegada España");
			jLblYearToSpain.setMaximumSize(new Dimension(30, 14));
			jLblYearToSpain.setHorizontalAlignment(SwingConstants.LEFT);
			jLblYearToSpain.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblYearToSpain;
	}

	private GridBagConstraints getGridJLabelYearToSpain() {

		GridBagConstraints gbc_lblYearToSpain = new GridBagConstraints();
		gbc_lblYearToSpain.anchor = GridBagConstraints.WEST;
		gbc_lblYearToSpain.insets = new Insets(0, 20, 5, 5);
		gbc_lblYearToSpain.gridx = 4;
		gbc_lblYearToSpain.gridy = 2;

		return gbc_lblYearToSpain;
	}

	public JFormattedTextField getJTextFieldYearToSpain() {
		if (txfYearToSpain == null) {
			
			DecimalFormat formatter = new DecimalFormat("#0,000");
		
			txfYearToSpain = new JFormattedTextField(formatter);
			txfYearToSpain.setColumns(10);
			txfYearToSpain.setName("yearToSpain");
		}
		return txfYearToSpain;
	}

	private GridBagConstraints getGridJTextFieldYearToSpain() {

		GridBagConstraints gbc_txfYearToSpain = new GridBagConstraints();
		gbc_txfYearToSpain.weightx = 1.0;
		gbc_txfYearToSpain.insets = new Insets(0, 0, 5, 0);
		gbc_txfYearToSpain.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfYearToSpain.gridx = 5;
		gbc_txfYearToSpain.gridy = 2;

		return gbc_txfYearToSpain;

	}

	private JLabel getJLabelCreateDate() {

		if (jLblCreateDate == null) {
			jLblCreateDate = new JLabel("Fecha Alta");
			jLblCreateDate.setMaximumSize(new Dimension(30, 14));
			jLblCreateDate.setHorizontalAlignment(SwingConstants.LEFT);
			jLblCreateDate.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblCreateDate;
	}

	private GridBagConstraints getGridJLabelCreateDate() {

		GridBagConstraints gbc_lblCreateDate = new GridBagConstraints();
		gbc_lblCreateDate.anchor = GridBagConstraints.WEST;
		gbc_lblCreateDate.insets = new Insets(0, 20, 0, 5);
		gbc_lblCreateDate.gridx = 0;
		gbc_lblCreateDate.gridy = 3;

		return gbc_lblCreateDate;
	}

	private JXDatePicker getJXCreateDate() {

		if (jxCreateDate == null) {
			jxCreateDate = new JXDatePicker();
			jxCreateDate.setName("createDate");
			jxCreateDate.setInputVerifier(peopleVerifier);
		}

		return jxCreateDate;
	}

	private GridBagConstraints getGridJXCreateDate() {

		GridBagConstraints gbc_txfCreateDate = new GridBagConstraints();
		gbc_txfCreateDate.weightx = 1.0;
		gbc_txfCreateDate.insets = new Insets(0, 0, 0, 5);
		gbc_txfCreateDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfCreateDate.gridx = 1;
		gbc_txfCreateDate.gridy = 3;

		return gbc_txfCreateDate;

	}

	private JLabel getJLabelReactivateDate() {

		if (jLblReactivateDate == null) {
			jLblReactivateDate = new JLabel("Fecha Reactivacion");
			jLblReactivateDate.setMaximumSize(new Dimension(30, 14));
			jLblReactivateDate.setHorizontalAlignment(SwingConstants.LEFT);
			jLblReactivateDate.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblReactivateDate;
	}

	private GridBagConstraints getGridJLabelReactivateDate() {

		GridBagConstraints gbc_lblReactivateDate = new GridBagConstraints();
		gbc_lblReactivateDate.anchor = GridBagConstraints.WEST;
		gbc_lblReactivateDate.insets = new Insets(0, 20, 0, 5);
		gbc_lblReactivateDate.gridx = 2;
		gbc_lblReactivateDate.gridy = 3;

		return gbc_lblReactivateDate;
	}

	private JXDatePicker getJXReactivateDate() {

		if (jxReactivateDate == null) {
			jxReactivateDate = new JXDatePicker();
			jxReactivateDate.setEnabled(false);
		}

		return jxReactivateDate;
	}

	private GridBagConstraints getGridJXReactivateDate() {

		GridBagConstraints gbc_txfReactivateDate = new GridBagConstraints();
		gbc_txfReactivateDate.weightx = 1.0;
		gbc_txfReactivateDate.insets = new Insets(0, 0, 0, 5);
		gbc_txfReactivateDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfReactivateDate.gridx = 3;
		gbc_txfReactivateDate.gridy = 3;

		return gbc_txfReactivateDate;

	}

	private GridBagConstraints getGridComboBoxSex() {

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.weightx = 1.0;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 5;
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
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 4;
		gbcCheckActive.gridy = 3;
		return gbcCheckActive;
	}
	
	private JLabel getJLabelDateBorn() {

		if (jLblDateBorn == null) {
			jLblDateBorn = new JLabel("Fecha Nacimiento");
			jLblDateBorn.setMaximumSize(new Dimension(30, 14));
			jLblDateBorn.setHorizontalAlignment(SwingConstants.LEFT);
			jLblDateBorn.setFont(new Font("Verdana", Font.PLAIN, 14));
		}

		return jLblDateBorn;
	}

	private GridBagConstraints getGridJLabelDateBorn() {

		GridBagConstraints gbc_lblCreateDate = new GridBagConstraints();
		gbc_lblCreateDate.anchor = GridBagConstraints.WEST;
		gbc_lblCreateDate.insets = new Insets(0, 20, 0, 5);
		gbc_lblCreateDate.gridx = 0;
		gbc_lblCreateDate.gridy = 4;

		return gbc_lblCreateDate;
	}

	private JXDatePicker getJXDateBorn() {

		if (jxDateBorn == null) {
			jxDateBorn = new JXDatePicker();
			jxDateBorn.setName("dateBorn");
			
		}

		return jxDateBorn;
	}

	private GridBagConstraints getGridJXDateBorn() {

		GridBagConstraints gbc_txfCreateDate = new GridBagConstraints();
		gbc_txfCreateDate.weightx = 1.0;
		gbc_txfCreateDate.insets = new Insets(0, 0, 0, 5);
		gbc_txfCreateDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfCreateDate.gridx = 1;
		gbc_txfCreateDate.gridy = 4;

		return gbc_txfCreateDate;

	}

	/* FUNCIONES PANEL ACCIONES */

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

	private JButton getJButtonAccept() {
		if (jBtnAccept == null) {
			jBtnAccept = new JButton("Aceptar");
			jBtnAccept.setIcon(
					new ImageIcon(JManageEditPeople.class.getResource("/com/reparadoras/images/icon-check.png")));
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

	private JButton getJButtonCancel() {
		if (jBtnCancel == null) {
			jBtnCancel = new JButton("Cancelar");
			jBtnCancel.setIcon(
					new ImageIcon(JManageEditPeople.class.getResource("/com/reparadoras/images/icon-cancel.png")));
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

	private JLabel getJLblFirstSurname() {
		if (jLblFirstSurname == null) {
			jLblFirstSurname = new JLabel("Primer Apellido");
			jLblFirstSurname.setPreferredSize(new Dimension(80, 25));
			jLblFirstSurname.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jLblFirstSurname;
	}
}
