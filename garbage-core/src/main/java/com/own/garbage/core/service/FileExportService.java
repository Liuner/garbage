package com.own.garbage.core.service;

import com.own.garbage.core.bo.GarbageReqBaseBo;
import com.own.garbage.core.bo.GarbageRspBaseBo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @ClassName FileExportService
 * @Description 文件导出服务
 * @Author liugs
 * @Date 2023/8/1 0001 14:59
 */
public interface FileExportService {


    /**
     * 描述 触发导出
     * @param reqBo
     * @return com.own.garbage.core.bo.GarbageRspBaseBo
     * @author liugs
     * @date 2023/8/1 0001 15:03
     */
    GarbageRspBaseBo trigger(GarbageReqBaseBo reqBo) throws IOException, InvalidFormatException;

}
