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
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
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
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
	private ProgramDAO programDAO;
	private HomeDAO homeDAO;
	private AddressDAO addressDAO;
	

	private JFileChooser jfc = null;

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
		
		homeDAO = new HomeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		addressDAO = new AddressDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		// getContentPane().setLayout(getGridContentPane());
		getContentPane().add(getFileChooser());

		// int returnValue = getFileChooser().showSaveDialog(null);
		int returnValue = getFileChooser().showDialog(null, "Selecciona fichero");
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			
			importData(jfc.getSelectedFile());

		}

	}

	public void importData(File file) {

		
			readExcelFileXls(jfc.getSelectedFile());

		 
	}

	public void readExcelFileXls(File file)  {

		try{
			InputStream targetStream = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(targetStream);

			// Acceso a la primera hoja del documento
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<String> data = new ArrayList<String>();
			Map<String, Program> mapProgram = new HashMap<String, Program>();

			// Recorremos las filas del documento
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();
				if (row.getRowNum() >= 2) {

					Iterator cells = row.cellIterator();
					String key = "";

					Program program = new Program();
					People people = new People();
					Family family = new Family();
					Home home = new Home();
					Address address = new Address();
					home.setAddress(address);
					family.setHome(home);

					program.setPeople(people);
					program.setFamily(family);

					while (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
						key = extractDataRow(cell, mapProgram, key, program);
											
					}
					
					addressDAO.insert(address);
					homeDAO.insert(home);
					familyDAO.insert(family);
					peopleDAO.insert(people);
					programDAO.insert(program);
				}

			}
		}
		catch(Exception e){
			logger.error("Se ha producido un error en la importacion de datos  " + e.getMessage());
		}
	
	}

	public String extractDataRow(HSSFCell cell, Map<String, Program> mapProgram, String key, Program program) {

		String primaryKey = key;
		try{
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
				
				if (cell.getDateCellValue()!=null) {
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
				mapProgram.get(key).getFamily().getHome().getAddress().setPostalCode(String.valueOf(cell.getNumericCellValue()));
				break;
			case 18:
				mapProgram.get(key).getFamily().getHome().getAddress().setStreet(cell.getStringCellValue());
				break;
			case 19:
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					mapProgram.get(key).getFamily().getHome().getAddress().setGate(cell.getStringCellValue());
				}else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					mapProgram.get(key).getFamily().getHome().getAddress().setGate(String.valueOf(cell.getNumericCellValue()));
				}
				
				break;
			case 20:
				mapProgram.get(key).getFamily().getHome().getAddress().setFloor(cell.getStringCellValue());
				break;
			case 21:
				mapProgram.get(key).getFamily().getHome().getAddress().setTelephone(String.valueOf(cell.getNumericCellValue()));
				break;
			case 22:
				mapProgram.get(key).getFamily().getHome().getAddress().setTelephoneContact(String.valueOf(cell.getNumericCellValue()));
				break;
			case 23:
				// mapProgram.get(key).getFamily().getHome().setHomeType(homeType);
				break;
			case 24:
				mapProgram.get(key).getFamily().getHome().setRegHolding(cell.getStringCellValue());
				break;
			case 25:
					mapProgram.get(key).getFamily().getHome().setNumberRooms(new Double(cell.getNumericCellValue()).intValue());
				break;
			case 26:
					mapProgram.get(key).getFamily().getHome().setNumberPeople(new Double(cell.getNumericCellValue()).intValue());
				break;
			case 27:
					mapProgram.get(key).getFamily().getHome().setNumberFamilies(new Double(cell.getNumericCellValue()).intValue());
				break;
			case 28:
				mapProgram.get(key).getFamily().getHome().setOtherInfo(cell.getStringCellValue());

				break;
			}
		}
		catch(Exception e){
		
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

	private GridBagLayout getGridContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };

		return gridBagLayout;
	}

}
