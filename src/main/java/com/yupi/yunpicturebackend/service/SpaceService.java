package com.yupi.yunpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yunpicturebackend.model.dto.space.SpaceAddRequest;
import com.yupi.yunpicturebackend.model.dto.space.SpaceQueryRequest;
import com.yupi.yunpicturebackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yunpicturebackend.model.entity.User;
import com.yupi.yunpicturebackend.model.vo.SpaceVO;
import javax.servlet.http.HttpServletRequest;

/**
* @author 12600kf
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2024-12-25 15:15:20
*/
public interface SpaceService extends IService<Space> {
    /**
     * 创建空间
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);
    /**
     * 获取查询对象
     * @param spaceQueryRequest
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 获取空间包装类（单条）
     * 其实就是空间脱敏，但是spaceVO中有一个属性是user，可以关联查询出是哪个用户创建的这个空间，所以在方法的实现中会加上这user。
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取空间包装类（分页）
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 校验空间
     * @param space
     * @param add 是否为创建时校验
     */
    void validSpace(Space space, boolean add);
    void fillSpaceBySpaceLevel(Space space);
}
