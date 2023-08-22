package com.own.garbage.starter.controller;

import com.own.garbage.core.bo.GarbageReqBaseBo;
import com.own.garbage.core.bo.GarbageRspBaseBo;
import com.own.garbage.core.service.ControllerMapService;
import com.own.garbage.core.service.ValidateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ValidateController
 * @Description
 * @Author liugs
 * @Date 2023/7/7 0007 16:27
 */
@RestController
@RequestMapping("garbage")
public class ValidateController {

    private final ValidateService validateService;

    private final ControllerMapService controllerMapService;

    public ValidateController(ValidateService validateService,
                              ControllerMapService controllerMapService) {
        this.validateService = validateService;
        this.controllerMapService = controllerMapService;
    }

    @PostMapping("validate")
    @ResponseBody
    public Object validate(Object obj) {
        return validateService.validate(obj);
    }

    @PostMapping("getExcel")
    @ResponseBody
    public Object getExcel(Object obj) {
        return validateService.getExcel(obj);
    }


    @PostMapping("getMap")
    @ResponseBody
    public GarbageRspBaseBo getMap(GarbageReqBaseBo obj) {
        return controllerMapService.getLcpMap(obj);
    }

    @PostMapping("getPortalMap")
    @ResponseBody
    public GarbageRspBaseBo getPortalMap(GarbageReqBaseBo obj) {
        return controllerMapService.getPortalMap(obj);
    }



}
