package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

import com.reparadoras.caritas.dao.AddressDAO;
import com.reparadoras.caritas.dao.AuthorizationTypeDAO;
import com.reparadoras.caritas.dao.ExpensesDAO;
import com.reparadoras.caritas.dao.FamilyDAO;
import com.reparadoras.caritas.dao.FamilyTypeDAO;
import com.reparadoras.caritas.dao.HomeDAO;
import com.reparadoras.caritas.dao.HomeTypeDAO;
import com.reparadoras.caritas.dao.IncomesDAO;
import com.reparadoras.caritas.dao.JobSituationDAO;
import com.reparadoras.caritas.dao.OtherInfoDAO;
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
import com.reparadoras.caritas.model.OtherInfo;
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
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;

public class JManageImportData extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	static final Logger logger = Logger.getLogger(JManageImportData.class);

	private JDesktopPane desktop = null;
	private JPanel jPanelFile = null;

	private JPanel jPanelContent = null;

	private JPanel jPanelTData = null;
	private JButton btnExit = null;
	//private JButton btnImportFile = null;
	private JLabel lblUploadTemplate = null;
	private JFileChooser jfc = null;
	private JTextArea textArea = null;
	private JScrollPane scroll = null;
	private JLabel lblDownloadTemplate = null;

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
	private OtherInfoDAO otherInfoDAO;
	private IncomesDAO incomeDAO;
	private ExpensesDAO expenseDAO;
	private RelativeDAO relativeDAO;

	

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

		otherInfoDAO = new OtherInfoDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		incomeDAO = new IncomesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		expenseDAO = new ExpensesDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		relativeDAO = new RelativeDAO(MyBatisConnectionFactory.getSqlSessionFactory());

	}

	public void initComponents() {

	}

	public void addListener() {
		
	
		
		getJLabelUpload().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int returnValue = getFileChooser().showDialog(null, "Selecciona fichero");
				if (returnValue == JFileChooser.APPROVE_OPTION) {

					boolean state = importData(jfc.getSelectedFile());
					
					if (state) {
						textArea.append("\n");
						textArea.append("******************: " + "\n");
						textArea.append("Carga Copia Seguridad: " + "\n");
						textArea.append("******************: " + "\n");
						textArea.append(". \n");
						textArea.append("Se ha cargado correctamente la copia de seguridad");	
					}else {
						textArea.append("Carga Copia Seguridad: " + "\n");
						textArea.append("******************: " + "\n");
						textArea.append(". \n");
						textArea.append("No se ha podido cargar la copia de seguridad seleccionada");	
					}
					

				}
			}
		});
		getJLabelUpload().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				getJLabelUpload().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		

		this.getJButtonExit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();

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

		
		getJPanelFile().add(getJLabelDownload(), getGridJLabelDownload());
		getJPanelFile().add(getJButtonExit(), getGridButtonExit());
		getJPanelFile().add(getJLabelUpload(), getGridJLabelUpload());

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

	private JLabel getJLabelUpload() {
		if (lblUploadTemplate == null) {
			lblUploadTemplate = new JLabel("Carga datos");
			lblUploadTemplate.setMaximumSize(new Dimension(180, 14));
			lblUploadTemplate.setPreferredSize(new Dimension(180, 14));
			lblUploadTemplate.setBorder(new LineBorder(Color.RED, 1, true));
			lblUploadTemplate.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblUploadTemplate.setForeground(Color.RED);

			lblUploadTemplate.setHorizontalAlignment(SwingConstants.CENTER);
		}

		return lblUploadTemplate;
	}

	private GridBagConstraints getGridJLabelUpload() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.ipady = 20;
		gbc_btnExit.ipadx = 20;
		gbc_btnExit.insets = new Insets(10, 10, 10, 25);
		gbc_btnExit.gridx = 2;
		gbc_btnExit.gridy = 0;

		return gbc_btnExit;
	}
	
	private JLabel getJLabelDownload() {
		if (lblDownloadTemplate == null) {
			lblDownloadTemplate = new JLabel("Descarga Plantilla.xls");
			lblDownloadTemplate.setMaximumSize(new Dimension(180, 14));
			lblDownloadTemplate.setPreferredSize(new Dimension(180, 14));
			lblDownloadTemplate.setBorder(new LineBorder(Color.RED, 1, true));
			lblDownloadTemplate.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblDownloadTemplate.setForeground(Color.RED);

			lblDownloadTemplate.setHorizontalAlignment(SwingConstants.CENTER);
		}

		return lblDownloadTemplate;
	}

	private GridBagConstraints getGridJLabelDownload() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.ipady = 20;
		gbc_btnExit.ipadx = 20;
		gbc_btnExit.insets = new Insets(10, 10, 10, 25);
		gbc_btnExit.gridx = 1;
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

	public boolean importData(File file) {
		
		boolean state = false;
		String extension = getFileExtension(jfc.getSelectedFile());

		if (extension != null && (extension.equals("sql") || extension.equals("SQL"))) {
			try {
				String command = Constants.MYSQL_LOAD_PATH + " mysql -u" + Constants.MYSQL_USER +  "-password"  + Constants.MYSQL_PASS  + file.getAbsolutePath();
				Runtime.getRuntime().exec(command);	
				state = true;
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}else {
			state = false;
		} 
		
		return state;

	}


}
