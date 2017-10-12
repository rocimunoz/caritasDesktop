package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
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
import com.reparadoras.caritas.ui.JMainWindow.Task_BackupBBDD;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

@SuppressWarnings("serial")

public class JManageBackup extends AbstractJInternalFrame {

	static final Logger logger = Logger.getLogger(JManageBackup.class);

	private JDesktopPane desktop = null;
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
	
	//private StringBuffer sbuffer = new StringBuffer();

	private JFileChooser jfc = null;
	private JFrame frame = null;
	private JPanel panel = null;
	private JTextArea textArea = null;
	private JScrollPane scroll = null;
	private JLabel label = null;
	private JButton button = null;

	private People selectedPeople;

	public JManageBackup(JDesktopPane desktop) {

		super(desktop);
		this.desktop = desktop;
		this.setTitle("Gestion Personas");
		this.setVisible(true);

		this.pack();
		this.setResizable(false);
		this.setClosable(true);

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

		// getContentPane().setLayout(getGridContentPane());
		getContentPane().add(getFileChooser());

		// int returnValue = getFileChooser().showSaveDialog(null);
		int returnValue = getFileChooser().showDialog(null, "Selecciona fichero");
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			
			importData(jfc.getSelectedFile());
			
		}

	}

	
	
	public void importData(File file) {

		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Se esta realizando la copia de los datos, por favor espere unos instantes ... ");
        textArea = new JTextArea();
        textArea = new JTextArea(16, 58);
        textArea.setEditable(false); // set textArea non-editable
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        button= new JButton("Aceptar");
        
        
        button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				
			}
		});
      
       
       
        
        panel.setLayout(getGridContentPane());
        
        GridBagConstraints gbc_jPanelLabel = new GridBagConstraints();
        gbc_jPanelLabel.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelLabel.weightx = 1.0;
        gbc_jPanelLabel.weighty = 1.0;
        gbc_jPanelLabel.fill = GridBagConstraints.NONE;
        gbc_jPanelLabel.gridx = 0;
        gbc_jPanelLabel.gridy = 0;
        
        GridBagConstraints gbc_jPanelTextArea = new GridBagConstraints();
        gbc_jPanelTextArea.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelTextArea.weightx = 1.0;
        gbc_jPanelTextArea.weighty = 1.0;
        gbc_jPanelTextArea.fill = GridBagConstraints.BOTH;
        gbc_jPanelTextArea.gridx = 0;
        gbc_jPanelTextArea.gridy = 1;
        
        GridBagConstraints gbc_jPanelButton = new GridBagConstraints();
        gbc_jPanelButton.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelButton.weightx = 1.0;
        gbc_jPanelButton.weighty = 1.0;
        gbc_jPanelButton.fill = GridBagConstraints.NONE;
        gbc_jPanelButton.gridx = 0;
        gbc_jPanelButton.gridy = 2;
        
        panel.add(label, gbc_jPanelLabel);
        panel.add(scroll, gbc_jPanelTextArea);
        panel.add(button, gbc_jPanelButton);
        
        frame.add(panel);
        frame.pack();
        frame.setSize(800,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
        
        String extension = getFileExtension(jfc.getSelectedFile());
		
		if (extension!=null && (extension.equals("xls") || extension.equals("xlsx"))){
			// new Task_BackupBBDD(jfc.getSelectedFile()).execute();
			
			readExcelFileXls(jfc.getSelectedFile());
		}
		else{
			textArea.append("Lo siento, no es posible importar datos. El fichero " + jfc.getSelectedFile().getName() + " no es un fichero valido");
		}
        
       
        
	}
	
	/*
	  class Task_BackupBBDD extends SwingWorker<Void, String> {

	        File file;
	        public Task_BackupBBDD(File file) {
	            this.file = file;
	        }

	        @Override
	        protected void process(List<String> chunks) {
	        	readExcelFileXls(this.file);
	        }

	        @Override
	        protected Void doInBackground() throws Exception {

	            publish("Cargando fichero .....");
	            Thread.sleep(1000);
	          

	            return null;
	        }

	        @Override
	        protected void done() {
	            try {
	                get();
	                //JOptionPane.showMessageDialog(file.get, "Copia de seguridad realizada", "Success", JOptionPane.INFORMATION_MESSAGE);
	                
	                
	                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	            } catch (ExecutionException | InterruptedException e) {
	                e.printStackTrace();
	            }
	            
	         
	        }
	    }*/

	public  void readExcelFileXls(File file) {

		int countOK = 0;
		int countKO = 0;
		int countExist = 0;
		int countTotal = 0;
		try {
			//this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			InputStream targetStream = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(targetStream);

			// Acceso a la primera hoja del documento
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<String> data = new ArrayList<String>();
			Map<String, Program> mapProgram = new HashMap<String, Program>();
			Map<String, Income> mapIncomes = new HashMap<String, Income>();
			Map<String, Expense> mapExpenses = new HashMap<String, Expense>();

			// Recorremos las filas del documento
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				
				try{
					if (row.getRowNum() >= 2) {
						countTotal++;
						Iterator cells = row.cellIterator();
						String key = "";

						

						while (cells.hasNext()) {
							HSSFCell cell = (HSSFCell) cells.next();
							key = extractDataRow(cell, mapProgram, mapIncomes, mapExpenses, key);

						}

						//Busco si ya existe 
						
						List<People> listPeopleExist = peopleDAO.findPeople(mapProgram.get(key).getPeople());
						if (listPeopleExist!=null && listPeopleExist.isEmpty()){
							addressDAO.insert(mapProgram.get(key).getFamily().getHome().getAddress());
							homeDAO.insert(mapProgram.get(key).getFamily().getHome());
							familyDAO.insert(mapProgram.get(key).getFamily());
							peopleDAO.insert(mapProgram.get(key).getPeople());
							programDAO.insertExcel(mapProgram.get(key));
							if (mapIncomes.get(key)!=null){
								mapIncomes.get(key).setProgram(mapProgram.get(key));
								mapIncomes.get(key).setPeople(mapProgram.get(key).getPeople().getName());
								incomeDAO.insert(mapIncomes.get(key));
							}
							
							
							textArea.append("Insertado registro: " + mapProgram.get(key).getPeople().getName() + "\n");
							countOK++;
						}
						else{
							countExist++;
							textArea.append("El registro "  + mapProgram.get(key).getPeople().getName() +  " ya existe en BBDD. No se insertará \n");
						}
						
						
						
						
						
					}
				}
				catch(Exception e){
					logger.error("Se ha producido un error " + e.getMessage());
					textArea.append("Error insertando la fila " + row.getRowNum() + "\n");
					countKO++;
				}
				

			}
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} catch (Exception e) {
			logger.error("Se ha producido un error en la importacion de datos  " + e.getMessage());
			
		}
		
		textArea.append("\n");
		textArea.append("******************: " + "\n");
		textArea.append("Resumen: " + "\n");
		textArea.append("******************: " + "\n");
		textArea.append("Registros totales leidos: " + countTotal+ "\n");
		textArea.append("Registros insertados correctamente: " + countOK+ "\n");
		textArea.append("Registros no insertados por errores : " + countKO+ "\n");
		textArea.append("Registros no insertados por existir ya en Base de Datos : " + countExist+ "\n");
		
		
		
		

	}

	public  String extractDataRow(HSSFCell cell, Map<String, Program> mapProgram, Map<String, Income> mapIncomes, Map<String, Expense> mapExpenses, String key) {

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
				/*
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					mapProgram.get(key).getFamily().getHome().getAddress().setGate(cell.getStringCellValue());
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					mapProgram.get(key).getFamily().getHome().getAddress()
							.setGate(String.valueOf(cell.getNumericCellValue()));
				}*/

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
				mapProgram.get(key).getFamily().getHome().setNumberRooms(Integer.parseInt(cell.getStringCellValue()));
				break;
			case 26:
				mapProgram.get(key).getFamily().getHome().setNumberPeople(Integer.parseInt(cell.getStringCellValue()));
				break;
			case 27:
				mapProgram.get(key).getFamily().getHome()
						.setNumberFamilies(Integer.parseInt(cell.getStringCellValue()));
				break;
			case 28:
				mapProgram.get(key).getFamily().getHome().setOtherInfo(cell.getStringCellValue());

				break;
			case 64:
				String nemonic = cell.getStringCellValue();
				FamilyType familyType = getFamilyType(nemonic);
				mapProgram.get(key).getFamily().setFamilyType(familyType);

				break;
			case 66:
				String nemonicAuthorization = cell.getStringCellValue();
				AuthorizationType authorizationType = getAuthorizationType(nemonicAuthorization);
				mapProgram.get(key).setAuthorizationType(authorizationType);

				break;
			case 67:
				String nemonicJobSituation = cell.getStringCellValue();
				JobSituation jobSituation = getJobSituation(nemonicJobSituation);
				mapProgram.get(key).setJobSituation(jobSituation);

				break;
			case 68:
				String nemonicStudies = cell.getStringCellValue();
				Studies studies = getStudies(nemonicStudies);
				mapProgram.get(key).setStudies(studies);
				break;
			case 69:
				if (mapIncomes.get(key) == null){
					Income income = new Income();
					income.setConcept(cell.getStringCellValue());
					mapIncomes.put(key, income);	
				}else{
					mapIncomes.get(key).setConcept(cell.getStringCellValue());
				}
				
				break;
			case 70:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
					mapIncomes.get(key).setAmount(Integer.parseInt(cell.getStringCellValue()));
				}
				
				break;
			case 71:
				mapIncomes.get(key).setEndDate(cell.getDateCellValue());
				break;
		
		}

		} catch (Exception e) {

			logger.error(e);
		}

		return primaryKey;

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
		}else if (nemonic.equals("FP-GM")) {
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
	
	private String getFileExtension(File file){
		
		String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return null;
	    }
	}
	

	private GridBagLayout getGridContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };

		return gridBagLayout;
	}

}
