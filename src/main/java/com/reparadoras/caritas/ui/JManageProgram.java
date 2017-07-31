package com.reparadoras.caritas.ui;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import com.reparadoras.caritas.dao.PeopleDAO;
import com.reparadoras.caritas.dao.ProgramDAO;
import com.reparadoras.caritas.model.People;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.components.AbstractJInternalFrame;
import com.reparadoras.caritas.ui.components.TicketsPeopleTableModel;


public class JManageProgram extends AbstractJInternalFrame
{

    private static final long serialVersionUID = 1L;

    private JDesktopPane desktop = null;
    private JPanel jPanelFilter = null;
    private JLabel lblName = null;
    private JComboBox<People> cbPeople = null;
    private JPanel jPanelContent = null;
    private JPanel jPanelActions = null;

    private TicketsPeopleTableModel ticketsPeopleTableModel = null;
    private JTable tablePeople = null;
    private JScrollPane scrollPaneJTable = null;
    private JButton btnSaveTicket;
    private JButton btnFilterTicket;
    private People people = null;

    private ProgramDAO programDAO;
    private PeopleDAO peopleDAO;


    /**
     * @wbp.parser.constructor
     */
    public JManageProgram(AbstractJInternalFrame jCicIFParent, boolean modal, int executingMode, String title, People people)
    {

	super(jCicIFParent, modal);
	this.setVisible(true);
	this.pack();

	this.moveToFront();
	this.setClosable(true);
	this.setMaximizable(true);
	this.setResizable(true);
	this.setTitle(title);

	this.people = people;

	programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());

