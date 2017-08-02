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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXDatePicker;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.PeopleTableModel;
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
		getJtabPane1().add("Direccion", new JPanelAddress());
		getJtabPane1().add("Vivienda", new JPanelHome());
		getJtabPane1().add("Familia", new JPanelFamily());
		getJtabPane1().setEnabledAt(1, true);
		getJtabPane1().setEnabledAt(0, true);
		
		getJtabPane1().setBackgroundAt(0, Color.WHITE);
		
		
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
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
	
	
	
	
	/*DIRECCION */
	
	
	
	
	
	
	
	
	
	
	
	


	
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
