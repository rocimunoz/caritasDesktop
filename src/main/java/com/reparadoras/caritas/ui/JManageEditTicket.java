package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.utils.PeopleVerifier;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXBusyLabel;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")

public class JManageEditTicket extends AbstractJInternalFrame {

	static final Logger logger = Logger.getLogger(JManageEditTicket.class);
	
	private JPanel jPanelContentPane;

	private JCheckBox jckJanuary;
	private JCheckBox jckFebruary;
	private JCheckBox jckMarch;
	private JCheckBox jckApril;
	private JCheckBox jckMay;
	private JCheckBox jckJune;
	private JCheckBox jckJuly;
	private JCheckBox jckAugust;
	private JCheckBox jckSeptember;
	private JCheckBox jckOctober;
	private JCheckBox jckNovember;
	private JCheckBox jckDecember;
	
	private JPanel jPanelPersonalData;
	
	
	private JPanel jPanelActions;
	private JButton jBtnAccept;
	private JButton jBtnCancel;

	private PeopleDAO peopleDAO;
	
	private int executingMode;

	private Ticket selectedTicket;
	
	private AbstractJInternalFrame jCicIFParent;
	
	public JManageEditTicket(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title,
			Ticket ticket) throws Exception {
		super(jCicIFParent, modal);
		setVisible(true);
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setSize(935, 225);
		this.setTitle(title);

		this.selectedTicket = ticket;
		this.executingMode = executingMode;
		this.jCicIFParent = jCicIFParent;
		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		initComponents();
		configureModeEdition(executingMode);
		
		getContentPane().setLayout(getGridLayoutContentPane());
		getContentPane().add(getJPanelContentPane(), getGridBagConstraintsJPaneContentPane());
		getJPanelContentPane().setLayout(getGridLayoutJPaneContentPane());

		getJPanelContentPane().add(getJPanelPersonalData(), getGridJPanelPersonalData());
		getJPanelPersonalData().setLayout(getGridLayoutJPanelPersonalData());

		getJPanelPersonalData().add(getJckJanuary(), this.getGridJCheckJanuary());
		getJPanelPersonalData().add(getJckFebruary(), this.getGridJCheckFebruary());
		getJPanelPersonalData().add(getJckMarch(), this.getGridJCheckMarch());
		getJPanelPersonalData().add(getJckApril(), this.getGridJCheckApril());
		getJPanelPersonalData().add(getJckMay(), this.getGridJCheckMay());
		getJPanelPersonalData().add(getJckJune(), this.getGridJCheckJune());
		getJPanelPersonalData().add(getJckJuly(), this.getGridJCheckJuly());
		getJPanelPersonalData().add(getJckAugust(), this.getGridJCheckAugust());
		getJPanelPersonalData().add(getJckSeptember(), this.getGridJCheckSeptember());
		getJPanelPersonalData().add(getJckOctober(), this.getGridJCheckOctober());
		getJPanelPersonalData().add(getJckNovember(), this.getGridJCheckNovember());
		getJPanelPersonalData().add(getJckDecember(), this.getGridJCheckDecember());
		
		
		
		getJPanelContentPane().add(getJPanelActions(), getGridBagConstraintsJPanelActions());
		getJPanelActions().setLayout(getGridLayoutJPanelActions());

		getJButtonCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseWindow();
			}
		});

		getJButtonAccept().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cerrar Transaccion
				onCloseWindow();
				

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
			logger.error("Error onCloseWindow " + e.getMessage());
		}
	}

	private void initComponents() {
		

	}

	private void configureModeEdition(int mode) {

		if (mode == JWindowParams.IMODE_SELECT) {
			// Deshabilito campos

			//this.getJTextFieldName().setEditable(false);
			

			this.getJButtonAccept().setVisible(false);
			this.getJButtonCancel().setText("Salir");
		} else {
			// Habilito campos
			//this.getJTextFieldDni().setEditable(true);

			

			this.getJButtonAccept().setVisible(true);
			this.getJButtonCancel().setText("Cancelar");
		}
	}

	

	







	/* FUNCIONES PANEL CONTENT PANE */

	private GridBagLayout getGridLayoutContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };

		return gridBagLayout;
	}

	private JPanel getJPanelContentPane() {

		if (jPanelContentPane == null) {
			jPanelContentPane = new JPanel();
			jPanelContentPane.setMaximumSize(new Dimension(10, 10));
			jPanelContentPane.setBorder(null);
		}
		return jPanelContentPane;
	}

	private GridBagLayout getGridLayoutJPaneContentPane() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0 };

		return gbl_panel;
	}

	private GridBagConstraints getGridBagConstraintsJPaneContentPane() {

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.weightx = 1.0;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;

		return gbc_panel;
	}

	/* FUNCIONES PANEL PERSONAL DATA */
	private JPanel getJPanelPersonalData() {

		if (jPanelPersonalData == null) {
			jPanelPersonalData = new JPanel();
			jPanelPersonalData.setBorder(
					new TitledBorder(null, "Limpieza Ticket", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			

			((javax.swing.border.TitledBorder) jPanelPersonalData.getBorder())
					.setTitleFont(new Font("Verdana", Font.ITALIC, 18));

		}

		return jPanelPersonalData;
	}

	private GridBagConstraints getGridJPanelPersonalData() {

		GridBagConstraints gbc_jPanelPersonalData = new GridBagConstraints();
		gbc_jPanelPersonalData.weighty = 1.0;
		gbc_jPanelPersonalData.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelPersonalData.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPersonalData.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelPersonalData.gridx = 0;
		gbc_jPanelPersonalData.gridy = 0;

		return gbc_jPanelPersonalData;
	}

	private GridBagLayout getGridLayoutJPanelPersonalData() {
		GridBagLayout gbl_jPanelPersonalData = new GridBagLayout();
		gbl_jPanelPersonalData.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0 };
		gbl_jPanelPersonalData.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		return gbl_jPanelPersonalData;
	}


	private JCheckBox getJckJanuary() {
		if (jckJanuary == null) {
			jckJanuary = new JCheckBox("Enero");

			jckJanuary.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckJanuary;
	}

	private GridBagConstraints getGridJCheckJanuary() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 0;
		gbcCheckActive.gridy = 0;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckFebruary() {
		if (jckFebruary == null) {
			jckFebruary = new JCheckBox("Febrero");

			jckFebruary.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckFebruary;
	}

	private GridBagConstraints getGridJCheckFebruary() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 1;
		gbcCheckActive.gridy = 0;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckMarch() {
		if (jckMarch == null) {
			jckMarch = new JCheckBox("Marzo");

			jckMarch.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckMarch;
	}

	private GridBagConstraints getGridJCheckMarch() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 2;
		gbcCheckActive.gridy = 0;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckApril() {
		if (jckApril == null) {
			jckApril = new JCheckBox("Abril");

			jckApril.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckApril;
	}

	private GridBagConstraints getGridJCheckApril() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 3;
		gbcCheckActive.gridy = 0;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckMay() {
		if (jckMay == null) {
			jckMay = new JCheckBox("Mayo");

			jckMay.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckMay;
	}

	private GridBagConstraints getGridJCheckMay() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 0;
		gbcCheckActive.gridy = 1;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckJune() {
		if (jckJune == null) {
			jckJune = new JCheckBox("Junio");

			jckJune.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckJune;
	}

	private GridBagConstraints getGridJCheckJune() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 1;
		gbcCheckActive.gridy = 1;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckJuly() {
		if (jckJuly == null) {
			jckJuly = new JCheckBox("Julio");

			jckJuly.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckJuly;
	}

	private GridBagConstraints getGridJCheckJuly() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 2;
		gbcCheckActive.gridy = 1;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckAugust() {
		if (jckAugust == null) {
			jckAugust = new JCheckBox("Agosto");

			jckAugust.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckAugust;
	}

	private GridBagConstraints getGridJCheckAugust() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 3;
		gbcCheckActive.gridy = 1;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckSeptember() {
		if (jckSeptember == null) {
			jckSeptember = new JCheckBox("Septiembre");

			jckSeptember.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckSeptember;
	}

	private GridBagConstraints getGridJCheckSeptember() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 0;
		gbcCheckActive.gridy = 2;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckOctober() {
		if (jckOctober == null) {
			jckOctober = new JCheckBox("Octubre");

			jckOctober.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckOctober;
	}

	private GridBagConstraints getGridJCheckOctober() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 1;
		gbcCheckActive.gridy = 2;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckNovember() {
		if (jckNovember == null) {
			jckNovember = new JCheckBox("Noviembre");

			jckNovember.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckNovember;
	}

	private GridBagConstraints getGridJCheckNovember() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 2;
		gbcCheckActive.gridy = 2;
		return gbcCheckActive;
	}
	
	private JCheckBox getJckDecember() {
		if (jckDecember == null) {
			jckDecember = new JCheckBox("Diciembre");

			jckDecember.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jckDecember;
	}

	private GridBagConstraints getGridJCheckDecember() {
		GridBagConstraints gbcCheckActive = new GridBagConstraints();
		gbcCheckActive.anchor = GridBagConstraints.WEST;
		gbcCheckActive.insets = new Insets(0, 15, 0, 5);
		gbcCheckActive.gridx = 3;
		gbcCheckActive.gridy = 2;
		return gbcCheckActive;
	}

	

	

	/* FUNCIONES PANEL ACCIONES */

	private JPanel getJPanelActions() {

		if (jPanelActions == null) {
			jPanelActions = new JPanel();
			jPanelActions.setMinimumSize(new Dimension(100, 100));
			jPanelActions.setMaximumSize(new Dimension(1000, 1000));
		}

		return jPanelActions;
	}

	private GridBagConstraints getGridBagConstraintsJPanelActions() {
		GridBagConstraints gbc_jPanelActions = new GridBagConstraints();
		gbc_jPanelActions.weighty = 1.0;
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
					new ImageIcon(JManageEditTicket.class.getResource("/img/icon-check.png")));
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
					new ImageIcon(JManageEditTicket.class.getResource("/img/icon-cancel.png")));
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
