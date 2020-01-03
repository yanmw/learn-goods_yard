package com.ymw.admin.sevice.sys;

import com.ymw.admin.model.sys.SysAnnouncement;
import com.baomidou.mybatisplus.service.IService;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
public interface SysAnnouncementService extends IService<SysAnnouncement> {

    boolean save(SysAnnouncement sysAnnouncement);

    PageResult findPage(PageRequest pageRequest);

    SysAnnouncement findOneById(Long id);
}
