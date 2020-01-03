package com.ymw.admin.controller.sys;

import java.util.List;

import com.ymw.admin.dao.sys.SysRoleMapper;
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

import com.ymw.admin.constants.SysConstants;
import com.ymw.admin.model.sys.SysRole;
import com.ymw.admin.model.sys.SysRoleMenu;
import com.ymw.admin.sevice.sys.SysRoleService;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;

/**
 * 角色控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("/qr/role")
@Api(tags = "角色管理")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysBasisService sysBasisService;
	
	@PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
	@PostMapping(value="/save")
	@ApiOperation(value = "新增/更新角色")
	@Log(title = "新增/更新角色")
	public HttpResult save(@RequestBody SysRole record) {
		SysRole role = sysRoleService.findById(record.getId());
		if(role != null) {
			if(SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
				return HttpResult.error("超级管理员不允许修改!");
			}
		}
		// 新增角色
		if((record.getId() == null || record.getId() ==0) && !sysRoleService.findByName(record.getName()).isEmpty()) {
			return HttpResult.error("角色名已存在!");
		}
		int result = sysRoleService.save(record);
		if (result > 0) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok();
		} else {
			return HttpResult.error();
		}
	}

	@PreAuthorize("hasAuthority('sys:role:delete')")
	@PostMapping(value="/delete")
	@ApiOperation(value = "删除角色")
	@Log(title = "删除角色")
	public HttpResult delete(@RequestBody List<SysRole> records) {
		int result = sysRoleService.delete(records);
		if (result > 0) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok();
		} else {
			return HttpResult.error();
		}
	}

	@PreAuthorize("hasAuthority('sys:role:view')")
	@PostMapping(value="/findPage")
	@ApiOperation(value = "查询角色，带分页")
	public HttpResult findPage(@RequestBody PageRequest pageRequest) {
		return HttpResult.ok(sysRoleService.findPage(pageRequest));
	}
	
	@PreAuthorize("hasAuthority('sys:role:view')")
	@GetMapping(value="/findAll")
	@ApiOperation(value = "查询所有角色")
	public HttpResult findAll() {
		return HttpResult.ok(sysRoleService.findAll());
	}
	
	@PreAuthorize("hasAuthority('sys:role:view')")
	@GetMapping(value="/findRoleMenus")
	@ApiOperation(value = "根据角色id查询可以查看的所有菜单")
	public HttpResult findRoleMenus(@RequestParam Long roleId) {
		return HttpResult.ok(sysRoleService.findRoleMenus(roleId));
	}
	
	@PreAuthorize("hasAuthority('sys:role:view')")
	@PostMapping(value="/saveRoleMenus")
	@ApiOperation(value = "新增/更新角色-菜单数据")
	public HttpResult saveRoleMenus(@RequestBody List<SysRoleMenu> records) {
		for(SysRoleMenu record:records) {
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(record.getRoleId());
			if(SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
				// 如果是超级管理员，不允许修改
				return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
			}
		}
		return HttpResult.ok(sysRoleService.saveRoleMenus(records));
	}
}
