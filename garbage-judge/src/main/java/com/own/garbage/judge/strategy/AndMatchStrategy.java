/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.own.garbage.judge.strategy;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.own.garbage.common.dto.ConditionDTO;
import com.own.garbage.judge.judge.PredicateJudgeFactory;
import com.own.garbage.spi.Join;

import java.util.List;

/**
 * This is and match strategy.
 */
@Join
public class AndMatchStrategy extends AbstractMatchStrategy implements MatchStrategy {

    @Override
    public Boolean match(final List<ConditionDTO> conditionDTOList, final Object obj) {
        JSONObject json = JSON.parseObject(JSON.toJSONString(obj));
        return conditionDTOList
                .stream()
                .allMatch(condition -> PredicateJudgeFactory.judge(condition, buildRealData(condition, json)));
    }
}
