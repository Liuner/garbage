package com.own.garbage.core.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.own.garbage.core.bo.GarbageColumnDataBo;
import com.own.garbage.core.bo.GarbageReqBaseBo;
import com.own.garbage.core.bo.GarbageRspBaseBo;
import com.own.garbage.core.bo.GarbageSheetDataBo;
import com.own.garbage.core.service.FileExportService;
import com.own.garbage.core.utils.excel.UserData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * @ClassName FileExportServiceImpl
 * @Description 文件导出服务 实现类
 * @Author liugs
 * @Date 2023/8/1 0001 15:03
 */
@Service
public class FileExportServiceImpl implements FileExportService {

    Map<String, String> dataFieldMap = new HashMap<>(16);
    // 有校验的列，用来后面做规则设置<sheetName,<列号，字段>>
    Map<String, Map<Integer,String>> validateMap = new HashMap<>(16);

    @Override
    public GarbageRspBaseBo trigger(GarbageReqBaseBo reqBo) throws IOException, InvalidFormatException {
        GarbageRspBaseBo retBo = new GarbageRspBaseBo();
        // 加载模板
        File templateFile = FileUtil.file(reqBo.getFilePath());

        // 更新模板，将动态表头及其取值字段写入模板
        List<GarbageSheetDataBo> sheetList = getColumnList();
        FileInputStream fis = new FileInputStream(templateFile);

        // 有校验的列，用来后面做规则设置<sheetName,<列号，字段>>
        validateMap = addHead(sheetList, fis);

        // 获取数据
        List<UserData> userDataList = UserData.getDemoData();
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(userDataList));
        // 多次填充
        String filePath = "E:\\User\\windows\\Desktop\\fillTable11.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(filePath).withTemplate("E:\\User\\windows\\Desktop\\fillDataTemplateNew.xlsx").build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet("Sheet1").build();
        WriteSheet writeSheet3 = EasyExcel.writerSheet("Sheet3").build();

        List<String> method = new ArrayList<>();
        method.add("自动上架");
        method.add("手动上架");
        method.add("无需上架");

        List<String>  extend = new ArrayList<>();
        extend.add("个");
        extend.add("台");
        extend.add("箱");

        List<String> sex = new ArrayList<>();
        sex.add("男");
        sex.add("女");

        List<JSONObject> dataArray = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        allLists.add(method);
        allLists.add(extend);

        // 获取最长列表角标
        int maxSizeIndex = allLists.stream().mapToInt(List::size).max().orElse(0);
        for (int i = 0; i < maxSizeIndex; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("method", (i < method.size()) ? method.get(i) : "");
            jsonObject.put("extend", (i < extend.size()) ? extend.get(i) : "");
            jsonObject.put("sex", (i < sex.size()) ? sex.get(i) : "");
            dataArray.add(jsonObject);
        }
        String methodAddress = dataFieldMap.get("{.method}");
        String add = "Sheet2!" + methodAddress;
        int firstDataRow = Integer.parseInt(methodAddress.substring(methodAddress.length()-1));
        methodAddress = StrUtil.replaceLast(methodAddress, String.valueOf(firstDataRow), String.valueOf(firstDataRow + method.size() - 1));
        dataFieldMap.put("{.method}", add + ":" +methodAddress);

        methodAddress = dataFieldMap.get("{.sex}");
        add = "Sheet2!" + methodAddress;
        firstDataRow = Integer.parseInt(methodAddress.substring(methodAddress.length()-1));
        methodAddress = StrUtil.replaceLast(methodAddress, String.valueOf(firstDataRow), String.valueOf(firstDataRow + sex.size() - 1));
        dataFieldMap.put("{.sex}", add + ":" +methodAddress);

        methodAddress = dataFieldMap.get("{.extend}");
        add = "Sheet2!" + methodAddress;
        firstDataRow = Integer.parseInt(methodAddress.substring(methodAddress.length()-1));
        methodAddress = StrUtil.replaceLast(methodAddress, String.valueOf(firstDataRow), String.valueOf(firstDataRow + extend.size() - 1));
        dataFieldMap.put("{.extend}", add + ":" +methodAddress);

        System.out.println("dataFieldMap：" + JSON.toJSONString(dataFieldMap));

        WriteSheet writeSheet2 = EasyExcel.writerSheet("Sheet2").build();

        excelWriter.fill(jsonArray, writeSheet1);
        excelWriter.fill(jsonArray, writeSheet3);
        excelWriter.fill(dataArray, writeSheet2);
        excelWriter.close();

        setDateValidation(filePath);


