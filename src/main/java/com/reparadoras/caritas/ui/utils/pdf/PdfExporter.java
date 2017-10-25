package com.reparadoras.caritas.ui.utils.pdf;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.reparadoras.caritas.CaritasGUI;
import com.reparadoras.caritas.dao.AuthorizationTypeDAO;
import com.reparadoras.caritas.dao.RelativeDAO;
import com.reparadoras.caritas.model.Address;
import com.reparadoras.caritas.model.AuthorizationType;
import com.reparadoras.caritas.model.Family;
import com.reparadoras.caritas.model.FamilyType;
import com.reparadoras.caritas.model.Home;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;

public class PdfExporter {

	// private static final Log LOG = LogFactory.getLog(PdfExporter.class);

	private static final String PDF_EXTENSION = ".pdf";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private static final String OBSERVATION_TYPES_SEPARATOR = "|";
	private static final float DOCUMENT_MARGIN_LEFT = 36;
	private static final float DOCUMENT_MARGIN_RIGHT = 36;
	private static final float DOCUMENT_MARGIN_TOP = 10;
	private static final float DOCUMENT_MARGIN_BOTTOM = 50;
	private static final String TABLE_HEADER_BACKGROUND_COLOR = "#E2001A";
	private static final String TABLE_CELL_HEADER_BACKGROUND_COLOR = "#EDEDED";
	private static final float MINIMUN_HEIGHT_CELLS = 20;
	private static final float SPACING_BEFORE_TABLE = 10;
	private static final float SPACING_AFTER_TABLE = 20;
	private static final int TABLES_COL_NUMBER = 4;
	private static final int RESOLUTION_DATA_TABLE_COL_NUMBER = 2;
	private static final Font TITLE_FONT = new Font(FontFamily.HELVETICA, 20, Font.NORMAL);
	private static final Font TITLE_12_FONT = new Font(FontFamily.HELVETICA, 12, Font.NORMAL);
	private static final Font TITLE_10_FONT_BOLD = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font TITLE_10_FONT = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font TABLE_HEADER_FONT = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	private static final Font TABLE_CELL_HEADER_FONT = new Font(FontFamily.HELVETICA, 9, Font.BOLD);
	private static final Font TABLE_CELL_FONT = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	private RelativeDAO relativeDAO;
	private AuthorizationTypeDAO authorizationTypeDAO;

	public File export(Program program, File file) throws DocumentException, IOException {

		try {

			relativeDAO = new RelativeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			authorizationTypeDAO = new AuthorizationTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			HeaderFooterPageEvent event = new HeaderFooterPageEvent();

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();

			addMetaData(document);
			addTitlePage(document);
			// addContent(document);
			addAddress(document, program.getFamily().getHome().getAddress());
			addHome(document, program.getFamily().getHome());
			addFamily(document, program.getFamily());
			addFamilyType(document, program.getFamily());
			addAuthorizationType(document, program.getAuthorizationType());
			document.close();
		} catch (Exception e) {
			System.out.println(e);
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
		// We add one empty line
		addEmptyLine(paragraph, 1);
		// Lets write a big header
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

		Image img = Image.getInstance(PdfExporter.class.getResource("/com/reparadoras/images/logo2.png"));
		img.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);
		document.add(img);
		// Start a new page
		document.newPage();
	}

