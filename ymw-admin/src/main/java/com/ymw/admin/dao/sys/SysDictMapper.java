package com.ymw.admin.dao.sys;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import com.ymw.admin.model.sys.SysDict;

public interface SysDictMapper extends BaseMapper<SysDict> {
    int deleteByPrimaryKey(Long id);

    Integer insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
    
    List<SysDict> findPage(Pagination page);
    
    List<SysDict> findPageByLabel(Pagination page, @Param(value = "label") String label);

    List<SysDict> findByLable(@Param(value = "label") String label);
}