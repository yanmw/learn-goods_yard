package com.ymw.admin.controller.sys;

import java.util.List;

import com.ymw.admin.sevice.sys.SysBasisService;
import com.ymw.admin.util.Log;
import com.ymw.admin.util.PasswordUtils;
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
import com.ymw.admin.model.sys.SysUser;
import com.ymw.admin.sevice.sys.SysUserService;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;

/**
 * 用户控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/qr/user")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysBasisService sysBasisService;

	@ApiOperation(value = "新增或更新用户信息",notes = "新增时不传id")
	@PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
	@PostMapping(value="/save")
	@Log(title = "新增/更新人员")
	public HttpResult save(@RequestBody SysUser record) {
		SysUser user = sysUserService.findById(record.getId());
		if(user != null) {
			if(SysConstants.ADMIN.equalsIgnoreCase(user.getUsername())) {
				return HttpResult.error("超级管理员不允许修改!");
			}
		}
		if(record.getPassword() != null) {
			String salt = PasswordUtils.getSalt();
			if(user == null) {
				// 新增用户
				if(sysUserService.findByUsername(record.getUsername()) != null) {
					return HttpResult.error("用户名已存在!");
				}
				String password = PasswordUtils.encode(record.getPassword(), salt);
				record.setSalt(salt);
				record.setPassword(password);
			} else {
				// 修改用户, 且修改了密码
				if(!record.getPassword().equals(user.getPassword())) {
					String password = PasswordUtils.encode(record.getPassword(), salt);
					record.setSalt(salt);
					record.setPassword(password);
				}
			}
		}
		int result = sysUserService.save(record);
		if (result > 0) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok(sysUserService.save(record));
		} else {
			return HttpResult.error();
		}
	}

	@ApiOperation(value = "删除用户")
	@PreAuthorize("hasAuthority('sys:user:delete')")
	@PostMapping(value="/delete")
	@Log(title = "删除人员")
	public HttpResult delete(@RequestBody List<SysUser> records) {
		for(SysUser record:records) {
			SysUser sysUser = sysUserService.findById(record.getId());
			if(sysUser != null && SysConstants.ADMIN.equalsIgnoreCase(sysUser.getUsername())) {
				return HttpResult.error("超级管理员不允许删除!");
			}
		}
		int result = sysUserService.delete(records);
		if (result > 0) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok();
		} else {
			return HttpResult.error();
		}
	}

	@ApiOperation(value = "根据用户名模糊查找")
	@PreAuthorize("hasAuthority('sys:user:view')")
	@GetMapping(value="/findByName")
	public HttpResult findByUserName(@RequestParam String name) {
		return HttpResult.ok(sysUserService.findByUsername(name));
	}

	@ApiOperation(value = "根据用户名查找所有权限")
	@PreAuthorize("hasAuthority('sys:user:view')")
	@GetMapping(value="/findPermissions")
	public HttpResult findPermissions(@RequestParam String name) {
		return HttpResult.ok(sysUserService.findPermissions(name));
	}

	@ApiOperation(value = "根据用户id查询所有角色")
	@PreAuthorize("hasAuthority('sys:user:view')")
	@GetMapping(value="/findUserRoles")
	public HttpResult findUserRoles(@RequestParam Long userId) {
		return HttpResult.ok(sysUserService.findUserRoles(userId));
	}

	@ApiOperation(value = "带查询的分页")
	@PreAuthorize("hasAuthority('sys:user:view')")
	@PostMapping(value="/findPage")
	public HttpResult findPage(@RequestBody PageRequest pageRequest) {
		return HttpResult.ok(sysUserService.findPage(pageRequest));
	}
	
}
