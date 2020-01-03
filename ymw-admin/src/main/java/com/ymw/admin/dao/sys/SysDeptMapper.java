package com.ymw.admin.dao.sys;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.ymw.admin.model.sys.SysDept;

public interface SysDeptMapper extends BaseMapper<SysDept> {
    int deleteByPrimaryKey(Long id);

    Integer insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
    
    List<SysDept> findPage(Pagination page);
    
    List<SysDept> findAll();

    List<SysDept> findListByParentId(SysDept domainType);
}