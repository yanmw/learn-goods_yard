package com.ymw.core.page;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * MyBatis 分页查询助手
 * @author Louis
 * @date Aug 19, 2018
 */
public class PageResultUtil {

	/**
	 * 将分页信息封装到统一的接口
	 * @param
	 * @param
	 * @return
	 */
	public static PageResult getPageResult(Page<?> pageInfo,List<?>list) {
		PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getCurrent());
        pageResult.setPageSize(pageInfo.getSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(list);
		return pageResult;
	}

}
