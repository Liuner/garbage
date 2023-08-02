package com.own.garbage.core.utils.excel;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.text.StrFormatter;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName PoiValidationUtil
 * @Description
 * @Author liugs
 * @Date 2023/7/25 0025 16:26
 */
public class PoiValidationUtil {

    public static void getRule() throws IOException, InvalidFormatException {
        String excelFileName = "demo.xlsx";
        int header1 = 1;
        List<String> sheetNameList = Arrays.asList("Sheet1", "Sheet2", "Sheet2323");
        File file = FileUtil.file(excelFileName);
        if (ExcelTypeEnum.XLSX.name().equalsIgnoreCase(FileNameUtil.getSuffix(file))) {
            Workbook workbook = new XSSFWorkbook(file);
            for (String sheetName : sheetNameList) {
                // Assuming Sheet1 and Sheet2 are the sheets that have data validation rules set
                Sheet sheet = workbook.getSheet(sheetName);
                if (null == sheet) {
                    continue;
                }

                // Get the data validation rules from Sheet1
                // 一共多少列
                int totalColumns  = sheet.getRow(header1).getLastCellNum();
                DataValidation dataValidation;
                for (int columnIndex = 0; columnIndex < totalColumns; columnIndex ++) {
                    dataValidation =  getDataValidationForColumn(sheet, columnIndex);
                    if (dataValidation != null) {
                        DataValidationConstraint constraint = dataValidation.getValidationConstraint();
                        System.out.println(StrFormatter.format("{}, Column {} Data Validation Formula: {}", sheetName, columnIndex, constraint.getFormula1()));
                    }
                }
                System.out.println("=========================================");
            }

            workbook.close();
        }
    }

    // Helper method to get the data validation for a specific column in a sheet
    private static DataValidation getDataValidationForColumn(Sheet sheet, int column) {
        for (DataValidation validation : sheet.getDataValidations()) {
            CellRangeAddressList addressList = validation.getRegions();
            if (addressList != null && addressList.getCellRangeAddresses().length > 0) {
                CellRangeAddress address = addressList.getCellRangeAddresses()[0];
                if (address.getFirstColumn() == column) {
                    return validation;
                }
            }
        }
        return null;
    }


    public static void generate() {
        try {
            String excelFileName = "existing_sheet.xlsx";
            Workbook workbook;
            Sheet sheet1;
            Sheet sheet2;

            // Check if the Excel file already exists
            File existingFile = new File(excelFileName);
            if (existingFile.exists()) {
                // If the file exists, read it first to preserve existing data
                FileInputStream inputStream = new FileInputStream(excelFileName);
                workbook = new XSSFWorkbook(inputStream);
                sheet1 = workbook.getSheet("Sheet1");
                sheet2 = workbook.getSheet("Sheet2");
            } else {
                // If the file doesn't exist, create a new workbook
                workbook = new XSSFWorkbook();
                sheet1 = workbook.createSheet("Sheet1");
                sheet2 = workbook.createSheet("Sheet2");
            }

            // For demonstration, let's assume you have new headers to add to Sheet1 and Sheet2
            String[] newHeadersSheet1 = {"New Header1", "New Header2"};
            String[] newHeadersSheet2 = {"New HeaderA", "New HeaderB"};

            // Find the last row number of the existing headers in Sheet1 and Sheet2
            int lastRowNumSheet1 = sheet1.getLastRowNum();
            int lastRowNumSheet2 = sheet2.getLastRowNum();

            // Create the header row for Sheet1
            Row headerRowSheet1 = sheet1.createRow(lastRowNumSheet1 + 1); // Start the new header after the existing header
            for (int i = 0; i < newHeadersSheet1.length; i++) {
                Cell cell = headerRowSheet1.createCell(i);
                cell.setCellValue(newHeadersSheet1[i]);
            }

            // Create the header row for Sheet2
            Row headerRowSheet2 = sheet2.createRow(lastRowNumSheet2 + 1); // Start the new header after the existing header
            for (int i = 0; i < newHeadersSheet2.length; i++) {
                Cell cell = headerRowSheet2.createCell(i);
                cell.setCellValue(newHeadersSheet2[i]);
            }

            // Add data to the third sheet (data page)
            Sheet sheet3 = workbook.createSheet("Sheet3");
            for (int i = 0; i < 10; i++) {
                Row row = sheet3.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue("Data " + (i + 1));
            }

            // Add or update the data validation to the cells in Sheet1 and Sheet2 to allow selection from Sheet3 data
            DataValidationHelper validationHelper = sheet1.getDataValidationHelper();
            DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Sheet3!$A$1:$A$10");
            // Assuming Sheet1 has data in column A from row lastRowNumSheet1 + 2 to 100
            CellRangeAddressList addressListSheet1 = new CellRangeAddressList(lastRowNumSheet1 + 2, 100, 0, 0);
            DataValidation validationSheet1 = validationHelper.createValidation(constraint, addressListSheet1);
            sheet1.addValidationData(validationSheet1);

            DataValidationHelper validationHelper2 = sheet2.getDataValidationHelper();
            DataValidationConstraint constraint2 = validationHelper2.createFormulaListConstraint("Sheet3!$A$1:$A$10");
            // Assuming Sheet2 has data in column A from row lastRowNumSheet2 + 2 to 100
            CellRangeAddressList addressListSheet2 = new CellRangeAddressList(lastRowNumSheet2 + 2, 100, 0, 0);
            DataValidation validationSheet2 = validationHelper2.createValidation(constraint2, addressListSheet2);
            sheet2.addValidationData(validationSheet2);

            // Save the updated Excel workbook to a file
            try (FileOutputStream outputStream = new FileOutputStream(excelFileName)) {
                workbook.write(outputStream);
            }

            System.out.println("Excel file has been updated with new headers and data validation.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
