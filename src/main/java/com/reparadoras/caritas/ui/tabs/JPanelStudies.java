package com.reparadoras.caritas.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.reparadoras.caritas.model.Studies;
import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;

import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class JPanelStudies extends JPanel {

	private JPanel jPanelRadioButton;
	private JRadioButton jRadioNoReadNoWrite;
	private JRadioButton jRadioReadWrite;
	private JRadioButton jRadioChild;
	private JRadioButton jRadioSchool;
	private JRadioButton jRadioHighSchool;
	private JRadioButton jRadioBachelor;
	private JRadioButton jRadioFP;
	private JRadioButton jRadioFPHigh;
	private JRadioButton jRadioUniversity;

	public JPanelStudies() {

		createGUIPanel();

	}

	private void createGUIPanel() {

		this.setLayout(getGridStudies());

		// Añado elementos del Tab Vivienda
		this.add(getJPanelRadioButton(), getGridJPanelRadio());

	}

	private GridBagLayout getGridStudies() {
		GridBagLayout gbl_LayoutStudies = new GridBagLayout();
		gbl_LayoutStudies.columnWeights = new double[] { 0.0 };
		gbl_LayoutStudies.rowWeights = new double[] { 0.0 };

		return gbl_LayoutStudies;
	}

	private JPanel getJPanelRadioButton() {
		if (jPanelRadioButton == null) {

			jPanelRadioButton = new JPanel();
			jPanelRadioButton.setLayout(new GridLayout(0, 2, 0, 0));
			jPanelRadioButton.add(getjRadioNoReadNoWrite());
			jPanelRadioButton.add(getjRadioReadWrite());
			jPanelRadioButton.add(getjRadioChild());
			jPanelRadioButton.add(getjRadioSchool());
			jPanelRadioButton.add(getjRadioHighSchool());
			jPanelRadioButton.add(getjRadioBachelor());
			jPanelRadioButton.add(getjRadioFP());
			jPanelRadioButton.add(getjRadioFPHigh());
			jPanelRadioButton.add(getjRadioUniversity());

			ButtonGroup groupL1 = new ButtonGroup();
			groupL1.add(getjRadioNoReadNoWrite());
			groupL1.add(getjRadioReadWrite());
			groupL1.add(getjRadioChild());
			groupL1.add(getjRadioSchool());
			groupL1.add(getjRadioHighSchool());
			groupL1.add(getjRadioBachelor());
			groupL1.add(getjRadioFP());
			groupL1.add(getjRadioFPHigh());
			groupL1.add(getjRadioUniversity());

		}
		return jPanelRadioButton;
	}

	private GridBagConstraints getGridJPanelRadio() {
		GridBagConstraints gbc_jPanelRadio = new GridBagConstraints();
		gbc_jPanelRadio.weighty = 1.0;
		gbc_jPanelRadio.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelRadio.weightx = 1.0;
		gbc_jPanelRadio.insets = new Insets(0, 10, 5, 0);
		gbc_jPanelRadio.gridx = 0;
		gbc_jPanelRadio.gridy = 0;

		return gbc_jPanelRadio;
	}

	public JRadioButton getjRadioNoReadNoWrite() {

		if (jRadioNoReadNoWrite == null) {
			jRadioNoReadNoWrite = new JRadioButton("Sin alfabetizar");

			jRadioNoReadNoWrite.setMargin(new Insets(20, 20, 2, 20));
			jRadioNoReadNoWrite.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioNoReadNoWrite;
	}

	public JRadioButton getjRadioReadWrite() {

		if (jRadioReadWrite == null) {
			jRadioReadWrite = new JRadioButton("Lee y escribe");

			jRadioReadWrite.setMargin(new Insets(20, 20, 2, 20));
			jRadioReadWrite.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioReadWrite;
	}

	public JRadioButton getjRadioChild() {

		if (jRadioChild == null) {
			jRadioChild = new JRadioButton("Infantil");

			jRadioChild.setMargin(new Insets(20, 20, 2, 20));
			jRadioChild.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioChild;
	}

	public JRadioButton getjRadioSchool() {

		if (jRadioSchool == null) {
			jRadioSchool = new JRadioButton("Primaria");

			jRadioSchool.setMargin(new Insets(20, 20, 2, 20));
			jRadioSchool.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioSchool;
	}

	public JRadioButton getjRadioHighSchool() {

		if (jRadioHighSchool == null) {
			jRadioHighSchool = new JRadioButton("ESO -FP Básico");

			jRadioHighSchool.setMargin(new Insets(20, 20, 2, 20));
			jRadioHighSchool.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioHighSchool;
	}

	public JRadioButton getjRadioBachelor() {

		if (jRadioBachelor == null) {
			jRadioBachelor = new JRadioButton("Bachillerato");

			jRadioBachelor.setMargin(new Insets(20, 20, 2, 20));
			jRadioBachelor.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioBachelor;
	}

	public JRadioButton getjRadioFP() {

		if (jRadioFP == null) {
			jRadioFP = new JRadioButton("FP Grado Medio con familia profesional");

			jRadioFP.setMargin(new Insets(20, 20, 2, 20));
			jRadioFP.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioFP;
	}

	public JRadioButton getjRadioFPHigh() {

		if (jRadioFPHigh == null) {
			jRadioFPHigh = new JRadioButton("FP Grado Superior con familia profesional");

			jRadioFPHigh.setMargin(new Insets(20, 20, 2, 20));
			jRadioFPHigh.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioFPHigh;
	}

	public JRadioButton getjRadioUniversity() {

		if (jRadioUniversity == null) {
			jRadioUniversity = new JRadioButton("Enseñanzas universitarias");

			jRadioUniversity.setMargin(new Insets(20, 20, 2, 20));
			jRadioUniversity.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioUniversity;
	}

	public void cleanStudies() {
		this.getjRadioNoReadNoWrite().setSelected(false);
		this.getjRadioReadWrite().setSelected(false);
		this.getjRadioChild().setSelected(false);
		this.getjRadioSchool().setSelected(false);
		this.getjRadioHighSchool().setSelected(false);
		this.getjRadioBachelor().setSelected(false);
		this.getjRadioFP().setSelected(false);
		this.getjRadioFPHigh().setSelected(false);
		this.getjRadioUniversity().setSelected(false);

	}

	public void fillData(Studies studies) {

		if (studies != null) {
			switch (studies.getId()) {
			case 1:
				this.getjRadioNoReadNoWrite().setSelected(true);
				break;
			case 2:
				this.getjRadioReadWrite().setSelected(true);
				break;
			case 3:
				this.getjRadioChild().setSelected(true);
				break;
			case 4:
				this.getjRadioSchool().setSelected(true);
				break;
			case 5:
				this.getjRadioHighSchool().setSelected(true);
				break;
			case 6:
				this.getjRadioBachelor().setSelected(true);
				break;
			case 7:
				this.getjRadioFP().setSelected(true);
				break;
			case 8:
				this.getjRadioFPHigh().setSelected(true);
				break;
			case 9:
				this.getjRadioUniversity().setSelected(true);
				break;
			}
		}
	}

}
