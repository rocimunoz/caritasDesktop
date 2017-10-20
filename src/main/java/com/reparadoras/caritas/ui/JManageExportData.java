package com.reparadoras.caritas.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
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
import com.reparadoras.caritas.filter.FilterProgram;
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
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;

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
			readBBDDAndGenerateExcel(file);
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

	public void readBBDDAndGenerateExcel(File file) {

		try {

			Map<String, Program> mapProgram = new HashMap<String, Program>();
			Map<String, List<Income>> mapIncomes = new HashMap<String, List<Income>>();
			Map<String, List<Expense>> mapExpenses = new HashMap<String, List<Expense>>();
			Map<String, List<Relative>> mapRelatives = new HashMap<String, List<Relative>>();

			List<Program> listPrograms = programDAO.findProgram(new FilterProgram());
			List<Relative> listRelatives = relativeDAO.findAllRelatives();
			for (Program program : listPrograms) {
				String dni = program.getPeople().getDni();
				if (dni != null && !dni.equals("")) {
					mapProgram.put(dni, program);

					// TODO: incomes
					// TODO: expesnes
					
				}
			}
			
			for (Relative relative : listRelatives) {
				Family family = relative.getFamily();
				FilterProgram filter = new FilterProgram();
				filter.setIdFamily(family.getId());
				List<Program> programs = programDAO.findProgram(filter);
				if (programs!=null && !programs.isEmpty() && programs.size()== 1){
					Program programRelatives = programs.get(0);
					String dni = programRelatives.getPeople().getDni();
					//TODO
					
					if (mapRelatives.get(dni)!=null){
						mapRelatives.get(dni).add(relative);
					}else{
						List<Relative> relatives = new ArrayList<>();
						relatives.add(relative);
						mapRelatives.put(dni, relatives);
					}
				}
			}
			XSSFWorkbook workbookCloned = cloneTemplateExcel(file);
			 generateSheetProgram(mapProgram, workbookCloned, file);
			generateSheetRelatives(mapRelatives, workbookCloned, file);
			//generateSheetIncomes(mapIncomes, workbookCloned, file);
			//generateSheetExpenses(mapExpenses, workbookCloned, file);

			FileOutputStream fileOut = new FileOutputStream(file);

			workbookCloned.write(fileOut);
			
			fileOut.flush();
			fileOut.close();

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

	public XSSFWorkbook cloneTemplateExcel(File destinationPath) {

		URL url = JManageExportData.class.getResource("/com/reparadoras/caritas/ui/utils/template.xlsx");
		File fTemplate = new File(url.getPath());
		FileInputStream excelFileTemplate;
		XSSFWorkbook workbook = null;
		try {
			excelFileTemplate = new FileInputStream(fTemplate);
			workbook = new XSSFWorkbook(excelFileTemplate);
			FileOutputStream outputStream = new FileOutputStream(destinationPath.getPath());
			workbook.write(outputStream);

			// workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return workbook;

	}

	public void generateSheetIncomes(Map<String, List<Income>> mapIncomes, XSSFWorkbook workbook,
			FileOutputStream file) {

	}

	public void generateSheetExpenses(Map<String, List<Expense>> mapExpenses, XSSFWorkbook workbook,
			FileOutputStream file) {

	}

	public void generateSheetRelatives(Map<String, List<Relative>> mapRelatives, XSSFWorkbook workbook,
			File file) {

		try{
			XSSFSheet sheet = workbook.getSheetAt(1);
			int rowNumber = 2;
			XSSFRow row = sheet.createRow(rowNumber);
			for (String key : mapRelatives.keySet()) {
				
				List<Relative> listRelatives = mapRelatives.get(key);
				for (Relative relative : listRelatives) {
					
					XSSFCell cell = row.createCell(0);
					cell.setCellValue(key);
					cell = row.createCell(1);
					cell.setCellValue("");
					cell = row.createCell(2);
					cell.setCellValue(relative.getRelationShip());
					cell = row.createCell(3);
					cell.setCellValue("");
					cell = row.createCell(4);
					cell.setCellValue(relative.getSurname());
					cell = row.createCell(5);
					cell.setCellValue(relative.getName());
					cell = row.createCell(6);
					cell.setCellValue(relative.getDateBorn());
					cell = row.createCell(7);
					cell.setCellValue("");
					cell = row.createCell(8);
					cell.setCellValue(relative.getSituation());
					
				row = sheet.createRow(++rowNumber);

					
					
				}
		}
			
			

		
		
			
		}catch(Exception e){
			// TODO Auto-generated catch block
						e.printStackTrace();
						
		}
	}

	public void generateSheetProgram(Map<String, Program> mapProgram, XSSFWorkbook workbook, File file) {

		try {
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowNumber = 2;
			XSSFRow row = sheet.createRow(rowNumber);

			for (String key : mapProgram.keySet()) {

				Program program = mapProgram.get(key);
				XSSFCell cell = row.createCell(0);
				cell.setCellValue(program.getPeople().getDni());
				cell = row.createCell(1);
				cell.setCellValue(program.getPeople().getPassport());
				cell = row.createCell(2);
				cell.setCellValue(program.getPeople().getCreateDate());
				cell = row.createCell(3);
				cell.setCellValue(program.getPeople().getReactivateDate());
				cell = row.createCell(4);
				if (program.getPeople().isActive() != null && program.getPeople().isActive()) {
					cell.setCellValue("X");
				}
				cell = row.createCell(5);
				cell.setCellValue(program.getPeople().getName());
				cell = row.createCell(6);
				cell.setCellValue(program.getPeople().getFirstSurname());
				cell = row.createCell(7);
				cell.setCellValue(program.getPeople().getSecondSurname());
				cell = row.createCell(8);
				cell.setCellValue(program.getPeople().getSex());
				cell = row.createCell(9);
				cell.setCellValue(program.getPeople().getDateBorn());
				cell = row.createCell(10);
				cell.setCellValue(program.getPeople().getCountry());
				cell = row.createCell(11);
				cell.setCellValue(program.getPeople().getNationality());
				cell = row.createCell(12);
				cell.setCellValue(program.getPeople().getCivilStatus());
				cell = row.createCell(15);
				if (program.getPeople().getYearToSpain() != null) {
					cell.setCellValue(program.getPeople().getYearToSpain());
				}

				cell = row.createCell(16);
				cell.setCellValue(program.getFamily().getHome().getAddress().getTown());
				
				cell = row.createCell(17);
				cell.setCellValue(program.getFamily().getHome().getAddress().getPostalCode());
				cell = row.createCell(18);
				cell.setCellValue(program.getFamily().getHome().getAddress().getStreet());
				cell = row.createCell(19);
				cell.setCellValue(program.getFamily().getHome().getAddress().getGate());
				cell = row.createCell(20);
				cell.setCellValue(program.getFamily().getHome().getAddress().getFloor());
				cell = row.createCell(21);
				cell.setCellValue(program.getFamily().getHome().getAddress().getTelephone());
				cell = row.createCell(22);
				cell.setCellValue(program.getFamily().getHome().getAddress().getTelephoneContact());
				// home type
				cell = row.createCell(23);
				cell.setCellValue("");
				cell = row.createCell(24);
				cell.setCellValue(program.getFamily().getHome().getRegHolding());
				cell = row.createCell(25);
				cell.setCellValue(program.getFamily().getHome().getNumberRooms());
				cell = row.createCell(26);
				cell.setCellValue(program.getFamily().getHome().getNumberPeople());
				cell = row.createCell(27);
				cell.setCellValue(program.getFamily().getHome().getNumberFamilies());
				cell = row.createCell(28);
				cell.setCellValue(program.getFamily().getHome().getOtherInfo());
				// Family Type
				cell = row.createCell(29);
				cell.setCellValue(getNemonicFamilyType(program.getFamily().getFamilyType())); //todo : nemonic
				cell = row.createCell(30);
				cell.setCellValue(program.getFamily().getOtherInfo());
				// Tipo Autorizacion
				cell = row.createCell(31);
				if (program.getAuthorizationType() != null) {
					cell.setCellValue(getNemonicAuthorizationType(program.getAuthorizationType()));
				}

				// Situacion Laboral
				cell = row.createCell(32);
				if (program.getJobSituation() != null) {
					cell.setCellValue(getNemonicJobSituation(program.getJobSituation()));
				}

				// Estudios
				cell = row.createCell(33);
				if (program.getStudies() != null) {
					cell.setCellValue(getNemonicStudies(program.getStudies()));
				}

				row = sheet.createRow(++rowNumber);

			}

			

			
			// fileOut.flush();
			// fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}

	/*
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
	}*/

	/*
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
	}*/

	/*
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

	}*/

	public String getNemonicFamilyType(FamilyType familyType) {
		try {
			if (familyType.getDescription().equals("Sola")) {
				return "S";
			} else if (familyType.getDescription().equals("Pareja con Hijos")) {
				return "PCH";
			} else if (familyType.getDescription().equals("Pareja sin Hijos")) {
				return "PSH";
			} else if (familyType.getDescription().equals("Monoparental")) {
				return "M";
			} else if (familyType.getDescription().equals("Otra")) {
				return "O";
			} else {
				return "S";
			}
		} catch (Exception e) {
			return "S";
		}

	}

	public String getNemonicAuthorizationType(AuthorizationType aType) {
		try {
			if (aType.getDescription().equals("Autorización Residencia")) {
				return "AR";
			} else if (aType.getDescription().equals("Autorización Residencia y Trabajo")) {
				return "ART";
			} else if (aType.getDescription().equals("Estudios")) {
				return "E";
			} else if (aType.getDescription().equals("Turismo")) {
				return "T";
			} else if (aType.getDescription().equals("Refugiado")) {
				return "R";
			} else {
				return "AR";
			}
		} catch (Exception e) {
			return "AR";
		}

	}

	public String getNemonicJobSituation(JobSituation jType) {

		try {
			if (jType.getDescription().equals("Parado")) {
				return "P";
			} else if (jType.getDescription().equals("Con Trabajo Normalizado")) {
				return "TN";
			} else if (jType.getDescription().equals("Con Trabajo Marginal o Economia Sumergida")) {
				return "TM";
			} else if (jType.getDescription().equals("Labores del hogar (ama de casa)")) {
				return "Ama de casa";
			} else if (jType.getDescription().equals("Pensionista o Jubilado")) {
				return "Pe";
			} else if (jType.getDescription().equals("Otros inactivos (estudiantes, menores")) {
				return "O";
			} else
				return "P";

		} catch (Exception e) {
			return "P";
		}
	}

	public String getNemonicStudies(Studies sType) {
		try {
			if (sType.getDescription().equals("No sabe leer ni escribir")) {
				return "NLE";
			} else if (sType.getDescription().equals("Sólo sabe leer y escribir")) {
				return "SLE";
			} else if (sType.getDescription().equals("Infanil")) {
				return "I";
			} else if (sType.getDescription().equals("Primaria")) {
				return "P";
			} else if (sType.getDescription().equals("Secundaria")) {
				return "S";
			} else if (sType.getDescription().equals("Bachillerato")) {
				return "B";
			} else if (sType.getDescription().equals("FP-Grado Medio")) {
				return "FP-GM";
			} else if (sType.getDescription().equals("FP-Grado Superior")) {
				return "FP-GS";
			} else if (sType.getDescription().equals("Universidad Diplomado")) {
				return "UL";
			} else
				return "NLE";
		} catch (Exception e) {
			return "NLE";
		}

	}

}
