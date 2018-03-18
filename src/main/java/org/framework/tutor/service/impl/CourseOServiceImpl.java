package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.mapper.CourseOMapper;
import org.framework.tutor.service.CourseOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程订单服务层实现类
 * @author chengxi
 */
@Component
public class CourseOServiceImpl implements CourseOService {

    @Autowired
    private CourseOMapper courseOMapper;

    /**
     * 获取指定课程的订购数量
     * @param cid
     * @return
     */
    @Override
    public Integer getOrderCount(Integer cid) {

        return courseOMapper.getOrderCount(cid);
    }

    /**
     * 获取指定用户的对应课程订购信息
     * @param username
     * @param cid
     * @return
     */
    @Override
    public CourseOrder getUserOrder(String username, Integer cid) {

        return courseOMapper.getUserOrder(username, cid);
    }

    /**
     * 修改用户订单状态
     * @param username
     * @param cid
     * @param state
     * @return
     */
    @Override
    public boolean modUserState(String username, Integer cid, Integer state) {

        return courseOMapper.modUserState(username, cid, state);
    }

    /**
     * 加入购物车
     * @param username
     * @param cid
     * @param state
     * @return
     */
    @Override
    public boolean addUserOrder(String username, Integer cid, Integer state) {

        return courseOMapper.addUserOrder(username, cid, state);
    }

    /**
     * 获取用户的订单/购物车数据
     * @param username
     * @param status
     * @param startpos
     * @return
     */
    @Override
    public List<CourseOrder> getMyOrder(String username, Integer status, Integer startpos) {

        return courseOMapper.getMyOrder(username, status, startpos);
    }

    /**
     * 删除指定用户的购物车物品
     * @param id
     * @param username
     * @return
     */
    @Override
    public int delMyCart(Integer id, String username) {

        return courseOMapper.delMyCart(id, username);
    }

    /**
     * 获取指定用户的购物车物品总数
     * @param username
     * @return
     */
    @Override
    public Integer getMyCartCount(String username) {

        return courseOMapper.getMyCartCount(username);
    }

    /**
     * 将指定订单放入回收站
     * @param oid
     * @return
     */
    @Override
    public Integer setInCycle(Integer oid) {

        return courseOMapper.setInCycle(oid);
    }

    /**
     * 根据订单号和用户名获取订单数据
     * @param username
     * @param oid
     * @return
     */
    @Override
    public CourseOrder getByIdAndUser(String username, Integer oid) {

        return courseOMapper.getByIdAndUser(username, oid);
    }
}
