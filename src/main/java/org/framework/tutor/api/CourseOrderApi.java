package org.framework.tutor.api;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseOrderApi {

    /**
     * 获取课程订购数据
     * @param cid 课程id
     */
    public String getOrderInfo(Integer cid) throws IOException ;

    /**
     * 加入购物车
     * @param cid 课程id
     */
    public String addCart(Integer cid) throws IOException ;

    /**
     * 获取用户购物车数据
     * @param startpos 开始位置
     */
    public String getMyCart(Integer startpos) throws IOException ;

    /**
     * 删除指定用户的购物车物品
     * @param id 购物车物品id
     */
    public String delMyCart(Integer id) throws IOException;

    /**
     * 获取指定用户的购物车物品总数
     */
    public String getMyCartCount() throws IOException;

    /**
     * 获取用户已支付/未支付/已失效订单数据
     * @param status 订单状态
     * @param startpos 开始位置
     */
    public String getMyOrder(String status, Integer startpos) throws IOException ;

    /**
     * 将指定订单放入回收站
     * @param oid 订单id
     */
    public String setInCycle(Integer oid) throws IOException ;

    /**
     * 获取家教的课程订单总数
     */
    public String getOrderCount() throws IOException;

    /**
     * 课程联系申请
     */
    String addCourseOrder(Integer cardId) throws MessagingException;
}
