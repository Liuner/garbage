package com.own.garbage.core.utils.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName PortalFileTest
 * @Description 前端controller文件解析
 * @Author liugs
 * @Date 2023/8/21 0021 10:33
 */
public class PortalFileTest {

    private static final Pattern URL_ALIAS_GROUP = java.util.regex.Pattern.compile("[a-zA-Z]{3,6}_[^}]*");
    private static final Pattern URL_ALIAS_PATTERN = Pattern.compile("[a-zA-Z]{3,6}_[^:]*");
    private static final Pattern URL_PATTERN = Pattern.compile("control:[^\\}]*");
    private static final Pattern PATH_PATTERN_GROUP = Pattern.compile("path:[^!]*");
    private static final Pattern PATH_PATTERN = Pattern.compile("path:[^,]*");
    private static final Pattern IMPORT_PATTERN = Pattern.compile("@[^']*");
    private static final Pattern PAGE_URL_PATTERN = Pattern.compile("DYC_URL[^,]*");

    public static void main(String[] args) {
        // 获取controller别名
//        getControllerAliasMapping();

        // 获取页面路由映射关系
        getPageRoutMapping();
    }

    /**
     * 描述 获取页面路由映射
     * @param
     * @return void
     * @author liugs
     * @date 2023/8/21 0021 11:20
     */
    private static void getPageRoutMapping() {
        // 所有页面存放目录
        String filePath = "E:\\all_Codes\\bcm\\bcm-portal\\dyc-portal-pc-vue\\src\\pages";
        File grandFile = FileUtil.newFile(filePath);
        if (grandFile.isDirectory()) {
            File[] parentFile = grandFile.listFiles();
            for (File file : parentFile) {

            }
        }


    }

    /**
     * 描述 获取controller别名
     *
     * @param
     * @return void
     * @author liugs
     * @date 2023/8/21 0021 11:19
     */
    private static void getControllerAliasMapping() {
        String filePath = "E:\\all_Codes\\bcm\\bcm-portal\\dyc-portal-pc-vue\\src\\api\\url";
        File file = FileUtil.newFile(filePath);
        if (file.isDirectory()) {
            File[] urlJs = file.listFiles();
            JSONObject controllerJson = new JSONObject();
            Matcher aliasMatcher;
            Matcher urlMatcher;
            String fileContent;
            for (File urlItem : urlJs) {
                fileContent = FileUtil.readString(urlItem, StandardCharsets.UTF_8);
                Matcher matcher = URL_ALIAS_GROUP.matcher(fileContent);
                while (matcher.find()) {
                    String alias;
                    String controller;
                    String controllerGroup = matcher.group();
                    aliasMatcher = URL_ALIAS_PATTERN.matcher(controllerGroup);
                    if (aliasMatcher.find()) {
                        alias = aliasMatcher.group();
                    } else {
                        System.out.println("没有匹配到别名：" + controllerGroup);
                        continue;
                    }
                    urlMatcher = URL_PATTERN.matcher(controllerGroup);
                    if (urlMatcher.find()) {
                        controller = urlMatcher.group();
                    } else {
                        System.out.println("没有匹配到controller：" + controllerGroup);
                        continue;
                    }
                    controllerJson.put(StrUtil.sub(controller, controller.indexOf("'") + 1, controller.lastIndexOf("'")), alias);
                }
            }
            System.out.println("地址映射JSON：" + controllerJson.toJSONString());
        }
    }
}