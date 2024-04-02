package com.example.comfywishlist.service;

import com.example.comfywishlist.entity.WishItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public byte[] createExcelFile(List<WishItem> wishItems) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Wish Items");
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 8000);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Title", "URL", "Price", "Creation Date"};
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (WishItem wishItem : wishItems) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(wishItem.getId());
            row.createCell(1).setCellValue(wishItem.getTitle());
            row.createCell(2).setCellValue(wishItem.getUrl());
            row.createCell(3).setCellValue(wishItem.getPrice().doubleValue());
            row.createCell(4).setCellValue(wishItem.getDateCreated().toString());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
