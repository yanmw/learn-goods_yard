package com.ymw.admin.sevice.sys;

import java.util.List;

import com.ymw.admin.model.sys.SysMenu;
import com.ymw.core.service.CurdService;

/**
 * 菜单管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysMenuService extends CurdService<SysMenu> {

	/**
	 * 查询菜单树,用户ID和用户名为空则查询全部
	 * @param menuType 获取菜单类型，0：获取所有菜单，包含按钮，1：获取所有菜单，不包含按钮
	 * @param userId 
	 * @return
	 */
	List<SysMenu> findTree(String userName, int menuType);

	/**
	 * 根据用户名查找菜单列表
	 * @param userName
	 * @return
	 */
	List<SysMenu> findByUser(String userName);
}
