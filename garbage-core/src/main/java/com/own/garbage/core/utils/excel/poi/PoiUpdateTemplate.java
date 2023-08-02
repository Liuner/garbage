package com.own.garbage.core.utils.excel.poi;

import com.own.garbage.core.bo.GarbageColumnDataBo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PoiUpdateTemplate
 * @Description
 * @Author liugs
 * @Date 2023/8/1 0001 10:33
 */
public class PoiUpdateTemplate {

    public static void main(String[] args) throws IOException {
        String templateFile = "E:\\User\\windows\\Desktop\\fillDataTemplate.xlsx";

        FileInputStream fis = new FileInputStream(templateFile);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Get the last column number (index) in the existing sheet
        int lastColumnNum = sheet.getRow(1).getLastCellNum();
        // 获取表头行
        Row headRow = sheet.getRow(1);
        // 获取最后一个单元格的样式
        CellStyle cellStyle = headRow.getCell(lastColumnNum - 1).getCellStyle();

        // 模板字段配置行
        Row fieldRow = sheet.getRow(2);

        GarbageColumnDataBo newColumn = new GarbageColumnDataBo();
        newColumn.setHeadName("性别");
        newColumn.setFieldName("sex");

        GarbageColumnDataBo newColumn1 = new GarbageColumnDataBo();
        newColumn1.setHeadName("扩展");
        newColumn1.setFieldName("extend");

        List<GarbageColumnDataBo> newColumnDataBos = new ArrayList<>();
        newColumnDataBos.add(newColumn);
        newColumnDataBos.add(newColumn1);

        for (GarbageColumnDataBo item : newColumnDataBos) {
            Cell cell = headRow.createCell(lastColumnNum);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(item.getHeadName());

            Cell cell1 = fieldRow.createCell(lastColumnNum ++);
            cell1.setCellValue("{." + item.getFieldName() + "}");
        }

        FileOutputStream outputStream = new FileOutputStream("E:\\User\\windows\\Desktop\\fillDataTemplateNew.xlsx");
        workbook.write(outputStream);

        outputStream.close();
        fis.close();
        workbook.close();
    }
}
