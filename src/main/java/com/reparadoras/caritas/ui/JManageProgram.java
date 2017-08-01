package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.jdesktop.swingx.JXDatePicker;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.PeopleTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import java.awt.Component;

public class JManageProgram extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	private JDesktopPane desktop = null;
	private JPanel jPanelFilter = null;
	private JLabel lblName = null;
	private JTextField tfName;
	private JButton btnSearchPeople = null;
	private JPanel jPanelContent = null;
	private PeopleTableModel peopleTableModel = null;
	private JLabel lblDni;
	private JTextField tfDni;
	private JCheckBox ckActive;
	
	private PeopleDAO peopleDAO;
	private ProgramDAO programDAO;
	
	private JTabbedPane jtabPane1;
	private JPanel jPanelFamily;
	private JPanel jPanelAddress;
	private JPanel jPanelHouse;
	
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
	
	/*VIEW HOUSE*/
	private JLabel lblTypeHouse;
	private JTextField tfTypeHouse;
	private JLabel lblRegHolding;
	private JTextField tfRegHolding;
	private JLabel lblNumberRooms;
	private JComboBox cbNumberRooms;
	private JLabel lblNumberPeople;
	private JComboBox cbNumberPeople;
	private JLabel lblNumberFamilies;
	private JComboBox cbNumberFamilies;
	private JLabel lblOtherInfo;
	private JTextArea taOtherInfo;
	
	
	
	
	
	
	

	
	/**
	 * @wbp.parser.constructor
	 */
	public JManageProgram(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title, People people){
		
		super(jCicIFParent, modal);
		this.setVisible(true);
		this.pack();
		
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);
		
		//this.people = people;
		
		
		
		createGUIComponents();
		initComponents();
		
	}

	public JManageProgram(JDesktopPane desktop) {
		super(desktop);
		this.desktop = desktop;
		this.setTitle("Gestion Personas");
		this.setVisible(true);

		this.pack();
		this.setResizable(true);
		this.setClosable(true);
		
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		
		
		createGUIComponents();
		initComponents();

		filterPeople();
		
		addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
                // do something  
            	filterPeople();
            	System.out.println("evento internal frame");
            }
        });
	
		getJButtonSearch().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				filterPeople();
			}
		});

		

	}

	
	public void createGUIComponents(){
		getContentPane().setLayout(getGridContentPane());
		getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());

		// Añado elementos del JPanelFilter
		getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
		getJPanelFilter().add(getJLabelDni(), getGridJLabelDni());
		getJPanelFilter().add(getJTextFieldDni(), getGridJTextFieldDni());
		
		getJPanelFilter().add(getCkActive(), getGridJCheckBoxdActive());
		getJPanelFilter().add(getJLabelName(), getGridJLabelName());
		getJPanelFilter().add(getJTextFieldName(), getGridJTextFieldName());
		getJPanelFilter().add(getJButtonSearch(), getGridButtonSearch());

		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());
		
		
		getJPanelContent().add(getJtabPane1(), getGridJTabPane());
		getJtabPane1().add("Direccion", getJPanelAddress());
		getJtabPane1().add("Vivienda", getJPanelHouse());
		getJtabPane1().setEnabledAt(1, true);
		getJtabPane1().setEnabledAt(0, true);
		
		getJtabPane1().setBackgroundAt(0, Color.WHITE);
		
		getJPanelAddress().setLayout(getGridLayoutAddress());
		getJPanelHouse().setLayout(getGridLayoutHouse());
		
		
		//getJtabPane1().add("Familia", getJPanelFamily());
		//Añado elementos del Tab Direccion
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
		getJPanelAddress().add(this.getJLabelPlace(), this.getGridJLabelPlace());
		getJPanelAddress().add(this.getJTextFieldPlace(), this.getGridJTextFieldPlace());
		
		//Añado elementos del Tab Vivienda
		getJPanelHouse().add(getJLabelTypeHouse(), getGridJLabelTypeHouse());
		getJPanelHouse().add(getJTextFieldTypeHouse(), getGridJTextFieldTypeHouse());
		getJPanelHouse().add(getJLabelRegHolding(), getGridJLabelRegHolding());
		getJPanelHouse().add(getJTextFieldRegHolding(), getGridJTextFieldRegHolding());
		getJPanelHouse().add(getJLabelNumberRooms(), getGridJLabelNumberRooms());
		getJPanelHouse().add(getJComboNumberRooms(), getGridJComboNumberRooms());
		getJPanelHouse().add(getJLabelNumberPeople(), getGridJLabelNumberPeople());
		getJPanelHouse().add(getJComboNumberPeople(), getGridJComboNumberPeople());
		getJPanelHouse().add(getJLabelNumberFamilies(), getGridJLabelNumberFamilies());
		getJPanelHouse().add(getJComboNumberFamilies(), getGridJComboNumberFamilies());
		getJPanelHouse().add(getJLabelOtherInfo(), getGridJLabelOtherInfo());
		getJPanelHouse().add(getJTextAreaOtherInfo(), getGridJTextAreaOtherInfo());
	
		
	}
	
	public void initComponents(){
		this.getCkActive().setSelected(true);
	}
	
	
	/* FUNCIONES DEL GETCONTENTPANE */

	private GridBagLayout getGridContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };

		return gridBagLayout;
	}

	/* FUNCIONES DEL PANEL DE FILTRO */

	private GridBagLayout getGridLayoutJPanelFilter() {

		GridBagLayout gbl_jPanelFilter = new GridBagLayout();
		gbl_jPanelFilter.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_jPanelFilter.rowWeights = new double[] { 0.0, 0.0 };

		return gbl_jPanelFilter;
	}

	private JPanel getJPanelFilter() {
		if (jPanelFilter == null) {
			jPanelFilter = new JPanel();
			jPanelFilter.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Busqueda Personas",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));
			
		
		}

		return jPanelFilter;
	}

	private GridBagConstraints getGridJPanelFilter() {
		GridBagConstraints gbc_jPanelFilter = new GridBagConstraints();
		gbc_jPanelFilter.weightx = 1.0;
		gbc_jPanelFilter.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelFilter.fill = GridBagConstraints.BOTH;
		gbc_jPanelFilter.gridx = 0;
		gbc_jPanelFilter.gridy = 0;

		return gbc_jPanelFilter;
	}

	private JLabel getJLabelName() {

		if (lblName == null) {
			lblName = new JLabel("Nombre Persona ");
			lblName.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblName;
	}

	private GridBagConstraints getGridJLabelName() {
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.BOTH;
		gbc_lblName.insets = new Insets(0, 5, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;

		return gbc_lblName;
	}

	private JTextField getJTextFieldName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setColumns(10);
		}

		return tfName;

	}
	
	private GridBagConstraints getGridJTextFieldName() {

		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.insets = new Insets(0, 0, 0, 5);
		gbc_tfName.weightx = 1.0;
		gbc_tfName.fill = GridBagConstraints.BOTH;
		gbc_tfName.gridx = 1;
		gbc_tfName.gridy = 1;

		return gbc_tfName;
	}

	private JLabel getJLabelDni() {
		if (lblDni == null) {
			lblDni = new JLabel("Dni:");
			lblDni.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblDni;
	}
	
	private GridBagConstraints getGridJLabelDni() {
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 0;
		gbc_lblDni.gridy = 0;

		return gbc_lblDni;
	}
	
	private JTextField getJTextFieldDni() {
		if (tfDni == null) {
			tfDni = new JTextField();
			tfDni.setColumns(10);
		}
		return tfDni;
	}
	
	private GridBagConstraints getGridJTextFieldDni() {

		GridBagConstraints gbc_tfDni = new GridBagConstraints();
		gbc_tfDni.anchor = GridBagConstraints.NORTH;
		gbc_tfDni.insets = new Insets(0, 0, 5, 5);
		gbc_tfDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDni.gridx = 1;
		gbc_tfDni.gridy = 0;

		return gbc_tfDni;
	}
	
	
	private JCheckBox getCkActive() {
		if (ckActive == null) {
			ckActive = new JCheckBox("Activo");
		}
		return ckActive;
	}
	
	private GridBagConstraints getGridJCheckBoxdActive() {
		GridBagConstraints gbc_tfActive = new GridBagConstraints();
		gbc_tfActive.anchor = GridBagConstraints.NORTH;
		gbc_tfActive.insets = new Insets(0, 0, 5, 5);
		gbc_tfActive.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfActive.gridx = 2;
		gbc_tfActive.gridy = 0;
		
		return gbc_tfActive;
		
	}
	
	private JButton getJButtonSearch() {
		if (btnSearchPeople == null) {
			btnSearchPeople = new JButton("Filtrar");
			btnSearchPeople
					.setIcon(new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-search.png")));
		}

		return btnSearchPeople;
	}

	private GridBagConstraints getGridButtonSearch() {

		GridBagConstraints gbc_btnSearchPeople = new GridBagConstraints();
		gbc_btnSearchPeople.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearchPeople.gridx = 2;
		gbc_btnSearchPeople.gridy = 1;

		return gbc_btnSearchPeople;
	}

	/* FUNCIONES DEL PANEL DE CONTENIDO */

	private JPanel getJPanelContent() {
		if (jPanelContent == null) {
			jPanelContent = new JPanel();
			jPanelContent.setBorder(new LineBorder(new Color(0, 0, 0)));
			
		}
		return jPanelContent;

	}

	private GridBagConstraints getGridJPanelContent() {

		GridBagConstraints gbc_jPanelContent = new GridBagConstraints();
		gbc_jPanelContent.weighty = 1.0;
		gbc_jPanelContent.weightx = 1.0;
		gbc_jPanelContent.anchor = GridBagConstraints.NORTH;
		gbc_jPanelContent.fill = GridBagConstraints.BOTH;
		gbc_jPanelContent.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelContent.gridx = 0;
		gbc_jPanelContent.gridy = 1;

		return gbc_jPanelContent;
	}

	private GridBagLayout getGridLayoutJPanelContent() {
		GridBagLayout gbl_jPanelContent = new GridBagLayout();
		gbl_jPanelContent.columnWeights = new double[] { 1.0 };
		gbl_jPanelContent.rowWeights = new double[] { 1.0 };
		return gbl_jPanelContent;
	}
	
	private JTabbedPane getJtabPane1() {
		if (jtabPane1 == null) {
			jtabPane1 = new JTabbedPane(JTabbedPane.TOP);
		}
		return jtabPane1;
	}
	
	private GridBagConstraints getGridJTabPane() {

		GridBagConstraints gbc_jTabPane = new GridBagConstraints();
		gbc_jTabPane.weighty = 1.0;
		gbc_jTabPane.weightx = 1.0;
		gbc_jTabPane.anchor = GridBagConstraints.NORTH;
		gbc_jTabPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTabPane.insets = new Insets(0, 0, 5, 0);
		gbc_jTabPane.gridx = 0;
		gbc_jTabPane.gridy = 0;

		return gbc_jTabPane;
	}
	
	
	/* FAMILIA */
	private JPanel getJPanelFamily() {
		if (jPanelFamily == null) {
			jPanelFamily = new JPanel();
			jPanelFamily.setBorder(new LineBorder(new Color(0, 0, 0)));
			
		}
		return jPanelFamily;

	}
	
	/* VIVIENDA*/
	private JPanel getJPanelHouse() {
		if (jPanelHouse == null) {
			jPanelHouse = new JPanel();
		}
		return jPanelHouse;

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
		gbc_lblNumberRooms.anchor = GridBagConstraints.WEST;
		gbc_lblNumberRooms.insets = new Insets(10, 20, 0, 5);
		gbc_lblNumberRooms.gridx = 0;
		gbc_lblNumberRooms.gridy = 1;
		return gbc_lblNumberRooms;
	}
	/*DIRECCION */
	
	private JComboBox<Integer> getJComboNumberRooms() {

		if ( cbNumberRooms== null) {
			cbNumberRooms = new JComboBox<Integer>();
			
		}
		return cbNumberRooms;
	}
	
	private GridBagConstraints getGridJComboNumberRooms() {
		GridBagConstraints gbc_cbNumberRooms = new GridBagConstraints();
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
		gbc_lblOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_lblOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_lblOtherInfo.gridx = 0;
		gbc_lblOtherInfo.gridy = 3;
		return gbc_lblOtherInfo;
	}
	
	private JTextArea getJTextAreaOtherInfo() {

		if ( taOtherInfo== null) {
			taOtherInfo = new JTextArea();
			taOtherInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
			taOtherInfo.setWrapStyleWord(true);
			taOtherInfo.setLineWrap(true);
			taOtherInfo.setRows(5);
			taOtherInfo.setColumns(60);
			
		}
		return taOtherInfo;
	}
	
	private GridBagConstraints getGridJTextAreaOtherInfo() {
		GridBagConstraints gbc_taOtherInfo = new GridBagConstraints();
		gbc_taOtherInfo.weightx = 1.0;
		gbc_taOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_taOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_taOtherInfo.gridx = 1;
		gbc_taOtherInfo.gridy = 3;
		return gbc_taOtherInfo;
	}
	
	/*DIRECCION */
	
	
	
	private JPanel getJPanelAddress() {
		if (jPanelAddress == null) {
			jPanelAddress = new JPanel();
			jPanelAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
			
		}
		return jPanelAddress;
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
	
	private GridBagLayout getGridLayoutHouse() {
		GridBagLayout gbl_LayoutHouse = new GridBagLayout();
		gbl_LayoutHouse.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0};
		gbl_LayoutHouse.rowWeights = new double[] { };

		return gbl_LayoutHouse;
	}
	
	
	
	


	
	private PeopleTableModel getPeopleTableModel(){
		
		if (peopleTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Dni", "Nombre", "Apellidos"};
			peopleTableModel = new PeopleTableModel(Arrays.asList(columnIdentifiers));
		}
		
		return peopleTableModel;
	}
	
	

	
	/* EVENTOS */

	public void filterPeople(){
		String filterDni = this.getJTextFieldDni().getText();
		String filterName = this.getJTextFieldName().getText();
		boolean filterActive = this.getCkActive().isSelected();
		People filterPeople = new People();
		if (filterDni!=null && !filterDni.equals("")){
			filterPeople.setDni(filterDni);
		}
		if (filterName!=null && !filterName.equals("")){
			filterPeople.setName(filterName);
		}
		
			filterPeople.setActive(filterActive);
				
		List<People> listPeople = peopleDAO.findPeople(filterPeople);
		if (listPeople!=null && !listPeople.isEmpty()){
			this.getPeopleTableModel().clearTableModelData();
			this.getPeopleTableModel().addRows(listPeople);
			
		}
		else{
			JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda");
		}
	
	}
	
	
	public Program getProgramPeople(People people){
		
		Program program = programDAO.findProgramById(people);
		return program;
		
		
	}
	
	public void createProgramPeople(People people){
		
	}
	
	
	
	
	
	
}
