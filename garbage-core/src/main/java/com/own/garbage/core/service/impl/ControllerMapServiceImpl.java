package com.own.garbage.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.FieldUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.own.garbage.core.bo.GarbageReqBaseBo;
import com.own.garbage.core.bo.GarbageRspBaseBo;
import com.own.garbage.core.dao.ControllerMenuMapMapper;
import com.own.garbage.core.dao.SysMenuMapper;
import com.own.garbage.core.dao.po.ControllerMenuMapPO;
import com.own.garbage.core.dao.po.SysMenuPO;
import com.own.garbage.core.service.ControllerMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName ControllerMapServiceImpl
 * @Description 请求路径映射
 * @Author liugs
 * @Date 2023/8/9 0009 15:44
 */
@Slf4j
@Service
public class ControllerMapServiceImpl implements ControllerMapService {

    private final Pattern CONTROLLER_PATTERN = Pattern.compile("\\/metaData[^,]*,");

    private final Pattern URL_ALIAS_GROUP = Pattern.compile("[a-zA-Z]{3,6}_[^}]*");
    private final Pattern URL_ALIAS_PATTERN = Pattern.compile("[a-zA-Z]{3,6}_[^:]*");
    private final Pattern URL_PATTERN = Pattern.compile("control:[^\\}]*");
    private final Pattern PATH_PATTERN_GROUP = Pattern.compile("path:[^!]*");
    private final Pattern PATH_PATTERN = Pattern.compile("path:[^,]*");
    private final Pattern IMPORT_PATTERN = Pattern.compile("@[^']*");
    private final Pattern PAGE_URL_PATTERN = Pattern.compile("DYC_URL[^,]*");


    private final ControllerMenuMapMapper controllerMenuMapMapper;
    private final SysMenuMapper sysMenuMapper;

    public ControllerMapServiceImpl(ControllerMenuMapMapper controllerMenuMapMapper,
                                    SysMenuMapper sysMenuMapper) {
        this.controllerMenuMapMapper = controllerMenuMapMapper;
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    public GarbageRspBaseBo getLcpMap(GarbageReqBaseBo reqBo) {
        GarbageRspBaseBo retBo = new GarbageRspBaseBo();
        // 将所有前端文件及controller读入到数据库
        File packageFile = FileUtil.file(reqBo.getFilePath());
        Map<String, List<String>> fileControllerMap = new HashMap<>(packageFile.listFiles().length);
        // 文件夹，遍历
        if (FileUtil.isDirectory(packageFile)) {
            File[] subFiles = packageFile.listFiles();
            Matcher matcher;
            String fileContent;
            String fileName;
            String controllerPath;
            for (File file : subFiles) {
                fileName = StrUtil.removeSuffix(FileUtil.getName(file), ".vue");
                fileContent = FileUtil.readString(file, Charset.defaultCharset());
                matcher = CONTROLLER_PATTERN.matcher(fileContent);
                List<String> controllerList = new ArrayList<>();
                while (matcher.find()) {
                    controllerPath = StrUtil.removeSuffix(matcher.group(), "',");
                    log.info("{}中的controller：{}", file, controllerPath);
                    controllerList.add(controllerPath);
                }
                fileControllerMap.put(fileName, controllerList);
            }
        }

        // 从路由文件中读取页面、路由关系
        String indexPath = "E:\\all_Codes\\bcm\\bcm-lcp-portal\\dyc-lowcode\\src\\router\\index.js";
        String indexStr = FileUtil.readString(FileUtil.file(indexPath), Charset.defaultCharset());
        List<JSONObject> indexJson = JSON.parseArray(indexStr, JSONObject.class);
        Map<String, String> indexFileNameMap = indexJson.stream()
                .collect(Collectors.toMap(item-> StrUtil.removePrefix(item.getString("name"), "P"), item -> StrUtil.removePrefix(item.getString("path"), "/")));

        // 将所有菜单查询出来
        List<SysMenuPO> menPoList = sysMenuMapper.getList(new SysMenuPO());
        menPoList = menPoList.stream().filter(item -> StrUtil.isNotEmpty(item.getMenuUri())).collect(Collectors.toList());
        Map<String, String> routeMenuMap = new HashMap<>(16);
        menPoList.forEach(item -> {
            String menuUrl = StrUtil.subSuf(item.getMenuUri(), (item.getMenuUri().lastIndexOf("/") +1));
            if (StrUtil.isEmpty(routeMenuMap.get(menuUrl))) {
                routeMenuMap.put(menuUrl, item.getMenuName());
            } else {
                routeMenuMap.put(menuUrl, routeMenuMap.get(menuUrl) + "," + item.getMenuName());
            }
        });

        List<ControllerMenuMapPO> relPoList = new ArrayList<>();

        Iterator<Map.Entry<String, List<String>>> iterator = fileControllerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            ControllerMenuMapPO fileRelPo = new ControllerMenuMapPO();
            fileRelPo.setPageName(entry.getKey());
            fileRelPo.setRoute(indexFileNameMap.get(entry.getKey()));
            fileRelPo.setMenuName(routeMenuMap.get(fileRelPo.getRoute()));
            if (CollectionUtil.isEmpty(entry.getValue())) {
                fileRelPo.setController("not found");
                relPoList.add(fileRelPo);
                continue;
            }
            entry.getValue().forEach(item -> {
                ControllerMenuMapPO itemRelPo = new ControllerMenuMapPO();
                BeanUtils.copyProperties(fileRelPo, itemRelPo);
                itemRelPo.setController(item);
                relPoList.add(itemRelPo);
            });
        }

        controllerMenuMapMapper.insertBatch(relPoList);

        return retBo;
    }

