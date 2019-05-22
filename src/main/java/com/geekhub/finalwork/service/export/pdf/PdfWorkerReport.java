package com.geekhub.finalwork.service.export.pdf;

import com.geekhub.finalwork.service.orders.dto.Worker;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PdfWorkerReport extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"worker.pdf\"");

        @SuppressWarnings("unchecked")
        List<Worker> workers = (List<Worker>) model.get("workerList");
        document.add(new Paragraph("Workers on " + LocalDate.now()));
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("First name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Last name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Telephone", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Address", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
        for (Worker worker : workers) {
            table.addCell(String.valueOf(worker.getId()));
            table.addCell(worker.getFirstName());
            table.addCell(worker.getLastName());
            table.addCell(worker.getTelephone());
            table.addCell(worker.getAddress());
            table.addCell(worker.getEmail());
        }
        document.add(table);
    }
}