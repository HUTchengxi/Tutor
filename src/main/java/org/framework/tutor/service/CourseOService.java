package org.framework.tutor.service;

import org.framework.tutor.domain.CourseOrder;

import java.util.List;

/**
 * 课程订单服务层接口
 * @author chengxi
 */
public interface CourseOService {

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
}
