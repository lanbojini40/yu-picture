package com.yupi.yunpicturebackend.model.dto.space;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Lombok;
//空间级别
@Data
@AllArgsConstructor //这个注解表示Lombok会为你生成一个接收所有参数的构造函数
public class SpaceLevel {
    /***
     * 值
     */
    private int value;
    /**
     * 中文
     */
    private String text;
    /**
     * 最大数量
     */
    private long maxCount;
    /**
     * 最大容量
     */
    private long maxSize;
}
