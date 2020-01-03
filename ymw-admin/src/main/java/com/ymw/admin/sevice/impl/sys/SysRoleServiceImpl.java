package com.ymw.admin.sevice.impl.sys;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.util.Log;
import com.ymw.core.page.PageResultUtil;
import com.ymw.admin.constants.SysConstants;
import com.ymw.admin.dao.sys.SysMenuMapper;
import com.ymw.admin.dao.sys.SysRoleMapper;
import com.ymw.admin.dao.sys.SysRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymw.admin.model.sys.SysMenu;
import com.ymw.admin.model.sys.SysRole;
import com.ymw.admin.model.sys.SysRoleMenu;
import com.ymw.admin.sevice.sys.SysRoleService;
import com.ymw.core.page.ColumnFilter;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;

@Service
public class SysRoleServiceImpl  implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public int save(SysRole record) {
		if(record.getId() == null || record.getId() == 0) {
			return sysRoleMapper.insertSelective(record);
		}
		return sysRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(SysRole record) {
		return sysRoleMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public int delete(List<SysRole> records) {
		for(SysRole record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public SysRole findById(Long id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		ColumnFilter columnFilter = pageRequest.getColumnFilter("name");
		Page<SysRole> page = new Page<>(pageRequest.getPageNum(),pageRequest.getPageSize());
		if(columnFilter != null && columnFilter.getValue() != null) {
			List<SysRole> list = sysRoleMapper.findPageByName(page, columnFilter.getValue());
			return PageResultUtil.getPageResult(page,list);
		}
		List<SysRole> list = sysRoleMapper.findPage(page);
		return PageResultUtil.getPageResult(page,list);
	}

	@Override
	public List<SysRole> findAll() {
		return sysRoleMapper.findAll();
	}

	public SysRoleMapper getSysRoleMapper() {
		return sysRoleMapper;
	}

	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	@Override
	public List<SysMenu> findRoleMenus(Long roleId) {
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
		if(SysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
			// 如果是超级管理员，返回全部
			return sysMenuMapper.findAll();
		}
		return sysMenuMapper.findRoleMenus(roleId);
	}

	@Transactional
	@Override
	public int saveRoleMenus(List<SysRoleMenu> records) {
		if(records == null || records.isEmpty()) {
			return 1;
		}
		Long roleId = records.get(0).getRoleId(); 
		sysRoleMenuMapper.deleteByRoleId(roleId);
		for(SysRoleMenu record:records) {
			sysRoleMenuMapper.insertSelective(record);
		}
		return 1;
	}

	@Override
	public List<SysRole> findByName(String name) {
		return sysRoleMapper.findByName(name);
	}
	
}
