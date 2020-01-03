package com.ymw.admin.controller.sys;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ymw.admin.model.sys.SysAnnouncement;
import com.ymw.admin.model.sys.SysAnnouncementReceiver;
import com.ymw.admin.sevice.sys.SysAnnouncementReceiverService;
import com.ymw.admin.sevice.sys.SysAnnouncementService;
import com.ymw.admin.util.Log;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
@RestController
@RequestMapping("/qr/sysAnnouncement")
@Api(tags = "公告发布管理")
public class SysAnnouncementController {

    @Autowired
    private SysAnnouncementService sysAnnouncementService;

    @Autowired
    private SysAnnouncementReceiverService receiverService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "发布/修改公告")
    @Log(title = "发布/修改公告")
    public HttpResult save(@RequestBody SysAnnouncement sysAnnouncement) {
        boolean b = sysAnnouncementService.save(sysAnnouncement);
        if (b) {
            return HttpResult.ok();
        } else {
            return HttpResult.error();
        }
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除公告")
    @Log(title = "删除公告")
    public HttpResult delete(@RequestBody List<Long> idList) {
        if (idList != null && idList.size() > 0) {
            boolean b = sysAnnouncementService.deleteBatchIds(idList);
            if (b) {
                EntityWrapper<SysAnnouncementReceiver> ew = new EntityWrapper<>();
                ew.in("announcement_id",idList);
                boolean bb = receiverService.delete(ew);
                if (bb) {
                    return HttpResult.ok();
                } else {
                    return HttpResult.error("接收人数据未删除成功");
                }
            } else {
                return HttpResult.error();
            }
        }
        return HttpResult.ok();
    }

    @PostMapping(value = "/findPage")
    @ApiOperation(value = "查询所有发布公告")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        PageResult pageResult = sysAnnouncementService.findPage(pageRequest);
        return HttpResult.ok(pageResult);
    }

    @GetMapping(value = "/detail/{id}")
    @ApiOperation(value = "单个公告详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "当前数据id",required = true,paramType = "path")
    })
    public HttpResult detail(@PathVariable Long id) {
        SysAnnouncement sysAnnouncement = sysAnnouncementService.findOneById(id);
        return HttpResult.ok(sysAnnouncement);
    }
}