	private void addAddress(Document document, Address address) throws DocumentException {

		Paragraph paragraph = new Paragraph();
		// paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
		paragraph.add(new Paragraph("DIRECCION", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("Municipio: " + getNullRepresentation(address.getTown()), TITLE_10_FONT));
		paragraph.add(new Paragraph("Calle:  " + getNullRepresentation(address.getStreet()), TITLE_10_FONT));
		paragraph.add(new Paragraph("Portal: " + address.getGate(), TITLE_10_FONT));
		paragraph.add(new Paragraph("Piso y Mano:  " + address.getFloor(), TITLE_10_FONT));
		paragraph.add(new Paragraph("Tfno. domicilio  " + address.getTelephone(), TITLE_10_FONT));
		paragraph.add(new Paragraph("Tfno. contacto  " + address.getTelephoneContact(), TITLE_10_FONT));
		addEmptyLine(paragraph, 1);
		document.add(paragraph);

		// Lets write a big header
	}

	private void addHome(Document document, Home home) throws DocumentException {

		Paragraph paragraph = new Paragraph();

		paragraph.add(new Paragraph("DATOS VIVIENDA", TITLE_10_FONT_BOLD));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Paragraph("Tipo: " + "", TITLE_10_FONT));
		paragraph
				.add(new Paragraph("Régimen Tenencia:  " + getNullRepresentation(home.getRegHolding()), TITLE_10_FONT));
		paragraph.add(new Paragraph("Número Habitaciones:  " + getIntNullRepresentation(home.getNumberRooms()),
				TITLE_10_FONT));
		paragraph.add(new Paragraph("Número personas que residen:  " + getIntNullRepresentation(home.getNumberPeople()),
				TITLE_10_FONT));
		paragraph.add(new Paragraph("Número familias nucleares:  " + getIntNullRepresentation(home.getNumberFamilies()),
				TITLE_10_FONT));
		paragraph.add(new Paragraph("Otros datos:  " + getNullRepresentation(home.getOtherInfo()), TITLE_10_FONT));
		addEmptyLine(paragraph, 1);
		document.add(paragraph);

		// Lets write a big header
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
		table.setTotalWidth(new float[] { 100, 100, 100, 100, 100 });
		table.setLockedWidth(true);

		// first row
		PdfPCell cell = new PdfPCell(new Phrase("PARENTESCO", TITLE_10_FONT_BOLD));
		cell.setFixedHeight(30);
		cell.setBorder(Rectangle.BOX);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		// second row
		cell = new PdfPCell(new Phrase("APELLIDOS", TITLE_10_FONT_BOLD));
		cell.setFixedHeight(30);
		cell.setBorder(Rectangle.BOX);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("NOMBRE", TITLE_10_FONT_BOLD));
		cell.setFixedHeight(30);
		cell.setBorder(Rectangle.BOX);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("F.NAC", TITLE_10_FONT_BOLD));
		cell.setFixedHeight(30);
		cell.setBorder(Rectangle.BOX);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		// third row
		cell = new PdfPCell(new Phrase("SITUACION", TITLE_10_FONT_BOLD));
		cell.setFixedHeight(30);
		cell.setBorder(Rectangle.BOX);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		table.setHeaderRows(1);
		if (relatives != null && !relatives.isEmpty()) {

			for (Relative relative : relatives) {
				cell = new PdfPCell(new Phrase(relative.getRelationShip(), TITLE_10_FONT));
				cell.setFixedHeight(30);
				cell.setBorder(Rectangle.BOX);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(relative.getSurname(), TITLE_10_FONT));
				cell.setFixedHeight(30);
				cell.setBorder(Rectangle.BOX);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(relative.getName(), TITLE_10_FONT));
				cell.setFixedHeight(30);
				cell.setBorder(Rectangle.BOX);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("Fecha", TITLE_10_FONT));
				cell.setFixedHeight(30);
				cell.setBorder(Rectangle.BOX);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(relative.getSituation(), TITLE_10_FONT));
				cell.setFixedHeight(30);
				cell.setBorder(Rectangle.BOX);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
		}

		document.add(paragraphtitulo);

		document.add(table);

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
		Phrase residencia = null;
		Phrase residenciaTrabajo = null ;
		Phrase estudios = null; 
		Phrase turismo = null ; 
		Phrase refugiado = null;
		Phrase indocumentado = null;
		Phrase irregular = null;
		
		String tab = "     ";
		String noTab ="";

		if (atype != null) {
			AuthorizationType atypeFilter = new AuthorizationType();
			atypeFilter.setId(atype.getId());
			AuthorizationType atypeBBDD = authorizationTypeDAO.findAuthorizationType(atypeFilter);
			if (atypeBBDD.getDescription().equals("Autorización Residencia")) {
				regular = new Phrase(this.getCheckTrue(noTab,"SITUACION ADMTVA. REGULAR"));
				residencia= new Phrase(this.getCheckTrue(tab,"AUTORIZACION RESIDENCIA"));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,"AUT. RESIDENCIA Y TRABAJO"));
				estudios= new Phrase(this.getCheckFalse(tab,"ESTUDIOS"));
				turismo= new Phrase(this.getCheckFalse(tab,"TURISMO"));
				refugiado= new Phrase(this.getCheckFalse(tab,"REFUGIADO"));
				indocumentado= new Phrase(this.getCheckFalse(noTab,"INDOCUMENTADO"));
				irregular= new Phrase(this.getCheckFalse(noTab,"SITUACIÓN ADMTVA IRREGULAR"));
			} else if (atypeBBDD.getDescription().equals("Autorización Residencia y Trabajo")) {
				regular = new Phrase(this.getCheckTrue(tab,"SITUACION ADMTVA. REGULAR"));
				residencia= new Phrase(this.getCheckFalse(tab,"AUTORIZACION RESIDENCIA"));
				residenciaTrabajo= new Phrase(this.getCheckTrue(tab,"AUT. RESIDENCIA Y TRABAJO"));
				estudios= new Phrase(this.getCheckFalse(tab,"ESTUDIOS"));
				turismo= new Phrase(this.getCheckFalse(tab,"TURISMO"));
				refugiado= new Phrase(this.getCheckFalse(tab,"REFUGIADO"));
				indocumentado= new Phrase(this.getCheckFalse(noTab,"INDOCUMENTADO"));
				irregular= new Phrase(this.getCheckFalse(noTab,"SITUACIÓN ADMTVA IRREGULAR"));
			} else if (atypeBBDD.getDescription().equals("Estudios")) {
				regular = new Phrase(this.getCheckTrue(noTab,"SITUACION ADMTVA. REGULAR"));
				residencia= new Phrase(this.getCheckFalse(tab,"AUTORIZACION RESIDENCIA"));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,"AUT. RESIDENCIA Y TRABAJO"));
				estudios= new Phrase(this.getCheckTrue(tab,"ESTUDIOS"));
				turismo= new Phrase(this.getCheckFalse(tab,"TURISMO"));
				refugiado= new Phrase(this.getCheckFalse(tab,"REFUGIADO"));
				indocumentado= new Phrase(this.getCheckFalse(noTab,"INDOCUMENTADO"));
				irregular= new Phrase(this.getCheckFalse(noTab,"SITUACIÓN ADMTVA IRREGULAR"));
			} else if (atypeBBDD.getDescription().equals("Turismo")) {
				regular = new Phrase(this.getCheckTrue(noTab,"SITUACION ADMTVA. REGULAR"));
				residencia= new Phrase(this.getCheckFalse(tab,"AUTORIZACION RESIDENCIA"));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,"AUT. RESIDENCIA Y TRABAJO"));
				estudios= new Phrase(this.getCheckFalse(tab,"ESTUDIOS"));
				turismo= new Phrase(this.getCheckTrue(tab,"TURISMO"));
				refugiado= new Phrase(this.getCheckFalse(tab,"REFUGIADO"));
				indocumentado= new Phrase(this.getCheckFalse(noTab,"INDOCUMENTADO"));
				irregular= new Phrase(this.getCheckFalse(noTab,"SITUACIÓN ADMTVA IRREGULAR"));
			} else if (atypeBBDD.getDescription().equals("Refugiado")) {
				regular = new Phrase(this.getCheckTrue(noTab,"SITUACION ADMTVA. REGULAR"));
				residencia= new Phrase(this.getCheckFalse(tab,"AUTORIZACION RESIDENCIA"));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,"AUT. RESIDENCIA Y TRABAJO"));
				estudios= new Phrase(this.getCheckFalse(tab,"ESTUDIOS"));
				turismo= new Phrase(this.getCheckFalse(tab,"TURISMO"));
				refugiado= new Phrase(this.getCheckTrue(tab,"REFUGIADO"));
				indocumentado= new Phrase(this.getCheckFalse(noTab,"INDOCUMENTADO"));
				irregular= new Phrase(this.getCheckFalse(noTab,"SITUACIÓN ADMTVA IRREGULAR"));
			} else if (atypeBBDD.getDescription().equals("Indocumentado")) {
				regular = new Phrase(this.getCheckFalse(noTab,"SITUACION ADMTVA. REGULAR"));
				residencia= new Phrase(this.getCheckFalse(tab,"AUTORIZACION RESIDENCIA"));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,"AUT. RESIDENCIA Y TRABAJO"));
				estudios= new Phrase(this.getCheckFalse(tab,"ESTUDIOS"));
				turismo= new Phrase(this.getCheckFalse(tab,"TURISMO"));
				refugiado= new Phrase(this.getCheckFalse(tab,"REFUGIADO"));
				indocumentado= new Phrase(this.getCheckTrue(noTab,"INDOCUMENTADO"));
				irregular= new Phrase(this.getCheckFalse(noTab,"SITUACIÓN ADMTVA IRREGULAR"));
			}
			else if (atypeBBDD.getDescription().equals("Irregular")) {
				regular = new Phrase(this.getCheckFalse(noTab,"SITUACION ADMTVA. REGULAR"));
				residencia= new Phrase(this.getCheckFalse(tab,"AUTORIZACION RESIDENCIA"));
				residenciaTrabajo= new Phrase(this.getCheckFalse(tab,"AUT. RESIDENCIA Y TRABAJO"));
				estudios= new Phrase(this.getCheckFalse(tab,"ESTUDIOS"));
				turismo= new Phrase(this.getCheckFalse(tab,"TURISMO"));
				refugiado= new Phrase(this.getCheckFalse(tab,"REFUGIADO"));
				indocumentado= new Phrase(this.getCheckFalse(noTab,"INDOCUMENTADO"));
				irregular= new Phrase(this.getCheckTrue(noTab,"SITUACIÓN ADMTVA IRREGULAR"));
			}
		}

		PdfPTable tableChecks = new PdfPTable(1);
		tableChecks.setWidthPercentage(100);
		
		PdfPCell cellCheckTypeFamily = new PdfPCell();
		cellCheckTypeFamily = new PdfPCell(regular);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		
		cellCheckTypeFamily = new PdfPCell(residencia);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		

		
		cellCheckTypeFamily = new PdfPCell(residenciaTrabajo);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		

		
		cellCheckTypeFamily = new PdfPCell(estudios);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		

		
		cellCheckTypeFamily = new PdfPCell(turismo);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		
		cellCheckTypeFamily = new PdfPCell(refugiado);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		
		cellCheckTypeFamily = new PdfPCell(indocumentado);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		
		cellCheckTypeFamily = new PdfPCell(irregular);
		setCellStyleTableNoBorder(cellCheckTypeFamily);
		tableChecks.addCell(cellCheckTypeFamily);
		

		document.add(paragraphtitulo);
		document.add(tableChecks);
		
	}

	private static void addContent(Document document) throws DocumentException {
		Anchor anchor = new Anchor("First Chapter", catFont);
		anchor.setName("First Chapter");

		// Second parameter is the number of the chapter
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		Paragraph subPara = new Paragraph("Subcategory 1", subFont);
		Section subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Hello"));

		subPara = new Paragraph("Subcategory 2", subFont);
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Paragraph 1"));
		subCatPart.add(new Paragraph("Paragraph 2"));
		subCatPart.add(new Paragraph("Paragraph 3"));

		// add a list
		// createList(subCatPart);
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 5);
		subCatPart.add(paragraph);

		// add a table
		createTable(subCatPart);

		// now add all this to the document
		document.add(catPart);

		// Next section
		anchor = new Anchor("Second Chapter", catFont);
		anchor.setName("Second Chapter");

		// Second parameter is the number of the chapter
		catPart = new Chapter(new Paragraph(anchor), 1);

		subPara = new Paragraph("Subcategory", subFont);
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("This is a very important message"));

		// now add all this to the document
		document.add(catPart);

	}

	private static void createTable(Section subCatPart) throws BadElementException {
		PdfPTable table = new PdfPTable(3);

		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);

		PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 2"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 3"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		table.setHeaderRows(1);

		table.addCell("1.0");
		table.addCell("1.1");
		table.addCell("1.2");
		table.addCell("2.1");
		table.addCell("2.2");
		table.addCell("2.3");

		subCatPart.add(table);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private PdfPCell getImageCell(byte[] imageByteArray) throws IOException, BadElementException {
		// Image image =
		// Image.getInstance(imageUtils.getResizedImageByteArrayFromByteArray(imageByteArray));
		// image.scalePercent(70);
		Paragraph p = new Paragraph();
		// p.add(new Chunk(image, 0, 0, true));
		return new PdfPCell(p);
	}

	private PdfPTable createTable(Integer colNumber) {
		PdfPTable table = new PdfPTable(colNumber);
		table.setWidthPercentage(100);
		table.setTableEvent(new TableBorderEvent());
		return table;
	}

	private PdfPCell createCellHeader(String title, Integer colspan) {
		PdfPCell cell = new PdfPCell(new Phrase(title, TABLE_HEADER_FONT));
		cell.setMinimumHeight(MINIMUN_HEIGHT_CELLS);
		cell.setColspan(colspan);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBackgroundColor(getBaseColorFromHex(TABLE_HEADER_BACKGROUND_COLOR));
		return cell;
	}

	private PdfPCell createCellParagraph(String text) {
		PdfPCell cell = new PdfPCell(new Phrase(text, TABLE_CELL_FONT));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setMinimumHeight(20);
		return cell;
	}

	private PdfPCell createCellDataHeader(String title) {
		PdfPCell cell = new PdfPCell(new Phrase(title, TABLE_CELL_HEADER_FONT));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setMinimumHeight(20);
		cell.setBackgroundColor(getBaseColorFromHex(TABLE_CELL_HEADER_BACKGROUND_COLOR));
		return cell;
	}

	private BaseColor getBaseColorFromHex(String hexColor) {
		Integer r = Integer.valueOf(hexColor.substring(1, 3), 16);
		Integer g = Integer.valueOf(hexColor.substring(3, 5), 16);
		Integer b = Integer.valueOf(hexColor.substring(5, 7), 16);
		return new BaseColor(r, g, b);
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

		String path = PdfExporter.class.getResource("/com/reparadoras/caritas/ui/utils/pdf/WINGDING.TTF").getPath();
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
		String path = PdfExporter.class.getResource("/com/reparadoras/caritas/ui/utils/pdf/WINGDING.TTF").getPath();
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

}
