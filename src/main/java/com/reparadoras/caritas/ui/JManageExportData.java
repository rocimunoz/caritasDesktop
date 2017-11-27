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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
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
import com.reparadoras.caritas.ui.utils.Constants;

public class JManageExportData extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	static final Logger logger = Logger.getLogger(JManageExportData.class);

	private JDesktopPane desktop = null;
	private JPanel jPanelFile = null;

	private JPanel jPanelContent = null;

	private JPanel jPanelTData = null;
	private JButton btnExit = null;
	private JButton btnImportFile = null;
	private JLabel lblInfo = null;

	private JTextArea textArea = null;
	private JScrollPane scroll = null;

	private ProgramDAO programDAO;

	private IncomesDAO incomeDAO;
	private ExpensesDAO expenseDAO;
	private RelativeDAO relativeDAO;
	private PeopleDAO peopleDAO;

	public int countOK = 0;
	public int countKO = 0;
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

		programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", "xls");
		fileChooser.setFileFilter(filter);
		int retval = fileChooser.showSaveDialog(getJButtonExportFile());
		if (retval == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file == null) {
				return;
			}

			if (!file.getAbsolutePath().endsWith(".xls")) {
				file = new File(fileChooser.getSelectedFile() + ".xls");
			}

			readBBDDAndGenerateExcel(file);
		}
	}

	public void createGUIComponents() {

		getContentPane().setLayout(getGridContentPane());

		// Añado elementos del JPanelFilter
		getJPanelFile().setLayout(getGridLayoutJPanelFile());
		getJPanelData().setLayout(getGridLayoutJPanelData());

		getJPanelFile().add(getJButtonExit(), getGridButtonExit());
		getJPanelFile().add(getJButtonExportFile(), getGridButtonImportFile());
		getJPanelFile().add(this.getJLabelInfo(), this.getGridLabelInfo());

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
			jPanelTData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Resultado Exportación",
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

			btnExit.setIcon(new ImageIcon(JManageProgram.class.getResource("/img/icon-exit.png")));
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
			btnImportFile = new JButton("Exportar");

			btnImportFile.setHorizontalAlignment(SwingConstants.RIGHT);
		}

		return btnImportFile;
	}

	private GridBagConstraints getGridLabelInfo() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.gridx = 1;
		gbc_btnExit.gridy = 0;

		return gbc_btnExit;
	}

	private JLabel getJLabelInfo() {

		if (lblInfo == null) {
			lblInfo = new JLabel("Seleccione una ruta  para guardar la exportacion: ");
		}

		return lblInfo;
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

			mapProgram = fillMapProgram();

			mapRelatives = fillMapRelatives();

			mapIncomes = fillMapIncomes();

			mapExpenses = fillMapExpenses();

			HSSFWorkbook workbookCloned = cloneTemplateExcel(file);
			generateSheetProgram(mapProgram, workbookCloned, file);
			generateSheetRelatives(mapRelatives, workbookCloned, file);
			generateSheetIncomes(mapIncomes, workbookCloned, file);
			generateSheetExpenses(mapExpenses, workbookCloned, file);

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
		textArea.append("Registros exportados correctamente: " + countOK + "\n");
		textArea.append("Registros no exportados por errores : " + countKO + "\n");
		textArea.append("Ruta destino del fichero exportado : " + file.getAbsolutePath() + "\n");

	}

	public Map<String, Program> fillMapProgram() {

		List<Program> listPrograms = programDAO.findProgram(new FilterProgram());
		Map<String, Program> mapProgram = new HashMap<String, Program>();
		for (Program program : listPrograms) {
			String dni = program.getPeople().getDni();
			String passport = program.getPeople().getPassport();
			if (dni != null && !dni.equals("")) {
				mapProgram.put(dni, program);

			} else if (passport != null && !passport.equals("")) {
				mapProgram.put(passport, program);
			} else {
				logger.warn("Registro no exportable.");
				
			}
		}

		return mapProgram;
	}

	public Map<String, List<Relative>> fillMapRelatives() {

		List<Relative> listRelatives = relativeDAO.findAllRelatives();
		Map<String, List<Relative>> mapRelatives = new HashMap<String, List<Relative>>();

		for (Relative relative : listRelatives) {
			Family family = relative.getFamily();
			FilterProgram filter = new FilterProgram();
			filter.setIdFamily(family.getId());
			List<Program> programs = programDAO.findProgram(filter);
			if (programs != null && !programs.isEmpty() && programs.size() == 1) {
				Program programRelatives = programs.get(0);
				String dni = programRelatives.getPeople().getDni();
				String passport = programRelatives.getPeople().getPassport();
				String key ="";
				if (dni!=null && !dni.equals("")){
					key = dni;
				}else{
					key = passport;
				}
				if (mapRelatives.get(key) != null) {
					mapRelatives.get(key).add(relative);
				} else {
					List<Relative> relatives = new ArrayList<>();
					relatives.add(relative);
					mapRelatives.put(key, relatives);
				}
			}
		}
		return mapRelatives;
	}

	public Map<String, List<Income>> fillMapIncomes() {

		List<Income> listIncomes = incomeDAO.findAllIncomes();
		Map<String, List<Income>> mapIncomes = new HashMap<String, List<Income>>();

		for (Income income : listIncomes) {
			String dni = income.getProgram().getPeople().getDni();
			String passport = income.getProgram().getPeople().getPassport();
			String key ="";
			if (dni!=null && !dni.equals("")){
				key = dni;
			}else{
				key = passport;
			}
			if (mapIncomes.get(key) != null) {
				mapIncomes.get(key).add(income);
			} else {
				List<Income> incomes = new ArrayList<>();
				incomes.add(income);
				mapIncomes.put(key, incomes);
			}

		}
		return mapIncomes;
	}

	public Map<String, List<Expense>> fillMapExpenses() {
		List<Expense> listExpenses = expenseDAO.findAllExpenses();

		Map<String, List<Expense>> mapExpenses = new HashMap<String, List<Expense>>();

		for (Expense expense : listExpenses) {
			String dni = expense.getProgram().getPeople().getDni();
			String passport = expense.getProgram().getPeople().getPassport();
			String key ="";
			if (dni!=null && !dni.equals("")){
				key = dni;
			}else{
				key = passport;
			}
			if (mapExpenses.get(key) != null) {
				mapExpenses.get(key).add(expense);
			} else {
				List<Expense> expenses = new ArrayList<>();
				expenses.add(expense);
				mapExpenses.put(key, expenses);
			}

		}
		return mapExpenses;
	}

	public HSSFWorkbook cloneTemplateExcel(File destinationPath) {

		URL url = JManageExportData.class.getResource("/com/reparadoras/caritas/ui/utils/template.xls");
		File fTemplate = new File(url.getPath());
		FileInputStream excelFileTemplate;
		HSSFWorkbook workbook = null;
		try {
			excelFileTemplate = new FileInputStream(fTemplate);
			workbook = new HSSFWorkbook(excelFileTemplate);
			FileOutputStream outputStream = new FileOutputStream(destinationPath.getPath());
			workbook.write(outputStream);

			// workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Se ha producido cloneTemplateExcel  " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Se ha producido cloneTemplateExcel  " + e.getMessage());
		}

		return workbook;

	}

	public void generateSheetIncomes(Map<String, List<Income>> mapIncomes, HSSFWorkbook workbook, File file) {

		try {
			HSSFSheet sheet = workbook.getSheetAt(2);
			int rowNumber = 2;
			HSSFRow row = sheet.createRow(rowNumber);
			for (String key : mapIncomes.keySet()) {

				People filter =new People();
				filter.setDni(key);
				List<People> test =peopleDAO.findPeople(filter);
				String dni ="";
				String passport ="";
				if (test!=null && !test.isEmpty()){
					dni =key;
				}else{
					passport=key;
				}
				
				List<Income> listIncomes = mapIncomes.get(key);
				for (Income income : listIncomes) {

					HSSFCell cell = row.createCell(0);
					cell.setCellValue(dni);
					cell = row.createCell(1);
					cell.setCellValue(passport);
					cell = row.createCell(2);
					cell.setCellValue(income.getConcept());
					cell = row.createCell(3);
					cell.setCellValue(income.getAmount());
					cell = row.createCell(4);
					cell.setCellStyle(getCellStyleDate(workbook));
					if (income.getEndDate() != null) {
						cell.setCellValue(income.getEndDate());
					}

					row = sheet.createRow(++rowNumber);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Se ha producido generateSheetIncomes  " + e.getMessage());

		}
	}

	public void generateSheetExpenses(Map<String, List<Expense>> mapExpenses, HSSFWorkbook workbook, File file) {

		try {
			HSSFSheet sheet = workbook.getSheetAt(3);
			int rowNumber = 2;
			HSSFRow row = sheet.createRow(rowNumber);
			for (String key : mapExpenses.keySet()) {

				People filter =new People();
				filter.setDni(key);
				List<People> test =peopleDAO.findPeople(filter);
				String dni ="";
				String passport ="";
				if (test!=null && !test.isEmpty()){
					dni =key;
				}else{
					passport=key;
				}
				
				List<Expense> listExpense = mapExpenses.get(key);
				for (Expense expense : listExpense) {

					HSSFCell cell = row.createCell(0);
					cell.setCellValue(dni);
					cell = row.createCell(1);
					cell.setCellValue(passport);
					cell = row.createCell(2);
					cell.setCellValue(expense.getConcept());
					cell = row.createCell(3);
					cell.setCellValue(expense.getAmount());
					cell = row.createCell(4);
					cell.setCellValue(expense.getRegularity());
					cell = row.createCell(5);
					cell.setCellStyle(getCellStyleDate(workbook));
					if (expense.getEndDate() != null) {
						cell.setCellValue(expense.getEndDate());
					}

					row = sheet.createRow(++rowNumber);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Se ha producido generateSheetExpenses  " + e.getMessage());

		}
	}

	public void generateSheetRelatives(Map<String, List<Relative>> mapRelatives, HSSFWorkbook workbook, File file) {

		try {
			HSSFSheet sheet = workbook.getSheetAt(1);
			int rowNumber = 2;
			HSSFRow row = sheet.createRow(rowNumber);
			for (String key : mapRelatives.keySet()) {

				People filter =new People();
				filter.setDni(key);
				List<People> test =peopleDAO.findPeople(filter);
				String dni ="";
				String passport ="";
				if (test!=null && !test.isEmpty()){
					dni =key;
				}else{
					passport=key;
				}
				
				List<Relative> listRelatives = mapRelatives.get(key);
				for (Relative relative : listRelatives) {

					
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(dni);
					cell = row.createCell(1);
					cell.setCellValue(passport);
					cell = row.createCell(2);
					cell.setCellValue(relative.getRelationShip());
					cell = row.createCell(3);
					cell.setCellValue("");
					cell = row.createCell(4);
					cell.setCellValue(relative.getSurname());
					cell = row.createCell(5);
					cell.setCellValue(relative.getName());
					cell = row.createCell(6);
					cell.setCellStyle(getCellStyleDate(workbook));
					if (relative.getDateBorn() != null) {
						cell.setCellValue(relative.getDateBorn());
					}

					cell = row.createCell(7);
					cell.setCellValue("");
					cell = row.createCell(8);
					cell.setCellValue(relative.getSituation());

					row = sheet.createRow(++rowNumber);

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Se ha producido generateSheetRelatives  " + e.getMessage());

		}
	}

	public void generateSheetProgram(Map<String, Program> mapProgram, HSSFWorkbook workbook, File file) {

		try {
			countTotal = mapProgram.size();
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowNumber = 2;
			HSSFRow row = sheet.createRow(rowNumber);

			for (String key : mapProgram.keySet()) {

				Program program = mapProgram.get(key);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(program.getPeople().getDni());
				cell = row.createCell(1);
				cell.setCellValue(program.getPeople().getPassport());
				cell = row.createCell(2);
				cell.setCellStyle(getCellStyleDate(workbook));
				if (program.getPeople().getCreateDate()!=null){
					cell.setCellValue(program.getPeople().getCreateDate());
				}
			

				cell = row.createCell(3);
				cell.setCellStyle(getCellStyleDate(workbook));
				if (program.getPeople().getReactivateDate()!=null){
					cell.setCellValue(program.getPeople().getReactivateDate());
				}
				
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
				cell.setCellStyle(getCellStyleDate(workbook));
				if (program.getPeople().getDateBorn()!=null){
					cell.setCellValue(program.getPeople().getDateBorn());
				}
				
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
				// home type //TODO
				cell = row.createCell(23);
				if (program.getFamily().getHome().getHomeType() != null) {
					cell.setCellValue(program.getFamily().getHome().getHomeType().getDescription());
				}

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
				cell.setCellValue(Constants.getNemonicFamilyType(program.getFamily().getFamilyType()));
				cell = row.createCell(30);
				cell.setCellValue(program.getFamily().getOtherInfo());
				// Tipo Autorizacion
				cell = row.createCell(31);
				if (program.getAuthorizationType() != null) {
					cell.setCellValue(Constants.getNemonicAuthorizationType(program.getAuthorizationType()));
				}

				// Situacion Laboral
				cell = row.createCell(32);
				if (program.getJobSituation() != null) {
					cell.setCellValue(Constants.getNemonicJobSituation(program.getJobSituation()));
				}

				// Estudios
				cell = row.createCell(33);
				if (program.getStudies() != null) {
					cell.setCellValue(Constants.getNemonicStudies(program.getStudies()));
				}
				if (program.getOtherInfo() != null) {
					cell = row.createCell(34);
					cell.setCellValue(program.getOtherInfo().getInstitutions());
					cell = row.createCell(35);
					cell.setCellValue(program.getOtherInfo().getDemand());
					cell = row.createCell(36);
					cell.setCellValue("");
					cell = row.createCell(37);
					cell.setCellValue("");
					cell = row.createCell(38);
					cell.setCellValue("");
					cell = row.createCell(39);
					cell.setCellValue("");
					cell = row.createCell(40);
					cell.setCellValue(program.getOtherInfo().getActuations());
				}

				row = sheet.createRow(++rowNumber);
				countOK++;

			}

			// fileOut.flush();
			// fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Se ha producido generateSheetProgram  " + e.getMessage());
			countKO++;

		}

	}

	public CellStyle getCellStyleDate(HSSFWorkbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

		return cellStyle;
	}

}