	createGUIComponents();
	initComponents();
	onFilterTicket(true);
	addListeners();
    }


    public JManageProgram(JDesktopPane desktop)
    {
	super(desktop);
	this.desktop = desktop;
	this.moveToFront();
	this.setClosable(true);
	this.setMaximizable(true);
	this.setResizable(true);
	this.setTitle(title);
	this.setVisible(true);
	this.pack();

	peopleDAO = new PeopleDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	programDAO = new ProgramDAO(MyBatisConnectionFactory.getSqlSessionFactory());

	createGUIComponents();
	initComponents();
	addListeners();

    }


    public void initComponents()
    {

	initCbPeople();
    }


    public void addListeners()
    {

	getBtnFilterTicket().addActionListener(new ActionListener()
	{

	    public void actionPerformed(ActionEvent e)
	    {

		onFilterTicket(false);
	    }
	});

    }


    public void initCbPeople()
    {

	if(this.people != null)
	{
	    this.getJComboBoxPeople().addItem(this.people);
	    this.getJComboBoxPeople().setSelectedItem(this.people);
	}
	else
	{
	    List<People> listPeople = peopleDAO.findAll();
	    People allPeople = new People();
	    allPeople.setName("TODOS");
	    allPeople.setIdPeople(-1);
	    listPeople.add(0, allPeople);
	    for (People p : listPeople)
	    {
		this.getJComboBoxPeople().addItem(p);
	    }
	}

    }


    public void createGUIComponents()
    {

	getContentPane().setLayout(getGridContentPane());
	// getContentPane().add(getJPanelFilter(), this.getGridJPanelFilter());

	// Añado elementos del JPanelFilter
	getJPanelFilter().setLayout(getGridLayoutJPanelFilter());
	getJPanelFilter().add(getJLabelName(), getGridJLabelName());
	getJPanelFilter().add(getJComboBoxPeople(), getGridJTextFieldName());
	getJPanelFilter().add(getBtnFilterTicket(), getGridJBtnFilter());

	// Añado elementos del JPanelContent
	getContentPane().add(getJPanelContent(), getGridJPanelContent());
	getJPanelContent().setLayout(getGridLayoutJPanelContent());

    }


    /* FUNCIONES DEL GETCONTENTPANE */

    private GridBagLayout getGridContentPane()
    {

	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[]
	{ 0 };
	gridBagLayout.rowHeights = new int[]
	{ 0 };
	gridBagLayout.columnWeights = new double[]
	{ 1.0 };
	gridBagLayout.rowWeights = new double[]
	{ 0.0, 1.0 };

	return gridBagLayout;
    }


    /* FUNCIONES DEL PANEL DE FILTRO */

    private GridBagLayout getGridLayoutJPanelFilter()
    {

	GridBagLayout gbl_jPanelFilter = new GridBagLayout();
	gbl_jPanelFilter.columnWidths = new int[]
	{ 211, 211, 0, 0, 0 };
	gbl_jPanelFilter.rowHeights = new int[]
	{ 20, 0, 0 };
	gbl_jPanelFilter.columnWeights = new double[]
	{ 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	gbl_jPanelFilter.rowWeights = new double[]
	{ 0.0, 0.0, Double.MIN_VALUE };

	return gbl_jPanelFilter;
    }


    private JPanel getJPanelFilter()
    {

	if(jPanelFilter == null)
	{
	    jPanelFilter = new JPanel();
	    jPanelFilter.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Busqueda Personas", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));

	}

	return jPanelFilter;
    }


    private GridBagConstraints getGridJPanelFilter()
    {

	GridBagConstraints gbc_jPanelFilter = new GridBagConstraints();
	gbc_jPanelFilter.weightx = 1.0;
	gbc_jPanelFilter.insets = new Insets(0, 0, 5, 0);
	gbc_jPanelFilter.fill = GridBagConstraints.BOTH;
	gbc_jPanelFilter.gridx = 0;
	gbc_jPanelFilter.gridy = 1;

	return gbc_jPanelFilter;
    }


    private JLabel getJLabelName()
    {

	if(lblName == null)
	{
	    lblName = new JLabel("Nombre Persona ");
	    lblName.setFont(new Font("Verdana", Font.PLAIN, 14));
	}
	return lblName;
    }


    private GridBagConstraints getGridJLabelName()
    {

	GridBagConstraints gbc_lblName = new GridBagConstraints();
	gbc_lblName.fill = GridBagConstraints.BOTH;
	gbc_lblName.insets = new Insets(0, 5, 5, 5);
	gbc_lblName.gridx = 0;
	gbc_lblName.gridy = 0;

	return gbc_lblName;
    }


    private JComboBox getJComboBoxPeople()
    {

	if(cbPeople == null)
	{
	    cbPeople = new JComboBox();

	}

	return cbPeople;

    }


    private GridBagConstraints getGridJTextFieldName()
    {

	GridBagConstraints gbc_tfName = new GridBagConstraints();
	gbc_tfName.insets = new Insets(0, 0, 5, 5);
	gbc_tfName.weightx = 1.0;
	gbc_tfName.fill = GridBagConstraints.BOTH;
	gbc_tfName.gridx = 1;
	gbc_tfName.gridy = 0;

	return gbc_tfName;
    }


    private JButton getBtnSaveTicket()
    {

	if(btnSaveTicket == null)
	{
	    btnSaveTicket = new JButton("Guardar");
	    btnSaveTicket.setIcon(new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-save.png")));
	}
	return btnSaveTicket;
    }


    private GridBagConstraints getGridJBtnFilter()
    {

	GridBagConstraints gbc_btnFilter = new GridBagConstraints();
	gbc_btnFilter.insets = new Insets(0, 0, 5, 5);
	gbc_btnFilter.weightx = 1.0;
	gbc_btnFilter.fill = GridBagConstraints.BOTH;
	gbc_btnFilter.gridx = 2;
	gbc_btnFilter.gridy = 0;

	return gbc_btnFilter;
    }


    private JButton getBtnFilterTicket()
    {

	if(btnFilterTicket == null)
	{
	    btnFilterTicket = new JButton("Buscar");

	    btnFilterTicket.setIcon(new ImageIcon(JManageProgram.class.getResource("/com/reparadoras/images/icon-search.png")));
	}
	return btnFilterTicket;
    }


    /* FUNCIONES DEL PANEL DE CONTENIDO */

    private JPanel getJPanelContent()
    {

	if(jPanelContent == null)
	{
	    jPanelContent = new JPanel();
	    jPanelContent.setBorder(new LineBorder(new Color(0, 0, 0)));
	}
	return jPanelContent;

    }


    private GridBagConstraints getGridJPanelContent()
    {

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


    private GridBagLayout getGridLayoutJPanelContent()
    {

	GridBagLayout gbl_jPanelContent = new GridBagLayout();
	gbl_jPanelContent.columnWidths = new int[]
	{ 548, 99, 1, 0 };
	gbl_jPanelContent.rowHeights = new int[]
	{ 23, 0, 0 };
	gbl_jPanelContent.columnWeights = new double[]
	{ 0.0, 0.0, 0.0, Double.MIN_VALUE };
	gbl_jPanelContent.rowWeights = new double[]
	{ 0.0, 0.0, Double.MIN_VALUE };
	return gbl_jPanelContent;
    }


    public void onFilterTicket(boolean create)
    {

	People filterPeople = (People) this.getJComboBoxPeople().getSelectedItem();

    }

}
