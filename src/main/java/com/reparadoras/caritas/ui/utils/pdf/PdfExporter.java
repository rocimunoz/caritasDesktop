package com.reparadoras.caritas.ui.utils.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
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
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

	public File export(Program program, File file) throws DocumentException, IOException {
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		//Document document = new Document(PageSize.A4, DOCUMENT_MARGIN_LEFT, DOCUMENT_MARGIN_RIGHT, DOCUMENT_MARGIN_TOP + event.getTableHeight(), DOCUMENT_MARGIN_BOTTOM);
		
		 Document document = new Document();
         PdfWriter.getInstance(document, new FileOutputStream(file));
         document.open();
         addMetaData(document);
         addTitlePage(document);
         addContent(document);
         document.close();
		
		
		return file;
	}
	
	private  void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }
	
	private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Programa de Atención Primaria", catFont));
        addEmptyLine(preface, 1);
      
        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
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
        createList(subCatPart);
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
	
	private static void createTable(Section subCatPart)
            throws BadElementException {
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
	
	private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
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
