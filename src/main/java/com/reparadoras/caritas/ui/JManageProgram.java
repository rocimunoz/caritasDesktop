package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.reparadoras.caritas.dao.AddressDAO;
import com.reparadoras.caritas.dao.FamilyDAO;
import com.reparadoras.caritas.dao.FamilyTypeDAO;
import com.reparadoras.caritas.dao.HomeDAO;
import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.model.Address;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.PeopleTableModel;
import com.reparadoras.caritas.ui.components.table.ProgramTableModel;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;
import com.reparadoras.caritas.ui.components.table.TicketsPeopleTableModel;
import com.reparadoras.caritas.ui.tabs.JPanelAddress;
import com.reparadoras.caritas.ui.tabs.JPanelEconomicSituation;
import com.reparadoras.caritas.ui.tabs.JPanelFamily;
import com.reparadoras.caritas.ui.tabs.JPanelHome;
import com.reparadoras.caritas.ui.tabs.JPanelJobSituation;
import com.reparadoras.caritas.ui.tabs.JPanelStudies;
import com.reparadoras.caritas.ui.tabs.JPanelTypeAuthorization;

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
	
	static final Logger logger = Logger.getLogger(JManageProgram.class);

	private JDesktopPane desktop = null;
	private JPanel jPanelFilter = null;
	private JLabel lblName = null;
	private JComboBox<People> cbPeople;
	private JButton btnSearchPeople = null;
	private JPanel jPanelContent = null;
	private ProgramTableModel programTableModel = null;
	private JLabel lblDni;
	private JTextField tfDni;
	private JCheckBox ckActive;
	private JPanel jPanelActions = null;
	private JButton btnSave = null;
	private JButton btnExit = null;
	private JPanel jPanelGrid;
	private JTable tableProgram = null;
	private JScrollPane scrollPaneJTable = null;

	private PeopleDAO peopleDAO;
	private FamilyDAO familyDAO;
	private FamilyTypeDAO familyTypeDAO;
	private ProgramDAO programDAO;
	private HomeDAO homeDAO;
	private AddressDAO addressDAO;

	private JTabbedPane jtabPane1;
	private JPanel jPanelFamily;
	private JPanel jPanelHome;
	private JPanel jPanelAddress;

	private People people = null;

	/**
	 * @wbp.parser.constructor
	 */
	public JManageProgram(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title,
			People people) {

		super(jCicIFParent, modal);
		this.setVisible(true);
		this.pack();

		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(false);
		this.setResizable(false);
		this.setTitle(title);

		this.people = people;

		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		familyDAO = new FamilyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		familyTypeDAO = new FamilyTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		homeDAO = new HomeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		addressDAO = new AddressDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		createGUIComponents();
		initComponents();
		addListeners();
		onFilterProgram(true);
		
		if (this.getProgramTableModel().getDomainObjects().size() == 1){
			this.getJTableProgram().setRowSelectionInterval(0, 0);
		}

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
		familyDAO = new FamilyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		familyTypeDAO = new FamilyTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		homeDAO = new HomeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		addressDAO = new AddressDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		
		createGUIComponents();
		initComponents();
		addListeners();

		onFilterProgram(true);
		
		if (this.getProgramTableModel().getDomainObjects().size() == 1){
			this.getJTableProgram().setRowSelectionInterval(0, 0);
		}

	}

	public void addListeners() {

		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				// do something
				System.out.println("evento internal frame");
			}
		});

		getJButtonSearch().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		getJButtonExit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();
			}
		});

		getJButtonSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				onSaveProgram();
			}
		});
		
		
		getJTableProgram().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            
	        	fillDataProgram();
	            
	        }
	    });
		
		this.getJPanelFamily().getBtnAddRelative().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openEditRelative(JWindowParams.IMODE_INSERT, "Nuevo Pariente");
				
			}
		});

	}

	public void createGUIComponents() {
		getContentPane().setLayout(getGridContentPane());

		getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());
		// getContentPane().add(getJPanelActions(),
		// this.getGridJPanelActions());
		getContentPane().add(getJPanelGrid(), this.getGridJPanelGrid());

		// Añado elementos del JPanelFilter
		getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
		getJPanelFilter().add(getJLabelDni(), getGridJLabelDni());
		getJPanelFilter().add(getJTextFieldDni(), getGridJTextFieldDni());

		getJPanelFilter().add(getCkActive(), getGridJCheckBoxdActive());
		getJPanelFilter().add(getJLabelName(), getGridJLabelName());
		getJPanelFilter().add(getJComboBoxPeople(), getGridJTextFieldName());
		getJPanelFilter().add(getJButtonSearch(), getGridButtonSearch());
		getJPanelFilter().add(getJButtonExit(), getGridButtonExit());

		getJPanelActions().setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		getJPanelActions().add(getJButtonSave());

		// Añado elementos del JPanelGrid
		getJPanelGrid().setLayout(getGridLayoutJPanelGrid());
		getJPanelGrid().add(getJPanelActions(), this.getGridJPanelActions());
		getJPanelGrid().add(this.getScrollPaneTable(), this.getGridJPanelScrollTable());

		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());

		getJPanelContent().add(getJtabPane1(), getGridJTabPane());
		getJtabPane1().add("Direccion", getJPanelAddress());
		getJtabPane1().add("Vivienda", getJPanelHome());
		getJtabPane1().add("Familia", getJPanelFamily());
		getJtabPane1().add("Tipo Autorización", new JPanelTypeAuthorization());
		getJtabPane1().add("Situación Laboral", new JPanelJobSituation());
		getJtabPane1().add("Estudios", new JPanelStudies());
		getJtabPane1().add("Situación Económica", new JPanelEconomicSituation());
		getJtabPane1().setEnabledAt(1, true);
		getJtabPane1().setEnabledAt(0, true);

		getJtabPane1().setBackgroundAt(0, Color.WHITE);

	}

	public void initComponents() {
		this.getCkActive().setSelected(true);
		initCbPeople();
		

	}

	public void initCbPeople() {

		if (this.people != null) {
			this.getJComboBoxPeople().addItem(this.people);
			this.getJComboBoxPeople().setSelectedItem(this.people);
		} else {
			List<People> listPeople = peopleDAO.findAll();
			People allPeople = new People();
			allPeople.setName("TODOS");
			allPeople.setId(-1);
			listPeople.add(0, allPeople);
			for (People p : listPeople) {
				this.getJComboBoxPeople().addItem(p);
			}
		}

	}

	/* TABS */

	private JPanelFamily getJPanelFamily() {
		if (jPanelFamily == null) {
			jPanelFamily = new JPanelFamily();
		}
		return (JPanelFamily) jPanelFamily;
	}
	
	private JPanelHome getJPanelHome() {
		if (jPanelHome == null) {
			jPanelHome = new JPanelHome();
		}
		return (JPanelHome) jPanelHome;
	}
	
	private JPanelAddress getJPanelAddress() {
		if (jPanelAddress == null) {
			jPanelAddress = new JPanelAddress();
		}
		return (JPanelAddress) jPanelAddress;
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
			jPanelFilter.setBorder(
					new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Busqueda Programa Atención Primaria",
							TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));

		}

		return jPanelFilter;
	}

	private GridBagConstraints getGridJPanelFilter() {
		GridBagConstraints gbc_jPanelFilter = new GridBagConstraints();
		gbc_jPanelFilter.weightx = 1.0;
		gbc_jPanelFilter.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelFilter.fill = GridBagConstraints.HORIZONTAL;
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

	private JComboBox<People> getJComboBoxPeople() {
		if (cbPeople == null) {
			cbPeople = new JComboBox<People>();

		}

		return cbPeople;

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
			btnSearchPeople.setIcon(
					new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-search.png")));
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

	private JButton getJButtonExit() {
		if (btnExit == null) {
			btnExit = new JButton("Salir al menu");

			btnExit.setIcon(new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-exit.png")));
		}

		return btnExit;
	}

	private GridBagConstraints getGridButtonExit() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.gridx = 3;
		gbc_btnExit.gridy = 1;

		return gbc_btnExit;
	}

	/* FUNCIONES DEL PANEL DE ACCIONES */

	private JPanel getJPanelActions() {
		if (jPanelActions == null) {
			jPanelActions = new JPanel();
			jPanelActions.setBorder(null);
		}

		return jPanelActions;
	}

	private GridBagConstraints getGridJPanelActions() {
		GridBagConstraints gbc_jPanelGrid = new GridBagConstraints();
		gbc_jPanelGrid.anchor = GridBagConstraints.WEST;
		gbc_jPanelGrid.weightx = 1.0;
		gbc_jPanelGrid.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelGrid.gridx = 0;
		gbc_jPanelGrid.gridy = 0;

		return gbc_jPanelGrid;
	}

	private JButton getJButtonSave() {
		if (btnSave == null) {
			btnSave = new JButton("Guardar");

			btnSave.setIcon(new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-save.png")));
		}

		return btnSave;
	}

	/* FUNCIONES DEL PANEL DEL GRID */

	private GridBagLayout getGridLayoutJPanelGrid() {

		GridBagLayout gbl_jPanelGrid = new GridBagLayout();
		gbl_jPanelGrid.columnWeights = new double[] { 0.0 };
		gbl_jPanelGrid.rowWeights = new double[] { 0.0 };

		return gbl_jPanelGrid;
	}

	private JPanel getJPanelGrid() {
		if (jPanelGrid == null) {
			jPanelGrid = new JPanel();
			jPanelGrid.setBorder(
					new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Listado Programa Atención Primaria",
							TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));
		}

		return jPanelGrid;
	}

	private GridBagConstraints getGridJPanelGrid() {
		GridBagConstraints gbc_jPanelGrid = new GridBagConstraints();
		gbc_jPanelGrid.weighty = 1.0;
		gbc_jPanelGrid.weightx = 1.0;
		gbc_jPanelGrid.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelGrid.fill = GridBagConstraints.BOTH;
		gbc_jPanelGrid.gridx = 0;
		gbc_jPanelGrid.gridy = 1;

		return gbc_jPanelGrid;
	}

	private JScrollPane getScrollPaneTable() {
		if (scrollPaneJTable == null) {
			scrollPaneJTable = new JScrollPane(getJTableProgram());
			scrollPaneJTable.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		}

		return scrollPaneJTable;
	}

	private GridBagConstraints getGridJPanelScrollTable() {

		GridBagConstraints gbc_jPanelScroll = new GridBagConstraints();
		gbc_jPanelScroll.weighty = 1.0;
		gbc_jPanelScroll.weightx = 1.0;
		gbc_jPanelScroll.fill = GridBagConstraints.BOTH;
		gbc_jPanelScroll.anchor = GridBagConstraints.WEST;
		gbc_jPanelScroll.gridx = 0;
		gbc_jPanelScroll.gridy = 1;

		return gbc_jPanelScroll;
	}

	private JTable getJTableProgram() {
		if (tableProgram == null) {

			tableProgram = new JTable(getProgramTableModel());
			tableProgram.setAutoCreateRowSorter(true);
			tableProgram.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}

		return tableProgram;
	}

	private ProgramTableModel getProgramTableModel() {

		if (programTableModel == null) {
			Object[] columnIdentifiers = new Object[] { "Dni", "Nombre", "Fecha" };
			programTableModel = new ProgramTableModel(Arrays.asList(columnIdentifiers));
		}

		return programTableModel;
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
		gbc_jPanelContent.gridy = 2;

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
		gbc_jTabPane.fill = GridBagConstraints.BOTH;
		gbc_jTabPane.insets = new Insets(0, 0, 5, 0);
		gbc_jTabPane.gridx = 0;
		gbc_jTabPane.gridy = 0;

		return gbc_jTabPane;
	}

	/* EVENTOS */
	
	public void fillDataProgram(){
		int rowIndex = this.getJTableProgram().getSelectedRow();
		if (rowIndex != -1) {
			//People people = (People) this.getJComboBoxPeople().getSelectedItem();
			Program selectedProgram = this.getProgramTableModel().getDomainObject(rowIndex);
			
			Family family = selectedProgram.getFamily();
			Home home = family.getHome();
			Address address = home.getAddress();
			//address
			this.getJPanelAddress().getJTextFieldFloor().setText(address.getFloor());
			this.getJPanelAddress().getJTextFieldGate().setText(address.getGate());
			//this.getJPanelAddress().getJTextFieldPlace().setText();
			this.getJPanelAddress().getJTextFieldStreet().setText(address.getStreet());
			this.getJPanelAddress().getJTextFieldTelephone().setText(address.getTelephone());
			this.getJPanelAddress().getJTextFieldTelephoneContact().setText(address.getTelephoneContact());
			this.getJPanelAddress().getJTextFieldTown().setText(address.getTown());
			
			//Home
			this.getJPanelHome().getJComboNumberFamilies().setSelectedItem(home.getNumberFamilies());
			this.getJPanelHome().getJComboNumberPeople().setSelectedItem(home.getNumberPeople());
			this.getJPanelHome().getJComboNumberRooms().setSelectedItem(home.getNumberRooms());
			this.getJPanelHome().getJTextAreaOtherInfo().setText(home.getOtherInfo());
			this.getJPanelHome().getJTextFieldRegHolding().setText(home.getRegHolding());
			//tipo de casa
			
			//Family
			this.getJPanelFamily().getJTextAreaFamilyOtherInfo();
		
			
			
			logger.info("fillDataprogram");
		}
	}

	public void onCreateProgramFirstTime(People filterPeople){
		Program programNewReset = new Program();
		
		Address address = new Address();
		addressDAO.insert(address);
		
		Home home = new Home();
		home.setAddress(address);
		homeDAO.insert(home);
		
		Family family = new Family();
		family.setHome(home);
		FamilyType fType = new FamilyType();
		fType.setId(1);
		family.setFamilyType(familyTypeDAO.findFamilyType(fType));
		familyDAO.insert(family);
		
		programNewReset.setFamily(family);
		programNewReset.setPeople(filterPeople);
		
		programDAO.insert(programNewReset);
		
		
	}
	
	public void onFilterProgram(boolean create) {
		People filterPeople = (People) this.getJComboBoxPeople().getSelectedItem();

		if (filterPeople.getId() != -1) {
			Program program = programDAO.findProgram(filterPeople);
			if (program != null) {
				this.getProgramTableModel().clearTableModelData();
				this.getProgramTableModel().addRow(program);

			} else {
				

				if (create) {
					int dialogResult = JOptionPane.showConfirmDialog(this,
							"No existen registros para los datos de búsqueda. ¿Quieres crear un nuevo programa de atención primaria?");
					if (dialogResult == JOptionPane.YES_OPTION) {
						
						onCreateProgramFirstTime(filterPeople);
						onFilterProgram(false);
					}

				} else {
					JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda");
				}

			}
		} else {
			List<Program> listPrograms = programDAO.findAll();
			this.getProgramTableModel().clearTableModelData();
			this.getProgramTableModel().addRows(listPrograms);
		}

	}
	
	public void openEditRelative(int openMode, String title) {

		JManageEditRelative jManageEditRelative = null;
		try {

			if ((openMode == JWindowParams.IMODE_SELECT || openMode == JWindowParams.IMODE_UPDATE)){
				int row = this.getJPanelFamily().getJTableRelatives().getSelectedRow();
				if (row!=-1){
					Relative relative = getJPanelFamily().getRelativesTableModel().getDomainObject(row);
				
					jManageEditRelative = new JManageEditRelative(this, true, openMode, title, null);
				}
				else{
					JOptionPane.showMessageDialog(null, "Seleccione un registro");
					return;
			    }
				
			}
			else{
				jManageEditRelative = new JManageEditRelative(this, true, openMode, title, null);
			}
			
			this.desktop.add(jManageEditRelative);
			jManageEditRelative.setVisible(true);
			jManageEditRelative.moveToFront();
			jManageEditRelative.show();
			
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onSaveFamily(Family family) throws Exception{
		
		try{
			family.setOtherInfo(getJPanelFamily().getJTextAreaFamilyOtherInfo().getText());
			
			String description = "";
			
			if (getJPanelFamily().getJRadioAlone().isSelected()){
				description = getJPanelFamily().getJRadioAlone().getText();
			}else if (getJPanelFamily().getJRadioMono().isSelected()){
				description = getJPanelFamily().getJRadioMono().getText();
			}else if (getJPanelFamily().getJRadioNoChildren().isSelected()){
				description = getJPanelFamily().getJRadioNoChildren().getText();
			}else if (getJPanelFamily().getJRadioWithChildren().isSelected()){
				description = getJPanelFamily().getJRadioWithChildren().getText();
			}else if (getJPanelFamily().getJRadioOther().isSelected()){
				description = getJPanelFamily().getJRadioOther().getText();
			}
			
			FamilyType fType = new FamilyType();
			fType.setDescription(description);
			family.setFamilyType(familyTypeDAO.findFamilyType(fType));
			
			familyDAO.update(family);
		}catch(Exception e){
			logger.info(e);
			throw new Exception();
		}
		
		
		
	}
	
	public void onSaveHome(Home home) throws Exception{
		
		try{
			
			home.setNumberFamilies((Integer) getJPanelHome().getJComboNumberFamilies().getSelectedItem());
			home.setNumberPeople((Integer) getJPanelHome().getJComboNumberPeople().getSelectedItem());
			home.setNumberRooms((Integer) getJPanelHome().getJComboNumberRooms().getSelectedItem());
			home.setOtherInfo(getJPanelHome().getJTextAreaOtherInfo().getText());
			home.setRegHolding(getJPanelHome().getJTextFieldRegHolding().getText());
			
			homeDAO.update(home);
			
		}catch(Exception e){
			logger.info(e);
			throw new Exception();
		}
	}
	
	public void onSaveAddress(Address address) throws Exception{
		try{
			address.setFloor(getJPanelAddress().getJTextFieldFloor().getText());
			address.setGate(getJPanelAddress().getJTextFieldGate().getText());
			//address.setPostalCode(getJPanelAddress().getjtextfield);
			address.setStreet(getJPanelAddress().getJTextFieldStreet().getText());
			address.setTelephone(getJPanelAddress().getJTextFieldTelephone().getText());
			address.setTelephoneContact(getJPanelAddress().getJTextFieldTelephoneContact().getText());
			address.setTown(getJPanelAddress().getJTextFieldTown().getText());
			addressDAO.update(address);
		}catch(Exception e){
			logger.info(e);
			throw new Exception();
		}
		
	}
	
	public void onSaveProgram() {

		int rowIndex = this.getJTableProgram().getSelectedRow();
		if (rowIndex != -1) {
			try {
				Program selectedProgram = this.getProgramTableModel().getDomainObject(rowIndex);
				People people = (People) this.getJComboBoxPeople().getSelectedItem();
				if (selectedProgram != null) {

					if (selectedProgram.getFamily() != null) {
						
						onSaveAddress(selectedProgram.getFamily().getHome().getAddress());
						onSaveHome(selectedProgram.getFamily().getHome());
						//onSaveFamily(selectedProgram.getFamily());
						
						
						
					}
					
					JOptionPane.showMessageDialog(this, "Se han actualizado los datos correctamente.");
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro", "Actualización Persona", JOptionPane.ERROR_MESSAGE);
				logger.info(e);
			}

		}

	}

}
