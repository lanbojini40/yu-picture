package com.yupi.yunpicturebackend.controller;

import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import com.yupi.yunpicturebackend.annotation.AuthCheck;
import com.yupi.yunpicturebackend.common.BaseResponse;
import com.yupi.yunpicturebackend.common.ResultUtils;
import com.yupi.yunpicturebackend.constant.UserConstant;
import com.yupi.yunpicturebackend.exception.BusinessException;
import com.yupi.yunpicturebackend.exception.ErrorCode;
import com.yupi.yunpicturebackend.manager.CosManager;
import lombok.extern.slf4j.Slf4j;
import org.jcp.xml.dsig.internal.dom.Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/file")
   public class FileController {

     @Resource
    private CosManager cosManager;
    /**
     * 上传文件测试
     * 以表单的形式返回就用PostMapping
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("test/upload")
    public BaseResponse<String> tsetUpLoadFile(@RequestPart("file")MultipartFile multipartFile) {
        //获取文件名
        String filename= multipartFile.getOriginalFilename();
        //定义并获取上传到cos的地址
        String filepath=String.format("/test/%s", filename);
        File file=null;
        try {
            //创建临时文件
            file= File.createTempFile(filepath,null);
            //将文件保存到临时文件中
            multipartFile.transferTo(file);
            //将临时文件上传到cos
            cosManager.putObject(filepath,file);
            //返回上传成功的路径
            return ResultUtils.success(filepath);
        } catch (Exception e) {
            log.error("file upload error filepath="+filepath,e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件上传失败");

        }finally {
            if (file!=null){
                    boolean delete=file.delete();
                    if (!delete){
                        log.error("file delete error filepath= "+ filepath);
                    }
            }
        }
    }
    /**
     * 测试文件下载
     *
     * @param filepath 文件路径
     * @param response 响应对象
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/test/download/")
    public void testDownloadFile(String filepath, HttpServletResponse response) throws IOException {
        COSObjectInputStream cosObjectInput = null;
        try {
            COSObject cosObject = cosManager.getObject(filepath);
            //获取输入流
            cosObjectInput = cosObject.getObjectContent();
            // 处理下载到的流
            byte[] bytes = IOUtils.toByteArray(cosObjectInput);
            // 设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filepath);
            // 写入响应
            response.getOutputStream().write(bytes);
            //做一个刷新，刷新到响应体中，就会返回给前端
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("file download error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载失败");
        } finally {
            if (cosObjectInput != null) {
                cosObjectInput.close();
            }
        }
    }

}

