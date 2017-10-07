package com.reparadoras.caritas.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.dao.TicketDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Ticket;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.JWindowParams;
import com.reparadoras.caritas.ui.components.combobox.ComboBoxRenderer;
import com.reparadoras.caritas.ui.components.table.PeopleTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
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

public class JManagePeople extends AbstractJInternalFrame {

	private static final long serialVersionUID = 1L;

	private JDesktopPane desktop = null;
	private JPanel jPanelFilter = null;
	private JLabel lblName = null;
	private JComboBox<People> cbPeople;
	private JButton btnSearchPeople = null;
	private JButton btnCleanPeople = null;
	
	private JPanel jPanelContent = null;
	private JPanel jPanelActions = null;
	private JButton btnNewPeople = null;
	private JButton btnViewPeople = null;
	private JButton btnUpdatePeople = null;
	private JButton btnDeletePeople = null;

	private JPanel jPanelTable = null;
	private PeopleTableModel peopleTableModel = null;
	private JTable tablePeople = null;
	private JScrollPane scrollPaneJTable = null;
	private JLabel lblDni;
	private JTextField tfDni;
	private JCheckBox ckActive;

	private PeopleDAO peopleDAO;
	private ProgramDAO programDAO;
	private TicketDAO ticketDAO;

	private JButton btnProgramPeople;
	private JButton btnTicketPeople;
	private JButton btnExitPeople;
	

