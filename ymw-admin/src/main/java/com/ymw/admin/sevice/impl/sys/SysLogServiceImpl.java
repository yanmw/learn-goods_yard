package com.ymw.admin.sevice.impl.sys;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.core.page.PageResultUtil;
import com.ymw.admin.dao.sys.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymw.admin.model.sys.SysLog;
import com.ymw.admin.sevice.sys.SysLogService;
import com.ymw.core.page.ColumnFilter;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;

@Service
public class SysLogServiceImpl  implements SysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public int save(SysLog record) {
		if(record.getId() == null || record.getId() == 0) {
			return sysLogMapper.insertSelective(record);
		}
		return sysLogMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(SysLog record) {
		return sysLogMapper.deleteByPrimaryKey(record.getId());
	}

	@Override
	public int delete(List<SysLog> records) {
		for(SysLog record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public SysLog findById(Long id) {
		return sysLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		ColumnFilter columnFilter = pageRequest.getColumnFilter("userName");
		Page<SysLog> page = new Page<>(pageRequest.getPageNum(),pageRequest.getPageSize());
		if(columnFilter != null) {
			List<SysLog> list = sysLogMapper.findPageByUserName(page,columnFilter.getValue());
			return PageResultUtil.getPageResult(page,list);
		}
		List<SysLog> list = sysLogMapper.findPage(page);
		return PageResultUtil.getPageResult(page,list);
	}
	
}