    @Override
    public GarbageRspBaseBo getPortalMap(GarbageReqBaseBo reqBo) {
        GarbageRspBaseBo retBo = new GarbageRspBaseBo();

        // 接口定义及接口映射
        Map<String, String> urlAliasMap = getUrlAliasMap();

        // 页面路由映射
        Map<String, String> routePageMap = getRoutePageMap();

        // 页面接口
        Map<String, List<String>> pageUrlAliasMap = getPageUrlAliasMap();

        Map<String, String> controllerAliasMap = new HashMap<>(16);
        Iterator<Map.Entry<String, List<String>>> iterator = pageUrlAliasMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            List<String> controllerAlias = entry.getValue();
            controllerAlias.forEach(item -> controllerAliasMap.put(item, routePageMap.get(entry.getKey())));
        }

        Map<String, String> controllerRouteMap = new HashMap<>(16);

        Iterator<Map.Entry<String, String>> iterator1 = urlAliasMap.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry<String, String> entry = iterator1.next();
            controllerRouteMap.put(entry.getKey(), controllerAliasMap.get(entry.getValue()));
        }

        // 将所有菜单查询出来
        List<SysMenuPO> menPoList = sysMenuMapper.getList(new SysMenuPO());
        menPoList = menPoList.stream().filter(item -> StrUtil.isNotEmpty(item.getMenuUri())).collect(Collectors.toList());
        Map<String, String> routeMenuMap = new HashMap<>(16);
        menPoList.forEach(item -> {
            String menuUrl = StrUtil.subSuf(item.getMenuUri(), (item.getMenuUri().lastIndexOf("/") +1));
            if (StrUtil.isEmpty(routeMenuMap.get(menuUrl))) {
                routeMenuMap.put(menuUrl, item.getMenuName());
            } else {
                routeMenuMap.put(menuUrl, routeMenuMap.get(menuUrl) + "," + item.getMenuName());
            }
        });

        List<ControllerMenuMapPO> relPoList = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator2 = controllerRouteMap.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> entry = iterator2.next();
            ControllerMenuMapPO fileRelPo = new ControllerMenuMapPO();
            fileRelPo.setPageName(entry.getValue());
            fileRelPo.setRoute(entry.getValue());
            fileRelPo.setMenuName(routeMenuMap.get(fileRelPo.getRoute()));
            fileRelPo.setController(entry.getKey());
            relPoList.add(fileRelPo);
        }

        controllerMenuMapMapper.insertBatch(relPoList);
        return retBo;
    }

    private Map<String, List<String>> getPageUrlAliasMap() {
        Map<String, List<String>> pageUrlAliasMap = new HashMap<>(16);
        String pagePath = "E:\\all_Codes\\bcm\\bcm-portal\\dyc-portal-pc-vue\\src\\pages";
        File file = FileUtil.file(pagePath);
        getPageUrlMap(file, pageUrlAliasMap);
        return pageUrlAliasMap;
    }

    private void getPageUrlMap(File file, Map<String, List<String>> pageUrlAliasMap) {
        if (file.isDirectory()) {
            File[] subFile = file.listFiles();
            for (File item : subFile) {
                getPageUrlMap(item, pageUrlAliasMap);
            }
        } else {
            List<String> urlList = new ArrayList<>();
            String pageContent = FileUtil.readString(file, Charset.defaultCharset());
            Matcher matcher = PAGE_URL_PATTERN.matcher(pageContent);
            while (matcher.find()) {
                String url = matcher.group();
                urlList.add(StrUtil.sub(url, url.indexOf(".") + 1, url.length()));
            }
            if (urlList.isEmpty()) {
                log.info(file.getAbsolutePath());
            }
            String fileName = FileUtil.getName(file);
            pageUrlAliasMap.put(StrUtil.sub(fileName, 0, fileName.indexOf(".")),  urlList);
        }
    }


    private Map<String, String> getRoutePageMap() {
        Map<String, String> routePageMap = new HashMap<>(16);
        // 读取路由的url
        String pagePath = "E:\\all_Codes\\bcm\\bcm-portal\\dyc-portal-pc-vue\\src\\pages";
        File pageDirectory = FileUtil.file(pagePath);
        recursion(pageDirectory, routePageMap);

        return routePageMap;
    }

    private void recursion(File file, Map<String, String> routePageMap) {
        File routes = null;
        if (file.isDirectory()) {
            File[] subFile = file.listFiles();
            Map<String, File> fileNameMap = Arrays.stream(subFile).collect(Collectors.toMap(item -> FileUtil.getName(item), Function.identity()));
            if (fileNameMap.keySet().contains("routes.js")) {
                routes = fileNameMap.get("routes.js");
            } else {
                for (File item :subFile) {
                    recursion(item, routePageMap);
                }
            }
        }

        if (routes != null) {
            String routeStr = FileUtil.readString(routes, Charset.defaultCharset());
            Matcher pathMatcher = PATH_PATTERN_GROUP.matcher(routeStr);
            Matcher routerMatcher;
            Matcher pageMather;
            String itemStr;
            while (pathMatcher.find()) {
                itemStr = pathMatcher.group();
                String router = null;
                String page = null;
                routerMatcher = PATH_PATTERN.matcher(itemStr);
                if (routerMatcher.find()) {
                    router = routerMatcher.group();
                }
                pageMather = IMPORT_PATTERN.matcher(itemStr);
                if (pageMather.find()) {
                    page = pageMather.group();
                }
                try {
                    routePageMap.put(StrUtil.sub(page, page.lastIndexOf("/") + 1, page.length()), StrUtil.sub(router, router.indexOf("'") + 1, router.lastIndexOf("'")));
                } catch (Exception e) {
                    log.info(itemStr);
                }
            }
        }
    }

    private Map<String, String> getUrlAliasMap() {
        // 读取apiUrl 定义
        String urlPath = "E:\\all_Codes\\bcm\\bcm-portal\\dyc-portal-pc-vue\\src\\api\\url";
        File urlPathFile = FileUtil.file(urlPath);
        Map<String, String> urlMap = new HashMap<>(16);
        if (urlPathFile.isDirectory()) {
            File[] urls = urlPathFile.listFiles();
            Matcher aliasMatcher;
            Matcher urlMatcher;
            for (File item : urls) {
                String urlInfos = FileUtil.readString(item, Charset.defaultCharset());
                // 匹配单个接口定义部分
                Matcher matcher = URL_ALIAS_GROUP.matcher(urlInfos);
                while (matcher.find()) {
                    String alias = null;
                    String url = null;
                    // 提取接口定义名称
                    String urlGroupStr = matcher.group();
                    aliasMatcher = URL_ALIAS_PATTERN.matcher(urlGroupStr);
                    if (aliasMatcher.find()) {
                        alias = aliasMatcher.group();
                    }
                    urlMatcher = URL_PATTERN.matcher(urlGroupStr);
                    if (urlMatcher.find()) {
                        url = urlMatcher.group();
                    }
                    try {
                        urlMap.put(StrUtil.sub(url, url.indexOf("'"), url.lastIndexOf("'")), alias);
                    } catch (Exception e) {
                        log.info(urlGroupStr);
                    }
                }
            }
        }
        return urlMap;
    }
}