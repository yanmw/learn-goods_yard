package com.ymw.admin.sevice.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.dao.sys.SysUserMapper;
import com.ymw.admin.util.Log;
import com.ymw.core.page.PageResultUtil;
import com.ymw.admin.dao.sys.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymw.admin.model.sys.SysDept;
import com.ymw.admin.sevice.sys.SysDeptService;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;

@Service
public class SysDeptServiceImpl implements SysDeptService {

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public int save(SysDept record) {
		if(record.getId() == null || record.getId() == 0) {
			return sysDeptMapper.insertSelective(record);
		}
		return sysDeptMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(SysDept record) {
		return sysDeptMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public int delete(List<SysDept> records) {
		List<SysDept> allList = sysDeptMapper.findAll();
		for (SysDept dept : records) {
			List<SysDept> tempList = new ArrayList<>();
			orgRecursion(tempList,allList,dept.getId());
			if (tempList != null && tempList.size() > 0) {
				return -1;
			} else {
				List<Long> list = new ArrayList<>();
				list.add(dept.getId());
				//删除出的是最底层的组织
				int count = sysUserMapper.selectByDept(list);
				//还有人，不能删除组织
				if (count > 0) {
					return 0;
				}
			}
		}
		return 1;
	}

	@Override
	public SysDept findById(Long id) {
		return sysDeptMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		Page<SysDept> page = new Page<>(pageRequest.getPageNum(),pageRequest.getPageSize());
		List<SysDept> list = sysDeptMapper.findPage(page);
		PageResult pageResult = PageResultUtil.getPageResult(page,list);
		return pageResult;
	}

	@Override
	public List<SysDept> findTree() {
		List<SysDept> sysDepts = new ArrayList<>();
		List<SysDept> depts = sysDeptMapper.findAll();
		for (SysDept dept : depts) {
			if (dept.getParentId() == null || dept.getParentId() == 0) {
				dept.setLevel(0);
				sysDepts.add(dept);
			}
		}
		findChildren(sysDepts, depts);
		return sysDepts;
	}

	@Override
	public List<SysDept> findAll() {
		return sysDeptMapper.findAll();
	}

	private void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
		for (SysDept sysDept : sysDepts) {
			List<SysDept> children = new ArrayList<>();
			for (SysDept dept : depts) {
				if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
					dept.setParentName(sysDept.getName());
					dept.setLevel(sysDept.getLevel() + 1);
					children.add(dept);
				}
			}
			sysDept.setChildren(children);
			findChildren(children, depts);
		}
	}

	public List<SysDept> getTreeMenuList(List<SysDept> domainList ,String pid) {
		//加入它本身
		SysDept model = new SysDept();
		model.setId(Long.parseLong(pid));
		domainList.add(model);
		//加入子集
		SysDept domainType = new SysDept();
		domainType.setParentId(Long.parseLong(pid));
		List<SysDept> list = sysDeptMapper.findListByParentId(domainType);
		for(SysDept type : list) {
			getTreeMenuList(domainList,String.valueOf(type.getId()));
		}
		return domainList;
	}

	/**
	 * 递归获取某个父机构节点下面的所有子机构节点
	 * @param childOrg 要返回的结果
	 * @param orgList  数据库查询出来的所有机构集合
	 * @param pid      父id
	 * 注:本身的机构节点不会添加进去
	 */
	public void orgRecursion(List<SysDept> childOrg,List<SysDept> orgList, Long pid) {
		for (SysDept org : orgList) {
			if (org.getParentId() != null) {
				//遍历出父id等于参数的id，add进子节点集合
				if (org.getParentId() == pid) {
					//递归遍历下一级
					orgRecursion(childOrg,orgList, org.getId());
					//末级机构才添加进去(依自己业务定义)
//					if (org.getOrgLevel() == 3) {
						childOrg.add(org);
//					}
				}
			}
		}
	}

}
