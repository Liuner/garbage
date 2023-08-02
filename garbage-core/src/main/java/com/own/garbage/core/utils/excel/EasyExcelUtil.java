package com.own.garbage.core.utils.excel;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName EasyExcelUtil
 * @Description
 * @Author liugs
 * @Date 2023/7/19 0019 15:23
 */
@Slf4j
public class EasyExcelUtil {

    public static void main(String[] args) {
        easyExcelWrite();
    }

    private static void easyExcelWrite() {
        String excelPath = "E:\\User\\windows\\Desktop\\writeTest.xlsx";

        List<UserData> dateList = UserData.getDemoData();

        EasyExcel.write(excelPath)
                .head(head())
                .sheet(1)
                .doWrite(dateList);
    }

    /**
     * 表头
     */
    private static List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("姓名" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("年龄" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("邮箱" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }
}
