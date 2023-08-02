package com.own.garbage.core.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GarbageRspBaseBo
 * @Description 出参基类
 * @Author liugs
 * @Date 2023/8/1 0001 15:00
 */
@Data
public class GarbageRspBaseBo implements Serializable {

    private static final long serialVersionUID = -2978983365560607208L;

    private String code;

    private String message;
}
