package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.filter.FilterTicket;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.combobox.ComboBoxRenderer;
import com.reparadoras.caritas.ui.components.datepicker.CaritasDatePickerCellEditor;
import com.reparadoras.caritas.ui.components.table.ColumnGroup;
import com.reparadoras.caritas.ui.components.table.FormattedCellRenderer;
import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.PeopleTableModel;
import com.reparadoras.caritas.ui.components.table.TicketsPeopleTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JManageTicket extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	private JDesktopPane desktop = null;
	private JPanel jPanelFilter = null;
	private JLabel lblName = null;
	private JLabel lblDni;
	private JTextField tfDni;
	private JCheckBox ckActive;
	private JLabel lblYear = null;
	private JTextField tfName;
	private JTextField tfYear;
	private JPanel jPanelContent = null;

	private JPanel jPanelTable = null;
	private TicketsPeopleTableModel ticketsPeopleTableModel = null;
	private JTable tablePeople = null;
	private JScrollPane scrollPaneJTable = null;
	private JButton btnSaveTicket;
	private JButton btnFilterTicket;
	private JButton btnExit = null;
	private JButton btnCleanPeople = null;
	private FilterTicket filterTicket = null;

	private TicketDAO ticketDAO;
	private PeopleDAO peopleDAO;

	/**
	 * @wbp.parser.constructor
	 */
	public JManageTicket(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title,
			FilterTicket filterTicket) {

		super(jCicIFParent, modal);
		this.setVisible(true);
		this.pack();

		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);

		this.filterTicket = filterTicket;

		ticketDAO = new TicketDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		createGUIComponents();
		initComponents();
		addListeners();
		
		this.getJTextFieldDni().setText(this.filterTicket.getDniPeople());
		this.getJTextFieldName().setText(this.filterTicket.getNamePeople());
		if (this.filterTicket.getYearTicket()!=null){
			this.getJTextFieldYear().setText(this.filterTicket.getYearTicket().toString());
		}
		
		onFilterTicket(true);
		
		this.getJButtonSearch().setVisible(false);
		this.getJButtonClean().setVisible(false);
		
	}

	public JManageTicket(JDesktopPane desktop) {
		super(desktop);
		this.desktop = desktop;
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);
		this.setVisible(true);
		this.pack();

		ticketDAO = new TicketDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		createGUIComponents();
		initComponents();
		addListeners();

		onFilterTicket(false);

	}

	public void initComponents() {

		this.getCkActive().setSelected(true);
		
	}

	public void addListeners() {

		getJButtonSearch().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFilterTicket(false);
			}
		});

		getBtnSaveTicket().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSaveTicket();
			}
		});

		getJButtonExit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();
			}
		});
		
		getJButtonClean().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanFilter();
			}
		});
	}

	
	public void cleanFilter() {
		this.getJTextFieldDni().setText("");
		this.getJTextFieldName().setText("");
		this.getJTextFieldYear().setText("");
		
		
	}
	
	

	public void createGUIComponents() {

		getContentPane().setLayout(getGridContentPane());
		// getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());

		// Añado elementos del JPanelFilter
		getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
		getJPanelFilter().add(getJLabelDni(), getGridJLabelDni());
		getJPanelFilter().add(getJTextFieldDni(), getGridJTextFieldDni());

		getJPanelFilter().add(getCkActive(), getGridJCheckBoxdActive());
		getJPanelFilter().add(getJLabelName(), getGridJLabelName());
		getJPanelFilter().add(getJTextFieldName(), getGridJTextFieldName());
		getJPanelFilter().add(getJLabelYear(), getGridJLabelYear());
		getJPanelFilter().add(getJTextFieldYear(), getGridJTextFieldYear());
		getJPanelFilter().add(getJButtonSearch(), getGridButtonSearch());
		getJPanelFilter().add(getJButtonClean(), getGridButtonClean());

		getJPanelFilter().add(getJButtonExit(), getGridButtonExit());

		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());

		getJPanelContent().add(getJPanelFilter(), getGridJPanelFilter());
		getJPanelContent().add(getJPanelTable(), getGridJPanelTable());
		getJPanelTable().setLayout(getGridLayoutJPanelTable());

		getJPanelTable().add(getBtnSaveTicket(), getGridJBtnSave());
		getJPanelTable().add(getScrollPaneTable(), getGridJPanelScrollTable());

		setGroupHeadersTicket();
		setRendererJXDatePicker();

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
		gbl_jPanelFilter.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gbl_jPanelFilter.rowWeights = new double[] { 0.0 };

		return gbl_jPanelFilter;
	}

	private JPanel getJPanelFilter() {
		if (jPanelFilter == null) {
			jPanelFilter = new JPanel();
			jPanelFilter.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Busqueda Personas",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
			((javax.swing.border.TitledBorder) jPanelFilter.getBorder())
			.setTitleFont(new Font("Verdana", Font.ITALIC, 18));
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
		gbc_lblName.insets = new Insets(0, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;

		return gbc_lblName;
	}

	private JTextField getJTextFieldName() {
		if (tfName == null) {
			tfName = new JTextField();

			
		}

		return tfName;

	}

	private GridBagConstraints getGridJTextFieldName() {

		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.insets = new Insets(0, 0, 5, 5);
		gbc_tfName.weightx = 1.0;
		gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfName.gridx = 1;
		gbc_tfName.gridy = 1;

		return gbc_tfName;
	}
	
	private JLabel getJLabelYear() {

		if (lblYear == null) {
			lblYear = new JLabel("Año ");
			lblYear.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblYear;
	}

	private GridBagConstraints getGridJLabelYear() {
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.BOTH;
		gbc_lblName.insets = new Insets(0, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;

		return gbc_lblName;
	}

	private JTextField getJTextFieldYear() {
		if (tfYear == null) {
			tfYear = new JTextField();
		}

		return tfYear;

	}

	private GridBagConstraints getGridJTextFieldYear() {

		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.insets = new Insets(0, 0, 5, 5);
		gbc_tfName.weightx = 1.0;
		gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfName.gridx = 1;
		gbc_tfName.gridy = 2;

		return gbc_tfName;
	}

	private GridBagConstraints getGridButtonSearch() {

		GridBagConstraints gbc_btnFilter = new GridBagConstraints();
		gbc_btnFilter.anchor = GridBagConstraints.WEST;
		gbc_btnFilter.insets = new Insets(0, 0, 5, 5);
		gbc_btnFilter.gridx = 2;
		gbc_btnFilter.gridy = 1;

		return gbc_btnFilter;
	}

	private JButton getJButtonSearch() {
		if (btnFilterTicket == null) {
			btnFilterTicket = new JButton("Buscar");

			btnFilterTicket
					.setIcon(new ImageIcon(JManageTicket.class.getResource("/com/reparadoras/images/icon-search.png")));
		}
		return btnFilterTicket;
	}
	
	private JButton getJButtonClean() {
		if (btnCleanPeople == null) {
			btnCleanPeople = new JButton("Limpiar");
			btnCleanPeople.setIcon(
					new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-clean-32.png")));
		}

		return btnCleanPeople;
	}

	private GridBagConstraints getGridButtonClean() {

		GridBagConstraints gbc_btnCleanPeople = new GridBagConstraints();
		gbc_btnCleanPeople.anchor = GridBagConstraints.WEST;
		gbc_btnCleanPeople.insets = new Insets(0, 0, 0, 5);
		gbc_btnCleanPeople.gridx = 3;
		gbc_btnCleanPeople.gridy = 1;

		return gbc_btnCleanPeople;
	}

	private JButton getJButtonExit() {
		if (btnExit == null) {
			btnExit = new JButton("Salir al menu");

			btnExit.setHorizontalAlignment(SwingConstants.RIGHT);

			btnExit.setIcon(new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-exit.png")));
		}

		return btnExit;
	}

	private GridBagConstraints getGridButtonExit() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.gridx = 4;
		gbc_btnExit.gridy = 1;

		return gbc_btnExit;
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
		gbl_jPanelContent.columnWeights = new double[] { 0.0 };
		gbl_jPanelContent.rowWeights = new double[] { 0.0, 0.0 };
		return gbl_jPanelContent;
	}

	/* FUNCIONES DEL PANEL JTABLE */

	private JPanel getJPanelTable() {

		if (jPanelTable == null) {
			jPanelTable = new JPanel();
		}
		return jPanelTable;
	}

	private GridBagConstraints getGridJPanelTable() {
		GridBagConstraints gbc_jPanelTable = new GridBagConstraints();
		gbc_jPanelTable.weighty = 1.0;
		gbc_jPanelTable.weightx = 1.0;
		gbc_jPanelTable.fill = GridBagConstraints.BOTH;
		gbc_jPanelTable.anchor = GridBagConstraints.WEST;
		gbc_jPanelTable.gridx = 0;
		gbc_jPanelTable.gridy = 1;

		return gbc_jPanelTable;

	}

	private GridBagLayout getGridLayoutJPanelTable() {

		GridBagLayout gbl_jPanelTable = new GridBagLayout();
		gbl_jPanelTable.columnWidths = new int[] { 1 };
		gbl_jPanelTable.rowHeights = new int[] { 1 };
		gbl_jPanelTable.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_jPanelTable.rowWeights = new double[] { Double.MIN_VALUE };

		return gbl_jPanelTable;
	}

	private GridBagConstraints getGridJBtnSave() {

		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.WEST;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.weightx = 1.0;
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 0;

		return gbc_btnSave;
	}

	private JButton getBtnSaveTicket() {
		if (btnSaveTicket == null) {
			btnSaveTicket = new JButton("Guardar");
			btnSaveTicket
					.setIcon(new ImageIcon(JManageTicket.class.getResource("/com/reparadoras/images/icon-save.png")));
		}
		return btnSaveTicket;
	}

	private JScrollPane getScrollPaneTable() {
		if (scrollPaneJTable == null) {
			scrollPaneJTable = new JScrollPane(getJTableTicketsPeople());
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

	private JTable getJTableTicketsPeople() {
		if (tablePeople == null) {
			TableModel tableModel = getTicketsPeopleTableModel();
			tablePeople = new JTable(tableModel) {

				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};

			tablePeople.setAutoCreateRowSorter(true);
			tablePeople.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tablePeople.setShowGrid(true);
			tablePeople.setShowVerticalLines(true);

		}

		return tablePeople;
	}

	private TicketsPeopleTableModel getTicketsPeopleTableModel() {

		if (ticketsPeopleTableModel == null) {
			Object[] columnIdentifiers = new Object[] { "Nombre", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts",
					"Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts",
					"Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts" };
			ticketsPeopleTableModel = new TicketsPeopleTableModel(Arrays.asList(columnIdentifiers));
		}

		return ticketsPeopleTableModel;
	}

	private void setRendererJXDatePicker() {
		TableColumn januaryColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(1);
		TableColumn februaryColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(3);
		TableColumn marchColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(5);
		TableColumn aprilColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(7);
		TableColumn mayColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(9);
		TableColumn juneColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(11);
		TableColumn julyColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(13);
		TableColumn augustColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(15);
		TableColumn septemberColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(17);
		TableColumn octoberColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(19);
		TableColumn novemberColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(21);
		TableColumn decemberColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(23);
		CaritasDatePickerCellEditor datePickerJanuary = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerFebruary = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerMarch = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerApril = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerMay = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerJune = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerJuly = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerAugust = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerSeptember = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerOctober = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerNovember = new CaritasDatePickerCellEditor();
		CaritasDatePickerCellEditor datePickerDecember = new CaritasDatePickerCellEditor();
		Calendar calendarMinJanuary = new GregorianCalendar(2017,0,01);
		Calendar calendarMaxJanuary = new GregorianCalendar(2017,0,31);
		Calendar calendarMinFebruary = new GregorianCalendar(2017,1,01);
		Calendar calendarMaxFebruary = new GregorianCalendar(2017,2,1);
		Calendar calendarMinMarch = new GregorianCalendar(2017,2,01);
		Calendar calendarMaxMarch = new GregorianCalendar(2017,2,31);
		Calendar calendarMinApril = new GregorianCalendar(2017,3,01);
		Calendar calendarMaxApril = new GregorianCalendar(2017,3,30);
		Calendar calendarMinMay = new GregorianCalendar(2017,4,01);
		Calendar calendarMaxMay = new GregorianCalendar(2017,4,31);
		Calendar calendarMinJune = new GregorianCalendar(2017,5,01);
		Calendar calendarMaxJune = new GregorianCalendar(2017,5,30);
		Calendar calendarMinJuly = new GregorianCalendar(2017,6,01);
		Calendar calendarMaxJuly = new GregorianCalendar(2017,6,31);
		Calendar calendarMinAugust = new GregorianCalendar(2017,7,01);
		Calendar calendarMaxAugust = new GregorianCalendar(2017,7,31);
		Calendar calendarMinSeptember = new GregorianCalendar(2017,8,01);
		Calendar calendarMaxSeptember = new GregorianCalendar(2017,8,30);
		Calendar calendarMinOctober = new GregorianCalendar(2017,9,01);
		Calendar calendarMaxOctober = new GregorianCalendar(2017,9,31);
		Calendar calendarMinNovember = new GregorianCalendar(2017,10,01);
		Calendar calendarMaxNovember = new GregorianCalendar(2017,10,30);
		Calendar calendarMinDecember = new GregorianCalendar(2017,11,01);
		Calendar calendarMaxDecember = new GregorianCalendar(2017,11,31);
		datePickerJanuary.setLowerBound(calendarMinJanuary.getTime());
		datePickerJanuary.setUpperBound(calendarMaxJanuary.getTime());
		datePickerFebruary.setLowerBound(calendarMinFebruary.getTime());
		datePickerFebruary.setUpperBound(calendarMaxFebruary.getTime());
		datePickerMarch.setLowerBound(calendarMinMarch.getTime());
		datePickerMarch.setUpperBound(calendarMaxMarch.getTime());
		datePickerApril.setLowerBound(calendarMinApril.getTime());
		datePickerApril.setUpperBound(calendarMaxApril.getTime());
		datePickerMay.setLowerBound(calendarMinMay.getTime());
		datePickerMay.setUpperBound(calendarMaxMay.getTime());
		datePickerJune.setLowerBound(calendarMinJune.getTime());
		datePickerJune.setUpperBound(calendarMaxJune.getTime());
		datePickerJuly.setLowerBound(calendarMinJuly.getTime());
		datePickerJuly.setUpperBound(calendarMaxJuly.getTime());
		datePickerAugust.setLowerBound(calendarMinAugust.getTime());
		datePickerAugust.setUpperBound(calendarMaxAugust.getTime());
		datePickerSeptember.setLowerBound(calendarMinSeptember.getTime());
		datePickerSeptember.setUpperBound(calendarMaxSeptember.getTime());
		datePickerOctober.setLowerBound(calendarMinOctober.getTime());
		datePickerOctober.setUpperBound(calendarMaxOctober.getTime());
		datePickerNovember.setLowerBound(calendarMinNovember.getTime());
		datePickerNovember.setUpperBound(calendarMaxNovember.getTime());
		datePickerDecember.setLowerBound(calendarMinDecember.getTime());
		datePickerDecember.setUpperBound(calendarMaxDecember.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		TableCellRenderer dateRenderer = new FormattedCellRenderer(simpleDateFormat);
		januaryColumn.setCellEditor(datePickerJanuary);
		januaryColumn.setCellRenderer(dateRenderer);
		februaryColumn.setCellEditor(datePickerFebruary);
		februaryColumn.setCellRenderer(dateRenderer);
		marchColumn.setCellEditor(datePickerMarch);
		marchColumn.setCellRenderer(dateRenderer);
		aprilColumn.setCellEditor(datePickerApril);
		aprilColumn.setCellRenderer(dateRenderer);
		mayColumn.setCellEditor(datePickerMay);
		mayColumn.setCellRenderer(dateRenderer);
		juneColumn.setCellEditor(datePickerJune);
		juneColumn.setCellRenderer(dateRenderer);
		julyColumn.setCellEditor(datePickerJuly);
		julyColumn.setCellRenderer(dateRenderer);
		augustColumn.setCellEditor(datePickerAugust);
		augustColumn.setCellRenderer(dateRenderer);
		septemberColumn.setCellEditor(datePickerSeptember);
		septemberColumn.setCellRenderer(dateRenderer);
		octoberColumn.setCellEditor(datePickerOctober);
		octoberColumn.setCellRenderer(dateRenderer);
		novemberColumn.setCellEditor(datePickerNovember);
		novemberColumn.setCellRenderer(dateRenderer);
		decemberColumn.setCellEditor(datePickerDecember);
		decemberColumn.setCellRenderer(dateRenderer);

	}

	private void setGroupHeadersTicket() {
		GroupableTableHeader header = (GroupableTableHeader) this.getJTableTicketsPeople().getTableHeader();
		TableColumnModel tableColumnModel = this.getJTableTicketsPeople().getColumnModel();
		ColumnGroup groupJanuary = new ColumnGroup("ENERO");
		groupJanuary.add(tableColumnModel.getColumn(1));
		groupJanuary.add(tableColumnModel.getColumn(2));
		ColumnGroup groupFebruary = new ColumnGroup("FEBRERO");
		groupFebruary.add(tableColumnModel.getColumn(3));
		groupFebruary.add(tableColumnModel.getColumn(4));
		ColumnGroup groupMarch = new ColumnGroup("MARZO");
		groupMarch.add(tableColumnModel.getColumn(5));
		groupMarch.add(tableColumnModel.getColumn(6));
		ColumnGroup groupApril = new ColumnGroup("ABRIL");
		groupApril.add(tableColumnModel.getColumn(7));
		groupApril.add(tableColumnModel.getColumn(8));
		ColumnGroup groupMay = new ColumnGroup("MAYO");
		groupMay.add(tableColumnModel.getColumn(9));
		groupMay.add(tableColumnModel.getColumn(10));
		ColumnGroup groupJune = new ColumnGroup("JUNIO");
		groupJune.add(tableColumnModel.getColumn(11));
		groupJune.add(tableColumnModel.getColumn(12));
		ColumnGroup groupJuly = new ColumnGroup("JULIO");
		groupJuly.add(tableColumnModel.getColumn(13));
		groupJuly.add(tableColumnModel.getColumn(14));
		ColumnGroup groupAugust = new ColumnGroup("AGOSTO");
		groupAugust.add(tableColumnModel.getColumn(15));
		groupAugust.add(tableColumnModel.getColumn(16));
		ColumnGroup groupSeptember = new ColumnGroup("SEPTIEMBRE");
		groupSeptember.add(tableColumnModel.getColumn(17));
		groupSeptember.add(tableColumnModel.getColumn(18));
		ColumnGroup groupOctober = new ColumnGroup("OCTUBRE");
		groupOctober.add(tableColumnModel.getColumn(19));
		groupOctober.add(tableColumnModel.getColumn(20));
		ColumnGroup groupNovember = new ColumnGroup("NOVIEMBRE");
		groupNovember.add(tableColumnModel.getColumn(21));
		groupNovember.add(tableColumnModel.getColumn(22));
		ColumnGroup groupDecember = new ColumnGroup("DICIEMBRE");
		groupDecember.add(tableColumnModel.getColumn(23));
		groupDecember.add(tableColumnModel.getColumn(24));

		header.addColumnGroup(groupJanuary);
		header.addColumnGroup(groupFebruary);
		header.addColumnGroup(groupMarch);
		header.addColumnGroup(groupApril);
		header.addColumnGroup(groupMay);
		header.addColumnGroup(groupJune);
		header.addColumnGroup(groupJuly);
		header.addColumnGroup(groupAugust);
		header.addColumnGroup(groupSeptember);
		header.addColumnGroup(groupOctober);
		header.addColumnGroup(groupNovember);
		header.addColumnGroup(groupDecember);

		Border blackline;

		blackline = BorderFactory.createLineBorder(Color.black);
		UIManager.getDefaults().put("TableHeader.cellBorder", blackline);

		header.setBorder(blackline);
	}

	/* EVENTOS */

	public void openEditPeople(int openMode, String title) {
		JManageEditPeople jManageEditPeople = null;
		try {

			if ((openMode == JWindowParams.IMODE_SELECT || openMode == JWindowParams.IMODE_UPDATE)) {
				int row = this.getJTableTicketsPeople().getSelectedRow();
				if (row != -1) {
					// Ticket people =
					// this.getJTableTicketsPeople().getDomainObject(row);
					// jManageEditPeople = new JManageEditPeople(this, true,
					// openMode, title, people);

				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un registro");
					return;
				}

			} else {
				jManageEditPeople = new JManageEditPeople(this, true, openMode, title, null);
			}

			this.desktop.add(jManageEditPeople);
			jManageEditPeople.setVisible(true);
			jManageEditPeople.moveToFront();
			jManageEditPeople.show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onFilterTicket(boolean create) {
		
		
		if (filterTicket == null){
			filterTicket = new FilterTicket();
		} 
		filterTicket.setActive(this.getCkActive().isSelected());
		filterTicket.setDniPeople(this.getJTextFieldDni().getText());
		filterTicket.setNamePeople(this.getJTextFieldName().getText());
		if (this.getJTextFieldYear().getText()!=null && !this.getJTextFieldYear().getText().equals("")){
			filterTicket.setYearTicket(Integer.parseInt(this.getJTextFieldYear().getText()));
		}
		

		
		
		List<Ticket> tickets = ticketDAO.findTicket(filterTicket);
		if (tickets != null && !tickets.isEmpty()) {
			this.getTicketsPeopleTableModel().clearTableModelData();
			this.getTicketsPeopleTableModel().addRows(tickets);

		} else {
			Ticket ticketNewReset = new Ticket();
			People filterPeople = new People();
			filterPeople.setDni(this.getJTextFieldDni().getText());
			filterPeople.setName(this.getJTextFieldName().getText());
			filterPeople.setId(filterTicket.getIdPeople());
			People peopleTicket = peopleDAO.findPeopleById(filterPeople);
			
			ticketNewReset.setPeople(peopleTicket);
			
			ticketNewReset.setYear(Calendar.getInstance().get(Calendar.YEAR));

			if (create) {
				if (JOptionPane.showConfirmDialog(this,
						"Este usuario no tiene registros de Vales todavia. ¿Quieres crearlo?",
						"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
					ticketDAO.insert(ticketNewReset);
					
					JOptionPane.showMessageDialog(this, "Se ha creado un registro para el usuario " + this.getJTextFieldName().getText() + " con todos los meses inicializados a 0");
					onFilterTicket(false);
					this.cleanFilter();
					
					
				}

			} else {
				//JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda");
			}

		}

	}

	public void onSaveTicket() {

		int row = this.getJTableTicketsPeople().getSelectedRow();
		if (row != -1) {
			Ticket ticket = this.getTicketsPeopleTableModel().getDomainObject(row);
			ticketDAO.update(ticket);
			onFilterTicket(false);
			JOptionPane.showMessageDialog(this, "Se han guardado los datos correctamente");
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
			return;
		}

	}

}
