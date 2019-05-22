package com.geekhub.finalwork.service.export.pdf;

import com.geekhub.finalwork.service.orders.dto.Order;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PdfInstrumentAndMaterialReport extends AbstractPdfView {
    private String nameOfFile;
    private String nameOfList;
    private String nameOfTable;
    private String nameOfElement;

    public PdfInstrumentAndMaterialReport(String nameOfFile, String nameOfList, String nameOfTable, String nameOfElement) {
        this.nameOfFile = nameOfFile;
        this.nameOfList = nameOfList;
        this.nameOfTable = nameOfTable;
        this.nameOfElement = nameOfElement;
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nameOfFile + ".pdf\"");

        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) model.get(nameOfList);
        document.add(new Paragraph(nameOfTable + " on " + LocalDate.now()));
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Title of " + nameOfElement, font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Purchase price", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date of purchase", font));
        table.addCell(cell);
        for (Order order : orders) {
            table.addCell(String.valueOf(order.getId()));
            table.addCell(order.getTitle());
            table.addCell(String.valueOf(order.getPrice()));
            table.addCell(String.valueOf(order.getDate()));
        }
        document.add(table);
    }
}