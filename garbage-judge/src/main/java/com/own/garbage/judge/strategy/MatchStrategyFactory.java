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


import com.own.garbage.common.dto.ConditionDTO;
import com.own.garbage.common.enums.MatchModeEnum;
import com.own.garbage.spi.ExtensionLoader;

import java.util.List;

/**
 * MatchStrategyFactory.
 */
public final class MatchStrategyFactory {
    
    private MatchStrategyFactory() {
    }
    
    /**
     * New instance match strategy.
     *
     * @param strategy the strategy
     * @return the match strategy
     */
    public static MatchStrategy newInstance(final Integer strategy) {
        String matchMode = MatchModeEnum.getMatchModeByCode(strategy);
        return ExtensionLoader.getExtensionLoader(MatchStrategy.class).getJoin(matchMode);
    }
    
    /**
     * Match boolean.
     *
     * @param strategy the strategy
     * @param conditionDTOList the condition data list
     * @param obj the obj
     * @return the boolean
     */
    public static boolean match(final Integer strategy, final List<ConditionDTO> conditionDTOList, final Object obj) {
        return newInstance(strategy).match(conditionDTOList, obj);
    }
}