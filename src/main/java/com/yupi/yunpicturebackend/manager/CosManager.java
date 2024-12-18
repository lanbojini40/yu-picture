package com.yupi.yunpicturebackend.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.yupi.yunpicturebackend.config.CosClientConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

//专门用来进行文件的上传和下载
@Component  //将这个类交给spring管理
public class CosManager {
    @Resource
    private CosClientConfig cosClientConfig;
    //引入存储对象客户端和相应的配置用于进行交互
    @Resource
    private COSClient cosClient;
    //创建一个方法用于上传文件
    /**
     * 上传对象
     *将本地文件上传到创建的云存储桶里
     * @param key  唯一键 将文件保存到对象存储的哪个位置
     * @param file 文件
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 下载对象
     *
     * @param key 唯一键
     */
    public COSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }

}
