package com.reparadoras.caritas.ui.utils;


import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEventSplit;


//public class TableBorderEvent implements PdfPTableEventAfterSplit
public class TableBorderEvent implements PdfPTableEventSplit
{

    protected int rowCount;
    protected boolean bottom = true;
    protected boolean top = true;


    public void setRowCount(int rowCount)
    {

	this.rowCount = rowCount;
    }


    public void splitTable(PdfPTable table)
    {

	if(table.getRows().size() != rowCount)
	{
	    bottom = false;
	}
    }


    public void afterSplitTable(PdfPTable table, PdfPRow startRow, int startIdx)
    {

	if(table.getRows().size() != rowCount)
	{
	    rowCount = table.getRows().size();
	    top = false;
	}
    }


    public void tableLayout(PdfPTable table, float[][] width, float[] height, int headerRows, int rowStart, PdfContentByte[] canvas)
    {

	float widths[] = width[0];
	float y1 = height[0];
	float y2 = height[height.length - 1];
	PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
	for (int i = 0; i < widths.length; i++)
	{
	    cb.moveTo(widths[i], y1);
	    cb.lineTo(widths[i], y2);
	}
	float x1 = widths[0];
	float x2 = widths[widths.length - 1];
	for (int i = top ? 0 : 1; i < (bottom ? height.length : height.length - 1); i++)
	{
	    cb.moveTo(x1, height[i]);
	    cb.lineTo(x2, height[i]);
	}
	cb.stroke();
	cb.resetRGBColorStroke();
	bottom = true;
	top = true;
    }

}
