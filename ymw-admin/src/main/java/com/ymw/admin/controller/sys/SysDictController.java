package com.ymw.admin.controller.sys;

import java.util.List;

import com.ymw.admin.sevice.sys.SysBasisService;
import com.ymw.admin.util.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ymw.admin.model.sys.SysDict;
import com.ymw.admin.sevice.sys.SysDictService;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 字典控制器
 *
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("/qr/dict")
@Api(tags = "字典管理")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysBasisService sysBasisService;

    @PreAuthorize("hasAuthority('sys:dict:add') AND hasAuthority('sys:dict:edit')")
    @PostMapping(value = "/save")
    @ApiOperation(value = "新增/更新字典")
    @Log(title = "新增/更新字典数据")
    public HttpResult save(@RequestBody SysDict record) {
        int result = sysDictService.save(record);
        if (result > 0) {
            sysBasisService.setBasisInfo();
            return HttpResult.ok();
        } else {
            return HttpResult.error();
        }
    }

    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除字典")
    @Log(title = "删除字典数据")
    public HttpResult delete(@RequestBody List<SysDict> records) {
        int result = sysDictService.delete(records);
        if (result > 0) {
            sysBasisService.setBasisInfo();
            return HttpResult.ok();
        } else {
            return HttpResult.error();
        }
    }

    @PreAuthorize("hasAuthority('sys:dict:view')")
    @PostMapping(value = "/findPage")
    @ApiOperation(value = "查询所有字典数据，带分页")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }

    @PreAuthorize("hasAuthority('sys:dict:view')")
    @GetMapping(value = "/findByLable")
    @ApiIgnore
    public HttpResult findByLable(@RequestParam String lable) {
        return HttpResult.ok(sysDictService.findByLable(lable));
    }
}
