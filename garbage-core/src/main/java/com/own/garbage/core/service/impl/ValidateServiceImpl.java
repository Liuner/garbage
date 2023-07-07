package com.own.garbage.core.service.impl;

import cn.hutool.json.JSONObject;
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
        obj.set("url", "/metaData/getUserInfo");
        Boolean result = MatchStrategyFactory.match(1, conditionList, obj);
        log.info("Match result：{}", result);
        return null;
    }
}
