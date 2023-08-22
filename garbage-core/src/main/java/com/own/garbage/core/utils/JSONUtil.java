package com.own.garbage.core.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;

import java.util.Map;

/**
 * @ClassName JSONUtil
 * @Description
 * @Author liugs
 * @Date 2023/8/7 0007 16:06
 */
public class JSONUtil {

    public static Map<String, Object> flatJson(Object obj) {
        return JsonFlattener.flattenAsMap(JSON.toJSONString(obj, JSONWriter.Feature.WriteNonStringValueAsString));
    }

    public static String unFlat(Map<String, Object> flatMap) {
        return JsonUnflattener.unflatten(flatMap);
    }

    public static JSONObject merge(Object source, Object target) {
        Map<String, Object> sourceMap = flatJson(source);
        Map<String, Object> targetMap = flatJson(target);
        targetMap.putAll(sourceMap);
        return JSON.parseObject(unFlat(targetMap));
    }
}
