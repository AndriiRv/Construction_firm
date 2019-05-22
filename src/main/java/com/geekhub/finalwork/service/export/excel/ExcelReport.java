package com.geekhub.finalwork.service.export.excel;

import com.geekhub.finalwork.service.orders.dto.Order;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelReport extends AbstractXlsView {
    private String nameOfFile;
    private String nameOfList;
    private String sheetname;
    private String title;

    public ExcelReport(String nameOfFile, String nameOfList, String sheetname, String title) {
        this.nameOfFile = nameOfFile;
        this.nameOfList = nameOfList;
        this.sheetname = sheetname;
        this.title = title;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nameOfFile + ".xls\"");

        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) model.get(nameOfList);
        Sheet sheet = workbook.createSheet(sheetname);
        sheet.setDefaultColumnWidth(20);
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Title of " + title);
        header.createCell(2).setCellValue("Purchase price");
        header.createCell(3).setCellValue("Date of purchase");
        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getTitle());
            row.createCell(2).setCellValue(order.getPrice());
            row.createCell(3).setCellValue(order.getDate().toString());
        }
    }
}