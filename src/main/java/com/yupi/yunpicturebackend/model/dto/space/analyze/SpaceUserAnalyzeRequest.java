package com.yupi.yunpicturebackend.model.dto.space.analyze;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
/**
 * 空间用户上传行为请求
 */
public class SpaceUserAnalyzeRequest extends SpaceAnalyzeRequest {  
  
    /**  
     * 用户 ID  
     */  
    private Long userId;  
  
    /**  
     * 时间维度：day / week / month  
     */  
    private String timeDimension;  
}
