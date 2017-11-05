package com.reparadoras.caritas.ui.utils.pdf;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
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
import com.reparadoras.caritas.model.MonthlyReport;
import com.reparadoras.caritas.model.OtherInfo;
import com.reparadoras.caritas.model.Program;
import com.reparadoras.caritas.model.Relative;
import com.reparadoras.caritas.model.Studies;
import com.reparadoras.caritas.mybatis.MyBatisConnectionFactory;
import com.reparadoras.caritas.ui.JManageProgram;

public class PdfMonthlyReport {

	static final Logger logger = Logger.getLogger(JManageProgram.class);

	private static final String PDF_EXTENSION = ".pdf";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	


	private static final Font TITLE_12_FONT = new Font(FontFamily.HELVETICA, 12, Font.NORMAL);
	private static final Font TITLE_10_FONT_BOLD = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font TITLE_8_FONT_BOLD = new Font(FontFamily.HELVETICA, 8, Font.BOLD);
	private static final Font TITLE_10_FONT = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
	





	private RelativeDAO relativeDAO;
	private AuthorizationTypeDAO authorizationTypeDAO;
	private JobSituationDAO jobSituationDAO;
	private StudiesDAO studiesDAO;
	private IncomesDAO incomesDAO;
	private ExpensesDAO expensesDAO;

	public File export(List<MonthlyReport> lista, File file) throws DocumentException, IOException {

		
		
		try {

			relativeDAO = new RelativeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			authorizationTypeDAO = new AuthorizationTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			jobSituationDAO = new JobSituationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			studiesDAO = new StudiesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			expensesDAO = new ExpensesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			incomesDAO = new IncomesDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 100f, 0f);
		
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			
			document.open();

			addMetaData(document);
			addTitlePage(document);
			addReport(document);
			
		
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

		Image img = Image.getInstance(PdfMonthlyReport.class.getResource("/com/reparadoras/images/logo2.png"));
		img.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);
		document.add(img);
		
		document.newPage();
	}

	
	private void addReport(Document document) throws DocumentException{
		
		PdfPTable table = new PdfPTable(19);
		//table.setTotalWidth(new float[] { 100, 100, 100, 100, 100});
		table.setTotalWidth(1200);
		
		//table.setLockedWidth(true);
		
		PdfPCell cell = new PdfPCell(new Phrase("F.Atencion", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Apellidos", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Nombre", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Documentación", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("F.Nacimiento", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Sexo", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Domicilio", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Nº Personas", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("T. Familia", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Estado Civil", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("H. <18", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("H. >18", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Año Llegada", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Nacionalidad", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("T. Autorización", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("S. Laboral", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Estudios", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Demandas", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Respuesta e Importe", TITLE_8_FONT_BOLD));
		setCellStyleTableWithBorder(cell);
		table.addCell(cell);
		
		document.add(table);
		
		
		
		
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
		cell = new PdfPCell(new Phrase("APELLIDOS", TITLE_10_FONT_BOLD));
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
				cell = new PdfPCell(new Phrase(relative.getSurname(), TITLE_10_FONT));
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
