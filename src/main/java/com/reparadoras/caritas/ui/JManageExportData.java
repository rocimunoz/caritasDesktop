package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

public class JManageExportData extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	static final Logger logger = Logger.getLogger(JManageExportData.class);

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

	public JManageExportData(JDesktopPane desktop) {
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

		getJButtonExportFile().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				saveToFile();
			}
		});
	}

	 protected void saveToFile() {
		    JFileChooser fileChooser = new JFileChooser();
		    int retval = fileChooser.showSaveDialog(getJButtonExportFile());
		    if (retval == JFileChooser.APPROVE_OPTION) {
		      File file = fileChooser.getSelectedFile();
		      if (file == null) {
		        return;
		      }
		      readBBDDAndGenerateExcel();
		    }
		  }

	public void createGUIComponents() {

		getContentPane().setLayout(getGridContentPane());
		// getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());

		// Añado elementos del JPanelFilter
		getJPanelFile().setLayout(getGridLayoutJPanelFile());
		getJPanelData().setLayout(getGridLayoutJPanelData());

		getJPanelFile().add(getJButtonExit(), getGridButtonExit());
		getJPanelFile().add(getJButtonExportFile(), getGridButtonImportFile());

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

	private JButton getJButtonExportFile() {
		if (btnImportFile == null) {
			btnImportFile = new JButton("Pincha para exportar");

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

	

	public void readBBDDAndGenerateExcel() {

		try {
			
			
			Map<String, Program> mapProgram = new HashMap<String, Program>();
			Map<String, Income> mapIncomes = new HashMap<String, Income>();
			Map<String, Expense> mapExpenses = new HashMap<String, Expense>();
			Map<String, List<Relative>> mapRelatives = new HashMap<String, List<Relative>>();

			List<Program> listPrograms = programDAO.findProgram(new People());
			for (Program program : listPrograms) {
				String dni = program.getPeople().getDni();
				if (dni!=null && !dni.equals("")){
					mapProgram.put(dni, program);
					
					//TODO: incomes
					//TODO: expesnes
					//TODO: RELATIVES
				}
			}
			
			generateExcel(mapProgram);

			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} catch (Exception e) {
			logger.error("Se ha producido un error en la exportacion de datos  " + e.getMessage());

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

	public void generateExcel(Map<String, Program> mapProgram){
		
		
	}
	
	public void extractDataSheet_0(Iterator itRows, Map<String, Program> mapProgram, Map<String, Income> mapIncomes,
			Map<String, Expense> mapExpenses) {

		while (itRows.hasNext()) {
			HSSFRow row = (HSSFRow) itRows.next();

			try {
				if (row.getRowNum() >= 2) {
					countTotal++;
					Iterator cells = row.cellIterator();
					String key = "";

					while (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
						key = extractDataRow_Sheet0(cell, mapProgram, mapIncomes, mapExpenses, key);
					}

					// Busco si ya existe

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
						if (mapIncomes.get(key) != null) {
							mapIncomes.get(key).setProgram(mapProgram.get(key));
							mapIncomes.get(key).setPeople(mapProgram.get(key).getPeople().getName());
							incomeDAO.insert(mapIncomes.get(key));
						}

						if (mapExpenses.get(key) != null) {
							mapExpenses.get(key).setProgram(mapProgram.get(key));
							expenseDAO.insert(mapExpenses.get(key));
						}

						textArea.append("Insertado registro: " + mapProgram.get(key).getPeople().getName() + "\n");
						countOK++;
					} else {
						countExist++;
						textArea.append("El registro " + mapProgram.get(key).getPeople().getName()
								+ " ya existe en BBDD. No se insertará \n");
						errorRegister.add(mapProgram.get(key).getPeople().getDni());
					}

				}
			} catch (Exception e) {
				logger.error("Se ha producido un error " + e.getMessage());
				textArea.append("Error insertando la fila " + row.getRowNum() + "\n");
				countKO++;
			}

		}
	}

	public void extractDataSheet_1(Iterator itRows, Map<String, List<Relative>> mapRelatives,
			Map<String, Program> mapProgram) {

		while (itRows.hasNext()) {
			HSSFRow row = (HSSFRow) itRows.next();

			try {
				if (row.getRowNum() >= 2) {

					Iterator cells = row.cellIterator();
					String key = "";

					while (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
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

							for (Relative relative : listRelatives) {
								relative.setFamily(mapProgram.get(dni).getFamily());
								relativeDAO.insert(relative);
							}
						}
					}

				}
			} catch (Exception e) {
				logger.error("Se ha producido un error " + e.getMessage());
				textArea.append("Error tratando familiares del dni:  " + dni + "\n");
			}

		});
	}

	public String extractDataRow_Sheet1(HSSFCell cell, Map<String, List<Relative>> mapRelatives, String key) {

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
		}

		return primaryKey;
	}

	public String extractDataRow_Sheet0(HSSFCell cell, Map<String, Program> mapProgram, Map<String, Income> mapIncomes,
			Map<String, Expense> mapExpenses, String key) {

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
				mapProgram.put(cell.getStringCellValue(), program);

				mapProgram.get(cell.getStringCellValue()).getPeople().setDni(cell.getStringCellValue());
				primaryKey = cell.getStringCellValue();

				break;
			case 1:
				mapProgram.get(key).getPeople().setPassport(cell.getStringCellValue());
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
				mapProgram.get(key).getFamily().getHome().getAddress()
						.setPostalCode(String.valueOf(cell.getNumericCellValue()));
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
				// mapProgram.get(key).getFamily().getHome().setHomeType(homeType);
				break;
			case 24:
				mapProgram.get(key).getFamily().getHome().setRegHolding(cell.getStringCellValue());
				break;
			case 25:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapProgram.get(key).getFamily().getHome()
							.setNumberRooms(Integer.parseInt(cell.getStringCellValue()));
				}

				break;
			case 26:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapProgram.get(key).getFamily().getHome()
							.setNumberPeople(Integer.parseInt(cell.getStringCellValue()));
				}

				break;
			case 27:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapProgram.get(key).getFamily().getHome()
							.setNumberFamilies(Integer.parseInt(cell.getStringCellValue()));
				}

				break;
			case 28:
				mapProgram.get(key).getFamily().getHome().setOtherInfo(cell.getStringCellValue());

				break;
			case 29:
				String nemonic = cell.getStringCellValue();
				FamilyType familyType = getFamilyType(nemonic);
				mapProgram.get(key).getFamily().setFamilyType(familyType);

				break;
			case 31:
				String nemonicAuthorization = cell.getStringCellValue();
				AuthorizationType authorizationType = getAuthorizationType(nemonicAuthorization);
				mapProgram.get(key).setAuthorizationType(authorizationType);

				break;
			case 32:
				String nemonicJobSituation = cell.getStringCellValue();
				JobSituation jobSituation = getJobSituation(nemonicJobSituation);
				mapProgram.get(key).setJobSituation(jobSituation);

				break;
			case 33:
				String nemonicStudies = cell.getStringCellValue();
				Studies studies = getStudies(nemonicStudies);
				mapProgram.get(key).setStudies(studies);
				break;
			case 34:
				if (mapIncomes.get(key) == null) {
					Income income = new Income();
					income.setConcept(cell.getStringCellValue());
					mapIncomes.put(key, income);
				} else {
					mapIncomes.get(key).setConcept(cell.getStringCellValue());
				}

				break;
			case 35:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapIncomes.get(key).setAmount(Double.parseDouble(cell.getStringCellValue()));
				}

				break;
			case 36:
				mapIncomes.get(key).setEndDate(cell.getDateCellValue());
				break;

			case 37:
				if (mapExpenses.get(key) == null) {
					Expense expense = new Expense();
					expense.setConcept(cell.getStringCellValue());
					mapExpenses.put(key, expense);
				} else {
					mapExpenses.get(key).setConcept(cell.getStringCellValue());
				}

				break;
			case 38:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue() != null && !cell.getStringCellValue().equals("")) {
					mapExpenses.get(key).setAmount(Double.parseDouble(cell.getStringCellValue()));
				}
				break;
			case 39:
				mapExpenses.get(key).setRegularity(cell.getStringCellValue());
				;
				break;
			case 40:
				mapExpenses.get(key).setEndDate(cell.getDateCellValue());
				break;

			}

		} catch (Exception e) {

			logger.error(e);
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

	public FamilyType getFamilyType(String nemonic) {
		FamilyType fType = new FamilyType();
		if (nemonic.equals("S")) {
			fType.setDescription("Sola");

		} else if (nemonic.equals("PCH")) {
			fType.setDescription("Pareja con Hijos");

		} else if (nemonic.equals("PSH")) {
			fType.setDescription("Pareja sin Hijos");
		} else if (nemonic.equals("M")) {
			fType.setDescription("Monoparental");
		} else if (nemonic.equals("O")) {
			fType.setDescription("Otra");
		} else
			fType.setDescription("Sola");

		return familyTypeDAO.findFamilyType(fType);

	}

	public AuthorizationType getAuthorizationType(String nemonic) {
		AuthorizationType aType = new AuthorizationType();
		if (nemonic.equals("AR")) {
			aType.setDescription("Autorización Residencia");

		} else if (nemonic.equals("ART")) {
			aType.setDescription("Autorización Residencia y Trabajo");

		} else if (nemonic.equals("E")) {
			aType.setDescription("Estudios");
		} else if (nemonic.equals("T")) {
			aType.setDescription("Turismo");
		} else if (nemonic.equals("R")) {
			aType.setDescription("Refugiado");
		} else
			aType.setDescription("Autorización Residencia");

		return authorizationTypeDAO.findAuthorizationType(aType);

	}

	public JobSituation getJobSituation(String nemonic) {
		JobSituation jType = new JobSituation();
		if (nemonic.equals("P")) {
			jType.setDescription("Parado");

		} else if (nemonic.equals("TN")) {
			jType.setDescription("Con Trabajo Normalizado");

		} else if (nemonic.equals("TM")) {
			jType.setDescription("Con Trabajo Marginal o Economia Sumergida");
		} else if (nemonic.equals("Ama de casa")) {
			jType.setDescription("Labores del hogar (ama de casa)");
		} else if (nemonic.equals("Pe")) {
			jType.setDescription("Pensionista o Jubilado");
		} else if (nemonic.equals("O")) {
			jType.setDescription("Otros inactivos (estudiantes, menores");
		} else
			jType.setDescription("Parado");

		return jobSituationDAO.findJobSituation(jType);

	}

	public Studies getStudies(String nemonic) {
		Studies sType = new Studies();
		if (nemonic.equals("NLE")) {
			sType.setDescription("No sabe leer ni escribir");

		} else if (nemonic.equals("SLE")) {
			sType.setDescription("Sólo sabe leer y escribir");

		} else if (nemonic.equals("I")) {
			sType.setDescription("Infanil");
		} else if (nemonic.equals("P")) {
			sType.setDescription("Primaria");
		} else if (nemonic.equals("S")) {
			sType.setDescription("Secundaria");
		} else if (nemonic.equals("B")) {
			sType.setDescription("Bachillerato");
		} else if (nemonic.equals("FP-GM")) {
			sType.setDescription("FP-Grado Medio");
		} else if (nemonic.equals("FP-GS")) {
			sType.setDescription("FP-Grado Superior");
		} else if (nemonic.equals("UL")) {
			sType.setDescription("Universidad Diplomado");
		}

		else
			sType.setDescription("No sabe leer ni escribir");

		return studiesDAO.findStudies(sType);

	}

}
