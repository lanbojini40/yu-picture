package com.yupi.yunpicturebackend.api.imagesearch;

import com.yupi.yunpicturebackend.api.imagesearch.model.ImageSearchResult;
import com.yupi.yunpicturebackend.api.imagesearch.sub.GetImageFirstUrlApi;
import com.yupi.yunpicturebackend.api.imagesearch.sub.GetImageListApi;
import com.yupi.yunpicturebackend.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 这是一种新的设计模式，叫做门面模式，通过将多个接口调用集成到一个接口里，使得前端只需要调用这一个集成的接口即可，而无需知道内部的具体实现
 */
@Slf4j
public class ImageSearchApiFacade {

    /**
     * 搜索图片
     *
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;
    }

    public static void main(String[] args) {
        // 测试以图搜图功能
        String imageUrl = "https://www.codefather.cn/logo.png";
        List<ImageSearchResult> resultList = searchImage(imageUrl);
        System.out.println("结果列表" + resultList);
    }
}
