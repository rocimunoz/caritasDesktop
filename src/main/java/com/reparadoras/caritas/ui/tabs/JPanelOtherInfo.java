package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.reparadoras.caritas.dao.HomeTypeDAO;
import com.reparadoras.caritas.dao.OtherInfoDAO;
import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.HomeType;
import com.reparadoras.caritas.model.OtherInfo;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;

public class JPanelOtherInfo extends JPanel {

	private JPanel jPanelOtherInfo;
	/* VIEW OTHER INFO */

	private JLabel lblInstitution;
	private JTextArea taInstitution;
	private JLabel lblDemand;
	private JTextArea taDemand;
	private JLabel lblDescription;
	private JTextArea taDescription;
	private JLabel lblActuations;
	private JTextArea taActuations;

	private OtherInfoDAO otherInfoDAO;

	public JPanelOtherInfo() {

		otherInfoDAO = new OtherInfoDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		createGUIPanel();

	}

	private void createGUIPanel() {

		this.setLayout(getGridLayoutHouse());

		getJPanelOtherInfo().setLayout(getGridLayoutPanelHome());
		this.add(getJPanelOtherInfo(), getGridJPanelOtherInfo());

		// Añado elementos del Tab Vivienda

		getJPanelOtherInfo().add(getJLabelInstitutions(), getGridJLabelInstitutions());
		getJPanelOtherInfo().add(this.getJTextAreaInstitutions(), this.getGridJTextAreaInstitutions());
		getJPanelOtherInfo().add(getJLabelDemand(), getGridJLabelDemand());
		getJPanelOtherInfo().add(this.getJTextAreaDemand(), this.getGridJTextAreaDemand());
		getJPanelOtherInfo().add(getJLabelDescription(), getGridJLabelDescription());
		getJPanelOtherInfo().add(this.getJTextAreaDescription(), this.getGridJTextAreaDescription());
		getJPanelOtherInfo().add(getJLabelActuations(), getGridJLabelActuations());
		getJPanelOtherInfo().add(this.getJTextAreaActuations(), this.getGridJTextAreaActuations());

		JScrollPane scrollJTextArea = new JScrollPane(getJTextAreaInstitutions(),
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		getJPanelOtherInfo().add(scrollJTextArea, getGridJTextAreaInstitutions());

	}

	private GridBagLayout getGridLayoutHouse() {
		GridBagLayout gbl_LayoutHouse = new GridBagLayout();
		gbl_LayoutHouse.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gbl_LayoutHouse.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };

		return gbl_LayoutHouse;
	}

	private GridBagLayout getGridLayoutPanelHome() {
		GridBagLayout gbl_LaoutHome = new GridBagLayout();
		gbl_LaoutHome.columnWeights = new double[] { 0.0 };
		gbl_LaoutHome.rowWeights = new double[] {};

		return gbl_LaoutHome;
	}

	private JPanel getJPanelOtherInfo() {
		if (jPanelOtherInfo == null) {
			jPanelOtherInfo = new JPanel();
		}
		return jPanelOtherInfo;
	}

	private GridBagConstraints getGridJPanelOtherInfo() {
		GridBagConstraints gbc_jPanelHome = new GridBagConstraints();
		gbc_jPanelHome.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelHome.weighty = 1.0;
		gbc_jPanelHome.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelHome.weightx = 1.0;
		gbc_jPanelHome.insets = new Insets(0, 10, 5, 0);
		gbc_jPanelHome.gridx = 0;
		gbc_jPanelHome.gridy = 0;

		return gbc_jPanelHome;
	}

