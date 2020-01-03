package com.ymw.admin.controller.sys;


import com.ymw.admin.sevice.sys.SysAnnouncementReceiverService;
import com.ymw.core.http.HttpResult;
import com.ymw.core.page.PageRequest;
import com.ymw.core.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 公告接收人表 前端控制器
 * </p>
 *
 * @author yan
 * @since 2019-12-24
 */
@RestController
@RequestMapping("/qr/sysAnnouncementReceiver")
@Api(tags = "公告接收人管理")
public class SysAnnouncementReceiverController {

    @Autowired
    SysAnnouncementReceiverService receiverService;

    @PostMapping(value = "/findPage")
    @ApiOperation(value = "当前登录人查询所有公告")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        PageResult pageResult = receiverService.findPage(pageRequest);
        return HttpResult.ok(pageResult);
    }

    @GetMapping(value = "/read/{id}")
    @ApiOperation(value = "标记已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "当前数据id",required = true,paramType = "path")
    })
    public HttpResult read(@PathVariable Long id) {
      return receiverService.read(id);

    }

}

