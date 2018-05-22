package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.mapper.CourseOrderMapper;
import org.framework.tutor.service.CourseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程订单服务层实现类
 * @author chengxi
 */
@Component
public class CourseOrderServiceImpl implements CourseOrderService {

    @Autowired
    private CourseOrderMapper courseOrderMapper;

    /**
     * 获取指定课程的订购数量
     * @param cid
     * @return
     */
    @Override
    public Integer getOrderCount(Integer cid) {

        return courseOrderMapper.getOrderCount(cid);
    }

    /**
     * 获取指定用户的对应课程订购信息
     * @param username
     * @param cid
     * @return
     */
    @Override
    public CourseOrder getUserOrder(String username, Integer cid) {

        return courseOrderMapper.getUserOrder(username, cid);
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

        return courseOrderMapper.modUserState(username, cid, state);
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

        return courseOrderMapper.addUserOrder(username, cid, state);
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

        return courseOrderMapper.getMyOrder(username, status, startpos);
    }

    /**
     * 删除指定用户的购物车物品
     * @param id
     * @param username
     * @return
     */
    @Override
    public int delMyCart(Integer id, String username) {

        return courseOrderMapper.delMyCart(id, username);
    }

    /**
     * 获取指定用户的购物车物品总数
     * @param username
     * @return
     */
    @Override
    public Integer getMyCartCount(String username) {

        return courseOrderMapper.getMyCartCount(username);
    }

    /**
     * 将指定订单放入回收站
     * @param oid
     * @return
     */
    @Override
    public Integer setInCycle(Integer oid) {

        return courseOrderMapper.setInCycle(oid);
    }

    /**
     * 根据订单号和用户名获取订单数据
     * @param username
     * @param oid
     * @return
     */
    @Override
    public CourseOrder getByIdAndUser(String username, Integer oid) {

        return courseOrderMapper.getByIdAndUser(username, oid);
    }

    /**
     * 获取家教的课程订单总数
     * @param username
     * @param now
     * @return
     */
    @Override
    public Integer getOrderCountNow(String username, String now) {

        return courseOrderMapper.getOrderCountNow(username, now);
    }

    /**
     * 根据状态获取指定用户的订单数据
     * @param cid
     * @param username
     * @param state
     * @return
     */
    @Override
    public CourseOrder getByUserAndState(Integer cid, String username, Integer state) {

        return courseOrderMapper.getByUserAndState(cid, username, state);
    }

    @Override
    public List<CourseOrder> getMyCourseOrderCount(Integer id) {
        return courseOrderMapper.getMyCourseOrderCount(id);
    }

    @Override
    public List<CourseOrder> getMyOrderListByIdList(String courseId) {
        return courseOrderMapper.getMyOrderListByIdList(courseId);
    }

    @Override
    public CourseOrder getById(Integer oid) {
        return courseOrderMapper.getById(oid);
    }

    @Override
    public void addCourseOrder(Integer cardId, String username, Integer status) {
        courseOrderMapper.addCourseOrder(cardId, username, status);
    }
}
