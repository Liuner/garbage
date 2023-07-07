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

package com.own.garbage.judge.judge;


import cn.hutool.core.util.StrUtil;
import com.own.garbage.common.dto.ConditionDTO;
import com.own.garbage.common.enums.OperatorEnum;
import com.own.garbage.spi.ExtensionLoader;

import java.util.Objects;

/**
 * Predicate judge factory.
 */
public final class PredicateJudgeFactory {
    
    private PredicateJudgeFactory() {
    }
    
    /**
     * New instance predicate judge.
     *
     * @param operator the operator
     * @return the predicate judge
     */
    public static PredicateJudge newInstance(final String operator) {
        return ExtensionLoader.getExtensionLoader(PredicateJudge.class).getJoin(processSpecialOperator(operator));
    }
    
    /**
     * judge request realData has by pass.
     *
     * @param conditionDTO condition data
     * @param realData realData
     * @return is true pass   is false not pass
     */
    public static Boolean judge(final ConditionDTO conditionDTO, final String realData) {
        if (Objects.isNull(conditionDTO) || StrUtil.isBlank(realData)) {
            return false;
        }
        return newInstance(conditionDTO.getOperator()).judge(conditionDTO, realData);
    }

    /**
     * process special operator, like = need to change to equals.
     *
     * @param operator {@linkplain OperatorEnum} alias
     * @return alias
     */
    private static String processSpecialOperator(final String operator) {
        return "=".equals(operator) ? "equals" : operator;
    }
}
