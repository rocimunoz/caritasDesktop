package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.RelativeDAO;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
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
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")

public class JManageEditRelative extends AbstractJInternalFrame {

	static final Logger logger = Logger.getLogger(JManageEditRelative.class);
	
	private JPanel jPanelContentPane;
	private JTextField txfName;

	private JPanel jPanelPersonalData;
	private JLabel jLblName;

	private JPanel jPanelActions;
	private JButton jBtnAccept;
	private JButton jBtnCancel;
	private JLabel jLblRelativeState;
	private JTextField txfSurname;
	private JLabel jLblDateBorn;
	private JLabel jLblRelationShip;
	private JTextField txfRelationShip;

	private JLabel jLblSituation;
	private JTextField txfSituation;
	
	private JComboBox<String> cbLiveWork;

	private JXDatePicker jxDateBorn;

	private int executingMode;
	private AbstractJInternalFrame jCicIFParent;

	private Relative selectedRelative;
	private Family family;
	private Integer rowIndex;
	
	private RelativeVerifier relativeVerifier = new RelativeVerifier();

	public JManageEditRelative(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title,
			Relative relative, Family family, Integer index) throws Exception {
		super(jCicIFParent, modal);
		setVisible(true);
		this.moveToFront();
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setSize(800, 300);
		this.setTitle(title);

		this.selectedRelative = relative;
		this.family = family;
		this.jCicIFParent = jCicIFParent;
		this.executingMode = executingMode;
		this.rowIndex = index;

		
		
		fillData(executingMode);

		getContentPane().setLayout(getGridLayoutContentPane());
		getContentPane().add(getJPanelContentPane(), getGridBagConstraintsJPaneContentPane());
		getJPanelContentPane().setLayout(getGridLayoutJPaneContentPane());

		getJPanelContentPane().add(getJPanelPersonalData(), getGridJPanelPersonalData());
		getJPanelPersonalData().setLayout(getGridLayoutJPanelPersonalData());
		//getJPanelPersonalData().add(getJLabelSurname(), getGridJLabelSurname());
		//getJPanelPersonalData().add(getJTextFieldSurname(), getGridJTextFieldSurname());
		
		getJPanelPersonalData().add(getJLabelRelativeState(), getGridJLabelLiveWork());
		getJPanelPersonalData().add(getJComboLiveWork(), getGridJComboLiveWork());
		
		getJPanelPersonalData().add(getJLabelName(), getGridJLabelName());
		getJPanelPersonalData().add(getJTextFieldName(), getGridJTextFieldName());
		getJPanelPersonalData().add(getJLblDateBorn(), getGridJLabelDateBorn());
		getJPanelPersonalData().add(getJXDateBorn(), getGridJXDateBorn());
		getJPanelPersonalData().add(getJLabelRelationShip(), getGridJLabelRelationShip());
		getJPanelPersonalData().add(getJTextFieldRelationShip(), getGridJTextFieldRelationShip());
		getJPanelPersonalData().add(getJLabelSituation(), getGridJLabelSituation());
		getJPanelPersonalData().add(getJTextFieldSituation(), getGridJTextFieldSituation());
		getJPanelContentPane().add(getJPanelActions(), getGridBagConstraintsJPanelActions());
		getJPanelActions().setLayout(getGridLayoutJPanelActions());

		getJButtonCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCloseWindow();
			}
		});

		getJButtonAccept().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkRequiredFields()){
					// Abrir transaccion
					if (executingMode == JWindowParams.IMODE_UPDATE) {
						Relative relative = onUpdateRelationShip();
						((JManageProgram) jCicIFParent).editRelative(relative, rowIndex);
					} else if (executingMode == JWindowParams.IMODE_INSERT) {
						Relative relative = onCreateRelationShip();
						((JManageProgram) jCicIFParent).addRelative(relative);
					}

					// Cerrar Transaccion
					onCloseWindow();
				}
			}
		});

		getJPanelActions().add(getJButtonAccept(), getGridBagConstraintsJButtonAccept());

		getJPanelActions().add(getJButtonCancel(), getGridBagConstraintsJButtonCancel());
		
		initCombos();

	}
	
	public void initCombos() {
		this.getJComboLiveWork().addItem("Convive");
		this.getJComboLiveWork().addItem("Trabaja");
		
	}

	public JComboBox<String> getJComboLiveWork() {
		if (cbLiveWork == null) {
			cbLiveWork = new JComboBox<String>();
		}
		return cbLiveWork;
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

	

	
	private boolean checkRequiredFields(){
		
		if (!getJTextFieldName().getText().equals("")){
			return true;
		}
		else{
			JOptionPane.showMessageDialog(this, "Rellene todos los campos correctamente", "Error Dialog", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void fillData(int mode) {
		if (mode == JWindowParams.IMODE_SELECT || mode == JWindowParams.IMODE_UPDATE) {

			this.getJTextFieldName().setText(this.selectedRelative.getName());
			this.getJTextFieldSurname().setText(this.selectedRelative.getLiveWork());
			this.getJTextFieldRelationShip().setText(this.selectedRelative.getRelationShip());
			this.getJTextFieldSituation().setText(this.selectedRelative.getSituation());
			this.getJXDateBorn().setDate(this.selectedRelative.getDateBorn());

		}

	}

	private Relative onUpdateRelationShip() {
		try {

			
			this.selectedRelative.setName(this.getJTextFieldName().getText());
			this.selectedRelative.setRelationShip(this.getJTextFieldRelationShip().getText());
			this.selectedRelative.setSituation(this.getJTextFieldSituation().getText());
			this.selectedRelative.setLiveWork((String) this.getJComboLiveWork().getSelectedItem());
			this.selectedRelative.setDateBorn(this.getJXDateBorn().getDate());
			this.selectedRelative.setFamily(this.family);

			return selectedRelative;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
			logger.error("Error onUpdateRelationShip " + e.getMessage());
		}

		return null;

	}

	private Relative onCreateRelationShip() {
		try {

			Relative relative = new Relative();
			relative.setName(this.getJTextFieldName().getText());
			relative.setRelationShip(this.getJTextFieldRelationShip().getText());
			relative.setSituation(this.getJTextFieldSituation().getText());
			relative.setLiveWork((String) this.getJComboLiveWork().getSelectedItem());
			relative.setDateBorn(this.getJXDateBorn().getDate());
			relative.setFamily(this.family);

			return relative;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible guardar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
			logger.error("Error onCreateRelationShip " + e.getMessage());
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
			jPanelPersonalData.setBorder(
					new TitledBorder(null, "Composicion Familiar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			((javax.swing.border.TitledBorder) jPanelPersonalData.getBorder()).
	        setTitleFont(new Font("Verdana", Font.ITALIC, 18));

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

	private JLabel getJLabelName() {

		if (jLblName == null) {
			jLblName = new JLabel("Nombre");
			jLblName.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblName.setPreferredSize(new Dimension(80, 25));
		}

		return jLblName;
	}

	private GridBagConstraints getGridJLabelName() {
		GridBagConstraints gbc_jLblName = new GridBagConstraints();
		gbc_jLblName.anchor = GridBagConstraints.WEST;
		gbc_jLblName.insets = new Insets(0, 20, 5, 5);
		gbc_jLblName.gridx = 0;
		gbc_jLblName.gridy = 0;

		return gbc_jLblName;
	}

	private JTextField getJTextFieldName() {

		if (txfName == null) {
			txfName = new JTextField();
			txfName.setColumns(10);
			txfName.setName("name");
			
		}

		return txfName;
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

	private JLabel getJLabelRelativeState() {

		if (jLblRelativeState == null) {
			jLblRelativeState = new JLabel("Convive/Trabaja");
			jLblRelativeState.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblRelativeState.setMinimumSize(new Dimension(20, 14));
			jLblRelativeState.setMaximumSize(new Dimension(20, 14));

		}

		return jLblRelativeState;
	}

	private GridBagConstraints getGridJLabelLiveWork() {
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.WEST;
		gbc_lblSurname.insets = new Insets(0, 15, 5, 5);
		gbc_lblSurname.gridx = 2;
		gbc_lblSurname.gridy = 0;

		return gbc_lblSurname;
	}

	private JTextField getJTextFieldSurname() {

		if (txfSurname == null) {
			txfSurname = new JTextField();
			txfSurname.setColumns(10);
			txfSurname.setName("surname");
			
		}

		return txfSurname;
	}

	private GridBagConstraints getGridJComboLiveWork() {

		GridBagConstraints gbc_txfSurname = new GridBagConstraints();
		gbc_txfSurname.weightx = 1.0;
		gbc_txfSurname.insets = new Insets(0, 0, 5, 0);
		gbc_txfSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfSurname.gridx = 3;
		gbc_txfSurname.gridy = 0;

		return gbc_txfSurname;

	}

	private JLabel getJLblDateBorn() {
		if (jLblDateBorn == null) {
			jLblDateBorn = new JLabel("Fecha Nacimiento");
			jLblDateBorn.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jLblDateBorn;
	}

	private GridBagConstraints getGridJLabelDateBorn() {

		GridBagConstraints gbc_jLblDateBorn = new GridBagConstraints();
		gbc_jLblDateBorn.anchor = GridBagConstraints.WEST;
		gbc_jLblDateBorn.weightx = 1.0;
		gbc_jLblDateBorn.insets = new Insets(0, 15, 5, 5);
		gbc_jLblDateBorn.gridx = 0;
		gbc_jLblDateBorn.gridy = 1;

		return gbc_jLblDateBorn;

	}

	private JXDatePicker getJXDateBorn() {

		if (jxDateBorn == null) {
			jxDateBorn = new JXDatePicker();
			jxDateBorn.setName("dateBorn");
			
		}

		return jxDateBorn;
	}

	private GridBagConstraints getGridJXDateBorn() {

		GridBagConstraints gbc_txfDateBorn = new GridBagConstraints();
		gbc_txfDateBorn.weightx = 1.0;
		gbc_txfDateBorn.insets = new Insets(0, 0, 5, 5);
		gbc_txfDateBorn.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfDateBorn.gridx = 1;
		gbc_txfDateBorn.gridy = 1;

		return gbc_txfDateBorn;

	}

	private JLabel getJLabelRelationShip() {

		if (jLblRelationShip == null) {
			jLblRelationShip = new JLabel("Parentesco");
			jLblRelationShip.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblRelationShip.setMinimumSize(new Dimension(20, 14));
			jLblRelationShip.setMaximumSize(new Dimension(20, 14));

		}

		return jLblRelationShip;
	}

	private GridBagConstraints getGridJLabelRelationShip() {
		GridBagConstraints gbc_lblRelationShip = new GridBagConstraints();
		gbc_lblRelationShip.anchor = GridBagConstraints.WEST;
		gbc_lblRelationShip.insets = new Insets(0, 15, 5, 5);
		gbc_lblRelationShip.gridx = 2;
		gbc_lblRelationShip.gridy = 1;

		return gbc_lblRelationShip;
	}

	private JTextField getJTextFieldRelationShip() {

		if (txfRelationShip == null) {
			txfRelationShip = new JTextField();
			txfRelationShip.setColumns(10);
			txfRelationShip.setName("relationShip");
			
		}

		return txfRelationShip;
	}

	private GridBagConstraints getGridJTextFieldRelationShip() {

		GridBagConstraints gbc_txfSurname = new GridBagConstraints();
		gbc_txfSurname.weightx = 1.0;
		gbc_txfSurname.insets = new Insets(0, 0, 5, 0);
		gbc_txfSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfSurname.gridx = 3;
		gbc_txfSurname.gridy = 1;

		return gbc_txfSurname;

	}

	private JLabel getJLabelSituation() {

		if (jLblSituation == null) {
			jLblSituation = new JLabel("Estudia/Curso");
			jLblSituation.setFont(new Font("Verdana", Font.PLAIN, 14));
			jLblSituation.setMinimumSize(new Dimension(20, 14));
			jLblSituation.setMaximumSize(new Dimension(20, 14));

		}

		return jLblSituation;
	}

	private GridBagConstraints getGridJLabelSituation() {
		GridBagConstraints gbc_lblSituation = new GridBagConstraints();
		gbc_lblSituation.anchor = GridBagConstraints.WEST;
		gbc_lblSituation.insets = new Insets(0, 15, 5, 5);
		gbc_lblSituation.gridx = 0;
		gbc_lblSituation.gridy = 2;

		return gbc_lblSituation;
	}

	private JTextField getJTextFieldSituation() {

		if (txfSituation == null) {
			txfSituation = new JTextField();
			txfSituation.setColumns(10);
			txfSituation.setName("situation");
			
		}

		return txfSituation;
	}

	private GridBagConstraints getGridJTextFieldSituation() {

		GridBagConstraints gbc_txfSituation = new GridBagConstraints();
		gbc_txfSituation.weightx = 1.0;
		gbc_txfSituation.insets = new Insets(0, 0, 5, 0);
		gbc_txfSituation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfSituation.gridx = 1;
		gbc_txfSituation.gridy = 2;

		return gbc_txfSituation;

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
			jBtnAccept.setIcon(new ImageIcon(JManageEditRelative.class.getResource("/img/icon-check.png")));
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
			jBtnCancel.setIcon(new ImageIcon(JManageEditRelative.class.getResource("/img/icon-cancel.png")));
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