	public JManagePeople(JDesktopPane desktop) {
		super(desktop);
		this.desktop = desktop;
		this.setTitle("Gestion Personas");
		this.setVisible(true);

		this.pack();
		this.setResizable(false);
		this.setClosable(true);

		peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		ticketDAO = new TicketDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		createGUIComponents();
		initComponents();

		filterPeople();

		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				// do something
				filterPeople();
				System.out.println("evento internal frame");
			}
		});

		getJButtonSearch().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filterPeople();
			}
		});
		
		getJButtonClean().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanFilter();
			}
		});

		getBtnNewPeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openEditPeople(JWindowParams.IMODE_INSERT, "Nueva Persona");
				
			}
		});

		getBtnViewPeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openEditPeople(JWindowParams.IMODE_SELECT, "Consulta Persona");
				

			}
		});

		getBtnUpdatePeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openEditPeople(JWindowParams.IMODE_UPDATE, "Edicion Persona");
				
			}
		});

		getBtnDeletePeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onDeletePeople();
				filterPeople();
			}
		});

		getBtnProgramPeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				onManageProgramPeople(JWindowParams.IMODE_UPDATE, "Programa Atención Primaria");
			}
		});

		getBtnTicketPeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				onManageTicketPeople();
			}
		});

		getBtnExitPeople().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();
			}
		});

	}

	public void createGUIComponents() {
		getContentPane().setLayout(getGridContentPane());
		getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());

		// Añado elementos del JPanelFilter
		getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
		getJPanelFilter().add(getJLabelDni(), getGridJLabelDni());
		getJPanelFilter().add(getJTextFieldDni(), getGridJTextFieldDni());

		getJPanelFilter().add(getCkActive(), getGridJCheckBoxdActive());
		getJPanelFilter().add(getJLabelName(), getGridJLabelName());
		getJPanelFilter().add(getJComboBoxPeople(), getGridJComboPeople());
		
		getJPanelFilter().add(getJButtonSearch(), getGridButtonSearch());
		getJPanelFilter().add(getJButtonClean(), getGridButtonClean());
		getJPanelFilter().add(getBtnExitPeople(), getGridButtonExit());

		// Añado elementos del JPanelContent
		getContentPane().add(getJPanelContent(), getGridJPanelContent());
		getJPanelContent().setLayout(getGridLayoutJPanelContent());

		// Añado elementos del JPanelActions
		getJPanelContent().add(getJPanelActions(), getGridJPanelActions());
		getJPanelActions().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		getJPanelActions().add(getBtnViewPeople());
		getJPanelActions().add(getBtnNewPeople());
		getJPanelActions().add(getBtnUpdatePeople());
		getJPanelActions().add(getBtnDeletePeople());
		getJPanelActions().add(getBtnProgramPeople());
		getJPanelActions().add(getBtnTicketPeople());

		getJPanelContent().add(getJPanelTable(), getGridJPanelTable());
		getJPanelTable().setLayout(getGridLayoutJPanelTable());

		getJPanelTable().add(getScrollPaneTable(), getGridJPanelScrollTable());
	}

	public void initComponents() {
		this.getCkActive().setSelected(true);
		initCbPeople();

	}

	public void initCbPeople() {

		List<People> listPeople = peopleDAO.findAll();
		
		for (People p : listPeople) {
			this.getJComboBoxPeople().addItem(p);
		}
		getJComboBoxPeople().setSelectedIndex(-1);

	}

	/* FUNCIONES DEL GETCONTENTPANE */

	private GridBagLayout getGridContentPane() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };

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
			
			((javax.swing.border.TitledBorder) jPanelFilter.getBorder()).
	        setTitleFont(new Font("Verdana", Font.ITALIC, 18));

		}

		return jPanelFilter;
	}

	private GridBagConstraints getGridJPanelFilter() {
		GridBagConstraints gbc_jPanelFilter = new GridBagConstraints();
		gbc_jPanelFilter.weightx = 1.0;
		gbc_jPanelFilter.insets = new Insets(0, 0, 5, 0);
		gbc_jPanelFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelFilter.gridx = 0;
		gbc_jPanelFilter.gridy = 0;

		return gbc_jPanelFilter;
	}

	private JLabel getJLabelName() {

		if (lblName == null) {
			lblName = new JLabel("Nombre Persona: ");
			lblName.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblName;
	}

	private GridBagConstraints getGridJLabelName() {
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 5, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;

		return gbc_lblName;
	}

	private JComboBox<People> getJComboBoxPeople() {
		if (cbPeople == null) {
			cbPeople = new JComboBox<People>();
			
			
			cbPeople.setRenderer(new ComboBoxRenderer("  -- TODOS -- "));
			cbPeople.setSelectedIndex(-1); //By default it selects first item, we don't want any selection
		}

		return cbPeople;

	}

	private GridBagConstraints getGridJComboPeople() {

		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.insets = new Insets(0, 0, 0, 5);
		gbc_tfName.weightx = 1.0;
		gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfName.gridx = 1;
		gbc_tfName.gridy = 1;

		return gbc_tfName;
	}

	private JLabel getJLabelDni() {
		if (lblDni == null) {
			lblDni = new JLabel("Dni:");
			lblDni.setFont(new Font("Verdana", Font.PLAIN, 14));
		}
		return lblDni;
	}

	private GridBagConstraints getGridJLabelDni() {
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.WEST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 0;
		gbc_lblDni.gridy = 0;

		return gbc_lblDni;
	}

	private JTextField getJTextFieldDni() {
		if (tfDni == null) {
			tfDni = new JTextField();
			tfDni.setColumns(10);
		}
		return tfDni;
	}

	private GridBagConstraints getGridJTextFieldDni() {

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

	private JButton getJButtonSearch() {
		if (btnSearchPeople == null) {
			btnSearchPeople = new JButton("Filtrar");
			btnSearchPeople
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-search.png")));
		}

		return btnSearchPeople;
	}

	private GridBagConstraints getGridButtonSearch() {

		GridBagConstraints gbc_btnSearchPeople = new GridBagConstraints();
		gbc_btnSearchPeople.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearchPeople.gridx = 2;
		gbc_btnSearchPeople.gridy = 1;

		return gbc_btnSearchPeople;
	}
	
	private JButton getJButtonClean() {
		if (btnCleanPeople == null) {
			btnCleanPeople = new JButton("Limpiar");
			btnCleanPeople
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-clean-32.png")));
		}

		return btnCleanPeople;
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
			btnExitPeople
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-exit.png")));
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
			//jPanelContent.setBorder(new LineBorder(new Color(0, 0, 0)));
			
			jPanelContent.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Listado Personas",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
			
			((javax.swing.border.TitledBorder) jPanelContent.getBorder()).
	        setTitleFont(new Font("Verdana", Font.ITALIC, 18));
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

	/* FUNCIONES DEL PANEL DE ACCIONES */

	private JPanel getJPanelActions() {

		if (jPanelActions == null) {
			jPanelActions = new JPanel();
		}
		return jPanelActions;
	}

	private GridBagConstraints getGridJPanelActions() {

		GridBagConstraints gbc_jPanelActions = new GridBagConstraints();
		gbc_jPanelActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelActions.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanelActions.insets = new Insets(0, 0, 0, 5);
		gbc_jPanelActions.gridx = 0;
		gbc_jPanelActions.gridy = 0;
		return gbc_jPanelActions;
	}

	/* BOTONES DE ACCION */

	private JButton getBtnNewPeople() {

		if (btnNewPeople == null) {
			btnNewPeople = new JButton("Nuevo");
			btnNewPeople
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-add.png")));
		}

		return btnNewPeople;
	}

	private JButton getBtnUpdatePeople() {

		if (btnUpdatePeople == null) {
			btnUpdatePeople = new JButton("Editar");
			btnUpdatePeople
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-update.png")));
		}

		return btnUpdatePeople;
	}

	private JButton getBtnDeletePeople() {

		if (btnDeletePeople == null) {
			btnDeletePeople = new JButton("Borrar");
			getBtnDeletePeople()
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-delete.png")));

		}

		return btnDeletePeople;
	}

	private JButton getBtnViewPeople() {

		if (btnViewPeople == null) {
			btnViewPeople = new JButton("Consultar");
			btnViewPeople
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-view.png")));
		}

		return btnViewPeople;
	}

	private JButton getBtnProgramPeople() {
		if (btnProgramPeople == null) {
			btnProgramPeople = new JButton("Programa Atencion Primaria");
			btnProgramPeople.setIcon(
					new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-program.png")));
		}
		return btnProgramPeople;
	}

	private JButton getBtnTicketPeople() {
		if (btnTicketPeople == null) {
			btnTicketPeople = new JButton("Vales");
			btnTicketPeople
					.setIcon(new ImageIcon(JManagePeople.class.getResource("/com/reparadoras/images/icon-ticket.png")));
		}
		return btnTicketPeople;
	}

	/* FUNCIONES DEL PANEL JTABLE */

	private JPanel getJPanelTable() {

		if (jPanelTable == null) {
			jPanelTable = new JPanel();
		}
		return jPanelTable;
	}

	private GridBagConstraints getGridJPanelTable() {
		GridBagConstraints gbc_jPanelTable = new GridBagConstraints();
		gbc_jPanelTable.weighty = 1.0;
		gbc_jPanelTable.weightx = 1.0;
		gbc_jPanelTable.fill = GridBagConstraints.BOTH;
		gbc_jPanelTable.anchor = GridBagConstraints.WEST;
		gbc_jPanelTable.gridx = 0;
		gbc_jPanelTable.gridy = 1;

		return gbc_jPanelTable;

	}

	private GridBagLayout getGridLayoutJPanelTable() {

		GridBagLayout gbl_jPanelTable = new GridBagLayout();
		gbl_jPanelTable.columnWidths = new int[] { 1 };
		gbl_jPanelTable.rowHeights = new int[] { 1 };
		gbl_jPanelTable.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_jPanelTable.rowWeights = new double[] { Double.MIN_VALUE };

		return gbl_jPanelTable;
	}

	private JScrollPane getScrollPaneTable() {
		if (scrollPaneJTable == null) {
			scrollPaneJTable = new JScrollPane(getJTablePeople());
			scrollPaneJTable.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		}

		return scrollPaneJTable;
	}

	private GridBagConstraints getGridJPanelScrollTable() {

		GridBagConstraints gbc_jPanelScroll = new GridBagConstraints();
		gbc_jPanelScroll.weighty = 1.0;
		gbc_jPanelScroll.weightx = 1.0;
		gbc_jPanelScroll.fill = GridBagConstraints.BOTH;
		gbc_jPanelScroll.anchor = GridBagConstraints.WEST;
		gbc_jPanelScroll.gridx = 0;
		gbc_jPanelScroll.gridy = 0;

		return gbc_jPanelScroll;
	}

	private JTable getJTablePeople() {
		if (tablePeople == null) {

			tablePeople = new JTable(getPeopleTableModel());
			tablePeople.setAutoCreateRowSorter(true);
			tablePeople.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tablePeople.setFont(new Font("Verdana", Font.PLAIN, 14));
			
			tablePeople.setRowMargin(5);
			tablePeople.setRowHeight(30);
			tablePeople.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
		}

		return tablePeople;
	}

	private PeopleTableModel getPeopleTableModel() {

		if (peopleTableModel == null) {
			Object[] columnIdentifiers = new Object[] { "DNI", "NOMBRE", "APELLIDO 1", "APELLIDO 2" };
			peopleTableModel = new PeopleTableModel(Arrays.asList(columnIdentifiers));
		}

		return peopleTableModel;
	}

	/* EVENTOS */

	public void cleanFilter(){
		this.getJTextFieldDni().setText("");
		this.getJComboBoxPeople().setSelectedIndex(-1);
	}
	
	public void filterPeople() {
		String filterDni = this.getJTextFieldDni().getText();
		boolean filterActive = this.getCkActive().isSelected();
		People filterPeople = new People();
		if (filterDni != null && !filterDni.equals("")) {
			filterPeople.setDni(filterDni);
		}
		
		People selectedPeople = (People)this.getJComboBoxPeople().getSelectedItem();
		if (selectedPeople != null && selectedPeople.getId()!=-1) {
			filterPeople.setName(selectedPeople.getName());
		}

		filterPeople.setActive(filterActive);

		List<People> listPeople = peopleDAO.findPeople(filterPeople);
		if (listPeople != null && !listPeople.isEmpty()) {
			this.getPeopleTableModel().clearTableModelData();
			this.getPeopleTableModel().addRows(listPeople);

		} else {
			this.getPeopleTableModel().clearTableModelData();
			JOptionPane.showMessageDialog(this, "No existen registros para los datos de búsqueda");
			
		}

	}

	private void onDeletePeople() {
		try {

			int rowIndex = this.getJTablePeople().getSelectedRow();
			if (rowIndex != -1) {

				if (JOptionPane.showConfirmDialog(null,
						"Se eliminará la persona seleccionada, sus vales y su programa de atención primaria. ¿Está seguro?",
						"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					People selectedPeople = this.getPeopleTableModel().getDomainObject(rowIndex);

					if (selectedPeople != null) {
						ticketDAO.delete(selectedPeople);
						programDAO.delete(selectedPeople);
						peopleDAO.delete(selectedPeople);
					}
					
					

					JOptionPane.showMessageDialog(this, "Se ha eliminado correctamente el registro seleccionado ",
							"Borrado Persona", JOptionPane.INFORMATION_MESSAGE);
				}


			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un registro");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se ha producido un error. No ha sido posible eliminar el registro",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void openEditPeople(int openMode, String title) {
		JManageEditPeople jManageEditPeople = null;
		try {

			if ((openMode == JWindowParams.IMODE_SELECT || openMode == JWindowParams.IMODE_UPDATE)) {
				int row = this.getJTablePeople().getSelectedRow();
				if (row != -1) {
					People people = this.getPeopleTableModel().getDomainObject(row);
					jManageEditPeople = new JManageEditPeople(this, true, openMode, title, people);
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un registro");
					return;
				}

			} else {
				jManageEditPeople = new JManageEditPeople(this, true, openMode, title, null);
			}

			this.desktop.add(jManageEditPeople);
			jManageEditPeople.setVisible(true);
			jManageEditPeople.moveToFront();
			jManageEditPeople.show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onManageProgramPeople(int openMode, String title) {
		JManageProgram jManageProgram = null;
		int row = getJTablePeople().getSelectedRow();
		
		//cuando ordeno pierde el orden. Solucion convertir la fila
		row = getJTablePeople().convertRowIndexToModel(row);

		
		if (row != -1) {
			People people = getPeopleTableModel().getDomainObject(row);

			if (people != null) {

				try {

					jManageProgram = new JManageProgram(this, true, openMode, title, people, this.desktop);
					this.desktop.add(jManageProgram);
					jManageProgram.setMaximum(true);
					jManageProgram.setMaximizable(false);
					jManageProgram.setVisible(true);
					jManageProgram.moveToFront();
					jManageProgram.show();
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// Abrir ventana programa con filtro
			}

		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
			return;
		}
	}

	public void onManageTicketPeople() {
		JManageTicket jManageTicket = null;
		int row = getJTablePeople().getSelectedRow();
		if (row != -1) {

			People people = getPeopleTableModel().getDomainObject(row);

			if (people != null) {

				try {

					jManageTicket = new JManageTicket(this, true, JWindowParams.IMODE_INSERT, title, people);
					this.desktop.add(jManageTicket);
					jManageTicket.setMaximum(true);
					jManageTicket.setMaximizable(true);
					jManageTicket.setVisible(true);
					jManageTicket.moveToFront();
					jManageTicket.show();
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un registro");
		}

	}

	
}
