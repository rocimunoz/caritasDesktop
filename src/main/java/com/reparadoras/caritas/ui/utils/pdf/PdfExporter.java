package com.reparadoras.caritas.ui.utils.pdf;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.net.URLClassLoader;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Color;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;

import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseField;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfBorderDictionary;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RadioCheckField;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.reparadoras.caritas.CaritasGUI;
import com.reparadoras.caritas.dao.AuthorizationTypeDAO;
import com.reparadoras.caritas.dao.ExpensesDAO;
import com.reparadoras.caritas.dao.IncomesDAO;
import com.reparadoras.caritas.dao.JobSituationDAO;
import com.reparadoras.caritas.dao.RelativeDAO;
import com.reparadoras.caritas.dao.StudiesDAO;
import com.reparadoras.caritas.model.Address;
import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.Expense;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.Income;
import com.reparadoras.caritas.model.JobSituation;
import com.reparadoras.caritas.model.OtherInfo;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.model.Studies;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.JManageProgram;
import com.reparadoras.caritas.ui.tabs.JPanelEconomicSituation;

public class PdfExporter {

	static final Logger logger = Logger.getLogger(JManageProgram.class);

	private static final String PDF_EXTENSION = ".pdf";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	


	private static final Font TITLE_12_FONT = new Font(FontFamily.HELVETICA, 12, Font.NORMAL);
	private static final Font TITLE_10_FONT_BOLD = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font TITLE_10_FONT = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
	



	private RelativeDAO relativeDAO;
	private AuthorizationTypeDAO authorizationTypeDAO;
	private JobSituationDAO jobSituationDAO;
	private StudiesDAO studiesDAO;
	private IncomesDAO incomesDAO;
	private ExpensesDAO expensesDAO;

