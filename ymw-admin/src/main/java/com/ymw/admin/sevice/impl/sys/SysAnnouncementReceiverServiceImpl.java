package com.ymw.admin.sevice.impl.sys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ymw.admin.constants.SysConstants;
import com.ymw.admin.dao.sys.SysAnnouncementMapper;
import com.ymw.admin.model.sys.SysAnnouncement;
import com.ymw.admin.model.sys.SysAnnouncementReceiver;
import com.ymw.admin.dao.sys.SysAnnouncementReceiverMapper;
import com.ymw.admin.sevice.sys.SysAnnouncementReceiverService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ymw.admin.util.ColumnUtil;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;
import com.ymw.core.page.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 公告接收人表 服务实现类
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
@Service
public class SysAnnouncementReceiverServiceImpl extends ServiceImpl<SysAnnouncementReceiverMapper, SysAnnouncementReceiver> implements SysAnnouncementReceiverService {

    @Autowired
    private SysAnnouncementReceiverMapper receiverMapper;

    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        String deptId = ColumnUtil.getValueByCoumn(pageRequest,"deptId");//当前登录人部门id
        String receiverId = ColumnUtil.getValueByCoumn(pageRequest,"receiverId");//当前登录人id
        String userId = ColumnUtil.getValueByCoumn(pageRequest,"userId");
        String title = ColumnUtil.getValueByCoumn(pageRequest,"title");
        String status = ColumnUtil.getValueByCoumn(pageRequest,"status");
        String startDate = ColumnUtil.getValueByCoumn(pageRequest,"startDate");
        String endDate = ColumnUtil.getValueByCoumn(pageRequest,"endDate");
        String type = ColumnUtil.getValueByCoumn(pageRequest,"type");
        Page<SysAnnouncement> page = new Page<>();
        List<SysAnnouncement> list = receiverMapper.findPage(page,deptId,userId,title,status,startDate,endDate,type,receiverId);

        return PageResultUtil.getPageResult(page,list);
    }

    @Override
    @Transactional
    public HttpResult read(Long id) {
        SysAnnouncementReceiver receiver = new SysAnnouncementReceiver();
        receiver.setId(id);
        receiver.setReadStatus(SysConstants.READ);
        //更新接收人表
        int result = receiverMapper.updateById(receiver);
        if (result > 0) {
            //判断是否所有人都已完成
            Long annId = receiverMapper.selectById(id).getAnnouncementId();
            EntityWrapper<SysAnnouncementReceiver> ew = new EntityWrapper<>();
            ew.eq("announcement_id",annId);
            List<SysAnnouncementReceiver> list = receiverMapper.selectList(ew);
            boolean flag = true;
            if (list != null && list.size() > 0) {
                for (SysAnnouncementReceiver receiver1 : list){
                    if (SysConstants.UNREAD.equals(receiver1.getReadStatus())) {
                        flag = false;
                    }
                }
            } else {
                return HttpResult.error("发布数据异常");
            }
            if (flag) {
                //所有人都已完成
                SysAnnouncement sysAnnouncement = new SysAnnouncement();
                sysAnnouncement.setId(annId);
                sysAnnouncement.setStatus(SysConstants.READ);
                sysAnnouncementMapper.updateById(sysAnnouncement);
            }
            return HttpResult.ok();
        }
        return HttpResult.error("更新失败");
    }
}
