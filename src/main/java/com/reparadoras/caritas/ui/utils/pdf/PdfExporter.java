package com.reparadoras.caritas.ui.utils.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.reparadoras.caritas.model.Program;

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
	private static final Font TABLE_HEADER_FONT = new
	Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	private static final Font TABLE_CELL_HEADER_FONT = new
	Font(FontFamily.HELVETICA, 9, Font.BOLD);
	private static final Font TABLE_CELL_FONT = new
	Font(FontFamily.HELVETICA, 9, Font.NORMAL);

	public File export(Program program, File file) throws DocumentException, IOException {
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		Document document = new Document(PageSize.A4, DOCUMENT_MARGIN_LEFT, DOCUMENT_MARGIN_RIGHT,
				DOCUMENT_MARGIN_TOP + event.getTableHeight(), DOCUMENT_MARGIN_BOTTOM);
		PdfWriter pdfWriter = null;
		
		FileOutputStream fileOut = null;
		
		fileOut = new FileOutputStream(file);
		pdfWriter = PdfWriter.getInstance(document, fileOut);
		pdfWriter.setPageEvent(event);
		document.open();
		document.add(new Paragraph("titulo", TITLE_FONT));
		
		document.close();
		fileOut.close();
		return file;
	}

	
	private PdfPCell getImageCell(byte[] imageByteArray) throws IOException, BadElementException {
		//Image image = Image.getInstance(imageUtils.getResizedImageByteArrayFromByteArray(imageByteArray));
		//image.scalePercent(70);
		Paragraph p = new Paragraph();
		//p.add(new Chunk(image, 0, 0, true));
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

}
