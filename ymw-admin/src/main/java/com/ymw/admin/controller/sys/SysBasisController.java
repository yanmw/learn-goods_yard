package com.ymw.admin.controller.sys;

import com.ymw.admin.sevice.sys.SysBasisService;
import com.ymw.core.http.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Api(tags = "基础信息管理")
@RestController
@RequestMapping(value = "/qr/sysBasis")
public class SysBasisController {

    @Autowired
    private SysBasisService sysBasisService;

    @ApiOperation(value = "更新缓存")
    @GetMapping(value = "/setBasisInfo")
    public HttpResult setBasisInfo() {
        sysBasisService.setBasisInfo();
        return HttpResult.ok();
    }

    @ApiOperation(value = "获取缓存信息")
    @GetMapping(value = "/getBasisInfo")
    public HttpResult getBasisInfo() {
        Map map = sysBasisService.getBasisInfo();
        return HttpResult.ok(map);
    }
}
