package com.yupi.yunpicturebackend.model.vo;

import lombok.Data;

import java.util.List;

//返回给前端的封装类

/**
 * 图片标签分类视图。视图的封装类都在vo里
 */
@Data
public class PictureTagCategory {
    /**
     * 标签列表
     */
    private List<String> tagList;
    /**
     * 分类列表
     */
    private  List<String> categoryList;
}
