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
import java.util.Date;
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
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.CaritasDatePickerCellEditor;
import com.reparadoras.caritas.ui.components.ColumnGroup;
import com.reparadoras.caritas.ui.components.FormattedCellRenderer;
import com.reparadoras.caritas.ui.components.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.PeopleTableModel;
import com.reparadoras.caritas.ui.components.TicketsPeopleTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
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
	private JComboBox<People> cbPeople = null;
	private JPanel jPanelContent = null;
	private JPanel jPanelActions = null;

	private JPanel jPanelTable = null;
	private TicketsPeopleTableModel ticketsPeopleTableModel = null;
	private JTable tablePeople = null;
	private JScrollPane scrollPaneJTable = null;
	private JButton btnSaveTicket;
	private JButton btnFilterTicket;
	private People people = null;
	
	private TicketDAO ticketDAO;
	private PeopleDAO peopleDAO;

	
	
	/**
	 * @wbp.parser.constructor
	 */
	public JManageTicket(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title, People people){
	
		super(jCicIFParent, modal);
		this.setVisible(true);
		this.pack();
		
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);
		
		this.people = people;
		
		ticketDAO = new TicketDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		
		createGUIComponents();
		initComponents();
		onFilterTicket(true);
		addListeners();
	}
	
	
	public JManageTicket(JDesktopPane desktop) {
		super(desktop);
		this.desktop = desktop;
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);
		
		ticketDAO = new TicketDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());


		createGUIComponents();
		initComponents();
		addListeners();

	}

	public void initComponents(){
		
		initCbPeople();
	}
	
	public void addListeners(){
		
		getBtnFilterTicket().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFilterTicket(false);
			}
		});
		
		this.getBtnSaveTicket().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSaveTicket();
			}
		});
	}
	

	
	public void initCbPeople(){
		
		
		if (this.people!=null){
			this.getJComboBoxPeople().addItem(this.people);
			this.getJComboBoxPeople().setSelectedItem(this.people);
		}
		else{
			List<People> listPeople = peopleDAO.findAll();
			for (People p : listPeople) {
				this.getJComboBoxPeople().addItem(p);
			}
		}
		
	}
	
	public void createGUIComponents(){
		
		getContentPane().setLayout(getGridContentPane());
		//getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());

		// Añado elementos del JPanelFilter
		getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
		getJPanelFilter().add(getJLabelName(), getGridJLabelName());
		getJPanelFilter().add(getJComboBoxPeople(), getGridJTextFieldName());
		getJPanelFilter().add(getBtnFilterTicket(), getGridJBtnFilter());
		getJPanelFilter().add(getBtnSaveTicket(), getGridJBtnSave());

		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());

		// Añado elementos del JPanelActions
		getJPanelContent().add(getJPanelActions(), getGridJPanelActions());
		getJPanelActions().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		getJPanelContent().add(getJPanelFilter(), getGridJPanelFilter());
		getJPanelContent().add(getJPanelTable(), getGridJPanelTable());
		getJPanelTable().setLayout(getGridLayoutJPanelTable());
		
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
		gbl_jPanelFilter.columnWidths = new int[] { 211, 211, 0, 0, 0 };
		gbl_jPanelFilter.rowHeights = new int[] { 20, 0, 0 };
		gbl_jPanelFilter.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_jPanelFilter.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };

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
		gbc_jPanelFilter.gridy = 1;

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
		gbc_lblName.insets = new Insets(0, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;

		return gbc_lblName;
	}

	private JComboBox getJComboBoxPeople() {
		if (cbPeople == null) {
			cbPeople = new JComboBox();
			
		}

		return cbPeople;

	}

	private GridBagConstraints getGridJTextFieldName() {

		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.insets = new Insets(0, 0, 5, 5);
		gbc_tfName.weightx = 1.0;
		gbc_tfName.fill = GridBagConstraints.BOTH;
		gbc_tfName.gridx = 1;
		gbc_tfName.gridy = 0;

		return gbc_tfName;
	}
	
	private GridBagConstraints getGridJBtnSave() {

		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.weightx = 1.0;
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.gridx = 3;
		gbc_btnSave.gridy = 0;

		return gbc_btnSave;
	}
	
	private JButton getBtnSaveTicket() {
		if (btnSaveTicket == null) {
			btnSaveTicket = new JButton("Guardar");
			btnSaveTicket.setIcon(new ImageIcon(JManageTicket.class.getResource("/com/reparadoras/images/icon-save.png")));
		}
		return btnSaveTicket;
	}
	
	private GridBagConstraints getGridJBtnFilter() {

		GridBagConstraints gbc_btnFilter = new GridBagConstraints();
		gbc_btnFilter.insets = new Insets(0, 0, 5, 5);
		gbc_btnFilter.weightx = 1.0;
		gbc_btnFilter.fill = GridBagConstraints.BOTH;
		gbc_btnFilter.gridx = 2;
		gbc_btnFilter.gridy = 0;

		return gbc_btnFilter;
	}
	
	private JButton getBtnFilterTicket() {
		if (btnFilterTicket == null) {
			btnFilterTicket = new JButton("Buscar");
			
			btnFilterTicket.setIcon(new ImageIcon(JManageTicket.class.getResource("/com/reparadoras/images/icon-search.png")));
		}
		return btnFilterTicket;
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
		gbl_jPanelContent.columnWidths = new int[] { 548, 99, 1, 0 };
		gbl_jPanelContent.rowHeights = new int[] { 23, 0, 0 };
		gbl_jPanelContent.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_jPanelContent.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		return gbl_jPanelContent;
	}

	/* FUNCIONES DEL PANEL DE ACCIONES */

	private JPanel getJPanelActions() {

		if (jPanelActions == null) {
			jPanelActions = new JPanel();
			
		}
		return jPanelActions;
	}

	private GridBagConstraints getGridJPanelActions() {

		GridBagConstraints gbc_jPanelActions = new GridBagConstraints();
		gbc_jPanelActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelActions.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelActions.insets = new Insets(0, 0, 0, 5);
		gbc_jPanelActions.gridx = 0;
		gbc_jPanelActions.gridy = 0;
		return gbc_jPanelActions;
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
		gbc_jPanelTable.gridy = 2;

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
	
	private JScrollPane getScrollPaneTable(){
		if (scrollPaneJTable == null){
			scrollPaneJTable = new JScrollPane(getJTableTicketsPeople());
			scrollPaneJTable.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		}
		
		return scrollPaneJTable;
	}
	
	private GridBagConstraints getGridJPanelScrollTable(){
		
		GridBagConstraints gbc_jPanelScroll = new GridBagConstraints();
		gbc_jPanelScroll.weighty = 1.0;
		gbc_jPanelScroll.weightx = 1.0;
		gbc_jPanelScroll.fill = GridBagConstraints.BOTH;
		gbc_jPanelScroll.anchor = GridBagConstraints.WEST;
		gbc_jPanelScroll.gridx = 0;
		gbc_jPanelScroll.gridy = 0;
		
		return gbc_jPanelScroll; 
	}
	
	private JTable getJTableTicketsPeople(){
		if (tablePeople == null){
			TableModel tableModel = getTicketsPeopleTableModel();
			tablePeople = new JTable(tableModel){

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
	
	private TicketsPeopleTableModel getTicketsPeopleTableModel(){
		
		if (ticketsPeopleTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Nombre", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts", "Fecha", "Pts"};
			ticketsPeopleTableModel = new TicketsPeopleTableModel(Arrays.asList(columnIdentifiers));
			}
		
		return ticketsPeopleTableModel;
	}
	
	private void setRendererJXDatePicker(){
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
		CaritasDatePickerCellEditor datePicker = new CaritasDatePickerCellEditor();
		
		
		SimpleDateFormat           simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy"); 
		TableCellRenderer          dateRenderer     = new FormattedCellRenderer(simpleDateFormat); 
		januaryColumn.setCellEditor(datePicker);
		januaryColumn.setCellRenderer(dateRenderer); 
		februaryColumn.setCellEditor(datePicker);
		februaryColumn.setCellRenderer(dateRenderer); 
		marchColumn.setCellEditor(datePicker);
		marchColumn.setCellRenderer(dateRenderer); 
		aprilColumn.setCellEditor(datePicker);
		aprilColumn.setCellRenderer(dateRenderer); 
		mayColumn.setCellEditor(datePicker);
		mayColumn.setCellRenderer(dateRenderer); 
		juneColumn.setCellEditor(datePicker);
		juneColumn.setCellRenderer(dateRenderer); 
		julyColumn.setCellEditor(datePicker);
		julyColumn.setCellRenderer(dateRenderer); 
		augustColumn.setCellEditor(datePicker);
		augustColumn.setCellRenderer(dateRenderer); 
		septemberColumn.setCellEditor(datePicker);
		septemberColumn.setCellRenderer(dateRenderer); 
		octoberColumn.setCellEditor(datePicker);
		octoberColumn.setCellRenderer(dateRenderer); 
		novemberColumn.setCellEditor(datePicker);
		novemberColumn.setCellRenderer(dateRenderer); 
		decemberColumn.setCellEditor(datePicker);
		decemberColumn.setCellRenderer(dateRenderer); 
		
	}
	
	private void setGroupHeadersTicket(){
		GroupableTableHeader header =  (GroupableTableHeader) this.getJTableTicketsPeople().getTableHeader();
		TableColumnModel tableColumnModel = this.getJTableTicketsPeople().getColumnModel();
		ColumnGroup groupJanuary = new ColumnGroup("Enero");
		groupJanuary.add(tableColumnModel.getColumn(1));
		groupJanuary.add(tableColumnModel.getColumn(2));
		ColumnGroup groupFebruary = new ColumnGroup("Febrero");
		groupFebruary.add(tableColumnModel.getColumn(3));
		groupFebruary.add(tableColumnModel.getColumn(4));
		ColumnGroup groupMarch = new ColumnGroup("Marzo");
		groupMarch.add(tableColumnModel.getColumn(5));
		groupMarch.add(tableColumnModel.getColumn(6));
		ColumnGroup groupApril = new ColumnGroup("Abril");
		groupApril.add(tableColumnModel.getColumn(7));
		groupApril.add(tableColumnModel.getColumn(8));
		ColumnGroup groupMay = new ColumnGroup("Mayo");
		groupMay.add(tableColumnModel.getColumn(9));
		groupMay.add(tableColumnModel.getColumn(10));
		ColumnGroup groupJune = new ColumnGroup("Junio");
		groupJune.add(tableColumnModel.getColumn(11));
		groupJune.add(tableColumnModel.getColumn(12));
		ColumnGroup groupJuly = new ColumnGroup("Julio");
		groupJuly.add(tableColumnModel.getColumn(13));
		groupJuly.add(tableColumnModel.getColumn(14));
		ColumnGroup groupAugust = new ColumnGroup("Agosto");
		groupAugust.add(tableColumnModel.getColumn(15));
		groupAugust.add(tableColumnModel.getColumn(16));
		ColumnGroup groupSeptember = new ColumnGroup("Septiembre");
		groupSeptember.add(tableColumnModel.getColumn(17));
		groupSeptember.add(tableColumnModel.getColumn(18));
		ColumnGroup groupOctober = new ColumnGroup("Octubre");
		groupOctober.add(tableColumnModel.getColumn(19));
		groupOctober.add(tableColumnModel.getColumn(20));
		ColumnGroup groupNovember = new ColumnGroup("Noviembre");
		groupNovember.add(tableColumnModel.getColumn(21));
		groupNovember.add(tableColumnModel.getColumn(22));
		ColumnGroup groupDecember = new ColumnGroup("Diciembre");
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
		UIManager.getDefaults().put("TableHeader.cellBorder",blackline);
		
		header.setBorder(blackline);
	}
	
	/* EVENTOS */

	public void openEditPeople(int openMode, String title) {
		JManageEditPeople jManageEditPeople = null;
		try {

			if ((openMode == JWindowParams.IMODE_SELECT || openMode == JWindowParams.IMODE_UPDATE)){
				int row = this.getJTableTicketsPeople().getSelectedRow();
				if (row!=-1){
					//Ticket people = this.getJTableTicketsPeople().getDomainObject(row);
					//jManageEditPeople = new JManageEditPeople(this, true, openMode, title, people);

					
				}
				else{
					JOptionPane.showMessageDialog(null, "Seleccione un registro");
					return;
			    }
				
			}
			else{
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
	
	public void onFilterTicket(boolean create){
		People filterPeople = (People)this.getJComboBoxPeople().getSelectedItem();
		
		Ticket ticket = ticketDAO.findTicket(filterPeople);
		if (ticket!=null){
			this.getTicketsPeopleTableModel().clearTableModelData();
			this.getTicketsPeopleTableModel().addRow(ticket);
			
		}
		else{
			Ticket ticketNewReset = new Ticket();
			ticketNewReset.setPeople(filterPeople);
			
			if (create){
				JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda. Se va a crear un nuevo registro de Vales");
				ticketDAO.insert(ticketNewReset);
				onFilterTicket(false);
			}
			else{
				JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda");
			}
			
		}
	
	}
	
	public void onSaveTicket(){
		
		
		int row = this.getJTableTicketsPeople().getSelectedRow();
		if (row!=-1){
			Ticket ticket = this.getTicketsPeopleTableModel().getDomainObject(row);
			ticketDAO.update(ticket);
			onFilterTicket(false);
			JOptionPane.showMessageDialog(this, "Se han guardado los datos correctamente");
			
			

			
		}
		else{
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
			return;
	    }
	
	}
	
	
}
