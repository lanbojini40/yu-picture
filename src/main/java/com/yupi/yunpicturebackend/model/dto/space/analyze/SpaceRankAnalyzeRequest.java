package com.yupi.yunpicturebackend.model.dto.space.analyze;

import lombok.Data;

import java.io.Serializable;

/**
 * 管理员对空间使用排行分析（仅管理员可用）
 */
@Data
public class SpaceRankAnalyzeRequest implements Serializable {
  
    /**  
     * 排名前 N 的空间  
     */  
    private Integer topN = 10;  
  
    private static final long serialVersionUID = 1L;  
}