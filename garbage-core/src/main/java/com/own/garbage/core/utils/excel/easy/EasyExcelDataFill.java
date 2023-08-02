package com.own.garbage.core.utils.excel.easy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.own.garbage.core.utils.excel.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName EasyExcelDataFill
 * @Description 数据填充
 * @Author liugs
 * @Date 2023/7/31 0031 15:47
 */
public class EasyExcelDataFill {

    public static void main(String[] args) {
        String templatePath = "E:\\User\\windows\\Desktop\\fillDataTemplate.xlsx";

        doDataFill(templatePath);
    }

    private static void doDataFill(String templatePath) {
        String filePath = "E:\\User\\windows\\Desktop\\fillTable11.xlsx";
        // 获取数据
        List<UserData> userDataList = UserData.getDemoData();
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(userDataList));

        // 多次填充
        ExcelWriter excelWriter = EasyExcel.write(filePath).withTemplate(templatePath).build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet("Sheet1").build();
        WriteSheet writeSheet3 = EasyExcel.writerSheet("Sheet3").build();

        List<String> method = new ArrayList<>();
        method.add("自动上架");
        method.add("手动上架");

        List<String>  unit = new ArrayList<>();
        unit.add("个");
        unit.add("台");
        unit.add("箱");

        JSONArray dataArray = new JSONArray();
        List<List<String>> allLists = new ArrayList<>();
        allLists.add(method);
        allLists.add(unit);

        // 获取最长列表角标
        int maxSizeIndex = allLists.stream().mapToInt(List::size).max().orElse(0);
        for (int i = 0; i < maxSizeIndex; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("method", (i < method.size()) ? method.get(i) : "");
            jsonObject.put("unit", (i < unit.size()) ? unit.get(i) : "");
            dataArray.add(jsonObject);
        }



        // 数据表填充
        WriteSheet writeSheet2 = EasyExcel.writerSheet("Sheet2").build();


        excelWriter.fill(jsonArray, writeSheet1);
        excelWriter.fill(jsonArray, writeSheet3);
        excelWriter.fill(dataArray, writeSheet2);

        excelWriter.close();
    }
}
