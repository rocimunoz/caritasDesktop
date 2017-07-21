package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

@SuppressWarnings("serial")

public class JImportPeople extends JFrame {
	
	
	private PeopleDAO peopleDAO;
	
	private JFileChooser jfc = null;
	
	private People selectedPeople;

	public JImportPeople() {
		
				
		//getContentPane().setLayout(getGridContentPane());
		getContentPane().add(getFileChooser());
		
		int returnValue = getFileChooser().showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (getFileChooser().getSelectedFile().isDirectory()) {
				System.out.println("Has seleccionado : " + jfc.getSelectedFile());
				readExcelFile(jfc.getSelectedFile());
				
			}
		}
		
	}
	
	public void readExcelFile(File file){
		
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(file);
			//Acceso a la primera hoja del documento
			XSSFSheet sheet = workbook.getSheetAt(0);
			List<String> data = new ArrayList<String>();
			 
			//Recorremos las filas del documento
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
	
	private JFileChooser getFileChooser(){
		if (jfc == null){
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS files", "xls");
			FileNameExtensionFilter filter2 = new FileNameExtensionFilter("XLSX files", "xlsx");

			jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Choose a directory to save your file: ");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
