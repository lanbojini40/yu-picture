package com.yupi.yunpicturebackend.manager.auth.model;

/**
 * 空间成员权限枚举常量，使用时直接使用定义的单词表示权限即可，防止拼写错误
 */
public interface SpaceUserPermissionConstant {
    /**  
     * 空间用户管理权限  
     */  
    String SPACE_USER_MANAGE = "spaceUser:manage";  
  
    /**  
     * 图片查看权限  
     */  
    String PICTURE_VIEW = "picture:view";  
  
    /**  
     * 图片上传权限  
     */  
    String PICTURE_UPLOAD = "picture:upload";  
  
    /**  
     * 图片编辑权限  
     */  
    String PICTURE_EDIT = "picture:edit";  
  
    /**  
     * 图片删除权限  
     */  
    String PICTURE_DELETE = "picture:delete";  
}
