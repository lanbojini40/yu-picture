package com.yupi.yunpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yunpicturebackend.model.dto.space.SpaceAddRequest;
import com.yupi.yunpicturebackend.model.dto.space.SpaceQueryRequest;
import com.yupi.yunpicturebackend.model.dto.spaceuser.SpaceUserAddRequest;
import com.yupi.yunpicturebackend.model.dto.spaceuser.SpaceUserQueryRequest;
import com.yupi.yunpicturebackend.model.entity.Space;
import com.yupi.yunpicturebackend.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.SpaceUserVO;
import com.yupi.yunpicturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 12600kf
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2025-01-06 16:03:28
*/
public interface SpaceUserService extends IService<SpaceUser> {
    /**
     * 创建空间成员
     *
     *@param spaceUserAddRequest
     * @return
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 获取查询空间成员对象
     *
     * @param spaceUserQueryRequest
     * @return
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    /**
     * 获取空间成员包装类（单条）
     * 其实就是空间脱敏，但是spaceVO中有一个属性是user，可以关联查询出是哪个用户创建的这个空间，所以在方法的实现中会加上这user。
     *
     * @param spaceUser
     * @param request
     * @return
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 获取空间成员包装类（列表）
     *
     * @param spaceUserList

     * @return
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);

    /**
     * 校验空间成员权限
     * @param spaceUser
     * @param add
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);



}
