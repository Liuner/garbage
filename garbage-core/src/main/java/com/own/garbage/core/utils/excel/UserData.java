package com.own.garbage.core.utils.excel;

import cn.hutool.core.date.DateTime;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserData
 * @Description
 * @Author liugs
 * @Date 2023/7/20 0020 14:38
 */
@Builder
@Data
public class UserData {

    private String name;

    private int age;

    private String email;

    private String address;

    private String createTime;

    private String extend;

    private String method;

    private String sex;

    public static List<UserData> getDemoData() {
        List<UserData> userDataList = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            userDataList.add(UserData.builder()
                            .name("Name" + i)
                            .age(i)
                            .email(i + "@dd.com")
                            .createTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"))
                            .address("重庆" + i)
                            .extend("个")
                            .method("自动上架")
                            .sex("男")
                    .build());
        }
        return userDataList;
    }
}
