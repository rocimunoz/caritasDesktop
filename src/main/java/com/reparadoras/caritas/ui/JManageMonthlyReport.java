package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.DocumentException;
import com.reparadoras.caritas.dao.AddressDAO;
import com.reparadoras.caritas.dao.AnswerDAO;
import com.reparadoras.caritas.dao.ExpensesDAO;
import com.reparadoras.caritas.dao.FamilyDAO;
import com.reparadoras.caritas.dao.HomeDAO;
import com.reparadoras.caritas.dao.IncomesDAO;
import com.reparadoras.caritas.dao.OtherInfoDAO;
import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.RelativeDAO;

import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.filter.FilterAnswer;
import com.reparadoras.caritas.filter.FilterProgram;

import com.reparadoras.caritas.filter.FilterTicket;
import com.reparadoras.caritas.model.Answer;
import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.JobSituation;
import com.reparadoras.caritas.model.MonthlyReport;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.model.Studies;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.combobox.ComboBoxRenderer;
import com.reparadoras.caritas.ui.components.table.PeopleTableModel;
import com.reparadoras.caritas.ui.utils.Constants;
import com.reparadoras.caritas.ui.utils.pdf.PdfExporter;
import com.reparadoras.caritas.ui.utils.pdf.PdfMonthlyReport;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class JManageMonthlyReport extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static SimpleDateFormat sdfCorto = new SimpleDateFormat("dd/MM/yyyy");

	private JDesktopPane desktop = null;
	private JPanel jPanelTData = null;
	private JPanel jPanelFilter = null;
	private JLabel lblMonth = null;

	private JTextArea textArea = null;
	private JScrollPane scroll = null;

	private JComboBox<String> cbMonth;
	private JButton btnReport = null;
	private JButton btnCleanReport = null;

	private JPanel jPanelContent = null;

	private JLabel lblYear;
	private JTextField tfYear;
	private JCheckBox ckActive;

	private JButton btnExitPeople;

	private TicketDAO ticketDAO;
	private ProgramDAO programDAO;
	private RelativeDAO relativeDAO;
	private AnswerDAO answerDAO;

	public JManageMonthlyReport(JDesktopPane desktop) {
		super(desktop);

		this.desktop = desktop;
		this.setTitle("Gestion Personas");
		this.setVisible(true);

		this.moveToFront();

		this.pack();
		this.setResizable(false);
		this.setClosable(true);

		createGUIComponents();
		initComponents();

		ticketDAO = new TicketDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		answerDAO = new AnswerDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		relativeDAO = new RelativeDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {

			}
		});

		getJButtonReport().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createPdfReport();
			}

		});

		getJButtonClean().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanFilter();
			}
		});

		getBtnExitPeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();
			}
		});

	}

	private void createPdfReport() {

		if (this.getJTextFieldYear().getText() != null && !this.getJTextFieldYear().getText().equals("")
				&& this.getJComboBoxMonth().getSelectedItem() != null) {
			PdfMonthlyReport exporter = new PdfMonthlyReport();

			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF FILES", "pdf");
			fileChooser.setFileFilter(filter);
			int retval = fileChooser.showSaveDialog(this.getJButtonReport());
			if (retval == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (file == null) {
					return;
				}

				if (!file.getAbsolutePath().endsWith(".pdf")) {
					file = new File(fileChooser.getSelectedFile() + ".pdf");
				}
				try {

					List<MonthlyReport> listReport = filterData();

					String month = (String) this.getJComboBoxMonth().getSelectedItem();
					String year = this.getJTextFieldYear().getText();
					exporter.export(listReport, file, month, year);
					textArea.append("\n");
					textArea.append("******************: " + "\n");
					textArea.append("Resumen: " + "\n");
					textArea.append("******************: " + "\n");
					textArea.append("Se ha generado el pdf correctamente. \n");
					textArea.append("Se ha guardado en: . " + file.getAbsolutePath() + "\n");

					if (listReport != null && !listReport.isEmpty()) {

						textArea.append("Registros añadidos al informe: \n");
						for (MonthlyReport monthlyReport : listReport) {
							textArea.append(monthlyReport.getNombre() + " " + monthlyReport.getApellidos() + "\n");
						}
					}

					JOptionPane.showMessageDialog(null, "Se ha generado el pdf correctamente.");

				} catch (DocumentException e) {
					JOptionPane.showMessageDialog(this,
							"Se ha producido un error. No ha sido posible generar el informe", "Generacion PDF",
							JOptionPane.ERROR_MESSAGE);
					// logger.info(e);

				} catch (FileNotFoundException e) {

					JOptionPane.showMessageDialog(this,
							"El fichero pdf se encuentra abierto. Cierrelo y vuelva a intentarlo.", "Generacion PDF",
							JOptionPane.ERROR_MESSAGE);
					// logger.info(e);
				} catch (IOException e) {

					JOptionPane.showMessageDialog(this,
							"Se ha producido un error. No ha sido posible imprimir el registro", "Generacion PDF",
							JOptionPane.ERROR_MESSAGE);
					// logger.info(e);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccioine un año y un mes para poder generar el informe");
		}

	}

	public void createGUIComponents() {
		getContentPane().setLayout(getGridContentPane());

		// Añado elementos del JPanelFilter
		getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
		getJPanelFilter().add(getJLabelYear(), getGridJLabelYear());
		getJPanelFilter().add(getJTextFieldYear(), getGridJTextFieldYear());

		getJPanelFilter().add(getCkActive(), getGridJCheckBoxdActive());
		getJPanelFilter().add(getJLabelMonth(), getGridJLabelMonth());
		getJPanelFilter().add(this.getJComboBoxMonth(), getGridJTextFieldMonth());

		getJPanelFilter().add(getJButtonReport(), getGridButtonReport());
		getJPanelFilter().add(getJButtonClean(), getGridButtonClean());
		getJPanelFilter().add(getBtnExitPeople(), getGridButtonExit());

		// Añado al panel data
		getJPanelData().setLayout(getGridLayoutJPanelData());
		getJPanelData().add(getJScrollPane(), getGridJScrollPane());

		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());

		getJPanelContent().add(getJPanelFilter(), getGridJPanelFilter());
		getJPanelContent().add(getJPanelData(), getGridJPanelData());

	}

	public void initComponents() {
		this.getCkActive().setSelected(true);

		initMonths();
		initYear();

	}

	private void initYear() {

		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		this.getJTextFieldYear().setText(String.valueOf(year));
	}

	private void initMonths() {

		this.getJComboBoxMonth().addItem("Enero");
		this.getJComboBoxMonth().addItem("Febrero");
		this.getJComboBoxMonth().addItem("Marzo");
		this.getJComboBoxMonth().addItem("Abril");
		this.getJComboBoxMonth().addItem("Mayo");
		this.getJComboBoxMonth().addItem("Junio");
		this.getJComboBoxMonth().addItem("Julio");
		this.getJComboBoxMonth().addItem("Agosto");
		this.getJComboBoxMonth().addItem("Septiembre");
		this.getJComboBoxMonth().addItem("Octubre");
		this.getJComboBoxMonth().addItem("Noviembre");
		this.getJComboBoxMonth().addItem("Diciembre");

	}

	/* FUNCIONES DEL GETCONTENTPANE */

	private GridBagLayout getGridContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0 };

		return gridBagLayout;
	}

	/* FUNCIONES DEL PANEL DE FILTRO */

	private GridBagLayout getGridLayoutJPanelFilter() {

		GridBagLayout gbl_jPanelFilter = new GridBagLayout();
		gbl_jPanelFilter.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0 };
		gbl_jPanelFilter.rowWeights = new double[] { 0.0, 0.0 };

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
		gbc_jPanelFilter.anchor = GridBagConstraints.NORTH;
		gbc_jPanelFilter.weightx = 1.0;
		gbc_jPanelFilter.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelFilter.fill = GridBagConstraints.BOTH;
		gbc_jPanelFilter.gridx = 0;
		gbc_jPanelFilter.gridy = 0;

		return gbc_jPanelFilter;
	}

	private JPanel getJPanelData() {

		if (jPanelTData == null) {
			jPanelTData = new JPanel();
			jPanelTData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Resultado Exportación",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

		}

		return jPanelTData;
	}

	private GridBagLayout getGridLayoutJPanelData() {

		GridBagLayout gbl_jPanelFilter = new GridBagLayout();
		gbl_jPanelFilter.columnWeights = new double[] { 0.0 };
		gbl_jPanelFilter.rowWeights = new double[] { 0.0 };

		return gbl_jPanelFilter;
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

	private JLabel getJLabelMonth() {

		if (lblMonth == null) {
			lblMonth = new JLabel("Mes: ");
			lblMonth.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblMonth;
	}

	private GridBagConstraints getGridJLabelMonth() {
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 5, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;

		return gbc_lblName;
	}

	private JComboBox<String> getJComboBoxMonth() {
		if (cbMonth == null) {
			if (cbMonth == null) {
				cbMonth = new JComboBox<String>();

			}
			return cbMonth;
		}

		return cbMonth;

	}

	private GridBagConstraints getGridJTextFieldMonth() {

		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.insets = new Insets(0, 0, 0, 5);
		gbc_tfName.weightx = 1.0;
		gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfName.gridx = 1;
		gbc_tfName.gridy = 1;

		return gbc_tfName;
	}

	private JLabel getJLabelYear() {
		if (lblYear == null) {
			lblYear = new JLabel("Año:");
			lblYear.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblYear;
	}

	private GridBagConstraints getGridJLabelYear() {
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.WEST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 0;
		gbc_lblDni.gridy = 0;

		return gbc_lblDni;
	}

	private JTextField getJTextFieldYear() {
		if (tfYear == null) {
			tfYear = new JTextField();
			tfYear.setColumns(10);
		}
		return tfYear;
	}

	private GridBagConstraints getGridJTextFieldYear() {

		GridBagConstraints gbc_tfDni = new GridBagConstraints();
		gbc_tfDni.weightx = 1.0;
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
			ckActive.setFont(new Font("Verdana", Font.PLAIN, 14));
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

	private JButton getJButtonReport() {
		if (btnReport == null) {
			btnReport = new JButton("Generar Informe");
			btnReport.setIcon(new ImageIcon(JManageMonthlyReport.class.getResource("/img/icon-report-32.png")));
		}

		return btnReport;
	}

	private GridBagConstraints getGridButtonReport() {

		GridBagConstraints gbc_btnSearchPeople = new GridBagConstraints();
		gbc_btnSearchPeople.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearchPeople.gridx = 2;
		gbc_btnSearchPeople.gridy = 1;

		return gbc_btnSearchPeople;
	}

	private JButton getJButtonClean() {
		if (btnCleanReport == null) {
			btnCleanReport = new JButton("Limpiar");
			btnCleanReport.setIcon(new ImageIcon(JManageMonthlyReport.class.getResource("/img/icon-clean-32.png")));
		}

		return btnCleanReport;
	}

	private GridBagConstraints getGridButtonClean() {

		GridBagConstraints gbc_btnCleanPeople = new GridBagConstraints();
		gbc_btnCleanPeople.anchor = GridBagConstraints.WEST;
		gbc_btnCleanPeople.insets = new Insets(0, 0, 0, 5);
		gbc_btnCleanPeople.gridx = 3;
		gbc_btnCleanPeople.gridy = 1;

		return gbc_btnCleanPeople;
	}

	private JButton getBtnExitPeople() {
		if (btnExitPeople == null) {
			btnExitPeople = new JButton("Salir al menú");
			btnExitPeople.setHorizontalAlignment(SwingConstants.RIGHT);
			btnExitPeople.setIcon(new ImageIcon(JManageMonthlyReport.class.getResource("/img/icon-exit.png")));
		}
		return btnExitPeople;
	}

	private GridBagConstraints getGridButtonExit() {

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.anchor = GridBagConstraints.EAST;
		gbc_btnExit.insets = new Insets(0, 0, 0, 20);
		gbc_btnExit.gridx = 3;
		gbc_btnExit.gridy = 1;

		return gbc_btnExit;
	}

	/* FUNCIONES DEL PANEL DE CONTENIDO */

	private JPanel getJPanelContent() {
		if (jPanelContent == null) {
			jPanelContent = new JPanel();
			// jPanelContent.setBorder(new LineBorder(new Color(0, 0, 0)));

			jPanelContent.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Listado Personas",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

			((javax.swing.border.TitledBorder) jPanelContent.getBorder())
					.setTitleFont(new Font("Verdana", Font.ITALIC, 18));
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

	/* EVENTOS */

	public void cleanFilter() {
		this.getJTextFieldYear().setText("");
		this.getJComboBoxMonth().setSelectedItem(null);

	}

	private String getAttentionTicket(String filterMonth, Ticket ticket) {

		String attention = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (ticket != null) {

			switch (filterMonth) {
			case "Enero":
				attention = attention + (ticket.getDateJanuary()!=null?sdf.format(ticket.getDateJanuary()):"");
				break;
			case "Febrero":
				attention =  attention + (ticket.getDateFebruary()!=null?sdf.format(ticket.getDateFebruary()):"");
				break;
			case "Marzo":
				attention = attention +  (ticket.getDateMarch()!=null?sdf.format(ticket.getDateMarch()):"");
				break;
			case "Abril":
				attention = attention + (ticket.getDateApril()!=null?sdf.format(ticket.getDateApril()):"");
				break;
			case "Mayo":
				attention = attention + (ticket.getDateMay()!=null?sdf.format(ticket.getDateMay()):"");
				break;
			case "Junio":
				attention = attention + (ticket.getDateJune()!=null?sdf.format(ticket.getDateJune()):"");
				break;
			case "Julio":
				attention = attention + (ticket.getDateJuly()!=null?sdf.format(ticket.getDateJuly()):"");
				break;
			case "Agosto":
				attention = attention + (ticket.getDateAugust()!=null?sdf.format(ticket.getDateAugust()):"");
				break;
			case "Septiembre":
				attention = attention + (ticket.getDateSeptember()!=null?sdf.format(ticket.getDateSeptember()):"");
				break;
			case "Octubre":
				attention = attention + (ticket.getDateOctober()!=null?sdf.format(ticket.getDateOctober()):"");
				break;
			case "Noviembre":
				attention = attention + (ticket.getDateNovember()!=null?sdf.format(ticket.getDateNovember()):"");
				break;
			case "Diciembre":
				attention = attention + (ticket.getDateDecember()!=null?sdf.format(ticket.getDateDecember()):"");
				break;
			}

		}

		return attention;
	}
	
	

	private Integer getValueTicket(String filterMonth, Ticket ticket) {

		Integer value = null;
		if (ticket != null) {
			switch (filterMonth) {
			case "Enero":
				value = ticket.getPointsJanuary();
				break;
			case "Febrero":
				value = ticket.getPointsFebruary();
				break;
			case "Marzo":
				value = ticket.getPointsMarch();
				break;
			case "Abril":
				value = ticket.getPointsApril();
				break;
			case "Mayo":
				value = ticket.getPointsMay();
				break;
			case "Junio":
				value = ticket.getPointsJune();
				break;
			case "Julio":
				value = ticket.getPointsJuly();
				break;
			case "Agosto":
				value = ticket.getPointsAugust();
				break;
			case "Septiembre":
				value = ticket.getPointsSeptember();
				break;
			case "Octubre":
				value = ticket.getPointsOctober();
				break;
			case "Noviembre":
				value = ticket.getPointsNovember();
				break;
			case "Diciembre":
				value = ticket.getPointsDecember();
				break;
			}
		}
		if (value!=null) {
			return value;
		}else {
			return 0;
		}
		
	}

	public List<MonthlyReport> filterData() {

		Map<Long, MonthlyReport> mapMonthlyReport = new HashMap<>();

		List<MonthlyReport> listReport = new ArrayList<MonthlyReport>();

		String filterYear = this.getJTextFieldYear().getText();
		String filterMonth = (String) this.getJComboBoxMonth().getSelectedItem();
		boolean filterActive = this.getCkActive().isSelected();
		FilterTicket filterTicket = new FilterTicket();

		filterTicket.setYearTicket(Integer.parseInt(filterYear));
		filterTicket.setActive(filterActive);

		FilterAnswer filterAnswer = new FilterAnswer();
		filterAnswer.setYearTicket(Integer.parseInt(filterYear));
		filterAnswer.setActive(filterActive);

		switch (filterMonth) {
		case "Enero":
			filterTicket.setPointsJanuary(true);
			filterAnswer.setMonth("Enero");
			break;
		case "Febrero":
			filterTicket.setPointsFebruary(true);
			filterAnswer.setMonth("Febrero");
			break;
		case "Marzo":
			filterTicket.setPointsMarch(true);
			filterAnswer.setMonth("Marzo");
			break;
		case "Abril":
			filterTicket.setPointsApril(true);
			filterAnswer.setMonth("Abril");
			break;
		case "Mayo":
			filterTicket.setPointsMay(true);
			filterAnswer.setMonth("Mayo");
			break;
		case "Junio":
			filterTicket.setPointsJune(true);
			filterAnswer.setMonth("Junio");
			break;
		case "Julio":
			filterTicket.setPointsJuly(true);
			filterAnswer.setMonth("Julio");
			break;
		case "Agosto":
			filterTicket.setPointsAugust(true);
			filterAnswer.setMonth("Agosto");
			break;
		case "Septiembre":
			filterTicket.setPointsSeptember(true);
			filterAnswer.setMonth("Septiembre");
			break;
		case "Octubre":
			filterTicket.setPointsOctober(true);
			filterAnswer.setMonth("Octubre");
			break;
		case "Noviembre":
			filterTicket.setPointsNovember(true);
			filterAnswer.setMonth("Noviembre");
			break;
		case "Diciembre":
			filterTicket.setPointsDecember(true);
			filterAnswer.setMonth("Diciembre");
			break;
		}

		List<Ticket> listTicket = ticketDAO.findTicket(filterTicket);
		List<Answer> listAnswer = answerDAO.findAnswer(filterAnswer);

		createMonthlyReportWithTicket(listTicket, filterMonth, mapMonthlyReport);
		createMonthlyReportWithAnswer(listAnswer, filterMonth, mapMonthlyReport);

		listReport.addAll(mapMonthlyReport.values());

		Collections.sort(listReport, (o1, o2) -> o1.getApellidos().compareTo(o2.getApellidos()));

		return listReport;

	}

	private void createMonthlyReportWithTicket(List<Ticket> listTicket, String filterMonth,
			Map<Long, MonthlyReport> mapMonthlyReport) {

		if (listTicket != null && !listTicket.isEmpty()) {

			for (Ticket ticket : listTicket) {
				People people = ticket.getPeople();
				mapMonthlyReport.put(people.getId(), createReport(people, ticket, filterMonth));

			}


		}
	}

	private void createMonthlyReportWithAnswer(List<Answer> listAnswer, String filterMonth,
			Map<Long, MonthlyReport> mapMonthlyReport) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (listAnswer != null && !listAnswer.isEmpty()) {

			for (Answer answer : listAnswer) {
				People people = answer.getPeople();

				if (mapMonthlyReport.get(people.getId()) == null) {

					Ticket ticket = null;
					mapMonthlyReport.put(people.getId(), createReport(people, ticket, filterMonth));
				}

				MonthlyReport report = mapMonthlyReport.get(people.getId());
				Date dateMoney = answer.getDate();
				Double money = answer.getMoney();
				if (money != null) {
					report.setRespuestaImporte(money);
					String atencionImporte = "";
					if (dateMoney!=null) {
					  atencionImporte = sdf.format(dateMoney);
							
					}
					if (report.getAtencion()!=null) {
						
						report.setAtencion(report.getAtencion() + "\n" + atencionImporte);
						
					}else {
						report.setAtencion(atencionImporte);
					}
					
				}

			}
		}
	}

	private MonthlyReport createReport(People people, Ticket ticket, String filterMonth) {

		MonthlyReport report = new MonthlyReport();
		Program program = null;
		FilterProgram filterProgram = new FilterProgram();
		filterProgram.setIdPeople(people.getId());
		
		report.setApellidos(people.getFirstSurname() + " "
				+ (people.getSecondSurname() != null ? people.getSecondSurname() : ""));
		report.setEstadoCivil(people.getCivilStatus());
		if (people.getDateBorn() != null) {
			report.setFechaNacimiento(sdfCorto.format(people.getDateBorn()));
		}

		report.setNacionalidad(people.getNationality());
		report.setNombre(people.getName());
		report.setSexo(people.getSex());
		String documentacion = "";
		if (people.getDni() != null && !people.getDni().equals("")) {
			documentacion = people.getDni();
		} else if (people.getPassport() != null && !people.getPassport().equals("")) {
			documentacion = people.getPassport();
		}
		report.setDocumentacion(documentacion);
		
		report.setEstadoCivil(people.getCivilStatus());
		if (people.getYearToSpain() != null) {
			report.setAnyoLlegada(String.valueOf(people.getYearToSpain()));
		}
		
		if (ticket != null) {
			report.setValorTicket(getValueTicket(filterMonth, ticket));
			report.setAtencion(getAttentionTicket(filterMonth, ticket));
		}
		
		List<Program> listPrograms = programDAO.findProgram(filterProgram);
		if (listPrograms != null && !listPrograms.isEmpty()) {
			program = listPrograms.get(0);
			//people = program.getPeople();
			
			String domicilio = "";
			String street = program.getFamily().getHome().getAddress().getStreet();
			if (street != null) {
				domicilio = street;
				String floor = program.getFamily().getHome().getAddress().getFloor();
				String gate = program.getFamily().getHome().getAddress().getGate();
				if (gate != null) {
					domicilio = domicilio + " " + gate;
					if (floor != null) {
						domicilio = domicilio + " " + floor;
					}
				}

			}
			report.setDomicilio(domicilio);
			report.setNumPersonas(String.valueOf(program.getFamily().getHome().getNumberFamilies()));
			String tipoFamilia = "";
			if (program.getFamily().getFamilyType() != null) {
				FamilyType familyType = new FamilyType();
				familyType.setDescription(program.getFamily().getFamilyType().getDescription());
				tipoFamilia = Constants.getNemonicFamilyType(familyType);
				report.setTipoFamilia(tipoFamilia);
			}

			

			String tipoAutorizacion = "";
			if (program.getAuthorizationType() != null) {
				AuthorizationType atype = new AuthorizationType();
				atype.setDescription(program.getAuthorizationType().getDescription());
				tipoAutorizacion = Constants.getNemonicAuthorizationType(atype);
				report.setTipoAutorizacion(tipoAutorizacion);

			}

			Family family = program.getFamily();
			if (family != null) {

				Relative filter = new Relative();
				filter.setFamily(family);
				List<Relative> relatives = relativeDAO.findRelative(filter);
				report.setHijosMenor18(String.valueOf(getHijos18(relatives)[0]));
				report.setHijosMayor18(String.valueOf(getHijos18(relatives)[1]));
			}

			String situacionLaboral = "";
			if (program.getJobSituation() != null) {
				JobSituation jtype = new JobSituation();
				jtype.setDescription(program.getJobSituation().getDescription());
				situacionLaboral = Constants.getNemonicJobSituation(jtype);
				report.setSituacionLaboral(situacionLaboral);

			}

			String estudios = "";
			if (program.getStudies() != null) {
				Studies studies = new Studies();
				studies.setDescription(program.getStudies().getDescription());
				estudios = Constants.getNemonicStudies(studies);
				report.setEstudios(estudios);

			}

//			if (program.getOtherInfo() != null) {
//				report.setDemandas(program.getOtherInfo().getDemand());
//			}
	
		}

		return report;
	}

	private Integer[] getHijos18(List<Relative> relatives) {
		Integer[] edadHijos = new Integer[2];
		int menor18 = 0;
		int mayor18 = 0;
		if (relatives != null) {
			for (Relative relative : relatives) {
				if (relative.getRelationShip() != null && !relative.getRelationShip().equals("")) {
					String relacion = relative.getRelationShip().toLowerCase();
					if (relacion.equals("hijo") || relacion.equals("hija")) {
						Date fecha = relative.getDateBorn();
						if (fecha != null) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(fecha);
							Calendar today = Calendar.getInstance();
							today.setTime(new Date());
							int year = calendar.get(Calendar.YEAR);
							int yearToday = today.get(Calendar.YEAR);
							if (yearToday - year >= 18) {
								++mayor18;
							} else {
								++menor18;
							}

						}

					}
				}

			}
		}

		edadHijos[0] = menor18;
		edadHijos[1] = mayor18;
		return edadHijos;
	}

}
