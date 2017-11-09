package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.RelativeDAO;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.utils.JobSituationVerifier;
import com.reparadoras.caritas.ui.utils.PeopleVerifier;
import com.reparadoras.caritas.ui.utils.RelativeVerifier;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Color;

@SuppressWarnings("serial")

public class JManageEditIncome extends AbstractJInternalFrame {

	static final Logger logger = Logger.getLogger(JManageEditIncome.class);
	
	private JPanel jPanelContentPane;
	private JTextField txfPeople;

	private JPanel jPanelPersonalData;
	private JLabel jLblPeople;

	private JPanel jPanelActions;
	private JButton jBtnAccept;
	private JButton jBtnCancel;
	private JLabel jLblAmount;
	private JFormattedTextField txfAmount;
	private JLabel jLblDateEnd;

	private JLabel jLblConcept;
	private JTextField txfConcept;
	
	private JXDatePicker jxDateBorn;

	private int executingMode;
	private AbstractJInternalFrame jCicIFParent;

	private Income selectedIncome;

	private Integer rowIndex;

	private JobSituationVerifier jobSituationVerifier = new JobSituationVerifier();

	public JManageEditIncome(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title,
			Income income, Integer index) throws Exception {
		super(jCicIFParent, modal);
		setVisible(true);
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setSize(800, 300);
		this.setTitle(title);

		this.selectedIncome = income;

		this.jCicIFParent = jCicIFParent;
		this.executingMode = executingMode;
		this.rowIndex = index;

		initComponents();

		fillData(executingMode);

		getContentPane().setLayout(getGridLayoutContentPane());
		getContentPane().add(getJPanelContentPane(), getGridBagConstraintsJPaneContentPane());
		getJPanelContentPane().setLayout(getGridLayoutJPaneContentPane());

		getJPanelContentPane().add(getJPanelPersonalData(), getGridJPanelPersonalData());
		getJPanelPersonalData().setLayout(getGridLayoutJPanelPersonalData());
		getJPanelPersonalData().add(getJLabelAmount(), getGridJLabelAmount());
		getJPanelPersonalData().add(getJTextFieldAmount(), getGridJTextFieldAmount());
		getJPanelPersonalData().add(getJLabelPeople(), getGridJLabelName());
		getJPanelPersonalData().add(getJTextFieldPeople(), getGridJTextFieldName());
		getJPanelPersonalData().add(getJLblDateEnd(), getGridJLabelDateEnd());
		getJPanelPersonalData().add(getJXDateEnd(), getGridJXDateEnd());
		getJPanelPersonalData().add(getJLabelSituation(), getGridJLabelSituation());
		getJPanelPersonalData().add(getJTextFieldConcept(), getGridJTextFieldSituation());
		getJPanelContentPane().add(getJPanelActions(), getGridBagConstraintsJPanelActions());
		getJPanelActions().setLayout(getGridLayoutJPanelActions());

		getJButtonCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseWindow();
			}
		});

		getJButtonAccept().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkRequiredFields()) {
					// Abrir transaccion
					if (executingMode == JWindowParams.IMODE_UPDATE) {
						Income income = onUpdateIncomes();
						((JManageProgram) jCicIFParent).editIncome(income, rowIndex);
					} else if (executingMode == JWindowParams.IMODE_INSERT) {
						Income income = onCreateIncome();
						((JManageProgram) jCicIFParent).addIncome(income);
					}

					// Cerrar Transaccion
					onCloseWindow();
				}
			}
		});

		getJPanelActions().add(getJButtonAccept(), getGridBagConstraintsJButtonAccept());

		getJPanelActions().add(getJButtonCancel(), getGridBagConstraintsJButtonCancel());

	}

	private void onCloseWindow() {
		try {
			this.setClosed(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initComponents() {

	}

	private boolean checkRequiredFields() {

		if (!getJTextFieldPeople().getText().equals("") && !getJTextFieldConcept().getText().equals("")
				&& this.getJXDateEnd().getDate() != null) {
			return true;
		} else {
			JOptionPane.showMessageDialog(this, "Rellene todos los campos correctamente", "Error Dialog",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void fillData(int mode) {
		if (mode == JWindowParams.IMODE_SELECT || mode == JWindowParams.IMODE_UPDATE) {

			this.getJTextFieldAmount().setText(this.selectedIncome.getAmount() + "");
			this.getJTextFieldConcept().setText(this.selectedIncome.getConcept());
			this.getJXDateEnd().setDate(this.selectedIncome.getEndDate());
			this.getJTextFieldPeople().setText(this.selectedIncome.getPeople());

		}

	}

	private Income onUpdateIncomes() {
		try {

			DecimalFormat formatter = new DecimalFormat("###,###.###");
			
			if (this.getJTextFieldAmount().getText() != null && !this.getJTextFieldAmount().getText().equals("")) {
				
				String amountString = this.getJTextFieldAmount().getText();
				double amountDouble = formatter.parse(amountString).doubleValue();
				
				//this.selectedIncome.setAmount(new Double(formatter.format(this.getJTextFieldAmount().getText()).replace(',', '.')));
				this.selectedIncome.setAmount(amountDouble);
			}

			this.selectedIncome.setConcept(this.getJTextFieldConcept().getText());
			this.selectedIncome.setEndDate(this.getJXDateEnd().getDate());
			this.selectedIncome.setPeople(this.getJTextFieldPeople().getText());

			return selectedIncome;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		return null;

	}

	private Income onCreateIncome() {
		try {

			Income income = new Income();
			if (this.getJTextFieldAmount().getText() != null && !this.getJTextFieldAmount().getText().equals("")) {
			
				
				DecimalFormat formatter = new DecimalFormat("#0,000.000");
				String amountString = this.getJTextFieldAmount().getText();
				double amountDouble = formatter.parse(amountString).doubleValue();
				income.setAmount(amountDouble);
				
			}

			income.setConcept(this.getJTextFieldConcept().getText());
			income.setEndDate(this.getJXDateEnd().getDate());
			income.setPeople(this.getJTextFieldPeople().getText());

			return income;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
			logger.error("Error onCreateIncome " + e.getMessage());
		}

		return null;

	}

	/* FUNCIONES PANEL CONTENT PANE */

	private GridBagLayout getGridLayoutContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };

		return gridBagLayout;
	}

	private JPanel getJPanelContentPane() {

		if (jPanelContentPane == null) {
			jPanelContentPane = new JPanel();
			jPanelContentPane.setBorder(null);
		}
		return jPanelContentPane;
	}

	private GridBagLayout getGridLayoutJPaneContentPane() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0 };

		return gbl_panel;
	}

	private GridBagConstraints getGridBagConstraintsJPaneContentPane() {

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weightx = 1.0;
		gbc_panel.weighty = 1.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;

		return gbc_panel;
	}

	/* FUNCIONES PANEL PERSONAL DATA */
	private JPanel getJPanelPersonalData() {

		if (jPanelPersonalData == null) {
			jPanelPersonalData = new JPanel();
			jPanelPersonalData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ingresos",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			((javax.swing.border.TitledBorder) jPanelPersonalData.getBorder())
					.setTitleFont(new Font("Verdana", Font.ITALIC, 18));

		}

		return jPanelPersonalData;
	}

	private GridBagConstraints getGridJPanelPersonalData() {

		GridBagConstraints gbc_jPanelPersonalData = new GridBagConstraints();
		gbc_jPanelPersonalData.weightx = 1.0;
		gbc_jPanelPersonalData.weighty = 1.0;
		gbc_jPanelPersonalData.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPersonalData.fill = GridBagConstraints.BOTH;
		gbc_jPanelPersonalData.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelPersonalData.gridx = 0;
		gbc_jPanelPersonalData.gridy = 0;

		return gbc_jPanelPersonalData;
	}

	private GridBagLayout getGridLayoutJPanelPersonalData() {
		GridBagLayout gbl_jPanelPersonalData = new GridBagLayout();
		gbl_jPanelPersonalData.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0 };
		gbl_jPanelPersonalData.rowWeights = new double[] { 0.0, 0.0 };
		return gbl_jPanelPersonalData;
	}

	private JLabel getJLabelPeople() {

		if (jLblPeople == null) {
			jLblPeople = new JLabel("Persona");
			jLblPeople.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblPeople.setPreferredSize(new Dimension(80, 25));
		}

		return jLblPeople;
	}

	private GridBagConstraints getGridJLabelName() {
		GridBagConstraints gbc_jLblName = new GridBagConstraints();
		gbc_jLblName.anchor = GridBagConstraints.WEST;
		gbc_jLblName.insets = new Insets(0, 20, 5, 5);
		gbc_jLblName.gridx = 0;
		gbc_jLblName.gridy = 0;

		return gbc_jLblName;
	}

	private JTextField getJTextFieldPeople() {

		if (txfPeople == null) {
			txfPeople = new JTextField();
			txfPeople.setColumns(10);
			txfPeople.setName("people");
			txfPeople.setInputVerifier(jobSituationVerifier);
		}

		return txfPeople;
	}

	private GridBagConstraints getGridJTextFieldName() {

		GridBagConstraints gbc_txfName = new GridBagConstraints();
		gbc_txfName.weightx = 1.0;
		gbc_txfName.insets = new Insets(0, 0, 5, 5);
		gbc_txfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfName.gridx = 1;
		gbc_txfName.gridy = 0;

		return gbc_txfName;

	}

	private JLabel getJLabelAmount() {

		if (jLblAmount == null) {
			jLblAmount = new JLabel("Cantidad");
			jLblAmount.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblAmount.setMinimumSize(new Dimension(20, 14));
			jLblAmount.setMaximumSize(new Dimension(20, 14));

		}

		return jLblAmount;
	}

	private GridBagConstraints getGridJLabelAmount() {
		GridBagConstraints gbc_jLblAmount = new GridBagConstraints();
		gbc_jLblAmount.anchor = GridBagConstraints.WEST;
		gbc_jLblAmount.insets = new Insets(0, 15, 5, 5);
		gbc_jLblAmount.gridx = 2;
		gbc_jLblAmount.gridy = 0;

		return gbc_jLblAmount;
	}

	private JFormattedTextField getJTextFieldAmount() {

		if (txfAmount == null) {
			
			DecimalFormat formatter = new DecimalFormat("#0,000.000");
			txfAmount = new JFormattedTextField(formatter);
			
			txfAmount.setColumns(10);
			txfAmount.setName("amount");
			txfAmount.setInputVerifier(jobSituationVerifier);
		}

		return txfAmount;
	}

	private GridBagConstraints getGridJTextFieldAmount() {

		GridBagConstraints gbc_txfAmount = new GridBagConstraints();
		gbc_txfAmount.weightx = 1.0;
		gbc_txfAmount.insets = new Insets(0, 0, 5, 0);
		gbc_txfAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfAmount.gridx = 3;
		gbc_txfAmount.gridy = 0;

		return gbc_txfAmount;

	}

	private JLabel getJLblDateEnd() {
		if (jLblDateEnd == null) {
			jLblDateEnd = new JLabel("Fecha Fin");
			jLblDateEnd.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jLblDateEnd;
	}

	private GridBagConstraints getGridJLabelDateEnd() {

		GridBagConstraints gbc_jLblDateEnd = new GridBagConstraints();
		gbc_jLblDateEnd.anchor = GridBagConstraints.WEST;
		gbc_jLblDateEnd.weightx = 1.0;
		gbc_jLblDateEnd.insets = new Insets(0, 15, 5, 5);
		gbc_jLblDateEnd.gridx = 0;
		gbc_jLblDateEnd.gridy = 2;

		return gbc_jLblDateEnd;

	}

	private JXDatePicker getJXDateEnd() {

		if (jxDateBorn == null) {
			jxDateBorn = new JXDatePicker();
			jxDateBorn.setName("dateBorn");
			jxDateBorn.setInputVerifier(jobSituationVerifier);
		}

		return jxDateBorn;
	}

	private GridBagConstraints getGridJXDateEnd() {

		GridBagConstraints gbc_txfDateBorn = new GridBagConstraints();
		gbc_txfDateBorn.weightx = 1.0;
		gbc_txfDateBorn.insets = new Insets(0, 0, 5, 5);
		gbc_txfDateBorn.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfDateBorn.gridx = 1;
		gbc_txfDateBorn.gridy = 2;

		return gbc_txfDateBorn;

	}

	private JLabel getJLabelSituation() {

		if (jLblConcept == null) {
			jLblConcept = new JLabel("Concepto");
			jLblConcept.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblConcept.setMinimumSize(new Dimension(20, 14));
			jLblConcept.setMaximumSize(new Dimension(20, 14));

		}

		return jLblConcept;
	}

	private GridBagConstraints getGridJLabelSituation() {
		GridBagConstraints gbc_jLblConcept = new GridBagConstraints();
		gbc_jLblConcept.anchor = GridBagConstraints.WEST;
		gbc_jLblConcept.insets = new Insets(0, 15, 5, 5);
		gbc_jLblConcept.gridx = 0;
		gbc_jLblConcept.gridy = 1;

		return gbc_jLblConcept;
	}

	private JTextField getJTextFieldConcept() {

		if (txfConcept == null) {
			txfConcept = new JTextField();
			txfConcept.setColumns(10);
			txfConcept.setName("concept");
			txfConcept.setInputVerifier(jobSituationVerifier);
		}

		return txfConcept;
	}

	private GridBagConstraints getGridJTextFieldSituation() {

		GridBagConstraints gbc_txfConcept = new GridBagConstraints();
		gbc_txfConcept.weightx = 1.0;
		gbc_txfConcept.insets = new Insets(0, 0, 5, 0);
		gbc_txfConcept.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfConcept.gridx = 1;
		gbc_txfConcept.gridy = 1;

		return gbc_txfConcept;

	}

	/* FUNCIONES PANEL ACCIONES */

	private JPanel getJPanelActions() {

		if (jPanelActions == null) {
			jPanelActions = new JPanel();
		}

		return jPanelActions;
	}

	private GridBagConstraints getGridBagConstraintsJPanelActions() {
		GridBagConstraints gbc_jPanelActions = new GridBagConstraints();
		gbc_jPanelActions.anchor = GridBagConstraints.SOUTH;
		gbc_jPanelActions.weightx = 1.0;
		gbc_jPanelActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelActions.gridx = 0;
		gbc_jPanelActions.gridy = 1;

		return gbc_jPanelActions;
	}

	private GridBagLayout getGridLayoutJPanelActions() {
		GridBagLayout gbl_jPanelActions = new GridBagLayout();
		gbl_jPanelActions.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_jPanelActions.rowWeights = new double[] { 0.0 };

		return gbl_jPanelActions;
	}

	private JButton getJButtonAccept() {
		if (jBtnAccept == null) {
			jBtnAccept = new JButton("Aceptar");
			jBtnAccept.setIcon(
					new ImageIcon(JManageEditIncome.class.getResource("/com/reparadoras/images/icon-check.png")));
		}

		return jBtnAccept;
	}

	private GridBagConstraints getGridBagConstraintsJButtonAccept() {
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAccept.insets = new Insets(0, 0, 0, 5);
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 0;

		return gbc_btnAccept;
	}

	private JButton getJButtonCancel() {
		if (jBtnCancel == null) {
			jBtnCancel = new JButton("Cancelar");
			jBtnCancel.setIcon(
					new ImageIcon(JManageEditIncome.class.getResource("/com/reparadoras/images/icon-cancel.png")));
		}

		return jBtnCancel;
	}

	private GridBagConstraints getGridBagConstraintsJButtonCancel() {
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 0;

		return gbc_btnCancel;
	}

}
