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

	private static final Font TITLE_14_FONT_BOLD = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
	private static final Font TITLE_12_FONT = new Font(FontFamily.HELVETICA, 12, Font.NORMAL);
	private static final Font TITLE_20_FONT_BOLD = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
	private static final Font TITLE_10_FONT_BOLD = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font TITLE_6_FONT_BOLD = new Font(FontFamily.HELVETICA, 7, Font.BOLD);
	private static final Font TITLE_10_FONT = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font TITLE_6_FONT = new Font(FontFamily.HELVETICA, 7, Font.NORMAL);

	private RelativeDAO relativeDAO;
	private AuthorizationTypeDAO authorizationTypeDAO;
	private JobSituationDAO jobSituationDAO;
	private StudiesDAO studiesDAO;
	private IncomesDAO incomesDAO;
	private ExpensesDAO expensesDAO;

	public File export(List<MonthlyReport> lista, File file, String month, String year)
			throws DocumentException, IOException {

		try {

			relativeDAO = new RelativeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			authorizationTypeDAO = new AuthorizationTypeDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			jobSituationDAO = new JobSituationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			studiesDAO = new StudiesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			expensesDAO = new ExpensesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
			incomesDAO = new IncomesDAO(MyBatisConnectionFactory.getSqlSessionFactory());

			//Document document = new Document(PageSize.A4.rotate(), 20f, 10f, 100f, 0f);
			Document document = new Document(PageSize.A4.rotate());
			
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();

			addMetaData(document);
			addTitle(document, month, year, lista);
			addReport(document, lista);

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

	private void addTitle(Document document, String month, String year, List<MonthlyReport> lista) throws DocumentException {
		Paragraph paragraphTitle = new Paragraph();
		paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);

		paragraphTitle.add(new Paragraph("PROGRAMA DE ATENCIÓN PRIMARIA", TITLE_20_FONT_BOLD));

		Paragraph paragraphSub = new Paragraph();
		
		Integer totalVales = 0;
		Double totalImporte = 0.0;
		
		for (MonthlyReport report : lista) {
			

			totalVales = totalVales + (report.getValorTicket()!=null?report.getValorTicket():0);
			totalImporte = totalImporte + (report.getRespuestaImporte()!=null?report.getRespuestaImporte():0);

		}

		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		table1.addCell(getCell("PARROQUIA: SANTA MARIA REPARADORAS", PdfPCell.ALIGN_LEFT));
		table1.addCell(getCell("TOTAL VALES: " + totalVales, PdfPCell.ALIGN_LEFT));
		
		PdfPTable table2 = new PdfPTable(2);
		table2.setWidthPercentage(100);
		table2.addCell(getCell("FECHA: " + month + "  " + year, PdfPCell.ALIGN_LEFT));
		table2.addCell(getCell("TOTAL IMPORTE: " + totalImporte, PdfPCell.ALIGN_LEFT));
		
		
		
		addEmptyLine(paragraphTitle, 1);
		paragraphSub.add(table1);
		paragraphSub.add(table2);
		
		addEmptyLine(paragraphSub, 1);

		document.add(paragraphTitle);
		document.add(paragraphSub);
	}

	
	public PdfPCell getCell(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Paragraph(text, TITLE_14_FONT_BOLD));
	    cell.setPadding(0);
	    cell.setHorizontalAlignment(alignment);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    return cell;
	}
	
	private void addReport(Document document, List<MonthlyReport> lista) throws DocumentException {

		try {

			PdfPTable table = new PdfPTable(6);
			table.setTotalWidth(new float[] { 2, 4, 4, 3, 1, 1 });
			table.setTotalWidth(PageSize.A4.getWidth());

			// table.setLockedWidth(true);

			PdfPCell cell = new PdfPCell(new Phrase("F.Atencion", TITLE_6_FONT_BOLD));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Apellidos", TITLE_6_FONT_BOLD));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Nombre", TITLE_6_FONT_BOLD));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Documentación", TITLE_6_FONT_BOLD));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);

//			cell = new PdfPCell(new Phrase("F. Nacimiento", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);

//			cell = new PdfPCell(new Phrase("Sexo", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("Domicilio", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("Nº Personas", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("T. Familia", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("Estado Civil", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("H. <18", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("H. >18", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);

//			cell = new PdfPCell(new Phrase("Año Llegada", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("Nacionalidad", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);

//			cell = new PdfPCell(new Phrase("T. Autorización", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("S. Laboral", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("Estudios", TITLE_6_FONT_BOLD));
//			setCellStyleTableWithBorder(cell);
//			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Vales", TITLE_6_FONT_BOLD));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Importe", TITLE_6_FONT_BOLD));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);

			if (lista != null && !lista.isEmpty()) {

				for (MonthlyReport report : lista) {
					String atencion = "";
					if (report.getAtencion()!=null) {
						atencion = report.getAtencion();
					}
					cell = new PdfPCell(new Phrase(atencion, TITLE_6_FONT));
					setCellStyleTableWithBorder(cell);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(report.getApellidos(), TITLE_6_FONT));
					setCellStyleTableWithBorder(cell);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(report.getNombre(), TITLE_6_FONT));
					setCellStyleTableWithBorder(cell);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(report.getDocumentacion(), TITLE_6_FONT));
					setCellStyleTableWithBorder(cell);
					table.addCell(cell);

//					cell = new PdfPCell(new Phrase(report.getFechaNacimiento(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getSexo(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getDomicilio(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getNumPersonas(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getTipoFamilia(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getEstadoCivil(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getHijosMenor18(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getHijosMayor18(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getAnyoLlegada(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getNacionalidad(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getTipoAutorizacion(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getSituacionLaboral(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);
//
//					cell = new PdfPCell(new Phrase(report.getEstudios(), TITLE_6_FONT));
//					setCellStyleTableWithBorder(cell);
//					table.addCell(cell);

					String valorTicket = "";
					if (report.getValorTicket()!=null) {
						valorTicket = report.getValorTicket() + "";
					}
					cell = new PdfPCell(new Phrase(valorTicket , TITLE_6_FONT));
					setCellStyleTableWithBorder(cell);
					table.addCell(cell);


					Double respuestaImporte = report.getRespuestaImporte();
					String respuestaImporteTexto = "";
					if (respuestaImporte!=null) {
						respuestaImporteTexto = String.valueOf(respuestaImporte);
					}
					cell = new PdfPCell(new Phrase(respuestaImporteTexto, TITLE_6_FONT));

					setCellStyleTableWithBorder(cell);
					table.addCell(cell);

				}
			}

			document.add(table);

		} catch (DocumentException e) {
			logger.error("Error obteniendo los datos del report: " + e);
			throw e;
		}

	}

	private void createEmptyCells(int num, PdfPTable table) {

		for (int i = 0; i < num; i++) {
			PdfPCell cell = new PdfPCell(new Phrase("", TITLE_10_FONT));
			setCellStyleTableWithBorder(cell);
			table.addCell(cell);
		}
	}

	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void addEmptyLinePhrase(Phrase paragraph, int number) {
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

	private PdfPCell setCellStyleTableNoBorder(PdfPCell cell) {
		cell.setFixedHeight(30);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;

	}

	private PdfPCell setCellStyleTableWithBorder(PdfPCell cell) {
		cell.setFixedHeight(30);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(Rectangle.BOX);
		return cell;

	}

}
