package com.own.garbage.starter.controller;

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

    public ValidateController(ValidateService validateService) {
        this.validateService = validateService;
    }

    @PostMapping("validate")
    @ResponseBody
    public Object validate(Object obj) {
        return validateService.validate(obj);
    }
}
