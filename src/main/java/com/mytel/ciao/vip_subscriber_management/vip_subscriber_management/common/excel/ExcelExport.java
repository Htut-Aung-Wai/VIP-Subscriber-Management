package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.excel;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ExcelExport {

    public static ByteArrayInputStream exportToExcel(List<VipSubscriber> subscribers) throws IOException {
        String[] columns = {
                "ID", "SUBSCRIBER NO", "VIP PACKAGE ID", "BRANCH", "PROPOSAL DOCUMENT NO", "REGISTRATION DATE", "EXPIRE DATE", "IS DELETED"
        };

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            Sheet sheet = workbook.createSheet("Expiring Subscribers");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setFontHeightInPoints((short) 12);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
            dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataCellStyle.setBorderBottom(BorderStyle.THIN);
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            dataCellStyle.setBorderLeft(BorderStyle.THIN);
            dataCellStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.cloneStyleFrom(dataCellStyle); // keep borders + alignment
            CreationHelper createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm:ss"));

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIndex = 1;
            for (VipSubscriber sub : subscribers) {
                Row row = sheet.createRow(rowIndex++);

                createCell(row, 0, sub.getId(), dataCellStyle);
                createCell(row, 1, sub.getSubscriberNo(), dataCellStyle);
                createCell(row, 2, sub.getVipPackageId(), dataCellStyle);
                createCell(row, 3, sub.getBranchName(), dataCellStyle);
                createCell(row, 4, sub.getProposalDocumentNo(), dataCellStyle);

                Cell regCell = row.createCell(5);
                if (sub.getRegistrationDate() != null) {
                    regCell.setCellValue(sub.getRegistrationDate());
                }
                regCell.setCellStyle(dateCellStyle);

                Cell expCell = row.createCell(6);
                if (sub.getExpiryDate() != null) {
                    expCell.setCellValue(sub.getExpiryDate());
                }
                expCell.setCellStyle(dateCellStyle);

                createCell(row, 7, sub.isDeleted() ? "Yes" : "No", dataCellStyle);
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private static void createCell(Row row, int colIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(colIndex);
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Timestamp) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }
}
