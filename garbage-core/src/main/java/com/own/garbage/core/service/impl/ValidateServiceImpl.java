package com.own.garbage.core.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.own.garbage.common.dto.ConditionDTO;
import com.own.garbage.core.service.ValidateService;
import com.own.garbage.judge.strategy.MatchStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ValidateServiceImpl
 * @Description
 * @Author liugs
 * @Date 2023/7/7 0007 16:16
 */
@Slf4j
@Service
public class ValidateServiceImpl implements ValidateService {


    @Override
    public Object validate(Object reqBo) {
        ConditionDTO dto = new ConditionDTO();
        dto.setOperator("contains");
        dto.setParamName("url");
        dto.setParamValue("/metaData");
        List<ConditionDTO> conditionList = new ArrayList<>();
        conditionList.add(dto);

        JSONObject obj = new JSONObject();
        obj.put("url", "/metaData/getUserInfo");
        Boolean result = MatchStrategyFactory.match(1, conditionList, obj);
        log.info("Match result：{}", result);
        return null;
    }

    @Override
    public Object getExcel(Object reqBo) {
        String json = "{\"code\":\"0\",\"data\":{\"sheetInfos\":[{\"addColumnInfos\":[{\"dataRuleField\":\"sex\",\"fieldName\":\"sex\",\"headerName\":\"性别\"},{\"dataRuleField\":\"unit\",\"fieldName\":\"unit\",\"headerName\":\"单位\"}],\"headRowNo\":1,\"ruleFlag\":\"0\",\"sheetName\":\"Sheet1\",\"tableData\":[{\"address\":\"重庆0\",\"age\":0,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"0@dd.com\",\"method\":\"自动上架\",\"name\":\"Name0\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆1\",\"age\":1,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"1@dd.com\",\"method\":\"自动上架\",\"name\":\"Name1\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆2\",\"age\":2,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"2@dd.com\",\"method\":\"自动上架\",\"name\":\"Name2\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆3\",\"age\":3,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"3@dd.com\",\"method\":\"自动上架\",\"name\":\"Name3\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆4\",\"age\":4,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"4@dd.com\",\"method\":\"自动上架\",\"name\":\"Name4\",\"sex\":\"男\",\"unit\":\"个\"}]},{\"addColumnInfos\":[{\"dataRuleField\":\"sex\",\"fieldName\":\"sex\",\"headerName\":\"性别\"},{\"dataRuleField\":\"unit\",\"fieldName\":\"unit\",\"headerName\":\"单位\"}],\"headRowNo\":1,\"ruleFlag\":\"0\",\"sheetName\":\"Sheet2\",\"tableData\":[{\"address\":\"重庆0\",\"age\":0,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"0@dd.com\",\"method\":\"自动上架\",\"name\":\"Name0\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆1\",\"age\":1,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"1@dd.com\",\"method\":\"自动上架\",\"name\":\"Name1\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆2\",\"age\":2,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"2@dd.com\",\"method\":\"自动上架\",\"name\":\"Name2\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆3\",\"age\":3,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"3@dd.com\",\"method\":\"自动上架\",\"name\":\"Name3\",\"sex\":\"男\",\"unit\":\"个\"},{\"address\":\"重庆4\",\"age\":4,\"createTime\":\"2023-08-03 17:04:42\",\"email\":\"4@dd.com\",\"method\":\"自动上架\",\"name\":\"Name4\",\"sex\":\"男\",\"unit\":\"个\"}]},{\"addColumnInfos\":[{\"fieldName\":\"method\",\"headerName\":\"上架方式\"},{\"fieldName\":\"unit\",\"headerName\":\"单位\"},{\"fieldName\":\"sex\",\"headerName\":\"性别\"}],\"headRowNo\":0,\"ruleFlag\":\"1\",\"ruleInfos\":[{\"filedCode\":\"method\",\"ruleValues\":[\"自动上架\",\"手动上架\",\"无需上架\"]},{\"filedCode\":\"unit\",\"ruleValues\":[\"个\",\"台\",\"箱\"]},{\"filedCode\":\"sex\",\"ruleValues\":[\"男\",\"女\"]}],\"sheetName\":\"Sheet3\"}],\"tableName\":\"ComplexNimble\"},\"message\":\"成功\"}";
        JSONObject obj = JSON.parseObject(json);
        return obj;
    }
}
