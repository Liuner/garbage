package com.own.garbage.core.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName GarbageSheetDataBo
 * @Description
 * @Author liugs
 * @Date 2023/8/1 0001 16:36
 */
@Data
public class GarbageSheetDataBo implements Serializable {

    private static final long serialVersionUID = -155924813849464562L;

    private String sheetName;

    private Integer headNum;

    private List<GarbageColumnDataBo> columns;
}
