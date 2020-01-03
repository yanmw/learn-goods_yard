package com.ymw.admin.dao.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.model.sys.SysAnnouncement;
import com.ymw.admin.model.sys.SysAnnouncementReceiver;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 公告接收人表 Mapper 接口
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
public interface SysAnnouncementReceiverMapper extends BaseMapper<SysAnnouncementReceiver> {

    List<SysAnnouncementReceiver> findList(@Param("id") Long id);

    List<SysAnnouncementReceiver> findListByAnnId(@Param("id") Long id);

    List<SysAnnouncement> findPage(Page<SysAnnouncement> page, @Param("deptId") String deptId, @Param("userId") String userId, @Param("title") String title, @Param("status") String status, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("type") String type, @Param("receiverId") String receiverId);
}
