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

import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.ui.components.table.GroupableTableHeader;
import com.reparadoras.caritas.ui.components.table.RelativesTableModel;

import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class JPanelAuthorizationType extends JPanel{
	
	
	private JPanel jPanelRadioButton;
	private JRadioButton jRadioSARegular;
	private JRadioButton jRadioSAIrregular;
	private JRadioButton jRadioTemporalResidenceWork;
	private JRadioButton jRadioTemporalResidence;
	private JRadioButton jRadioPermanentResidence;
	private JRadioButton jRadioWork;
	private JRadioButton jRadioStudy;
	private JRadioButton jRadioTourism;
	private JRadioButton jRadioRefugee;
	private JRadioButton jRadioUndocumented;
	
	
	public JPanelAuthorizationType() {
		
		createGUIPanel();
		
		addListeners();
		
	
	}
	
	private void addListeners(){
		
		getJRadioSARegular().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JRadioButton radio = (JRadioButton) e.getSource();
				if (radio.isSelected()){
					getJRadioTemporalResidence().setEnabled(true);
					getJRadioTemporalResidenceWork().setEnabled(true);
					getJRadioPermanentResidence().setEnabled(true);
					getJRadioTourism().setEnabled(true);
					getJRadioStudy().setEnabled(true);
					getJRadioRefugee().setEnabled(true);
					getJRadioWork().setEnabled(true);
				}
				
			}
		});
		
		 getJRadioUndocumented().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JRadioButton radio = (JRadioButton) e.getSource();
				if (radio.isSelected()){
					getJRadioTemporalResidence().setEnabled(false);
					getJRadioTemporalResidence().setSelected(false);
					getJRadioTemporalResidenceWork().setEnabled(false);
					getJRadioTemporalResidenceWork().setSelected(false);
					getJRadioPermanentResidence().setEnabled(false);
					getJRadioPermanentResidence().setSelected(false);

					getJRadioTourism().setEnabled(false);
					getJRadioTourism().setSelected(false);
					getJRadioStudy().setEnabled(false);
					getJRadioStudy().setSelected(false);
					getJRadioWork().setEnabled(false);
					getJRadioWork().setSelected(false);
					
					getJRadioRefugee().setEnabled(false);
					getJRadioRefugee().setSelected(false);
				}
				
			}
		});
		
		 this.getJRadioSAIrregular().addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JRadioButton radio = (JRadioButton) e.getSource();
					if (radio.isSelected()){
						getJRadioTemporalResidence().setEnabled(false);
						getJRadioTemporalResidence().setSelected(false);
						getJRadioTemporalResidenceWork().setEnabled(false);
						getJRadioTemporalResidenceWork().setSelected(false);
						getJRadioPermanentResidence().setEnabled(false);
						getJRadioPermanentResidence().setSelected(false);

						getJRadioTourism().setEnabled(false);
						getJRadioTourism().setSelected(false);
						getJRadioStudy().setEnabled(false);
						getJRadioStudy().setSelected(false);
						getJRadioWork().setEnabled(false);
						getJRadioWork().setSelected(false);
						
						getJRadioRefugee().setEnabled(false);
						getJRadioRefugee().setSelected(false);
					}
					
				}
			});
			
		
		
		
	}
	
	private void createGUIPanel(){
		
		
		this.setLayout(getGridLayoutHouse());
		
		//Añado elementos del Tab Vivienda
		this.add(getJPanelRadioButton(), getGridJPanelRadio());
		
		
	}
	
	private GridBagLayout getGridLayoutHouse() {
		GridBagLayout gbl_LayoutHouse = new GridBagLayout();
		gbl_LayoutHouse.columnWeights = new double[] { 0.0};
		gbl_LayoutHouse.rowWeights = new double[] { 0.0};

		return gbl_LayoutHouse;
	}
	
	
	private JPanel getJPanelRadioButton() {
		if (jPanelRadioButton == null) {
		
			jPanelRadioButton = new JPanel();
			jPanelRadioButton.setLayout(new GridLayout(0, 1, 0, 0));
			jPanelRadioButton.add(getJRadioSARegular());
			jPanelRadioButton.add(getJRadioTemporalResidence());
			jPanelRadioButton.add(getJRadioTemporalResidenceWork());
			jPanelRadioButton.add(getJRadioPermanentResidence());
			
			jPanelRadioButton.add(getJRadioStudy());
			jPanelRadioButton.add(getJRadioTourism());
			jPanelRadioButton.add(getJRadioWork());
			jPanelRadioButton.add(getJRadioRefugee());
			jPanelRadioButton.add(getJRadioUndocumented());
			jPanelRadioButton.add(getJRadioSAIrregular());
			
			ButtonGroup groupL2 = new ButtonGroup();
			groupL2.add(getJRadioTemporalResidence());
			groupL2.add(getJRadioTemporalResidenceWork());
			groupL2.add(getJRadioPermanentResidence());
			groupL2.add(getJRadioStudy());
			groupL2.add(getJRadioWork());
			groupL2.add(getJRadioTourism());
			groupL2.add(getJRadioRefugee());
			
			ButtonGroup groupL1 = new ButtonGroup();
			groupL1.add(getJRadioSARegular());
			groupL1.add(getJRadioUndocumented());
			groupL1.add(getJRadioSAIrregular());
			
			
			
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
	
	public JRadioButton getJRadioSARegular() {
		if (jRadioSARegular == null) {
			jRadioSARegular = new JRadioButton("Extracomunitario (No UE)");
			
			jRadioSARegular.setMargin(new Insets(20, 20, 2, 20));
			jRadioSARegular.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioSARegular;
	}
	
	public JRadioButton getJRadioSAIrregular() {
		if (jRadioSAIrregular == null) {
			jRadioSAIrregular = new JRadioButton("P.Situación administrativa irregular");
			jRadioSAIrregular.setMargin(new Insets(20, 20, 2, 20));
			jRadioSAIrregular.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioSAIrregular;
	}
	
	public JRadioButton getJRadioUndocumented() {
		if (jRadioUndocumented == null) {
			jRadioUndocumented = new JRadioButton("Sin documentar");
			jRadioUndocumented.setMargin(new Insets(20, 20, 2, 20));
			jRadioUndocumented.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioUndocumented;
	}
	
	public JRadioButton getJRadioTemporalResidence() {
		if (jRadioTemporalResidence == null) {
			jRadioTemporalResidence = new JRadioButton("Residencia Temporal");
			jRadioTemporalResidence.setMargin(new Insets(2, 50, 2, 20));
			jRadioTemporalResidence.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioTemporalResidence;
	}
	
	public JRadioButton getJRadioTemporalResidenceWork() {
		if (jRadioTemporalResidenceWork == null) {
			jRadioTemporalResidenceWork = new JRadioButton("Residencia y trabajo temporal");
			jRadioTemporalResidenceWork.setMargin(new Insets(2, 50, 2, 20));
			jRadioTemporalResidenceWork.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioTemporalResidenceWork;
	}
	
	public JRadioButton getJRadioPermanentResidence() {
		if (jRadioPermanentResidence == null) {
			jRadioPermanentResidence = new JRadioButton("Residencia permanente");
			jRadioPermanentResidence.setMargin(new Insets(2, 50, 2, 20));
			jRadioPermanentResidence.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioPermanentResidence;
	}
	
	public JRadioButton getJRadioStudy() {
		if (jRadioStudy == null) {
			jRadioStudy = new JRadioButton("Estudios");
			jRadioStudy.setMargin(new Insets(2, 50, 2, 20));
			jRadioStudy.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioStudy;
	}
	
	public JRadioButton getJRadioTourism() {
		if (jRadioTourism == null) {
			jRadioTourism = new JRadioButton("Turista");
			jRadioTourism.setMargin(new Insets(2, 50, 2, 20));
			jRadioTourism.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioTourism;
	}
	
	public JRadioButton getJRadioWork() {
		if (jRadioWork == null) {
			jRadioWork = new JRadioButton("Trabajo");
			jRadioWork.setMargin(new Insets(2, 50, 2, 20));
			jRadioWork.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioWork;
	}
	
	public JRadioButton getJRadioRefugee() {
		if (jRadioRefugee == null) {
			jRadioRefugee = new JRadioButton("Refugiado");
			jRadioRefugee.setMargin(new Insets(2, 50, 2, 20));
			jRadioRefugee.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return jRadioRefugee;
	}
	
	
	public void cleanAuthorization(){
		
		this.getJRadioTemporalResidence().setSelected(false);
		this.getJRadioSARegular().setSelected(false);
		this.getJRadioTemporalResidenceWork().setSelected(false);
		this.getJRadioPermanentResidence().setSelected(false);
		this.getJRadioStudy().setSelected(false);
		this.getJRadioTourism().setSelected(false);
		this.getJRadioWork().setSelected(false);
		this.getJRadioRefugee().setSelected(false);
		this.getJRadioUndocumented().setSelected(false);
		this.getJRadioSAIrregular().setSelected(false);
		
		
	}
	
	public void fillData(AuthorizationType aType){
		
		if (aType != null) {
			switch (aType.getId()) {
			case 1:
				this.getJRadioTemporalResidence().setSelected(true);
				this.getJRadioSARegular().setSelected(true);
				break;
			case 2:
				this.getJRadioTemporalResidenceWork().setSelected(true);
				this.getJRadioSARegular().setSelected(true);
				break;
			case 3:
				this.getJRadioStudy().setSelected(true);
				this.getJRadioSARegular().setSelected(true);
				break;
			case 4:
				this.getJRadioTourism().setSelected(true);
				this.getJRadioSARegular().setSelected(true);
				break;
			case 5:
				this.getJRadioRefugee().setSelected(true);
				this.getJRadioSARegular().setSelected(true);
				break;
			case 6:
				this.getJRadioUndocumented().setSelected(true);
				this.getJRadioSARegular().setSelected(false);
				break;
			case 7:
				this.getJRadioSAIrregular().setSelected(true);
				this.getJRadioSARegular().setSelected(false);
				break;
			case 8:
				this.getJRadioPermanentResidence().setSelected(true);
				this.getJRadioSARegular().setSelected(true);
				break;
			case 9:
				this.getJRadioWork().setSelected(true);
				this.getJRadioSARegular().setSelected(true);
				break;
				
			}
		}
	}

	
	
	
	
	
	
	
	
}
