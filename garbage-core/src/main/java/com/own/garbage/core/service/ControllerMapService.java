package com.own.garbage.core.service;

import com.own.garbage.core.bo.GarbageReqBaseBo;
import com.own.garbage.core.bo.GarbageRspBaseBo;

/**
 * @ClassName ControllerMapService
 * @Description controller菜单映射
 * @Author liugs
 * @Date 2023/8/9 0009 15:43
 */
public interface ControllerMapService {

    /**
     * 描述 获取映射
     * @param reqBo
     * @return com.own.garbage.core.bo.GarbageRspBaseBo
     * @author liugs
     * @date 2023/8/9 0009 15:43
     */
    GarbageRspBaseBo getLcpMap(GarbageReqBaseBo reqBo);

    /**
     * 描述 获取映射
     * @param reqBo
     * @return com.own.garbage.core.bo.GarbageRspBaseBo
     * @author liugs
     * @date 2023/8/9 0009 15:43
     */
    GarbageRspBaseBo getPortalMap(GarbageReqBaseBo reqBo);
}