        return retBo;
    }


    private void setDateValidation(String filePath) throws IOException, InvalidFormatException {
        File file = FileUtil.file(filePath);
        if (ExcelTypeEnum.XLSX.name().equalsIgnoreCase(FileNameUtil.getSuffix(file))) {
            Workbook workbook = new XSSFWorkbook(file);
            for (Map.Entry<String, Map<Integer, String>> sheetEntry : validateMap.entrySet()) {
                Map<Integer, String> validateColumn = sheetEntry.getValue();
                Sheet sheet = workbook.getSheet(sheetEntry.getKey());
                if (null == sheet) {
                    continue;
                }
                DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                for (Map.Entry<Integer, String> columnEntry : validateColumn.entrySet()) {
                    String key = "{." + columnEntry.getValue() + "}";
                    System.out.println(StrUtil.format("{}页第{}列规则为：{}", sheetEntry.getKey(), columnEntry.getKey(), dataFieldMap.get(key)));
                    DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(dataFieldMap.get(key));
                    CellRangeAddressList addressListSheet1 = new CellRangeAddressList(2, 20000, columnEntry.getKey(), columnEntry.getKey());
                    DataValidation validationSheet1 = validationHelper.createValidation(constraint, addressListSheet1);
                    sheet.addValidationData(validationSheet1);
                }

                // Save the updated Excel workbook to a file
                try (FileOutputStream outputStream = new FileOutputStream("E:\\User\\windows\\Desktop\\fillTable22.xlsx")) {
                    workbook.write(outputStream);
                }
            }
            workbook.close();
        }
    }

    private Map<String, Map<Integer, String>> addHead(List<GarbageSheetDataBo> sheetList, FileInputStream fis) throws IOException {
        Map<String, Map<Integer, String>> sheetMap = new HashMap<>(3);
        Workbook workbook = new XSSFWorkbook(fis);
        for (GarbageSheetDataBo sheetItem : sheetList) {
            Map<Integer, String> map = new HashMap<>(16);
            Sheet sheet = workbook.getSheet(sheetItem.getSheetName());

            // Get the last column number (index) in the existing sheet
            int lastColumnNum = sheet.getRow(1).getLastCellNum();
            // 获取表头行
            Row headRow = sheet.getRow(sheetItem.getHeadNum());
            // 获取最后一个单元格的样式
            CellStyle cellStyle = headRow.getCell(lastColumnNum - 1).getCellStyle();
            // 模板字段配置行
            Row fieldRow = sheet.getRow(sheetItem.getHeadNum() + 1);
            for (GarbageColumnDataBo item : sheetItem.getColumns()) {

                Cell cell = headRow.createCell(lastColumnNum);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(item.getHeadName());

                Cell dateFieldCell = fieldRow.createCell(lastColumnNum++);
                dateFieldCell.setCellValue("{." + item.getFieldName() + "}");
                if (StrUtil.isNotEmpty(item.getDataRuleField())) {
                    map.put(cell.getColumnIndex(), item.getDataRuleField());
                }
            }
            sheetMap.put(sheetItem.getSheetName(), map);
        }
        FileOutputStream outputStream = new FileOutputStream("E:\\User\\windows\\Desktop\\fillDataTemplateNew.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        fis.close();
        workbook.close();

        Workbook workbookNew = new XSSFWorkbook(IoUtil.toStream(FileUtil.file("E:\\User\\windows\\Desktop\\fillDataTemplateNew.xlsx")));
        Sheet sheet = workbookNew.getSheet("Sheet2");
        Row fieldRow = sheet.getRow(1);
        for (int i = 0; i < fieldRow.getLastCellNum(); i ++) {
            Cell cell = fieldRow.getCell(i);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().contains("{.")) {
                dataFieldMap.put(cell.getStringCellValue(), "$" + CellReference.convertNumToColString(cell.getAddress().getColumn()) + "$" + (cell.getAddress().getRow() + 1));
            }
        }
        System.out.println(JSON.toJSONString(dataFieldMap));
        return sheetMap;
    }

    /**
     * 描述 获取动态表头
     * @param
     * @return java.util.List<com.own.garbage.core.bo.GarbageColumnDataBo>
     * @author liugs
     * @date 2023/8/1 0001 15:21
     */
    private List<GarbageSheetDataBo> getColumnList() {
        List<GarbageSheetDataBo> sheetList = new ArrayList<>();

        GarbageColumnDataBo column1 = new GarbageColumnDataBo();
        column1.setHeadName("性别");
        column1.setFieldName("sex");
        column1.setDataRuleField("sex");

        GarbageColumnDataBo column2 = new GarbageColumnDataBo();
        column2.setHeadName("扩展参数");
        column2.setFieldName("extend");
        column2.setDataRuleField("extend");

        List<GarbageColumnDataBo> columnList = new ArrayList<>();
        columnList.add(column1);
        columnList.add(column2);

        GarbageSheetDataBo sheet1 = new GarbageSheetDataBo();
        sheet1.setSheetName("Sheet1");
        sheet1.setColumns(columnList);
        sheet1.setHeadNum(1);
        sheetList.add(sheet1);

        GarbageColumnDataBo column3 = new GarbageColumnDataBo();
        column3.setHeadName("扩展参数取值");
        column3.setFieldName("extend");

        GarbageColumnDataBo column4 = new GarbageColumnDataBo();
        column4.setHeadName("性别");
        column4.setFieldName("sex");

        List<GarbageColumnDataBo> columnList1 = new ArrayList<>();
        columnList1.add(column3);
        columnList1.add(column4);

        GarbageSheetDataBo sheet2 = new GarbageSheetDataBo();
        sheet2.setSheetName("Sheet2");
        sheet2.setColumns(columnList1);
        sheet2.setHeadNum(0);
        sheetList.add(sheet2);


        return sheetList;
    }
}
