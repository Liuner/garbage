package com.own.garbage.core.utils.excel;

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

    public static List<UserData> getDemoData() {
        List<UserData> userDataList = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            userDataList.add(UserData.builder()
                    .name("Name" + i)
                    .age(i)
                    .email(i + "@dd.com")
                    .build());
        }
        return userDataList;
    }
}
