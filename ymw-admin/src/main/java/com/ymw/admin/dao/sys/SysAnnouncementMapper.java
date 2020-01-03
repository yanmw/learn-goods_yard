package com.ymw.admin.dao.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.model.sys.SysAnnouncement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
public interface SysAnnouncementMapper extends BaseMapper<SysAnnouncement> {

    List<SysAnnouncement> findPage(Page<SysAnnouncement> page, @Param("deptId") String deptId, @Param("title") String title, @Param("name") String name, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("status") String status);

//    List<SysAnnouncement> findPage(Page<SysAnnouncement> page, String deptId, String title, String name, String startDate, String endDate, String status);
}
