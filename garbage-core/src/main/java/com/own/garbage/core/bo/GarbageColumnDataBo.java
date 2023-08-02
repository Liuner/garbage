package com.own.garbage.core.bo;

import lombok.Data;

/**
 * @ClassName GarbageColumnDataBo
 * @Description
 * @Author liugs
 * @Date 2023/7/31 0031 16:45
 */
@Data
public class GarbageColumnDataBo {

    private Integer sheetNum;

    private String headName;

    private String fieldName;

    private String dataRuleField;
}
