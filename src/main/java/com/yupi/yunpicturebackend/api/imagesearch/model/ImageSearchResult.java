package com.yupi.yunpicturebackend.api.imagesearch.model;

import lombok.Data;
//这个是调用的第三方接口以图搜图，然后将搜索到的纯净的信息返回给系统，即相图片的url和缩略url
//这个包用于存放返回给系统的图片的信息
@Data
public class ImageSearchResult {  
  
    /**  
     * 缩略图地址  
     */  
    private String thumbUrl;  
  
    /**  
     * 来源地址  
     */  
    private String fromUrl;  
}
