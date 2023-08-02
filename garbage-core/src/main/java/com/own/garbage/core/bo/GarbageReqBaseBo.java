package com.own.garbage.core.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GarbageReqBaseBo
 * @Description 入参基类
 * @Author liugs
 * @Date 2023/8/1 0001 15:01
 */
@Data
public class GarbageReqBaseBo implements Serializable {

    private static final long serialVersionUID = 7751023752780440093L;

    private String filePath;
}
