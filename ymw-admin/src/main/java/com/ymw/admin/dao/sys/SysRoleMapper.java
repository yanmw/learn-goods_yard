package com.ymw.admin.dao.sys;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import com.ymw.admin.model.sys.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    int deleteByPrimaryKey(Long id);

    Integer insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    List<SysRole> findPage(Pagination page);

	List<SysRole> findAll();
	
	List<SysRole> findPageByName(Pagination page, @Param(value = "name") String name);
	
	List<SysRole> findByName(@Param(value = "name") String name);
}