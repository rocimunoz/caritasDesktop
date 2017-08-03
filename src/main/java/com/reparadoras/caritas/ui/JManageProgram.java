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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.PeopleTableModel;
import com.reparadoras.caritas.ui.components.ProgramTableModel;
import com.reparadoras.caritas.ui.components.RelativesTableModel;
import com.reparadoras.caritas.ui.components.TicketsPeopleTableModel;

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
	private ProgramDAO programDAO;
	
	private JTabbedPane jtabPane1;
	
	private People people = null;
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public JManageProgram(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title, People people){
		
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
		
		
		createGUIComponents();
		initComponents();
		
		onFilterProgram(true);
		
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

		
		addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
                // do something  
            	System.out.println("evento internal frame");
            }
        });
	
		getJButtonSearch().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		

	}

	
	public void createGUIComponents(){
		getContentPane().setLayout(getGridContentPane());
		
		getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());
		//getContentPane().add(getJPanelActions(), this.getGridJPanelActions());
		getContentPane().add(getJPanelGrid(), this.getGridJPanelGrid());
		
		// Añado elementos del JPanelFilter
		getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
		getJPanelFilter().add(getJLabelDni(), getGridJLabelDni());
		getJPanelFilter().add(getJTextFieldDni(), getGridJTextFieldDni());
		
		getJPanelFilter().add(getCkActive(), getGridJCheckBoxdActive());
		getJPanelFilter().add(getJLabelName(), getGridJLabelName());
		getJPanelFilter().add(getJComboBoxPeople(), getGridJTextFieldName());
		getJPanelFilter().add(getJButtonSearch(), getGridButtonSearch());
		
		getJPanelActions().setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		getJPanelActions().add(getJButtonSave());
		getJPanelActions().add(getJButtonExit());
		
		//Añado elementos del JPanelGrid
		getJPanelGrid().setLayout(getGridLayoutJPanelGrid());
		getJPanelGrid().add(getJPanelActions(), this.getGridJPanelActions());
		getJPanelGrid().add(this.getScrollPaneTable(), this.getGridJPanelScrollTable());
		
		
		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());
		
		
		getJPanelContent().add(getJtabPane1(), getGridJTabPane());
		getJtabPane1().add("Direccion", new JPanelAddress());
		getJtabPane1().add("Vivienda", new JPanelHome());
		getJtabPane1().add("Familia", new JPanelFamily());
		getJtabPane1().setEnabledAt(1, true);
		getJtabPane1().setEnabledAt(0, true);
		
		getJtabPane1().setBackgroundAt(0, Color.WHITE);
		
	
		
	}
	
	public void initComponents(){
		this.getCkActive().setSelected(true);
		initCbPeople();
		
	}
	
public void initCbPeople(){
		
		
		if (this.people!=null){
			this.getJComboBoxPeople().addItem(this.people);
			this.getJComboBoxPeople().setSelectedItem(this.people);
		}
		else{
			List<People> listPeople = peopleDAO.findAll();
			People allPeople = new People();
			allPeople.setName("TODOS");
		    allPeople.setIdPeople(-1);
			listPeople.add(0, allPeople);
			for (People p : listPeople) {
				this.getJComboBoxPeople().addItem(p);
			}
		}
		
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
			jPanelFilter.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Busqueda Programa Atención Primaria",
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

	
	/* FUNCIONES DEL PANEL DE ACCIONES*/
	
	

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

	
	
	private JButton getJButtonExit() {
		if (btnExit == null) {
			btnExit = new JButton("Salir");
			btnExit.setIcon(new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-exit.png")));
		}

		return btnExit;
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
			jPanelGrid.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Listado Programa Atención Primaria",
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
	
	private JScrollPane getScrollPaneTable(){
		if (scrollPaneJTable == null){
			scrollPaneJTable = new JScrollPane(getJTableProgram());
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
		gbc_jPanelScroll.gridy = 1;
		
		return gbc_jPanelScroll; 
	}
	
	private JTable getJTableProgram(){
		if (tableProgram == null){
			
			tableProgram = new JTable(getProgramTableModel());
			tableProgram.setAutoCreateRowSorter(true);
			tableProgram.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		
		return tableProgram;
	}
	
	private ProgramTableModel getProgramTableModel(){
		
		if (programTableModel == null){
			Object[] columnIdentifiers = new Object[] { "Dni", "Nombre", "Fecha"};
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
		gbc_jTabPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTabPane.insets = new Insets(0, 0, 5, 0);
		gbc_jTabPane.gridx = 0;
		gbc_jTabPane.gridy = 0;

		return gbc_jTabPane;
	}
	
	
	
	
	

	
	/* EVENTOS */

	public void onFilterProgram(boolean create){
		People filterPeople = (People)this.getJComboBoxPeople().getSelectedItem();
		
		if (filterPeople.getIdPeople()!=-1){
			Program program = programDAO.findProgram(filterPeople);
			if (program!=null){
				this.getProgramTableModel().clearTableModelData();
				this.getProgramTableModel().addRow(program);
				
			}
			else{
				Program programNewReset = new Program();
				programNewReset.setPeople(filterPeople);
				programNewReset.setDateGBD(new Date());
				
				if (create){
					int dialogResult = JOptionPane.showConfirmDialog(this, "No existen registros para los datos de búsqueda. ¿Quieres crear un nuevo programa de atención primaria?");
					if (dialogResult == JOptionPane.YES_OPTION){
						
						programDAO.insert(programNewReset);
						onFilterProgram(false);
					}
					
				}
				else{
					JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda");
				}
				
			}
		}
		else{
			List<Program> listPrograms = programDAO.findAll();
			this.getProgramTableModel().clearTableModelData();
			this.getProgramTableModel().addRows(listPrograms);
		}
		
	
	}
	
	
	
	
	
	
}
