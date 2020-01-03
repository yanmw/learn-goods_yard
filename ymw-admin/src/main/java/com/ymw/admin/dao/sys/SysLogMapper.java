package com.ymw.admin.dao.sys;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import com.ymw.admin.model.sys.SysLog;

public interface SysLogMapper extends BaseMapper<SysLog> {
    int deleteByPrimaryKey(Long id);

    Integer insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
    
    List<SysLog> findPage(Pagination page);
    
    List<SysLog> findPageByUserName(Pagination page, @Param(value = "userName") String userName);
}