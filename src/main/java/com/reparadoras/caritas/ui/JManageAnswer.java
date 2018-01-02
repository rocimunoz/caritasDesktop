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

import org.apache.log4j.Logger;

import com.reparadoras.caritas.dao.AnswerDAO;
import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.filter.FilterAnswer;
import com.reparadoras.caritas.filter.FilterTicket;
import com.reparadoras.caritas.model.Answer;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.combobox.ComboBoxRenderer;
import com.reparadoras.caritas.ui.components.datepicker.CaritasDatePickerCellEditor;
import com.reparadoras.caritas.ui.components.table.AnswerPeopleTableModel;
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

public class JManageAnswer extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(JManageProgram.class);

	private JDesktopPane desktop = null;
	private JPanel jPanelFilter = null;
	private JLabel lblName = null;
	private JLabel lblDni;
	private JTextField tfDni;
	private JLabel lblPassport = null;
	private JTextField tfPassport;
	private JCheckBox ckActive;
	private JLabel lblYear = null;
	private JTextField tfName;
	private JTextField tfYear;
	private JPanel jPanelContent = null;

	private JPanel jPanelTable = null;
	private AnswerPeopleTableModel answersPeopleTableModel = null;
	private JTable tablePeople = null;
	private JScrollPane scrollPaneJTable = null;
	private JButton btnSaveTicket;
	private JButton btnFilterTicket;
	private JButton btnExit = null;
	private JButton btnCleanPeople = null;
	private FilterAnswer filterAnswer = null;

	private AnswerDAO answerDAO;
	private PeopleDAO peopleDAO;

	/**
	 * @wbp.parser.constructor
	 */
	public JManageAnswer(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title,
			FilterAnswer filterAnswer) {

		super(jCicIFParent, modal);
		this.setVisible(true);
		this.pack();

		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);

		this.filterAnswer = filterAnswer;

		answerDAO = new AnswerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		createGUIComponents();
		initComponents();
		addListeners();
		
		this.getJTextFieldDni().setText(this.filterAnswer.getDniPeople());
		this.getJTextFieldName().setText(this.filterAnswer.getNamePeople());
		this.getJTextFieldPassport().setText(this.filterAnswer.getPassportPeople());
		if (this.filterAnswer.getYearTicket()!=null){
			this.getJTextFieldYear().setText(this.filterAnswer.getYearTicket().toString());
		}
		if (this.filterAnswer.getActive()){
			this.getCkActive().setSelected(true);
		}else{
			this.getCkActive().setSelected(false);
		}
		
		onFilterAnswer(true);
		
		this.getJButtonSearch().setVisible(false);
		this.getJButtonClean().setVisible(false);
		
	}

	public JManageAnswer(JDesktopPane desktop) {
		super(desktop);
		this.desktop = desktop;
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);
		this.setVisible(true);
		this.pack();

		answerDAO = new AnswerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		createGUIComponents();
		initComponents();
		addListeners();

		onFilterAnswer(false);

	}

	public void initComponents() {

		this.getCkActive().setSelected(true);
		
	}

	public void addListeners() {

		getJButtonSearch().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFilterAnswer(false);
			}
		});

		getBtnSaveTicket().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSaveAnswer();
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
		getJPanelFilter().add(getJLabelPassport(), getGridJLabelPassport());
		getJPanelFilter().add(getJTextFieldPassport(), getGridJTextFieldPassport());
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
	
	private JLabel getJLabelPassport() {
		if (lblPassport == null) {
			lblPassport = new JLabel("Pasaporte:");
			lblPassport.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblPassport;
	}

	private GridBagConstraints getGridJLabelPassport() {
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.WEST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 0;
		gbc_lblDni.gridy = 1;

		return gbc_lblDni;
	}

	private JTextField getJTextFieldPassport() {
		if (tfPassport == null) {
			tfPassport = new JTextField();
			tfPassport.setColumns(10);
		}
		return tfPassport;
	}

	private GridBagConstraints getGridJTextFieldPassport() {

		GridBagConstraints gbc_tfDni = new GridBagConstraints();
		gbc_tfDni.weightx = 1.0;
		gbc_tfDni.anchor = GridBagConstraints.NORTH;
		gbc_tfDni.insets = new Insets(0, 0, 5, 5);
		gbc_tfDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDni.gridx = 1;
		gbc_tfDni.gridy = 1;

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
		gbc_lblName.gridy = 2;

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
		gbc_tfName.gridy = 2;

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
		gbc_lblName.gridy = 3;

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
		gbc_tfName.gridy = 3;

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
					.setIcon(new ImageIcon(JManageAnswer.class.getResource("/img/icon-search.png")));
		}
		return btnFilterTicket;
	}
	
	private JButton getJButtonClean() {
		if (btnCleanPeople == null) {
			btnCleanPeople = new JButton("Limpiar");
			btnCleanPeople.setIcon(
					new ImageIcon(JManagePeople.class.getResource("/img/icon-clean-32.png")));
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

			btnExit.setIcon(new ImageIcon(JManageProgram.class.getResource("/img/icon-exit.png")));
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
					.setIcon(new ImageIcon(JManageAnswer.class.getResource("/img/icon-save.png")));
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
			TableModel tableModel = getAnswersPeopleTableModel();
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

	private AnswerPeopleTableModel getAnswersPeopleTableModel() {

		if (answersPeopleTableModel == null) {
			Object[] columnIdentifiers = new Object[] { "Nombre", "Fecha", "Respuesta" };
			answersPeopleTableModel = new AnswerPeopleTableModel(Arrays.asList(columnIdentifiers));
		}

		return answersPeopleTableModel;
	}

	private void setRendererJXDatePicker() {
		TableColumn januaryColumn = this.getJTableTicketsPeople().getColumnModel().getColumn(1);
		
		CaritasDatePickerCellEditor datePickerJanuary = new CaritasDatePickerCellEditor();
		
	
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		TableCellRenderer dateRenderer = new FormattedCellRenderer(simpleDateFormat);
		januaryColumn.setCellEditor(datePickerJanuary);
		januaryColumn.setCellRenderer(dateRenderer);
		

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
			logger.error(e);
		}

	}

	public void onFilterAnswer(boolean create) {
		
		
		if (filterAnswer == null){
			filterAnswer = new FilterAnswer();
		} 
		
		filterAnswer.setDniPeople(this.getJTextFieldDni().getText());
		filterAnswer.setNamePeople(this.getJTextFieldName().getText());
		if (this.getJTextFieldYear().getText()!=null && !this.getJTextFieldYear().getText().equals("")){
			filterAnswer.setYearTicket(Integer.parseInt(this.getJTextFieldYear().getText()));
		}
		

		
		
		List<Answer> answers = answerDAO.findAnswer(filterAnswer);
		if (answers != null && !answers.isEmpty()) {
			this.getAnswersPeopleTableModel().clearTableModelData();
			this.getAnswersPeopleTableModel().addRows(answers);

		} else {
			Answer answerNewReset = new Answer();
			People filterPeople = new People();
			filterPeople.setDni(this.getJTextFieldDni().getText());
			filterPeople.setName(this.getJTextFieldName().getText());
			filterPeople.setId(filterAnswer.getIdPeople());
			People peopleTicket = peopleDAO.findPeopleById(filterPeople);
			
			answerNewReset.setPeople(peopleTicket);
			
			answerNewReset.setYear(Calendar.getInstance().get(Calendar.YEAR));

			if (create) {
				if (JOptionPane.showConfirmDialog(this,
						"Este usuario no tiene registros de Vales todavia. ¿Quieres crearlo?",
						"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
					answerDAO.insert(answerNewReset);
					
					JOptionPane.showMessageDialog(this, "Se ha creado un registro para el usuario " + this.getJTextFieldName().getText() + " con todos los meses inicializados a 0");
					onFilterAnswer(false);
					this.cleanFilter();
					
					
				}

			} else {
				//JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda");
			}

		}

	}

	public void onSaveAnswer() {

		int row = this.getJTableTicketsPeople().getSelectedRow();
		if (row != -1) {
			Answer answer = this.getAnswersPeopleTableModel().getDomainObject(row);
			answerDAO.update(answer);
			onFilterAnswer(false);
			JOptionPane.showMessageDialog(this, "Se han guardado los datos correctamente");
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
			return;
		}

	}

}
