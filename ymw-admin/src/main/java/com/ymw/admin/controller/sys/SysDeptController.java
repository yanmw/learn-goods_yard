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
import org.springframework.web.bind.annotation.RestController;

import com.ymw.admin.model.sys.SysDept;
import com.ymw.admin.sevice.sys.SysDeptService;
import com.ymw.core.http.HttpResult;

/**
 * 机构控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("/qr/dept")
@Api(tags = "部门管理")
public class SysDeptController {

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysBasisService sysBasisService;
	
	@PreAuthorize("hasAuthority('sys:dept:add') AND hasAuthority('sys:dept:edit')")
	@PostMapping(value="/save")
	@ApiOperation(value = "新增/更新部门")
	@Log(title = "新增/更新部门信息")
	public HttpResult save(@RequestBody SysDept record) {
		int result = sysDeptService.save(record);
		if (result > 0) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok();
		} else {
			return HttpResult.error();
		}
	}

	@PreAuthorize("hasAuthority('sys:dept:delete')")
	@PostMapping(value="/delete")
	@ApiOperation(value = "删除部门")
	@Log(title = "删除部门")
	public HttpResult delete(@RequestBody List<SysDept> records) {
		int status = sysDeptService.delete(records);
		if (status ==1) {
			sysBasisService.setBasisInfo();
			return HttpResult.ok();
		} else if (status == -1){
			return HttpResult.error("请逐级进行删除");
		} else {
			return HttpResult.error("您所选中的组织下还有人员，无法删除");
		}
	}

	@PreAuthorize("hasAuthority('sys:dept:view')")
	@GetMapping(value="/findTree")
	@ApiOperation(value = "查询部门树")
	public HttpResult findTree() {
		return HttpResult.ok(sysDeptService.findTree());
	}

}
