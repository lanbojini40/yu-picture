package com.yupi.yunpicturebackend.model.dto.space.analyze;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用空间分析请求,很多分析都需要有查询范围所以直接写一个通用类，后序所有请求类只需要复用这个类即可
 */
@Data
public class SpaceAnalyzeRequest implements Serializable {
  
    /**  
     * 空间 ID  
     */  
    private Long spaceId;  
  
    /**  
     * 是否查询公共图库  
     */  
    private boolean queryPublic;  
  
    /**  
     * 全空间分析  
     */  
    private boolean queryAll;  
  
    private static final long serialVersionUID = 1L;  
}
