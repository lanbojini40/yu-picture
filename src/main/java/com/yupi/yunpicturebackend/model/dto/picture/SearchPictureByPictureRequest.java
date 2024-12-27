package com.yupi.yunpicturebackend.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

/**
 * 以图搜图请求，因为前端只需要传一个图片id就可以获得后端数据库中图片的url
 * 然后用这个url去调用集成的那个接口就好了
 */
@Data
public class SearchPictureByPictureRequest implements Serializable {
  
    /**  
     * 图片 id  
     */  
    private Long pictureId;  
  
    private static final long serialVersionUID = 1L;  
}