	private JLabel getJLabelInstitutions() {

		if (lblInstitution == null) {
			lblInstitution = new JLabel("Instituciones por las que han pasado (fecha, persona, institution...)  ");
			lblInstitution.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblInstitution;
	}

	private GridBagConstraints getGridJLabelInstitutions() {
		GridBagConstraints gbc_lblOtherInfo = new GridBagConstraints();
		gbc_lblOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_lblOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_lblOtherInfo.gridx = 0;
		gbc_lblOtherInfo.gridy = 0;
		return gbc_lblOtherInfo;
	}

	public JTextArea getJTextAreaInstitutions() {

		if (taInstitution == null) {
			taInstitution = new JTextArea();
			taInstitution.setRows(5);
			taInstitution.setBorder(new LineBorder(new Color(0, 0, 0)));
			taInstitution.setWrapStyleWord(true);
			taInstitution.setLineWrap(true);

		}
		return taInstitution;
	}

	private GridBagConstraints getGridJTextAreaInstitutions() {
		GridBagConstraints gbc_taOtherInfo = new GridBagConstraints();
		gbc_taOtherInfo.gridwidth = 4;
		gbc_taOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_taOtherInfo.weightx = 1.0;
		gbc_taOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_taOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_taOtherInfo.gridx = 0;
		gbc_taOtherInfo.gridy = 1;
		return gbc_taOtherInfo;
	}

	private JLabel getJLabelDemand() {

		if (lblDemand == null) {
			lblDemand = new JLabel("Demanda que realiza a Cáritas  ");
			lblDemand.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblDemand;
	}

	private GridBagConstraints getGridJLabelDemand() {
		GridBagConstraints gbc_lblOtherInfo = new GridBagConstraints();
		gbc_lblOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_lblOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_lblOtherInfo.gridx = 0;
		gbc_lblOtherInfo.gridy = 2;
		return gbc_lblOtherInfo;
	}

	public JTextArea getJTextAreaDemand() {

		if (taDemand == null) {
			taDemand = new JTextArea();
			taDemand.setRows(5);
			taDemand.setBorder(new LineBorder(new Color(0, 0, 0)));
			taDemand.setWrapStyleWord(true);
			taDemand.setLineWrap(true);

		}
		return taDemand;
	}

	private GridBagConstraints getGridJTextAreaDemand() {
		GridBagConstraints gbc_taOtherInfo = new GridBagConstraints();
		gbc_taOtherInfo.gridwidth = 4;
		gbc_taOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_taOtherInfo.weightx = 1.0;
		gbc_taOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_taOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_taOtherInfo.gridx = 0;
		gbc_taOtherInfo.gridy = 3;
		return gbc_taOtherInfo;
	}

	private JLabel getJLabelDescription() {

		if (lblDescription == null) {
			lblDescription = new JLabel("Descripción Situación / Valoración Personal");
			lblDescription.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblDescription;
	}

	private GridBagConstraints getGridJLabelDescription() {
		GridBagConstraints gbc_lblOtherInfo = new GridBagConstraints();
		gbc_lblOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_lblOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_lblOtherInfo.gridx = 0;
		gbc_lblOtherInfo.gridy = 4;
		return gbc_lblOtherInfo;
	}

	public JTextArea getJTextAreaDescription() {

		if (taDescription == null) {
			taDescription = new JTextArea();
			taDescription.setRows(5);
			taDescription.setBorder(new LineBorder(new Color(0, 0, 0)));
			taDescription.setWrapStyleWord(true);
			taDescription.setLineWrap(true);

		}
		return taDescription;
	}

	private GridBagConstraints getGridJTextAreaDescription() {
		GridBagConstraints gbc_taOtherInfo = new GridBagConstraints();
		gbc_taOtherInfo.gridwidth = 4;
		gbc_taOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_taOtherInfo.weightx = 1.0;
		gbc_taOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_taOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_taOtherInfo.gridx = 0;
		gbc_taOtherInfo.gridy = 5;
		return gbc_taOtherInfo;
	}

	private JLabel getJLabelActuations() {

		if (lblActuations == null) {
			lblActuations = new JLabel("Actuaciones:");
			lblActuations.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblActuations;
	}

	private GridBagConstraints getGridJLabelActuations() {
		GridBagConstraints gbc_lblOtherInfo = new GridBagConstraints();
		gbc_lblOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_lblOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_lblOtherInfo.gridx = 0;
		gbc_lblOtherInfo.gridy = 6;
		return gbc_lblOtherInfo;
	}

	public JTextArea getJTextAreaActuations() {

		if (taActuations == null) {
			taActuations = new JTextArea();
			taActuations.setRows(5);
			taActuations.setBorder(new LineBorder(new Color(0, 0, 0)));
			taActuations.setWrapStyleWord(true);
			taActuations.setLineWrap(true);

		}
		return taActuations;
	}

	private GridBagConstraints getGridJTextAreaActuations() {
		GridBagConstraints gbc_taOtherInfo = new GridBagConstraints();
		gbc_taOtherInfo.gridwidth = 4;
		gbc_taOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_taOtherInfo.weightx = 1.0;
		gbc_taOtherInfo.anchor = GridBagConstraints.WEST;
		gbc_taOtherInfo.insets = new Insets(10, 20, 0, 5);
		gbc_taOtherInfo.gridx = 0;
		gbc_taOtherInfo.gridy = 7;
		return gbc_taOtherInfo;
	}

	public void cleanInfo() {

		this.getJTextAreaInstitutions().setText("");
		this.getJTextAreaDemand().setText("");
		this.getJTextAreaDescription().setText("");
		this.getJTextAreaActuations().setText("");

	}

	public void fillData(OtherInfo otherInfo) {

		if (otherInfo!=null){
			this.getJTextAreaInstitutions().setText(otherInfo.getInstitutions());
			this.getJTextAreaDemand().setText(otherInfo.getDemand());
			this.getJTextAreaDescription().setText(otherInfo.getDescription());
			this.getJTextAreaActuations().setText(otherInfo.getActuations());
		}
		

	}

}
