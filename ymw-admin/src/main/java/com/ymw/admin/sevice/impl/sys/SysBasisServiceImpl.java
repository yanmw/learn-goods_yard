package com.ymw.admin.sevice.impl.sys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ymw.admin.constants.SysConstants;
import com.ymw.admin.dao.sys.*;
import com.ymw.admin.sevice.sys.SysBasisService;
import com.ymw.admin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysBasisServiceImpl  implements SysBasisService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysDictMapper sysDictMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void setBasisInfo() {
        //所有人员信息
        redisUtil.set(SysConstants.ALL_USER,sysUserMapper.selectList(new EntityWrapper<>()),SysConstants.DATABASE0);
        //所有部门信息
        redisUtil.set(SysConstants.ALL_DEPT,sysDeptMapper.selectList(new EntityWrapper<>()),SysConstants.DATABASE0);
        //所有字典信息
        redisUtil.set(SysConstants.ALL_DICT,sysDictMapper.selectList(new EntityWrapper<>()),SysConstants.DATABASE0);
        //所有菜单信息
        redisUtil.set(SysConstants.ALL_MENU,sysMenuMapper.selectList(new EntityWrapper<>()),SysConstants.DATABASE0);
        //所有角色信息
        redisUtil.set(SysConstants.ALL_ROLE,sysRoleMapper.selectList(new EntityWrapper<>()),SysConstants.DATABASE0);
    }

    @Override
    public Map getBasisInfo() {
        Map<String,Object> map = new HashMap();
        //所有人员信息
        map.put(SysConstants.ALL_USER,redisUtil.get(SysConstants.ALL_USER,SysConstants.DATABASE0));
        //所有部门信息
        map.put(SysConstants.ALL_DEPT,redisUtil.get(SysConstants.ALL_DEPT,SysConstants.DATABASE0));
        //所有字典信息
        map.put(SysConstants.ALL_DICT,redisUtil.get(SysConstants.ALL_DICT,SysConstants.DATABASE0));
        //所有菜单信息
        map.put(SysConstants.ALL_MENU,redisUtil.get(SysConstants.ALL_MENU,SysConstants.DATABASE0));
        //所有角色信息
        map.put(SysConstants.ALL_ROLE,redisUtil.get(SysConstants.ALL_ROLE,SysConstants.DATABASE0));
        return map;
    }
}
