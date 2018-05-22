package org.framework.tutor.service;

import org.framework.tutor.domain.CourseOrder;

import java.util.List;

/**
 * 课程订单服务层接口
 * @author chengxi
 */
public interface CourseOrderService {

    /**
     * 获取指定课程的订购数量
     * @param cid
     * @return
     */
    Integer getOrderCount(Integer cid);

    /**
     * 获取指定用户的指定课程订购信息
     * @param username
     * @param cid
     * @return
     */
    CourseOrder getUserOrder(String username, Integer cid);

    /**
     * 修改用户订单状态
     * @param username
     * @param cid
     * @param state
     */
    boolean modUserState(String username, Integer cid, Integer state);

    /**
     * 加入购物车
     * @param username
     * @param cid
     * @param state
     * @return
     */
    boolean addUserOrder(String username, Integer cid, Integer state);

    /**
     * 获取我的购物车/订单数据
     * @param username
     * @param status
     * @param startpos
     * @return
     */
    List<CourseOrder> getMyOrder(String username, Integer status, Integer startpos);

    /**
     * 删除指定用户的购物车物品
     * @param id
     * @param username
     * @return
     */
    int delMyCart(Integer id, String username);

    /**
     * 获取指定用户的购物车物品总数
     * @param username
     * @return
     */
    Integer getMyCartCount(String username);

    /**
     * 将指定订单放入回收站
     * @param oid
     * @return
     */
    Integer setInCycle(Integer oid);

    /**
     * 根据订单号和用户名获取订单数据
     * @param username
     * @param oid
     * @return
     */
    CourseOrder getByIdAndUser(String username, Integer oid);

    /**
     * 获取家教的课程订单总数
     * @param username
     * @param now
     * @return
     */
    Integer getOrderCountNow(String username, String now);

    /**
     * 根据状态获取指定用户的订单相关数据
     * @param cid
     * @param username
     * @param state
     * @return
     */
    CourseOrder getByUserAndState(Integer cid, String username, Integer state);


    /**
     *
     * @Description 获取指定课程的订单数据
     * @param [id]
     * @return ActionMap
     * @author yinjimin
     * @date 2018/4/14
     */
    List<CourseOrder> getMyCourseOrderCount(Integer id);

    /**
     *
     * @Description 获取所有课程id对应的订单数据
     * @param [courseId]
     * @return java.util.List<org.framework.tutor.domain.CourseOrder>
     * @author yinjimin
     * @date 2018/4/18
     */
    List<CourseOrder> getMyOrderListByIdList(String courseId);

    /**
     *
     * @Description 获取对应的订单数据
     * @param [oid]
     * @return org.framework.tutor.domain.CourseOrder
     * @author yinjimin
     * @date 2018/4/18
     */
    CourseOrder getById(Integer oid);

    /**  
     * @Description 课程联系申请
     * @param cardId
     * @param cardId
     * @param cardId
     */
    void addCourseOrder(Integer cardId, String username, Integer status);
}
