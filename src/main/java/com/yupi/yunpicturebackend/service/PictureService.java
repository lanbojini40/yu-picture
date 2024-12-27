package com.yupi.yunpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicturebackend.model.dto.picture.*;
import com.yupi.yunpicturebackend.model.dto.user.UserQueryRequest;
import com.yupi.yunpicturebackend.model.entity.Picture;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.PictureVO;
import com.yupi.yunpicturebackend.model.vo.UserVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author 12600kf
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2024-12-18 20:12:11
*/
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     *
     */
    PictureVO uploadPicture(Object inputSource,  PictureUploadRequest pictureUploadRequest, User loginUser);
    /**
     * 获取查询对象，将http请求转化为mybatis请求
     * 服务器会处理
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片包装类（单条）
     * 其实就是图片脱敏，但是pictureVO中有一个属性是user，可以关联查询出是哪个用户创建的这个图片，所以在方法的实现中会加上这user。
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);

    /**
     * 图片审核
     * @param pictureReviewRequest
     * @param loginUser
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest,User loginUser);

    /**
     * 填充审核参数
     * @param picture
     * @param loginUser
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */

    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest, User loginUser);
    /**
     * 清除一个图片,清除的是一个cos对象存储的图片
     * @param oldPicture
     */
    @Async
    void clearPictureFile(Picture oldPicture);
    void checkPictureAuth(User loginUser, Picture picture);


    void deletePicture(long pictureId, User loginUser);

    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);
}
