package com.ymw.admin.util;

import com.ymw.admin.constants.SysConstants;
import com.ymw.admin.dao.sys.SysFileMapper;
import com.ymw.admin.model.sys.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FileUtils {

    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private ApplicationValueUtils applicationValueUtils;

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 文件上传
     * @param file
     * @return
     */
    public Map upload(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("success", false);
            map.put("msg", "文件为空");
            return map;
        }
        SysFile sysFile = new SysFile();
        // 获取文件名
        String fileName = file.getOriginalFilename();
        sysFile.setFormerName(fileName);
        //判断文件类型
        Integer fileType = FileTypeUtils.getType(fileName);
        sysFile.setFileType(fileType + "");
        //文件大小 单位：kb
        Long fileSizeB = file.getSize();
        double fileSizeKB = (double) fileSizeB / 1024;
        sysFile.setSize(new BigDecimal(fileSizeKB));
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        sysFile.setSuffixName(suffixName);
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = getFilePath(sysFile.getFileType());
        sysFile.setPath(filePath);
        // 解决中文问题，liunx下中文路径，图片显示问题
        fileName = UUID.randomUUID() + suffixName;
        sysFile.setRealName(fileName);
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            sysFileMapper.insert(sysFile);
            map.put("id", sysFile.getId());
            map.put("success", true);
            return map;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("success", false);
        map.put("msg", "上传失败");
        return map;
    }

    /**
     * 文件下载
     * @param id 文件id
     * @param response
     * @throws UnsupportedEncodingException
     */
    public void download(Long id, HttpServletResponse response) throws UnsupportedEncodingException {
        SysFile sysFile = sysFileMapper.selectById(id);
        if (sysFile != null) {
            String realPath = sysFile.getPath();
            String fileName = sysFile.getRealName();
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + fileName);// 设置文件名

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    logger.info(sysFile.getFormerName() + "---文件下载成功！");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 图片预览
     * @param id 文件id
     * @param response
     * @throws IOException
     */
    public void preview(Long id, HttpServletResponse response) throws IOException {
        SysFile sysFile = sysFileMapper.selectById(id);
        if (sysFile != null) {
            response.setContentType("image/*;charset=utf-8");
            response.setHeader("Content-Disposition", "inline; filename=" + sysFile.getRealName());
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(Files.readAllBytes(Paths.get(sysFile.getPath()).resolve(sysFile.getRealName())));
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * 视频或音频预览
     * @param id 文件id
     * @param response
     * @throws IOException
     */
    public void getVideoOrAudio(Long id, HttpServletResponse response) throws IOException {
        SysFile sysFile = sysFileMapper.selectById(id);
        if (sysFile != null) {
            if (SysConstants.VIDEO.equals(sysFile.getFileType())) {
                response.setContentType("video/*;charset=utf-8");
                response.setHeader("Content-Disposition", "inline; filename=" + sysFile.getRealName());
            }
            if (SysConstants.AUDIO.equals(sysFile.getFileType())) {
                response.setContentType("audio/*;charset=utf-8");
                response.setHeader("Content-Disposition", "inline; filename=" + sysFile.getRealName());
            }
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(Files.readAllBytes(Paths.get(sysFile.getPath()).resolve(sysFile.getRealName())));
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * 删除文件
     *
     * @param id 文件id
     * @return
     */
    public boolean deleteFile(Long id) {
        SysFile sysFile = sysFileMapper.selectById(id);
        boolean flag = false;
        File file = new File(sysFile.getPath() + sysFile.getRealName());
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            sysFileMapper.deleteById(id);
            flag = true;
        }
        return flag;
    }

    public String getFilePath(String fileType) {
        String filePath;
        switch (fileType) {
            case SysConstants.IMG:
                filePath = applicationValueUtils.getImg();
                break;
            case SysConstants.DOC:
                filePath = applicationValueUtils.getDoc();
                break;
            case SysConstants.VIDEO:
                filePath = applicationValueUtils.getVideo();
                break;
            case SysConstants.AUDIO:
                filePath = applicationValueUtils.getAudio();
                break;
            case SysConstants.OTHER:
                filePath = applicationValueUtils.getOther();
                break;
            default:
                return null;
        }

        return filePath;
    }
}
