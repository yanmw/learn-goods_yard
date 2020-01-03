package com.ymw.admin.sevice.sys;

import com.ymw.admin.model.sys.SysAnnouncementReceiver;
import com.baomidou.mybatisplus.service.IService;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;

/**
 * <p>
 * 公告接收人表 服务类
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
public interface SysAnnouncementReceiverService extends IService<SysAnnouncementReceiver> {

    PageResult findPage(PageRequest pageRequest);

    HttpResult read(Long id);
}
