package com.ymw.admin.sevice.sys;

import java.util.List;

import com.ymw.admin.model.sys.SysDept;
import com.ymw.core.service.CurdService;

/**
 * 机构管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface SysDeptService extends CurdService<SysDept> {

	/**
	 * 查询机构树
	 * @param userId 
	 * @return
	 */
	List<SysDept> findTree();

    List<SysDept> findAll();

    void orgRecursion(List<SysDept> tempList, List<SysDept> list, Long i);
}
