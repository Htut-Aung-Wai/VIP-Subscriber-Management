package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.excel;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipSubscriber;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelExport {

    public static ByteArrayInputStream exportToExcel(List<VipSubscriber> subscribers) throws IOException {
        String[] columns = {"ID", "SUBSCRIBER NO", "VIP PACKAGE ID", "Branch", "PROPOSAL DOCUMENT NO", "REGISTRATION DATE", "EXPIRY DATE", "IS DELETED"};

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            Sheet sheet = workbook.createSheet("Expiring Subscribers");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setFont(headerFont);

            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIndex = 1;
            for (VipSubscriber sub : subscribers) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(sub.getId());
                row.createCell(1).setCellValue(sub.getSubscriberNo());
                row.createCell(2).setCellValue(sub.getVipPackageId());
                row.createCell(3).setCellValue(sub.getBranchName());
                row.createCell(4).setCellValue(sub.getProposalDocumentNo());

                Cell regCell = row.createCell(5);
                regCell.setCellValue(sub.getRegistrationDate());
                regCell.setCellStyle(dateCellStyle);

                Cell expCell = row.createCell(6);
                expCell.setCellValue(sub.getExpiryDate());
                expCell.setCellStyle(dateCellStyle);

                row.createCell(7).setCellValue(sub.isDeleted());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
