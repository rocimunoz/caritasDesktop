package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.reparadoras.caritas.dao.AddressDAO;
import com.reparadoras.caritas.dao.AuthorizationTypeDAO;
import com.reparadoras.caritas.dao.ExpensesDAO;
import com.reparadoras.caritas.dao.FamilyDAO;
import com.reparadoras.caritas.dao.FamilyTypeDAO;
import com.reparadoras.caritas.dao.HomeDAO;
import com.reparadoras.caritas.dao.HomeTypeDAO;
import com.reparadoras.caritas.dao.IncomesDAO;
import com.reparadoras.caritas.dao.JobSituationDAO;
import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.RelativeDAO;
import com.reparadoras.caritas.dao.StudiesDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.filter.FilterTicket;
import com.reparadoras.caritas.model.Address;
import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.Expense;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.HomeType;
import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.JobSituation;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.model.Studies;
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
import com.reparadoras.caritas.ui.utils.Constants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class JManageImportData extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	static final Logger logger = Logger.getLogger(JManageImportData.class);

	private JDesktopPane desktop = null;
	private JPanel jPanelFile = null;

	private JPanel jPanelContent = null;

	private JPanel jPanelTData = null;
	private JButton btnExit = null;
	private JButton btnImportFile = null;
	private JFileChooser jfc = null;
	private JTextArea textArea = null;
	private JScrollPane scroll = null;

	private PeopleDAO peopleDAO;

	private FamilyDAO familyDAO;
	private FamilyTypeDAO familyTypeDAO;
	private AuthorizationTypeDAO authorizationTypeDAO;
	private JobSituationDAO jobSituationDAO;
	private StudiesDAO studiesDAO;
	private ProgramDAO programDAO;
	private HomeDAO homeDAO;
	private HomeTypeDAO homeTypeDAO;
	private AddressDAO addressDAO;
	private IncomesDAO incomeDAO;
	private ExpensesDAO expenseDAO;
	private RelativeDAO relativeDAO;

	public int countOK = 0;
	public int countKO = 0;
	public int countExist = 0;
	public int countTotal = 0;

	private List<String> errorRegister = new ArrayList<String>();

	/**
	 * @wbp.parser.constructor
	 */

	public JManageImportData(JDesktopPane desktop) {
		super(desktop);
		this.desktop = desktop;
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setTitle(title);
		this.setVisible(true);
		this.pack();

		createGUIComponents();
		initComponents();
		addListener();

		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		familyDAO = new FamilyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		familyTypeDAO = new FamilyTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		authorizationTypeDAO = new AuthorizationTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		jobSituationDAO = new JobSituationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		studiesDAO = new StudiesDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		homeDAO = new HomeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		homeTypeDAO = new HomeTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		addressDAO = new AddressDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		incomeDAO = new IncomesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		expenseDAO = new ExpensesDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		relativeDAO = new RelativeDAO(MyBatisConnectionFactory.getSqlSessionFactory());

	}

	public void initComponents() {

	}

	public void addListener() {

		this.getJButtonExit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}

		});

		getJButtonImportFile().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int returnValue = getFileChooser().showDialog(null, "Selecciona fichero");
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					importData(jfc.getSelectedFile());

				}
			}
		});
	}

	private JFileChooser getFileChooser() {
		if (jfc == null) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS files", "xls");
			FileNameExtensionFilter filter2 = new FileNameExtensionFilter("XLSX files", "xlsx");

			jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Elige un fichero para importar: ");
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jfc.addChoosableFileFilter(filter);
			jfc.addChoosableFileFilter(filter2);

		}

		return jfc;

	}

	public void createGUIComponents() {

		getContentPane().setLayout(getGridContentPane());
		// getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());

		// Añado elementos del JPanelFilter
		getJPanelFile().setLayout(getGridLayoutJPanelFile());
		getJPanelData().setLayout(getGridLayoutJPanelData());

		getJPanelFile().add(getJButtonExit(), getGridButtonExit());
		getJPanelFile().add(getJButtonImportFile(), getGridButtonImportFile());

		getJPanelData().add(getJScrollPane(), getGridJScrollPane());

		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());

		getJPanelContent().add(getJPanelFile(), getGridJPanelFile());
		getJPanelContent().add(getJPanelData(), getGridJPanelData());

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

	private GridBagLayout getGridLayoutJPanelFile() {

		GridBagLayout gbl_jPanelFilter = new GridBagLayout();
		gbl_jPanelFilter.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gbl_jPanelFilter.rowWeights = new double[] { 0.0 };

		return gbl_jPanelFilter;
	}

	private JPanel getJPanelFile() {
		if (jPanelFile == null) {
			jPanelFile = new JPanel();
			jPanelFile.setBorder(null);

		}

		return jPanelFile;
	}

	private GridBagConstraints getGridJPanelFile() {
		GridBagConstraints gbc_jPanelFilter = new GridBagConstraints();
		gbc_jPanelFilter.weightx = 1.0;
		gbc_jPanelFilter.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelFilter.fill = GridBagConstraints.BOTH;
		gbc_jPanelFilter.gridx = 0;
		gbc_jPanelFilter.gridy = 0;

		return gbc_jPanelFilter;
	}

	private GridBagLayout getGridLayoutJPanelData() {

		GridBagLayout gbl_jPanelFilter = new GridBagLayout();
		gbl_jPanelFilter.columnWeights = new double[] { 0.0 };
		gbl_jPanelFilter.rowWeights = new double[] { 0.0 };

		return gbl_jPanelFilter;
	}

	private JPanel getJPanelData() {
		if (jPanelTData == null) {
			jPanelTData = new JPanel();
			jPanelTData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Resultado Importacion",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

		}

		return jPanelTData;
	}

	private GridBagConstraints getGridJPanelData() {
		GridBagConstraints gbc_jPanelFilter = new GridBagConstraints();
		gbc_jPanelFilter.weighty = 1.0;
		gbc_jPanelFilter.weightx = 1.0;
		gbc_jPanelFilter.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelFilter.fill = GridBagConstraints.BOTH;
		gbc_jPanelFilter.gridx = 0;
		gbc_jPanelFilter.gridy = 1;

		return gbc_jPanelFilter;
	}

	private JScrollPane getJScrollPane() {
		if (scroll == null) {
			textArea = new JTextArea();
			textArea.setEditable(false); // set textArea non-editable
			scroll = new JScrollPane(textArea);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}

		return scroll;
	}

	private GridBagConstraints getGridJScrollPane() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.weighty = 1.0;
		gbc_btnExit.weightx = 1.0;
		gbc_btnExit.fill = GridBagConstraints.BOTH;
		gbc_btnExit.gridx = 0;
		gbc_btnExit.gridy = 0;

		return gbc_btnExit;
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
		gbc_btnExit.gridx = 3;
		gbc_btnExit.gridy = 0;

		return gbc_btnExit;
	}

	private JButton getJButtonImportFile() {
		if (btnImportFile == null) {
			btnImportFile = new JButton("Seleccione un fichero de importacion");

			btnImportFile.setHorizontalAlignment(SwingConstants.RIGHT);

			// btnImportFile.setIcon(new
			// ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-exit.png")));
		}

		return btnImportFile;
	}

	private GridBagConstraints getGridButtonImportFile() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.gridx = 2;
		gbc_btnExit.gridy = 0;

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

	private String getFileExtension(File file) {

		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return null;
		}
	}

	public void importData(File file) {

		String extension = getFileExtension(jfc.getSelectedFile());

		if (extension != null && (extension.equals("xls") || extension.equals("xlsx"))) {

			readExcelFileXls(jfc.getSelectedFile());
		} else {
			textArea.append("Lo siento, no es posible importar datos. El fichero " + jfc.getSelectedFile().getName()
					+ " no es un fichero valido");
		}

	}

	public void readExcelFileXls(File file) {

		try {
			// this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			InputStream targetStream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(targetStream);

			// Acceso a la primera hoja del documento
			XSSFSheet sheet = workbook.getSheetAt(0);

			XSSFSheet sheetRelatives = workbook.getSheetAt(1);
			XSSFSheet sheetIncomes = workbook.getSheetAt(2);
			XSSFSheet sheetExpenses = workbook.getSheetAt(3);

			List<String> data = new ArrayList<String>();
			Map<String, Program> mapProgram = new HashMap<String, Program>();
			Map<String, List<Income>> mapIncomes = new HashMap<String, List<Income>>();
			Map<String, List<Expense>> mapExpenses = new HashMap<String, List<Expense>>();
			Map<String, List<Relative>> mapRelatives = new HashMap<String, List<Relative>>();

			// Recorremos las filas del documento
			Iterator rows = sheet.rowIterator();
			Iterator rowsRelatives = sheetRelatives.rowIterator();
			Iterator rowsIncomes = sheetIncomes.rowIterator();
			Iterator rowsExpenses = sheetExpenses.rowIterator();

			// Extraemos los datos del programa
			extractDataSheet_0(rows, mapProgram);

			// Extraemos los datos de familiares
			extractDataSheet_1(rowsRelatives, mapRelatives, mapProgram);

			// Extraemos los datos de ingresos
			extractDataSheet_2(rowsIncomes, mapIncomes, mapProgram);

			// Extraemos los datos de gastos
			extractDataSheet_3(rowsExpenses, mapExpenses, mapProgram);

			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} catch (Exception e) {
			logger.error("Se ha producido un error en la importacion de datos  " + e.getMessage());

		}

		textArea.append("\n");
		textArea.append("******************: " + "\n");
		textArea.append("Resumen: " + "\n");
		textArea.append("******************: " + "\n");
		textArea.append("Registros totales leidos: " + countTotal + "\n");
		textArea.append("Registros insertados correctamente: " + countOK + "\n");
		textArea.append("Registros no insertados por errores : " + countKO + "\n");
		textArea.append("Registros no insertados por existir ya en Base de Datos : " + countExist + "\n");

	}

	/**************************************
	 * GENERAMOS LA PRIMERA HOJA DE PROGRAMA
	 ***************************************/

	public void extractDataSheet_0(Iterator itRows, Map<String, Program> mapProgram) {

		while (itRows.hasNext()) {
			XSSFRow row = (XSSFRow) itRows.next();

			try {
				if (row.getRowNum() >= 2) {
					countTotal++;
					Iterator cells = row.cellIterator();
					String key = "";

					while (cells.hasNext()) {
						XSSFCell cell = (XSSFCell) cells.next();
						key = extractDataRow_Sheet0(cell, mapProgram, key);
					}

					// Busco si ya existe
					if (key != null && !key.equals("")) {
						List<People> listPeopleExist = peopleDAO.findPeople(mapProgram.get(key).getPeople());
						if (listPeopleExist != null && listPeopleExist.isEmpty()) {
							addressDAO.insert(mapProgram.get(key).getFamily().getHome().getAddress());
							homeDAO.insert(mapProgram.get(key).getFamily().getHome());
							familyDAO.insert(mapProgram.get(key).getFamily());
							if (mapProgram.get(key).getPeople().isActive() == null) {
								mapProgram.get(key).getPeople().setActive(false);
							}
							peopleDAO.insert(mapProgram.get(key).getPeople());
							programDAO.insertExcel(mapProgram.get(key));

							textArea.append("Insertado registro: " + mapProgram.get(key).getPeople().getName() + "\n");
							countOK++;
						} else {
							countExist++;
							textArea.append("El registro " + mapProgram.get(key).getPeople().getName()
									+ " ya existe en BBDD. No se insertará \n");
							errorRegister.add(mapProgram.get(key).getPeople().getDni());
						}
					}

				}
			} catch (Exception e) {
				logger.error("Se ha producido un error " + e.getMessage());
				textArea.append("Error insertando la fila " + row.getRowNum() + "\n");
				countKO++;
			}

		}
	}

	public String extractDataRow_Sheet0(XSSFCell cell, Map<String, Program> mapProgram, String key) {

		Program program = new Program();
		People people = new People();
		Family family = new Family();
		Home home = new Home();
		Address address = new Address();
		home.setAddress(address);
		family.setHome(home);

		program.setPeople(people);
		program.setFamily(family);

		String primaryKey = key;
		try {
			switch (cell.getColumnIndex()) {
			case 0:
				if (!cell.getStringCellValue().equals("")) {
					mapProgram.put(cell.getStringCellValue(), program);

					mapProgram.get(cell.getStringCellValue()).getPeople().setDni(cell.getStringCellValue());
					primaryKey = cell.getStringCellValue();
				}

				break;
			case 1:
				if (primaryKey != null) {
					mapProgram.get(key).getPeople().setPassport(cell.getStringCellValue());
				} else {
					mapProgram.put(cell.getStringCellValue(), program);

					mapProgram.get(cell.getStringCellValue()).getPeople().setPassport(cell.getStringCellValue());
					primaryKey = cell.getStringCellValue();
				}

				break;
			case 2:

				if (cell.getDateCellValue() != null) {
					mapProgram.get(key).getPeople().setCreateDate(cell.getDateCellValue());
				}
				break;
			case 3:
				if (cell.getDateCellValue() != null) {
					mapProgram.get(key).getPeople().setReactivateDate(cell.getDateCellValue());
				}
				break;
			case 4:
				if (cell.getStringCellValue().equals("X")) {
					mapProgram.get(key).getPeople().setActive(true);
				} else
					mapProgram.get(key).getPeople().setActive(false);
				break;
			case 5:
				mapProgram.get(key).getPeople().setName(cell.getStringCellValue());
				break;
			case 6:
				mapProgram.get(key).getPeople().setFirstSurname(cell.getStringCellValue());
				break;
			case 7:
				mapProgram.get(key).getPeople().setSecondSurname(cell.getStringCellValue());
				break;
			case 8:
				mapProgram.get(key).getPeople().setSex(cell.getStringCellValue());
				break;
			case 9:
				mapProgram.get(key).getPeople().setDateBorn(cell.getDateCellValue());
				break;
			case 10:
				mapProgram.get(key).getPeople().setCountry(cell.getStringCellValue());
				break;
			case 11:
				mapProgram.get(key).getPeople().setNationality(cell.getStringCellValue());
				break;
			case 15:

				mapProgram.get(key).getPeople().setYearToSpain(new Double(cell.getNumericCellValue()).intValue());

				break;
			case 16:
				mapProgram.get(key).getFamily().getHome().getAddress().setTown(cell.getStringCellValue());
				break;
			case 17:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				mapProgram.get(key).getFamily().getHome().getAddress()
						.setPostalCode(String.valueOf(cell.getStringCellValue()));
				break;
			case 18:
				mapProgram.get(key).getFamily().getHome().getAddress().setStreet(cell.getStringCellValue());
				break;
			case 19:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				mapProgram.get(key).getFamily().getHome().getAddress().setGate(cell.getStringCellValue());

				break;
			case 20:
				mapProgram.get(key).getFamily().getHome().getAddress().setFloor(cell.getStringCellValue());
				break;
			case 21:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				mapProgram.get(key).getFamily().getHome().getAddress().setTelephone(cell.getStringCellValue());
				break;
			case 22:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				mapProgram.get(key).getFamily().getHome().getAddress().setTelephoneContact(cell.getStringCellValue());
				break;
			case 23:

				String description = cell.getStringCellValue();
				HomeType hFilter = new HomeType();
				hFilter.setDescription(cell.getStringCellValue());
				HomeType homeTypeBBDD = homeTypeDAO.findHomeType(hFilter);
				if (homeTypeBBDD != null) {
					mapProgram.get(key).getFamily().getHome().setHomeType(homeTypeBBDD);
				}

				break;
			case 24:
				mapProgram.get(key).getFamily().getHome().setRegHolding(cell.getStringCellValue());
				break;
			case 25:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapProgram.get(key).getFamily().getHome()
							.setNumberRooms((int) Double.parseDouble(cell.getStringCellValue()));
				}

				break;
			case 26:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapProgram.get(key).getFamily().getHome()
							.setNumberPeople((int) Double.parseDouble(cell.getStringCellValue()));
				}

				break;
			case 27:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapProgram.get(key).getFamily().getHome()
							.setNumberFamilies((int) Double.parseDouble(cell.getStringCellValue()));
				}

				break;
			case 28:
				mapProgram.get(key).getFamily().getHome().setOtherInfo(cell.getStringCellValue());

				break;
			case 29:
				String nemonic = cell.getStringCellValue();
				FamilyType familyType = familyTypeDAO.findFamilyType(Constants.getFamilyType(nemonic));
				mapProgram.get(key).getFamily().setFamilyType(familyType);

				break;
			case 31:
				String nemonicAuthorization = cell.getStringCellValue();
				AuthorizationType authorizationType = authorizationTypeDAO
						.findAuthorizationType(Constants.getAuthorizationType(nemonicAuthorization));
				mapProgram.get(key).setAuthorizationType(authorizationType);

				break;
			case 32:
				String nemonicJobSituation = cell.getStringCellValue();
				JobSituation jobSituation = jobSituationDAO
						.findJobSituation(Constants.getJobSituation(nemonicJobSituation));
				mapProgram.get(key).setJobSituation(jobSituation);

				break;
			case 33:
				String nemonicStudies = cell.getStringCellValue();
				Studies studies = studiesDAO.findStudies(Constants.getStudies(nemonicStudies));
				mapProgram.get(key).setStudies(studies);
				break;

			}

		} catch (Exception e) {

			logger.error(e);
			throw e;
		}

		return primaryKey;

	}

	/**************************************
	 * GENERAMOS LA SEGUNDA HOJA DE FAMILIARES
	 ***************************************/

	public void extractDataSheet_1(Iterator itRows, Map<String, List<Relative>> mapRelatives,
			Map<String, Program> mapProgram) {

		while (itRows.hasNext()) {
			XSSFRow row = (XSSFRow) itRows.next();

			try {
				if (row.getRowNum() >= 2) {

					Iterator cells = row.cellIterator();
					String key = "";

					while (cells.hasNext()) {
						XSSFCell cell = (XSSFCell) cells.next();
						key = extractDataRow_Sheet1(cell, mapRelatives, key);
					}

				}
			} catch (Exception e) {

				logger.error("Se ha producido un error al tratar los familiares " + e.getMessage());
				textArea.append("Error insertando la fila " + row.getRowNum() + "\n");
			}
		}

		mapRelatives.forEach((k, v) -> {
			boolean exist = false;
			String dni = k;
			List<Relative> listRelatives = v;
			// Si dni esta en el set, no inserto relatives

			for (String errorDni : errorRegister) {
				if (dni.equals(errorDni)) {
					exist = true;
					break;
				}
			}
			try {
				if (exist == false) {
					// Si dni no esta en el set y existe en BBDD inserto
					// relatives

					if (mapProgram.get(dni) != null) {
						List<People> listPeopleExist = peopleDAO.findPeople(mapProgram.get(dni).getPeople());
						if (listPeopleExist != null && !listPeopleExist.isEmpty()) {

							/*
							 * for (Relative relative : listRelatives) {
							 * relative.setFamily(mapProgram.get(dni).getFamily(
							 * )); relativeDAO.insert(relative); }
							 */
						}
					}

				}
			} catch (Exception e) {
				logger.error("Se ha producido un error " + e.getMessage());
				textArea.append("Error tratando familiares del dni:  " + dni + "\n");
			}

		});
	}

	public String extractDataRow_Sheet1(XSSFCell cell, Map<String, List<Relative>> mapRelatives, String key) {

		String primaryKey = key;
		List<Relative> listRelatives = null;
		Relative relative = null;
		try {
			switch (cell.getColumnIndex()) {
			case 0:

				if (mapRelatives.get(cell.getStringCellValue()) == null) {
					listRelatives = new ArrayList();
					relative = new Relative();

					listRelatives.add(relative);
					mapRelatives.put(cell.getStringCellValue(), listRelatives);
					primaryKey = cell.getStringCellValue();
				} else {

					mapRelatives.get(cell.getStringCellValue()).add(new Relative());
					primaryKey = cell.getStringCellValue();
				}
				break;
			case 2:
				mapRelatives.get(primaryKey).get(mapRelatives.get(primaryKey).size() - 1)
						.setRelationShip(cell.getStringCellValue());
				break;
			case 4:
				mapRelatives.get(primaryKey).get(mapRelatives.get(primaryKey).size() - 1)
						.setSurname(cell.getStringCellValue());

				break;
			case 5:
				mapRelatives.get(primaryKey).get(mapRelatives.get(primaryKey).size() - 1)
						.setName(cell.getStringCellValue());

				break;
			case 6:
				mapRelatives.get(primaryKey).get(mapRelatives.get(primaryKey).size() - 1)
						.setDateBorn(cell.getDateCellValue());
				break;
			case 8:
				mapRelatives.get(primaryKey).get(mapRelatives.get(primaryKey).size() - 1)
						.setSituation(cell.getStringCellValue());
				break;
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

		return primaryKey;
	}

	/**************************************
	 * GENERAMOS LA TERCERA HOJA DE INGRESOS
	 ***************************************/

	public void extractDataSheet_2(Iterator itRows, Map<String, List<Income>> mapIncomes,
			Map<String, Program> mapProgram) {

		while (itRows.hasNext()) {
			XSSFRow row = (XSSFRow) itRows.next();

			try {
				if (row.getRowNum() >= 2) {

					Iterator cells = row.cellIterator();
					String key = "";

					while (cells.hasNext()) {
						XSSFCell cell = (XSSFCell) cells.next();
						key = extractDataRow_Sheet2(cell, mapIncomes, key);
					}

				}
			} catch (Exception e) {

				logger.error("Se ha producido un error al tratar los familiares " + e.getMessage());
				textArea.append("Error insertando la fila " + row.getRowNum() + "\n");
			}
		}

		mapIncomes.forEach((k, v) -> {
			boolean exist = false;
			String dni = k;
			List<Income> listIncomes = v;
			// Si dni esta en el set, no inserto relatives

			for (String errorDni : errorRegister) {
				if (dni.equals(errorDni)) {
					exist = true;
					break;
				}
			}
			try {
				if (exist == false) {
					// Si dni no esta en el set y existe en BBDD inserto
					// relatives

					if (mapProgram.get(dni) != null) {
						List<People> listPeopleExist = peopleDAO.findPeople(mapProgram.get(dni).getPeople());
						if (listPeopleExist != null && !listPeopleExist.isEmpty()) {

							// Insert incomes
						}
					}

				}
			} catch (Exception e) {
				logger.error("Se ha producido un error " + e.getMessage());
				textArea.append("Error tratando ingresos del dni:  " + dni + "\n");
			}

		});
	}

	public String extractDataRow_Sheet2(XSSFCell cell, Map<String, List<Income>> mapIncomes, String key) {
		String primaryKey = key;
		List<Income> listIncomes = null;
		Income income = null;
		try {
			switch (cell.getColumnIndex()) {
			case 0:

				if (mapIncomes.get(cell.getStringCellValue()) == null) {
					listIncomes = new ArrayList();
					income = new Income();

					listIncomes.add(income);
					mapIncomes.put(cell.getStringCellValue(), listIncomes);
					primaryKey = cell.getStringCellValue();
				} else {

					mapIncomes.get(cell.getStringCellValue()).add(new Income());
					primaryKey = cell.getStringCellValue();
				}
				break;
			case 2:
				mapIncomes.get(primaryKey).get(mapIncomes.get(primaryKey).size() - 1)
						.setConcept(cell.getStringCellValue());
				break;
			case 3:
				mapIncomes.get(primaryKey).get(mapIncomes.get(primaryKey).size() - 1)
						.setAmount(cell.getNumericCellValue());
				break;
			case 4:
				mapIncomes.get(primaryKey).get(mapIncomes.get(primaryKey).size() - 1)
						.setEndDate(cell.getDateCellValue());
				break;
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

		return primaryKey;
	}

	/**************************************
	 * GENERAMOS LA CUARTA HOJA DE GASTOS
	 ***************************************/

	public void extractDataSheet_3(Iterator itRows, Map<String, List<Expense>> mapExpenses,
			Map<String, Program> mapProgram) {

		while (itRows.hasNext()) {
			XSSFRow row = (XSSFRow) itRows.next();

			try {
				if (row.getRowNum() >= 2) {

					Iterator cells = row.cellIterator();
					String key = "";

					while (cells.hasNext()) {
						XSSFCell cell = (XSSFCell) cells.next();
						key = extractDataRow_Sheet3(cell, mapExpenses, key);
					}

				}
			} catch (Exception e) {

				logger.error("Se ha producido un error al tratar los gastos " + e.getMessage());
				textArea.append("Error insertando la fila " + row.getRowNum() + "\n");
			}
		}

		mapExpenses.forEach((k, v) -> {
			boolean exist = false;
			String dni = k;
			List<Expense> listIncomes = v;
			// Si dni esta en el set, no inserto relatives

			for (String errorDni : errorRegister) {
				if (dni.equals(errorDni)) {
					exist = true;
					break;
				}
			}
			try {
				if (exist == false) {
					// Si dni no esta en el set y existe en BBDD inserto
					// relatives

					if (mapProgram.get(dni) != null) {
						List<People> listPeopleExist = peopleDAO.findPeople(mapProgram.get(dni).getPeople());
						if (listPeopleExist != null && !listPeopleExist.isEmpty()) {

							// Insert expenses
						}
					}

				}
			} catch (Exception e) {
				logger.error("Se ha producido un error " + e.getMessage());
				textArea.append("Error tratando ingresos del dni:  " + dni + "\n");
			}

		});
	}

	public String extractDataRow_Sheet3(XSSFCell cell, Map<String, List<Expense>> mapExpenses, String key) {
		String primaryKey = key;
		List<Expense> listExpenses = null;
		Expense expense = null;
		try {
			switch (cell.getColumnIndex()) {
			case 0:

				if (mapExpenses.get(cell.getStringCellValue()) == null) {
					listExpenses = new ArrayList();
					expense = new Expense();

					listExpenses.add(expense);
					mapExpenses.put(cell.getStringCellValue(), listExpenses);
					primaryKey = cell.getStringCellValue();
				} else {

					mapExpenses.get(cell.getStringCellValue()).add(new Expense());
					primaryKey = cell.getStringCellValue();
				}
				break;
			case 2:
				mapExpenses.get(primaryKey).get(mapExpenses.get(primaryKey).size() - 1)
						.setConcept(cell.getStringCellValue());
				break;
			case 3:
				mapExpenses.get(primaryKey).get(mapExpenses.get(primaryKey).size() - 1)
						.setAmount(cell.getNumericCellValue());
				break;
			case 4:
				mapExpenses.get(primaryKey).get(mapExpenses.get(primaryKey).size() - 1)
						.setRegularity(cell.getStringCellValue());
				break;
			case 5:
				mapExpenses.get(primaryKey).get(mapExpenses.get(primaryKey).size() - 1)
						.setEndDate(cell.getDateCellValue());
				break;
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

		return primaryKey;
	}

	public void readExcelFileXlsx(File file) {

		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(file);
			// Acceso a la primera hoja del documento
			XSSFSheet sheet = workbook.getSheetAt(0);
			List<String> data = new ArrayList<String>();

			// Recorremos las filas del documento
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				XSSFRow row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					XSSFCell cell = (XSSFCell) cells.next();
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						data.add(cell.getRichStringCellValue().getString());
					}
				}
			}
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
