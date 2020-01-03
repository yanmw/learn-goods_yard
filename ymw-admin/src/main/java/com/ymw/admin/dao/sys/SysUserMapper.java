package com.ymw.admin.dao.sys;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import com.ymw.admin.model.sys.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
    int deleteByPrimaryKey(Long id);

//    Integer insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    List<SysUser> findPage(Pagination page);
    
    SysUser findByUsername(@Param(value = "username") String username);
    
	List<SysUser> findPageByUsername(Pagination page, @Param(value = "username") String username);
	
	List<SysUser> findPageByUsernameAndEmail(Pagination page, @Param(value = "username") String username, @Param(value = "email") String email);

    int selectByDept(List<Long> list);
}