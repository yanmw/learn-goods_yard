package com.ymw.admin.controller.sys;

import java.util.List;

import com.ymw.admin.sevice.sys.SysBasisService;
import com.ymw.admin.util.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ymw.admin.model.sys.SysMenu;
import com.ymw.admin.sevice.sys.SysMenuService;
import com.ymw.core.http.HttpResult;

/**
 * 菜单控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("/qr/menu")
@Api(tags = "菜单管理")
public class SysMenuController {

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysBasisService sysBasisService;
	
	@PreAuthorize("hasAuthority('sys:menu:add') AND hasAuthority('sys:menu:edit')")
	@PostMapping(value="/save")
	@ApiOperation(value = "新增/更新菜单")
	@Log(title = "新增/更新菜单")
	public HttpResult save(@RequestBody SysMenu record) {
		int result = sysMenuService.save(record);
		if (result > 0) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok();
		} else {
			return HttpResult.error();
		}
	}

	@PreAuthorize("hasAuthority('sys:menu:delete')")
	@PostMapping(value="/delete")
	@ApiOperation(value = "删除菜单")
	@Log(title = "删除菜单")
	public HttpResult delete(@RequestBody List<SysMenu> records) {
		int result = sysMenuService.delete(records);
		if (result > 0) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok();
		} else {
			return HttpResult.error();
		}
	}

	@PreAuthorize("hasAuthority('sys:menu:view')")
	@PostMapping(value="/findNavTree")
	@ApiOperation(value = "根据当前登录人username查询可查看的菜单树")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userName",value="当前登录人username",required=true,paramType = "query")
	})
	public HttpResult findNavTree(@RequestParam String userName) {
		return HttpResult.ok(sysMenuService.findTree(userName, 1));
	}
	
	@PreAuthorize("hasAuthority('sys:menu:view')")
	@GetMapping(value="/findMenuTree")
	@ApiOperation(value = "查询所有菜单")
	public HttpResult findMenuTree() {
		return HttpResult.ok(sysMenuService.findTree(null, 0));
	}
}
