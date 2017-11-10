package com.reparadoras.caritas.ui.utils.pdf;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


public class HeaderFooterPageEvent extends PdfPageEventHelper
{

    // private static Logger LOG =
    // LoggerFactory.getLogger(HeaderFooterPageEvent.class);
    // private static final String IMG_VIESGO_LOGO =
    // "/VAADIN/themes/viesgo/images/viesgo-distribucion-logo.png";

    private static final float TOTAL_PAGES_TEMPLATE_WIDTH = 30;
    private static final float TOTAL_PAGES_TEMPLATE_HEIGHT = 16;
    private static final float HEADER_TABLE_WIDTH = 550;
    private static final float HEADER_TABLE_HEIGHT = 125;
    private static final int FOOTER_TABLE_RELATIVE_WIDTH_FIRST_COLUMN = 24;
    private static final int FOOTER_TABLE_RELATIVE_WIDTH_SECOND_COLUMN = 2;
    private static final float FOOTER_TABLE_WIDTH = 527;
    private static final float FOOTER_TABLE_HEIGHT = 60;
    private static final int HEADER_TABLE_COLUMNS_NUMBER = 1;
    private static final int FOOTER_TABLE_COLUMNS_NUMBER = 2;
    private static final float FOOTER_TABLE_X_POSITION = 34;
    private static final float FOOTER_TABLE_Y_POSITION = 50;
    private static final int FOOTER_WIDTH_MULTIPLE = 10;
    private static final int TOTAL_PAGES_TEMPLATE_Y_POSITION = 2;

    protected PdfPTable table;
    protected float tableHeight;

    private PdfTemplate totalPagesTemplate;
    private Image totalPages;


    @Override
    public void onOpenDocument(PdfWriter writer, Document document)
    {

	totalPagesTemplate = writer.getDirectContent().createTemplate(TOTAL_PAGES_TEMPLATE_WIDTH, TOTAL_PAGES_TEMPLATE_HEIGHT);
	try
	{
	    totalPages = Image.getInstance(totalPagesTemplate);
	    // totalPages.setRole(PdfName.AA);
	}
	catch (DocumentException de)
	{
	    // LOG.error(de.getMessage());
	}
    }


    public HeaderFooterPageEvent()
    {
	createHeaderTable();
    }


    private Image getViesgoLogo()
    {

	/*
	 * try { // return //
	 * Image.getInstance(VaadinService.getCurrent().getBaseDirectory().
	 * getAbsolutePath() // + IMG_VIESGO_LOGO); } catch (BadElementException
	 * | IOException e) { // LOG.error(e.getMessage()); }
	 */

	return null;
    }


    private void createHeaderTable()
    {

	table = new PdfPTable(HEADER_TABLE_COLUMNS_NUMBER);
	table.setTotalWidth(HEADER_TABLE_WIDTH);
	table.setLockedWidth(true);
	table.getDefaultCell().setFixedHeight(HEADER_TABLE_HEIGHT);
	table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	table.addCell(getViesgoLogo());
	tableHeight = table.getTotalHeight();
    }


    public float getTableHeight()
    {

	return tableHeight;
    }


    @Override
    public void onEndPage(PdfWriter writer, Document document)
    {

	table.writeSelectedRows(0, -1, document.left(), document.top() + ((document.topMargin() + getTableHeight()) / 2), writer.getDirectContent());
	addFooter(writer);
    }


    private void addFooter(PdfWriter writer)
    {

	PdfPTable footer = new PdfPTable(FOOTER_TABLE_COLUMNS_NUMBER);
	try
	{
	    footer.setWidths(new int[]
	    { FOOTER_TABLE_RELATIVE_WIDTH_FIRST_COLUMN, FOOTER_TABLE_RELATIVE_WIDTH_SECOND_COLUMN });
	    footer.setTotalWidth(FOOTER_TABLE_WIDTH);
	    footer.setLockedWidth(true);
	    footer.getDefaultCell().setFixedHeight(FOOTER_TABLE_HEIGHT);
	    footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	    footer.getDefaultCell().setBorder(Rectangle.TOP);
	    footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

	    footer.addCell(new Phrase("pagina"));

	    PdfPCell totalPageCount = new PdfPCell(totalPages);
	    totalPageCount.setBorder(Rectangle.TOP);
	    totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
	    footer.addCell(totalPageCount);

	    PdfContentByte canvas = writer.getDirectContent();
	    canvas.beginMarkedContentSequence(PdfName.AA); // Artifact
	    footer.writeSelectedRows(0, -1, FOOTER_TABLE_X_POSITION, FOOTER_TABLE_Y_POSITION, canvas);
	    canvas.endMarkedContentSequence();
	}
	catch (DocumentException de)
	{
	    // LOG.error(de.getMessage());
	}
    }


    @Override
    public void onCloseDocument(PdfWriter writer, Document document)
    {

	int totalLength = String.valueOf(writer.getPageNumber()).length();
	int totalWidth = totalLength * FOOTER_WIDTH_MULTIPLE;
	ColumnText.showTextAligned(totalPagesTemplate, Element.ALIGN_RIGHT, new Phrase(String.valueOf(writer.getPageNumber())), totalWidth, TOTAL_PAGES_TEMPLATE_Y_POSITION, 0);
    }

}
