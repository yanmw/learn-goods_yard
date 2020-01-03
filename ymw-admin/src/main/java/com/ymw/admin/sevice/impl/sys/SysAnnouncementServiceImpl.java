package com.ymw.admin.sevice.impl.sys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.dao.sys.SysAnnouncementReceiverMapper;
import com.ymw.admin.dao.sys.SysUserMapper;
import com.ymw.admin.model.sys.SysAnnouncement;
import com.ymw.admin.dao.sys.SysAnnouncementMapper;
import com.ymw.admin.model.sys.SysAnnouncementReceiver;
import com.ymw.admin.model.sys.SysUser;
import com.ymw.admin.sevice.sys.SysAnnouncementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ymw.admin.util.ColumnUtil;
import com.ymw.admin.util.DateUtil;
import com.ymw.common.utils.StringUtils;
import com.ymw.core.page.ColumnFilter;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;
import com.ymw.core.page.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
@Service
public class SysAnnouncementServiceImpl extends ServiceImpl<SysAnnouncementMapper, SysAnnouncement> implements SysAnnouncementService {

    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;

    @Autowired
    private SysAnnouncementReceiverMapper receiverMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional
    public boolean save(SysAnnouncement sysAnnouncement) {
        if (sysAnnouncement != null) {
            if (sysAnnouncement.getId() == null || sysAnnouncement.getId() == 0) {
                int result = sysAnnouncementMapper.insert(sysAnnouncement);
                if (result > 0) {
                    for (SysAnnouncementReceiver receiver : sysAnnouncement.getList()) {
                        receiver.setAnnouncementId(sysAnnouncement.getId());
                        receiverMapper.insert(receiver);
                    }
                }
                return true;
            } else {
                sysAnnouncementMapper.updateById(sysAnnouncement);
                for (SysAnnouncementReceiver receiver : sysAnnouncement.getList()) {
                    receiverMapper.updateById(receiver);
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        String deptId = ColumnUtil.getValueByCoumn(pageRequest,"deptId");
        String title = ColumnUtil.getValueByCoumn(pageRequest,"title");
        String startDate = ColumnUtil.getValueByCoumn(pageRequest,"startDate");
        String endDate = ColumnUtil.getValueByCoumn(pageRequest,"endDate");
        String name = ColumnUtil.getValueByCoumn(pageRequest,"name");
        String status = ColumnUtil.getValueByCoumn(pageRequest,"status");
        Page<SysAnnouncement> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<SysAnnouncement> list = sysAnnouncementMapper.findPage(page,deptId,title,name,startDate,endDate,status);
        for (SysAnnouncement announcement : list) {
            List<SysAnnouncementReceiver> receiverList = receiverMapper.findList(announcement.getId());
            announcement.setList(receiverList);
        }
        return PageResultUtil.getPageResult(page,list);
    }

    @Override
    public SysAnnouncement findOneById(Long id) {
        SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(id);
        List<SysAnnouncementReceiver> receivers = receiverMapper.findListByAnnId(id);
        sysAnnouncement.setList(receivers);
        return sysAnnouncement;
    }


}
