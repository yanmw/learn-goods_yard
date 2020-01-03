package com.ymw.admin.sevice.impl.sys;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.util.Log;
import com.ymw.core.page.PageResultUtil;
import com.ymw.admin.dao.sys.SysDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymw.admin.model.sys.SysDict;
import com.ymw.admin.sevice.sys.SysDictService;
import com.ymw.core.page.ColumnFilter;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;

@Service
public class SysDictServiceImpl  implements SysDictService {

	@Autowired
	private SysDictMapper sysDictMapper;

	@Override
	public int save(SysDict record) {
		if(record.getId() == null || record.getId() == 0) {
			return sysDictMapper.insertSelective(record);
		}
		return sysDictMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(SysDict record) {
		return sysDictMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public int delete(List<SysDict> records) {
		for(SysDict record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public SysDict findById(Long id) {
		return sysDictMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		ColumnFilter columnFilter = pageRequest.getColumnFilter("label");
		Page<SysDict> page = new Page<>(pageRequest.getPageNum(),pageRequest.getPageSize());
		if(columnFilter != null) {
			List<SysDict> list = sysDictMapper.findPageByLabel(page,columnFilter.getValue());
			return PageResultUtil.getPageResult(page,list);
//			return MybatisPageHelper.findPage(pageRequest, sysDictMapper, "findPageByLabel", columnFilter.getValue());
		}
		List<SysDict> list = sysDictMapper.findPage(page);
		return PageResultUtil.getPageResult(page,list);
//		return MybatisPageHelper.findPage(pageRequest, sysDictMapper);
	}

	@Override
	public List<SysDict> findByLable(String lable) {
		return sysDictMapper.findByLable(lable);
	}

}
