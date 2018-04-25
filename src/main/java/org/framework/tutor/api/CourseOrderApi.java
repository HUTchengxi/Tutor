package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface CourseOrderApi {

    /**
     * 获取课程订购数据
     * @param cid
     * @param request
     * @param response
     */
    public void getOrderInfo(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 加入购物车
     * @param cid
     * @param request
     * @param response
     */
    public void addCart(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 获取用户购物车数据
     * @param request
     * @param response
     */
    public void getMyCart(Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 删除指定用户的购物车物品
     * @param id
     * @param request
     * @param response
     */
    public void delMyCart(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取指定用户的购物车物品总数
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMyCartCount(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取用户已支付/未支付/已失效订单数据
     * @param status
     * @param startpos
     * @param request
     * @param response
     */
    public void getMyOrder(String status, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 将指定订单放入回收站
     * @param oid
     * @param request
     * @param response
     */
    public void setInCycle(Integer oid, HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 获取家教的课程订单总数
     * @param request
     * @param response
     * @throws IOException
     */
    public void getOrderCount(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
