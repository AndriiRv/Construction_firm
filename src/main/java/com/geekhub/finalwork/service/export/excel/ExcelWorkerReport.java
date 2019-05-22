package com.geekhub.finalwork.service.export.excel;

import com.geekhub.finalwork.service.orders.dto.Worker;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelWorkerReport extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"workers.xls\"");

        @SuppressWarnings("unchecked")
        List<Worker> workers = (List<Worker>) model.get("workerList");
        Sheet sheet = workbook.createSheet("Worker Data");
        sheet.setDefaultColumnWidth(20);
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("First name");
        header.createCell(2).setCellValue("Last name");
        header.createCell(3).setCellValue("Telephone");
        header.createCell(4).setCellValue("Address");
        header.createCell(5).setCellValue("E-mail");
        int rowNum = 1;
        for (Worker worker : workers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(worker.getId());
            row.createCell(1).setCellValue(worker.getFirstName());
            row.createCell(2).setCellValue(worker.getLastName());
            row.createCell(3).setCellValue(worker.getTelephone());
            row.createCell(4).setCellValue(worker.getAddress());
            row.createCell(5).setCellValue(worker.getEmail());
        }
    }
}