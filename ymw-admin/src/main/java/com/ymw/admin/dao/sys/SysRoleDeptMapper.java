package com.ymw.admin.dao.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ymw.admin.model.sys.SysRoleDept;

public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {
    int deleteByPrimaryKey(Long id);

    Integer insert(SysRoleDept record);

    int insertSelective(SysRoleDept record);

    SysRoleDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleDept record);

    int updateByPrimaryKey(SysRoleDept record);
}