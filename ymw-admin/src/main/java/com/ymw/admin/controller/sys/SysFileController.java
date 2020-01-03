package com.ymw.admin.controller.sys;


import com.ymw.admin.util.FileUtils;
import com.ymw.core.http.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 * 系统文件表 前端控制器
 * </p>
 *
 * @author yan
 * @since 2019-12-23
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/qr/sysFile")
public class SysFileController {

    @Autowired
    FileUtils fileUtils;

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "upload")
    public HttpResult upload(@RequestParam("test") MultipartFile file) {
        Map<String,Object> map = fileUtils.upload(file);
        boolean b = (boolean) map.get("success");
        if (b) {
            return HttpResult.ok(map);
        } else {
            return HttpResult.error((String) map.get("msg"));
        }
    }

    @ApiOperation(value = "文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="文件id",required=true,paramType = "path")
    })
    @GetMapping("/download/{id}")
    public void download(HttpServletResponse response, @PathVariable("id") Long id) throws UnsupportedEncodingException {
        fileUtils.download(id, response);
    }

    @ApiOperation(value = "图片预览")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="文件id",required=true)
    })
    @GetMapping("/preview/{id}")
    public void preview(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        fileUtils.preview(id,response);
    }

    @ApiOperation(value = "视频或音频预览")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="文件id",required=true)
    })
    @GetMapping("/getVideoOrAudio/{id}")
    public void getVideoOrAudio(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        fileUtils.getVideoOrAudio(id,response);
    }
}