	public File export(Program program, File file) throws DocumentException, IOException {

		
		
		try {

			relativeDAO = new RelativeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			authorizationTypeDAO = new AuthorizationTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			jobSituationDAO = new JobSituationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			studiesDAO = new StudiesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			expensesDAO = new ExpensesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			incomesDAO = new IncomesDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();

			addMetaData(document);
			addTitlePage(document);
			
			addAddressAndHome(document, program.getFamily().getHome());
			
			
			addFamily(document, program.getFamily());
			addFamilyType(document, program.getFamily());
			addAuthorizationType(document, program.getAuthorizationType());
			addJobSituation(document, program.getJobSituation());
			addStudies(document, program.getStudies());
			addIncomes(document, program);
			addExpenses(document, program);
			addOtherInfo(document, program.getOtherInfo());
			document.close();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

		return file;
	}

	private void addMetaData(Document document) {
		document.addTitle("My first PDF");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("Lars Vogel");
		document.addCreator("Lars Vogel");
	}

	private void addTitlePage(Document document) throws DocumentException, MalformedURLException, IOException {
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
		
		addEmptyLine(paragraph, 1);
		
		paragraph.add(new Paragraph("Nº SICCE _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("F. ALTA _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("NOMBRE _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("APELLIDOS _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("DNI/NIE _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("F.NACIMIENTO _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("PAIS ORIGEN _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("AÑO LLEGADA ESPAÑA _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("PROYECTO _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("ATIENDE _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("CÁRITAS PARROQUIAL _____________________", TITLE_12_FONT));
		addEmptyLine(paragraph, 1);
		addEmptyLine(paragraph, 1);

		ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        	logger.info(url.getFile());
        }
		
		Image img = Image.getInstance(PdfExporter.class.getResource("/img/logo2.PNG"));
		
		img.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);
		document.add(img);
		
		document.newPage();
	}

	private void addAddressAndHome(Document document, Home home) throws DocumentException {

		Paragraph paragraph = new Paragraph();
		
		paragraph.add(new Paragraph("DIRECCION", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("Municipio: " + getNullRepresentation(home.getAddress().getTown()), TITLE_10_FONT));
		
		paragraph.add(new Paragraph("Calle:  " + getNullRepresentation(home.getAddress().getStreet()), TITLE_10_FONT));
	
		paragraph.add(new Paragraph("Portal: " + getNullRepresentation(home.getAddress().getGate()), TITLE_10_FONT));
		
		paragraph.add(new Paragraph("Piso y Mano:  " + getNullRepresentation(home.getAddress().getFloor()), TITLE_10_FONT));
		
		paragraph.add(new Paragraph("Tfno. domicilio:  " + getNullRepresentation(home.getAddress().getTelephone()), TITLE_10_FONT));
		
		paragraph.add(new Paragraph("Tfno. contacto:  " + getNullRepresentation(home.getAddress().getTelephoneContact()), TITLE_10_FONT));
		
		paragraph.add(new Paragraph("Fecha Padron:  " + this.getNullDateRepresentation(home.getAddress().getCensus()), TITLE_10_FONT));
		
		paragraph.add(new Paragraph("Lugar:  " + getNullRepresentation(home.getAddress().getPlace()), TITLE_10_FONT));
		
		
		addEmptyLine(paragraph,1);
		
		
		
		Paragraph paragraphHome = new Paragraph();

		paragraphHome.add(new Paragraph("DATOS VIVIENDA", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraphHome, 1);
		if (home.getHomeType()!=null){
			paragraphHome.add(new Paragraph("Tipo: " + getNullRepresentation(home.getHomeType().getDescription()), TITLE_10_FONT));
		}else{
			paragraphHome.add(new Paragraph("Tipo: " + "", TITLE_10_FONT));
		}
		
		
		paragraphHome.add(new Paragraph("Régimen Tenencia:  " + getNullRepresentation(home.getRegHolding()), TITLE_10_FONT));
		
		paragraphHome.add(new Paragraph("Número Habitaciones:  " + getIntNullRepresentation(home.getNumberRooms()),TITLE_10_FONT));
		
		paragraphHome.add(new Paragraph("Número personas que residen:  " + getIntNullRepresentation(home.getNumberPeople()),TITLE_10_FONT));
		
		paragraphHome.add(new Paragraph("Número familias nucleares:  " + getIntNullRepresentation(home.getNumberFamilies()),TITLE_10_FONT));
		
		paragraphHome.add(new Paragraph("Otros datos:  " + getNullRepresentation(home.getOtherInfo()), TITLE_10_FONT));
		addEmptyLine(paragraphHome,1);
		
		document.add(paragraph);
		
		document.add(paragraphHome);

		
	}

	

	private void addFamily(Document document, Family family) throws DocumentException, IOException {

		List<Relative> relatives = new ArrayList<Relative>();
		relatives = family.getRelatives();

		Relative relativeFilter = new Relative();
		relativeFilter.setFamily(family);
		relatives = relativeDAO.findRelative(relativeFilter);

		Paragraph paragraphtitulo = new Paragraph();

		paragraphtitulo.add(new Paragraph("COMPOSICIÓN FAMILIAR", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraphtitulo, 1);

		PdfPTable table = new PdfPTable(5);
		table.setTotalWidth(new float[] { 100, 100, 100, 100, 100});
		table.setLockedWidth(true);

		// first row
		PdfPCell cell = new PdfPCell(new Phrase("PARENTESCO", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		// second row
		cell = new PdfPCell(new Phrase("CONVIVE/TRABAJAR", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("NOMBRE", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("F.NAC", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("SITUACION", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		//table.setHeaderRows(1);
		if (relatives != null && !relatives.isEmpty()) {

			for (Relative relative : relatives) {
				cell = new PdfPCell(new Phrase(relative.getRelationShip(), TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(relative.getLiveWork(), TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(relative.getName(), TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				table.addCell(cell);
				
				
				cell = new PdfPCell(new Phrase(sdf.format(relative.getDateBorn()), TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(relative.getSituation(), TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				table.addCell(cell);
			}
		}else{
			createEmptyCells(5, table);
			
		}

		document.add(paragraphtitulo);

		document.add(table);

	}
	
	private void createEmptyCells(int num, PdfPTable table ){
		
		for (int i=0;i<num;i++){
			PdfPCell cell = new PdfPCell(new Phrase("", TITLE_10_FONT));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);
		}
	}

	private void addFamilyType(Document document, Family family) throws DocumentException, IOException {

		Paragraph paragraphtitulo = new Paragraph();

		paragraphtitulo.add(new Paragraph("Tipo de familia", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraphtitulo, 1);

		PdfPTable tableChecks = new PdfPTable(5);
		tableChecks.setWidthPercentage(100);

		Phrase sola = null;
		Phrase parejaHijos = null;
		Phrase parejaSinHijos = null ;
		Phrase monoparental = null; 
		Phrase otra = null ; 
		Phrase noDisponible = null;

		if (family.getFamilyType() != null) {
			FamilyType type = family.getFamilyType();
			if (type.getDescription().equals("Sola")) {
				sola = new Phrase(this.getCheckTrue("","SOLA"));
				parejaHijos= new Phrase(this.getCheckFalse("","PAREJA CON HIJOS"));
				parejaSinHijos= new Phrase(this.getCheckFalse("","PAREJA SIN HIJOS"));
				monoparental= new Phrase(this.getCheckFalse("","MONOPARENTAL"));
				otra= new Phrase(this.getCheckFalse("","OTRA"));
			} else if (type.getDescription().equals("Pareja con Hijos")) {
				sola= new Phrase(this.getCheckFalse("","SOLA"));
				parejaHijos= new Phrase(this.getCheckTrue("","PAREJA CON HIJOS"));
				parejaSinHijos= new Phrase(this.getCheckFalse("","PAREJA SIN HIJOS"));
				monoparental= new Phrase(this.getCheckFalse("","MONOPARENTAL"));
				otra= new Phrase(this.getCheckFalse("","OTRA"));
			} else if (type.getDescription().equals("Pareja sin Hijos")) {
				sola= new Phrase(this.getCheckFalse("","SOLA"));
				parejaHijos= new Phrase(this.getCheckFalse("","PAREJA CON HIJOS"));
				parejaSinHijos= new Phrase(this.getCheckTrue("","PAREJA SIN HIJOS"));
				monoparental= new Phrase(this.getCheckFalse("","MONOPARENTAL"));
				otra= new Phrase(this.getCheckFalse("","OTRA"));
			} else if (type.getDescription().equals("Monoparental")) {
				sola= new Phrase(this.getCheckFalse("","SOLA"));
				parejaHijos= new Phrase(this.getCheckFalse("","PAREJA CON HIJOS"));
				parejaSinHijos= new Phrase(this.getCheckFalse("","PAREJA SIN HIJOS"));
				monoparental= new Phrase(this.getCheckTrue("", "MONOPARENTAL"));
				otra = new Phrase(this.getCheckFalse("","OTRA"));
			} else if (type.getDescription().equals("Otra")) {
				sola= new Phrase(this.getCheckFalse("","SOLA"));
				parejaHijos= new Phrase(this.getCheckFalse("","PAREJA CON HIJOS"));
				parejaSinHijos= new Phrase(this.getCheckFalse("","PAREJA SIN HIJOS"));
				monoparental= new Phrase(this.getCheckFalse("","MONOPARENTAL"));
				otra= new Phrase(this.getCheckTrue("","SOLA"));
			}
			
			
			
		}else{
			sola= new Phrase(this.getCheckFalse("","SOLA"));
			parejaHijos= new Phrase(this.getCheckFalse("","PAREJA CON HIJOS"));
			parejaSinHijos= new Phrase(this.getCheckFalse("","PAREJA SIN HIJOS"));
			monoparental= new Phrase(this.getCheckFalse("","MONOPARENTAL"));
			otra= new Phrase(this.getCheckFalse("","SOLA"));
		}

		// Sola
					PdfPCell cellCheckTypeFamily = new PdfPCell();
					cellCheckTypeFamily = new PdfPCell(sola);
					setCellStyleTableNoBorder(cellCheckTypeFamily);
					tableChecks.addCell(cellCheckTypeFamily);
					

					// Pareja Con hijos
					cellCheckTypeFamily = new PdfPCell(parejaHijos);
					setCellStyleTableNoBorder(cellCheckTypeFamily);
					tableChecks.addCell(cellCheckTypeFamily);
					

					// Pareja Sin hijos
					cellCheckTypeFamily = new PdfPCell(parejaSinHijos);
					setCellStyleTableNoBorder(cellCheckTypeFamily);
					tableChecks.addCell(cellCheckTypeFamily);
					

					// Monoparental
					cellCheckTypeFamily = new PdfPCell(monoparental);
					setCellStyleTableNoBorder(cellCheckTypeFamily);
					tableChecks.addCell(cellCheckTypeFamily);
					

					// otra
					cellCheckTypeFamily = new PdfPCell(otra);
					cellCheckTypeFamily.setFixedHeight(30);
					cellCheckTypeFamily.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellCheckTypeFamily.setBorder(Rectangle.NO_BORDER);
					tableChecks.addCell(cellCheckTypeFamily);
		
		

		Paragraph paragrap = new Paragraph();

		paragrap.add(new Paragraph("Otros datos:  " + getNullRepresentation(family.getOtherInfo()), TITLE_10_FONT));

		addEmptyLine(paragrap, 1);

		document.add(paragraphtitulo);
		document.add(tableChecks);
		document.add(paragrap);

		

	}
	
private void addAuthorizationType(Document document, AuthorizationType atype) throws DocumentException, IOException{
		
		
		
		Paragraph paragraphtitulo = new Paragraph();

		paragraphtitulo.add(new Paragraph("TIPO DE AUTORIZACIÓN", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraphtitulo, 1);

		Phrase regular = null;
		String regularCaption = "SITUACION ADMTVA. REGULAR";
		Phrase residencia = null;
		String residenciaCaption ="AUTORIZACION RESIDENCIA";
		Phrase residenciaTrabajo = null ;
		String residenciaTrabajoCaption = "AUT. RESIDENCIA Y TRABAJO";
		Phrase estudios = null; 
		String estudiosCaption = "ESTUDIOS";
		Phrase turismo = null ; 
		String turismoCaption = "TURISMO";
		Phrase refugiado = null;
		String refugiadoCaption = "REFUGIADO";
		Phrase indocumentado = null;
		String indocumentadoCaption = "INDOCUMENTADO";
		Phrase irregular = null;
		String irregularCaption = "SITUACIÓN ADMTVA IRREGULAR";
		
		String tab = "     ";
		String noTab ="";
		

		if (atype != null) {
			AuthorizationType atypeFilter = new AuthorizationType();
			atypeFilter.setId(atype.getId());
			AuthorizationType atypeBBDD = authorizationTypeDAO.findAuthorizationType(atypeFilter);
			if (atypeBBDD.getDescription().equals("Autorización Residencia")) {
				regular = new Phrase(this.getCheckTrue(noTab,regularCaption));
				residencia= new Phrase(this.getCheckTrue(tab,residenciaCaption));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,residenciaTrabajoCaption));
				estudios= new Phrase(this.getCheckFalse(tab,estudiosCaption));
				turismo= new Phrase(this.getCheckFalse(tab,turismoCaption));
				refugiado= new Phrase(this.getCheckFalse(tab,refugiadoCaption));
				indocumentado= new Phrase(this.getCheckFalse(noTab,indocumentadoCaption));
				irregular= new Phrase(this.getCheckFalse(noTab,irregularCaption));
			} else if (atypeBBDD.getDescription().equals("Autorización Residencia y Trabajo")) {
				regular = new Phrase(this.getCheckTrue(noTab,regularCaption));
				residencia= new Phrase(this.getCheckFalse(tab,residenciaCaption));
				residenciaTrabajo= new Phrase(this.getCheckTrue(tab,residenciaTrabajoCaption));
				estudios= new Phrase(this.getCheckFalse(tab,estudiosCaption));
				turismo= new Phrase(this.getCheckFalse(tab,turismoCaption));
				refugiado= new Phrase(this.getCheckFalse(tab,refugiadoCaption));
				indocumentado= new Phrase(this.getCheckFalse(noTab,indocumentadoCaption));
				irregular= new Phrase(this.getCheckFalse(noTab,irregularCaption));
			} else if (atypeBBDD.getDescription().equals("Estudios")) {
				regular = new Phrase(this.getCheckTrue(noTab,regularCaption));
				residencia= new Phrase(this.getCheckFalse(tab,residenciaCaption));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,residenciaTrabajoCaption));
				estudios= new Phrase(this.getCheckTrue(tab,estudiosCaption));
				turismo= new Phrase(this.getCheckFalse(tab,turismoCaption));
				refugiado= new Phrase(this.getCheckFalse(tab,refugiadoCaption));
				indocumentado= new Phrase(this.getCheckFalse(noTab,indocumentadoCaption));
				irregular= new Phrase(this.getCheckFalse(noTab,irregularCaption));
			} else if (atypeBBDD.getDescription().equals("Turismo")) {
				regular = new Phrase(this.getCheckTrue(noTab,regularCaption));
				residencia= new Phrase(this.getCheckFalse(tab,residenciaCaption));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,residenciaTrabajoCaption));
				estudios= new Phrase(this.getCheckFalse(tab,estudiosCaption));
				turismo= new Phrase(this.getCheckTrue(tab,turismoCaption));
				refugiado= new Phrase(this.getCheckFalse(tab,refugiadoCaption));
				indocumentado= new Phrase(this.getCheckFalse(noTab,indocumentadoCaption));
				irregular= new Phrase(this.getCheckFalse(noTab,irregularCaption));
			} else if (atypeBBDD.getDescription().equals("Refugiado")) {
				regular = new Phrase(this.getCheckTrue(noTab,regularCaption));
				residencia= new Phrase(this.getCheckFalse(tab,residenciaCaption));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,residenciaTrabajoCaption));
				estudios= new Phrase(this.getCheckFalse(tab,estudiosCaption));
				turismo= new Phrase(this.getCheckFalse(tab,turismoCaption));
				refugiado= new Phrase(this.getCheckTrue(tab,refugiadoCaption));
				indocumentado= new Phrase(this.getCheckFalse(noTab,indocumentadoCaption));
				irregular= new Phrase(this.getCheckFalse(noTab,irregularCaption));
			} else if (atypeBBDD.getDescription().equals("Indocumentado")) {
				regular = new Phrase(this.getCheckFalse(noTab,regularCaption));
				residencia= new Phrase(this.getCheckFalse(tab,residenciaCaption));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,residenciaTrabajoCaption));
				estudios= new Phrase(this.getCheckFalse(tab,estudiosCaption));
				turismo= new Phrase(this.getCheckFalse(tab,turismoCaption));
				refugiado= new Phrase(this.getCheckFalse(tab,refugiadoCaption));
				indocumentado= new Phrase(this.getCheckTrue(noTab,indocumentadoCaption));
				irregular= new Phrase(this.getCheckFalse(noTab,irregularCaption));
			}
			else if (atypeBBDD.getDescription().equals("Irregular")) {
				regular = new Phrase(this.getCheckFalse(noTab,regularCaption));
				residencia= new Phrase(this.getCheckFalse(tab,residenciaCaption));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,residenciaTrabajoCaption));
				estudios= new Phrase(this.getCheckFalse(tab,estudiosCaption));
				turismo= new Phrase(this.getCheckFalse(tab,turismoCaption));
				refugiado= new Phrase(this.getCheckFalse(tab,refugiadoCaption));
				indocumentado= new Phrase(this.getCheckFalse(noTab,indocumentadoCaption));
				irregular= new Phrase(this.getCheckTrue(noTab,irregularCaption));
			}
		}
		else{
			regular = new Phrase(this.getCheckFalse(noTab,regularCaption));
			residencia= new Phrase(this.getCheckFalse(tab,residenciaCaption));
			residenciaTrabajo= new Phrase(this.getCheckFalse(tab,residenciaTrabajoCaption));
			estudios= new Phrase(this.getCheckFalse(tab,estudiosCaption));
			turismo= new Phrase(this.getCheckFalse(tab,turismoCaption));
			refugiado= new Phrase(this.getCheckFalse(tab,refugiadoCaption));
			indocumentado= new Phrase(this.getCheckFalse(noTab,indocumentadoCaption));
			irregular= new Phrase(this.getCheckFalse(noTab,irregularCaption));
		}

		List<Phrase> listaCeldas =new ArrayList<Phrase>();
		listaCeldas.addAll(Arrays.asList(regular, residencia, residenciaTrabajo, estudios, turismo, refugiado, indocumentado, irregular));
		
		PdfPTable tableChecks = new PdfPTable(1);
		tableChecks.setWidthPercentage(100);
		
		for (Phrase phrase : listaCeldas) {
			PdfPCell cellCheckTypeFamily = new PdfPCell();
			cellCheckTypeFamily = new PdfPCell(phrase);
			setCellStyleTableNoBorder(cellCheckTypeFamily);
			tableChecks.addCell(cellCheckTypeFamily);
		}
		

		document.add(paragraphtitulo);
		document.add(tableChecks);
		
	}
	
private void addJobSituation(Document document, JobSituation jType) throws DocumentException, IOException{
	
	Paragraph paragraphtitulo = new Paragraph();

	paragraphtitulo.add(new Paragraph("SITUACIÓN LABORAL", TITLE_10_FONT_BOLD));
	addEmptyLine(paragraphtitulo, 1);

	Phrase parado = null;
	String paradoCaption = "PARADO";
	Phrase tNormalizado = null;
	String tNormalizadoCaption ="CON TRABAJO NORMALIZADO";
	Phrase tMarginal = null ;
	String tMarginalCaption = "CON TRABAJO MARGINAL O ECONOMÍA SUMERGIDA";
	Phrase hogar = null; 
	String hogarCaption = "LABORES DEL HOGAR (AMA DE CASA)";
	Phrase pensionista = null ; 
	String pensionistaCaption = "PENSIONISTA O JUBILADO";
	Phrase otro = null;
	String otroCaption = "OTROS INACTIVOS (ESTUDIANTES, MENORES)";
	
	String noTab ="";
	

	if (jType != null) {
		JobSituation jtypeFilter = new JobSituation();
		jtypeFilter.setId(jType.getId());
		JobSituation jTypeBBDD = jobSituationDAO.findJobSituation(jtypeFilter);
		if (jTypeBBDD.getDescription().equals("Parado")) {
			parado = new Phrase(this.getCheckTrue(noTab,paradoCaption));
			tNormalizado= new Phrase(this.getCheckFalse(noTab,tNormalizadoCaption));
			tMarginal= new Phrase(this.getCheckFalse(noTab,tMarginalCaption));
			hogar= new Phrase(this.getCheckFalse(noTab,hogarCaption));
			pensionista= new Phrase(this.getCheckFalse(noTab,pensionistaCaption));
			otro= new Phrase(this.getCheckFalse(noTab,otroCaption));
		} else if (jTypeBBDD.getDescription().equals("Con Trabajo Normalizado")) {
			parado = new Phrase(this.getCheckFalse(noTab,paradoCaption));
			tNormalizado= new Phrase(this.getCheckTrue(noTab,tNormalizadoCaption));
			tMarginal= new Phrase(this.getCheckFalse(noTab,tMarginalCaption));
			hogar= new Phrase(this.getCheckFalse(noTab,hogarCaption));
			pensionista= new Phrase(this.getCheckFalse(noTab,pensionistaCaption));
			otro= new Phrase(this.getCheckFalse(noTab,otroCaption));
		} else if (jTypeBBDD.getDescription().equals("Con Trabajo Marginal o Economia Sumergida")) {
			parado = new Phrase(this.getCheckFalse(noTab,paradoCaption));
			tNormalizado= new Phrase(this.getCheckFalse(noTab,tNormalizadoCaption));
			tMarginal= new Phrase(this.getCheckTrue(noTab,tMarginalCaption));
			hogar= new Phrase(this.getCheckFalse(noTab,hogarCaption));
			pensionista= new Phrase(this.getCheckFalse(noTab,pensionistaCaption));
			otro= new Phrase(this.getCheckFalse(noTab,otroCaption));
		} else if (jTypeBBDD.getDescription().equals("Labores del hogar (ama de casa)")) {
			parado = new Phrase(this.getCheckFalse(noTab,paradoCaption));
			tNormalizado= new Phrase(this.getCheckFalse(noTab,tNormalizadoCaption));
			tMarginal= new Phrase(this.getCheckFalse(noTab,tMarginalCaption));
			hogar= new Phrase(this.getCheckTrue(noTab,hogarCaption));
			pensionista= new Phrase(this.getCheckFalse(noTab,pensionistaCaption));
			otro= new Phrase(this.getCheckFalse(noTab,otroCaption));
		} else if (jTypeBBDD.getDescription().equals("Pensionista o Jubilado")) {
			parado = new Phrase(this.getCheckFalse(noTab,paradoCaption));
			tNormalizado= new Phrase(this.getCheckFalse(noTab,tNormalizadoCaption));
			tMarginal= new Phrase(this.getCheckFalse(noTab,tMarginalCaption));
			hogar= new Phrase(this.getCheckFalse(noTab,hogarCaption));
			pensionista= new Phrase(this.getCheckTrue(noTab,pensionistaCaption));
			otro= new Phrase(this.getCheckFalse(noTab,otroCaption));
		} else if (jTypeBBDD.getDescription().equals("Otros inactivos (estudiantes, menores")) {
			parado = new Phrase(this.getCheckFalse(noTab,paradoCaption));
			tNormalizado= new Phrase(this.getCheckFalse(noTab,tNormalizadoCaption));
			tMarginal= new Phrase(this.getCheckFalse(noTab,tMarginalCaption));
			hogar= new Phrase(this.getCheckFalse(noTab,hogarCaption));
			pensionista= new Phrase(this.getCheckFalse(noTab,pensionistaCaption));
			otro= new Phrase(this.getCheckTrue(noTab,otroCaption));
		}
	}else{
		parado = new Phrase(this.getCheckFalse(noTab,paradoCaption));
		tNormalizado= new Phrase(this.getCheckFalse(noTab,tNormalizadoCaption));
		tMarginal= new Phrase(this.getCheckFalse(noTab,tMarginalCaption));
		hogar= new Phrase(this.getCheckFalse(noTab,hogarCaption));
		pensionista= new Phrase(this.getCheckFalse(noTab,pensionistaCaption));
		otro= new Phrase(this.getCheckFalse(noTab,otroCaption));
	}

	PdfPTable tableChecks = new PdfPTable(1);
	tableChecks.setWidthPercentage(100);
	
	List<Phrase> listaCeldas =new ArrayList<Phrase>();
	listaCeldas.addAll(Arrays.asList(parado, tNormalizado, tMarginal, hogar, pensionista, otro));
	
	for (Phrase phrase : listaCeldas) {
		PdfPCell cellCheckTypeFamily = new PdfPCell();
		cellCheckTypeFamily = new PdfPCell(phrase);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
	}
	
	document.add(paragraphtitulo);
	document.add(tableChecks);
	
}

	private void addStudies(Document document, Studies studies) throws DocumentException, IOException{
		
		Paragraph paragraphtitulo = new Paragraph();

		paragraphtitulo.add(new Paragraph("ESTUDIOS", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraphtitulo, 1);

		Phrase noLee = null;
		String noLeeCaption = "NO SABE LEER NI ESCRIBIR";
		Phrase lee = null;
		String leeCaption ="SOLO SABE LEER Y ESCRIBIR";
		Phrase infantil = null ;
		String infantilCaption = "INFANTIL";
		Phrase primaria = null; 
		String primariaCaption = "PRIMARIA";
		Phrase secundaria = null ; 
		String secundariaCaption = "SECUNDARIA";
		Phrase bachillerato = null;
		String bachilleratoCaption = "BACHILLERATO";
		Phrase fpMedio = null;
		String fpMedioCaption = "FP-GRADO MEDIO";
		Phrase fpSuperior = null;
		String fpSuperiorCaption = "FP-GRADO SUPERIOR";
		Phrase universidad = null;
		String universidadCaption = "UNIVERSIDAD DIPLOMADO";
		
		String noTab ="";
		

		if (studies != null) {
			Studies studiesFilter = new Studies();
			studiesFilter.setId(studies.getId());
			Studies studiesBBDD = studiesDAO.findStudies(studiesFilter);
			if (studiesBBDD.getDescription().equals("No sabe leer ni escribir")) {
				noLee = new Phrase(this.getCheckTrue(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			} else if (studiesBBDD.getDescription().equals("Sólo sabe leer y escribir")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckTrue(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			} else if (studiesBBDD.getDescription().equals("Infantil")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckTrue(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			} else if (studiesBBDD.getDescription().equals("Primaria")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckTrue(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			} else if (studiesBBDD.getDescription().equals("Secundaria")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckTrue(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			} else if (studiesBBDD.getDescription().equals("Bachillerato")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckTrue(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			}else if (studiesBBDD.getDescription().equals("FP-Grado Medio")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckTrue(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			}else if (studiesBBDD.getDescription().equals("FP-Grado Superior")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckTrue(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
			}else if (studiesBBDD.getDescription().equals("Universidad Diplomado")) {
				noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
				lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
				infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
				primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
				secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
				bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
				fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
				fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
				universidad= new Phrase(this.getCheckTrue(noTab,universidadCaption));
			}
		}else{
			noLee = new Phrase(this.getCheckFalse(noTab,noLeeCaption));
			lee= new Phrase(this.getCheckFalse(noTab,leeCaption));
			infantil= new Phrase(this.getCheckFalse(noTab,infantilCaption));
			primaria= new Phrase(this.getCheckFalse(noTab,primariaCaption));
			secundaria= new Phrase(this.getCheckFalse(noTab,secundariaCaption));
			bachillerato= new Phrase(this.getCheckFalse(noTab,bachilleratoCaption));
			fpMedio= new Phrase(this.getCheckFalse(noTab,fpMedioCaption));
			fpSuperior= new Phrase(this.getCheckFalse(noTab,fpSuperiorCaption));
			universidad= new Phrase(this.getCheckFalse(noTab,universidadCaption));
		}

		PdfPTable tableChecks = new PdfPTable(2);
		tableChecks.setWidthPercentage(100);
		
		List<Phrase> listaCeldas =new ArrayList<Phrase>();
		listaCeldas.addAll(Arrays.asList(noLee, bachillerato, lee, fpMedio, infantil, fpSuperior, primaria, universidad, secundaria, new Phrase()));
		
		for (Phrase phrase : listaCeldas) {
			PdfPCell cellCheckTypeFamily = new PdfPCell();
			cellCheckTypeFamily = new PdfPCell(phrase);
			setCellStyleTableNoBorder(cellCheckTypeFamily);
			tableChecks.addCell(cellCheckTypeFamily);
		}
		
		document.add(paragraphtitulo);
		document.add(tableChecks);
		
	}
	
private void addIncomes(Document document, Program program) throws DocumentException, IOException{
		
		Double totalIncomes = 0.0;
	
		Paragraph paragraphtitulo = new Paragraph();

		paragraphtitulo.add(new Paragraph("SITUACIÓN ECONÓMICA", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraphtitulo, 1);
		
		Paragraph subtitulo = new Paragraph();

		subtitulo.add(new Paragraph("INGRESOS", TITLE_10_FONT));
		addEmptyLine(subtitulo, 1);
		
		PdfPTable tableIncomes = new PdfPTable(4);
		tableIncomes.setTotalWidth(new float[] { 120, 120, 120, 120 });
		tableIncomes.setLockedWidth(true);

		// first row
		PdfPCell cell = new PdfPCell(new Phrase("PERSONA", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		tableIncomes.addCell(cell);
		// second row
		cell = new PdfPCell(new Phrase("CONCEPTO", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		tableIncomes.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("CANTIDAD", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		tableIncomes.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("FECHA FIN", TITLE_10_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		tableIncomes.addCell(cell);

		
		tableIncomes.setHeaderRows(1);
		Income incomesFilter = new Income();
		incomesFilter.setProgram(program);
		List<Income> incomes = incomesDAO.findIncomes(incomesFilter);
		if (incomes != null && !incomes.isEmpty()) {

			for (Income income : incomes) {
				cell = new PdfPCell(new Phrase(income.getPeople(), TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				tableIncomes.addCell(cell);
				cell = new PdfPCell(new Phrase(income.getConcept(), TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				tableIncomes.addCell(cell);
				String amount = income.getAmount()!=null?String.valueOf(income.getAmount()):"";
				if (income.getAmount()!=null){
					totalIncomes = totalIncomes + income.getAmount();
				}
				cell = new PdfPCell(new Phrase(amount, TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				tableIncomes.addCell(cell);
				String endDate = income.getEndDate()!=null?sdf.format(income.getEndDate()):"";
				cell = new PdfPCell(new Phrase(endDate, TITLE_10_FONT));
				setCellStyleTableWithBorder(cell);
				tableIncomes.addCell(cell);
				
			}
		}else{
			this.createEmptyCells(4, tableIncomes);
		}

		Paragraph paragraphTotal = new Paragraph();

		paragraphTotal.add(new Paragraph("INGRESOS TOTALES: " + String.valueOf(totalIncomes), TITLE_10_FONT_BOLD));
		addEmptyLine(paragraphTotal, 1);
		
		document.add(paragraphtitulo);
		
		document.add(subtitulo);

		document.add(tableIncomes);
		
		document.add(paragraphTotal);
		
}

private void addExpenses(Document document, Program program) throws DocumentException, IOException{
	
	Double totalExpenses = 0.0;
	
	Paragraph subtitulo = new Paragraph();

	subtitulo.add(new Paragraph("GASTOS", TITLE_10_FONT));
	addEmptyLine(subtitulo, 1);
	
	PdfPTable tableExpenses = new PdfPTable(4);
	tableExpenses.setTotalWidth(new float[] { 120, 120, 120, 120 });
	tableExpenses.setLockedWidth(true);

	// first row
	PdfPCell cell = new PdfPCell(new Phrase("CONCEPTO", TITLE_10_FONT_BOLD));
	setCellStyleTableWithBorder(cell);
	tableExpenses.addCell(cell);
	// second row
	cell = new PdfPCell(new Phrase("CANTIDAD", TITLE_10_FONT_BOLD));
	setCellStyleTableWithBorder(cell);
	tableExpenses.addCell(cell);

	// third row
	cell = new PdfPCell(new Phrase("PERIODICIDAD", TITLE_10_FONT_BOLD));
	setCellStyleTableWithBorder(cell);
	tableExpenses.addCell(cell);

	// third row
	cell = new PdfPCell(new Phrase("FECHA FIN", TITLE_10_FONT_BOLD));
	setCellStyleTableWithBorder(cell);
	tableExpenses.addCell(cell);

	
	tableExpenses.setHeaderRows(1);
	Expense expenseFilter = new Expense();
	expenseFilter.setProgram(program);
	List<Expense> expenses = expensesDAO.findExpenses(expenseFilter);
	if (expenses != null && !expenses.isEmpty()) {

		for (Expense expense : expenses) {
			cell = new PdfPCell(new Phrase(expense.getConcept(), TITLE_10_FONT));
			setCellStyleTableWithBorder(cell);
			tableExpenses.addCell(cell);
			String amount = expense.getAmount()!=null?String.valueOf(expense.getAmount()):"";
			if (expense.getAmount()!=null){
				totalExpenses = totalExpenses + expense.getAmount();
			}
			cell = new PdfPCell(new Phrase(amount, TITLE_10_FONT));
			setCellStyleTableWithBorder(cell);
			tableExpenses.addCell(cell);
			cell = new PdfPCell(new Phrase(expense.getRegularity(), TITLE_10_FONT));
			setCellStyleTableWithBorder(cell);
			tableExpenses.addCell(cell);
			String endDate = expense.getEndDate()!=null?sdf.format(expense.getEndDate()):"";
			cell = new PdfPCell(new Phrase(endDate, TITLE_10_FONT));
			setCellStyleTableWithBorder(cell);
			tableExpenses.addCell(cell);
		}
	}else{
		this.createEmptyCells(4, tableExpenses);
	}

	Paragraph paragraphTotal = new Paragraph();

	paragraphTotal.add(new Paragraph("GASTOS TOTALES: " + String.valueOf(totalExpenses), TITLE_10_FONT_BOLD));
	addEmptyLine(paragraphTotal, 1);
	
	document.add(subtitulo);

	document.add(tableExpenses);
	
	document.add(paragraphTotal);
	
}


private void addOtherInfo(Document document, OtherInfo info) throws DocumentException{
	
	Paragraph paragraph = new Paragraph();
	
	paragraph.add(new Paragraph("INSTITUCIONES POR LAS QUE HAN PASADO (fecha, persona, institución, demanda, respuesta obtenida ...)", TITLE_10_FONT_BOLD));
	addEmptyLine(paragraph, 1);
	paragraph.add(new Paragraph(getNullRepresentation(info.getInstitutions()), TITLE_10_FONT));
	
	addEmptyLine(paragraph,1);
	
	paragraph.add(new Paragraph("DEMANDA QUE REALIZA A CARITAS", TITLE_10_FONT_BOLD));
	addEmptyLine(paragraph, 1);
	paragraph.add(new Paragraph(getNullRepresentation(info.getDemand()), TITLE_10_FONT));
	
	addEmptyLine(paragraph,1);
	
	paragraph.add(new Paragraph("DESCRIPCIÓN SITUACIÓN / VALORACIÓN PERSONAL", TITLE_10_FONT_BOLD));
	addEmptyLine(paragraph, 1);
	paragraph.add(new Paragraph(getNullRepresentation(info.getDescription()), TITLE_10_FONT));
	
	addEmptyLine(paragraph,1);
	
	paragraph.add(new Paragraph("ACTUACIONES", TITLE_10_FONT_BOLD));
	addEmptyLine(paragraph, 1);
	paragraph.add(new Paragraph(getNullRepresentation(info.getActuations()), TITLE_10_FONT));
	
	addEmptyLine(paragraph,1);
	
	document.add(paragraph);
	
	
}


	

	private  void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	private  void addEmptyLinePhrase(Phrase paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	

	

	

	

	
	private String getNullDateRepresentation(Date method) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try{
			if (method != null && !method.equals("")) {
				return sdf.format(method);
			} else {
				return "";
			}
		}catch(Exception e){
			return "";
		}
		
	}
	

	private String getNullRepresentation(String method) {
		if (method != null && !method.equals("")) {
			return method;
		} else {
			return "";
		}
	}

	private String getIntNullRepresentation(int method) {
		if (method == 0) {
			return "";
		} else {
			return String.valueOf(method);
		}
	}

	private Paragraph getCheckTrue(String tab, String message) throws DocumentException, IOException {

		String path = PdfExporter.class.getResource("/img/WINGDING.TTF").getPath();
		BaseFont base = BaseFont.createFont(path, BaseFont.IDENTITY_H, false);

		Font font = new Font(base, 12f, Font.NORMAL);
		char checked = '\u00FE';
		char unchecked = '\u00A8';
		Paragraph paragraphTabulacion = new Paragraph(tab);
		Paragraph paragraph =new Paragraph(String.valueOf(checked), font);
		Paragraph paragraphMessage = new Paragraph(" " + message, TITLE_10_FONT);
		paragraph.add(paragraphMessage);
		paragraphTabulacion.add(paragraph);
		return paragraphTabulacion;

	}

	private Paragraph getCheckFalse(String tab, String message) throws DocumentException, IOException {
		String path = PdfExporter.class.getResource("/img/WINGDING.TTF").getPath();
		BaseFont base = BaseFont.createFont(path, BaseFont.IDENTITY_H, false);

		Font font = new Font(base, 12f, Font.NORMAL);
		char checked = '\u00FE';
		char unchecked = '\u00A8';
		Paragraph paragraphTabulacion = new Paragraph(tab);
		Paragraph paragraph =new Paragraph(String.valueOf(unchecked), font);
		Paragraph paragraphMessage = new Paragraph(" " + message, TITLE_10_FONT);
		paragraph.add(paragraphMessage);
		paragraphTabulacion.add(paragraph);
		return paragraphTabulacion;
	}
	
	private PdfPCell setCellStyleTableNoBorder(PdfPCell cell){
		cell.setFixedHeight(30);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
		
	}
	
	private PdfPCell setCellStyleTableWithBorder(PdfPCell cell){
		cell.setFixedHeight(30);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.BOX);
		return cell;
		
	}

}
