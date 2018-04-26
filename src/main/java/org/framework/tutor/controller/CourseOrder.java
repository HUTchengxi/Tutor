package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseOrderApi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 课程订单控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/courseorder_con")
public class CourseOrder {

    @Autowired
    private CourseOrderApi courseOrderApi;

    /**
     * 获取课程订购数据
     * @param cid
     * @param request
     * @param response
     */
    @RequestMapping("/getorderinfo")
    public void getOrderInfo(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.getOrderInfo(cid, request, response);
    }

    /**
     * 加入购物车
     * @param cid
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/addcart")
    public void addCart(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.addCart(cid, request, response);
    }

    /**
     * 获取用户购物车数据
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycart")
    public void getMyCart(Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.getMyCart(startpos, request, response);
    }

    /**
     * 删除指定用户的购物车物品
     * @param id
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/delmycart")
    public void delMyCart(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.delMyCart(id, request, response);
    }

    /**
     * 获取指定用户的购物车物品总数
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycartcount")
    public void getMyCartCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.getMyCartCount(request, response);
    }

    /**
     * 获取用户已支付/未支付/已失效订单数据
     * @param status
     * @param startpos
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmyorder")
    public void getMyOrder(@RequestParam("status") String status, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.getMyOrder(status, startpos, request, response);
    }

    /**
     * 将指定订单放入回收站
     * @param oid
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/setinrecycle")
    public void setInCycle(Integer oid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.setInCycle(oid, request, response);
    }

    /**
     * 获取家教的课程订单总数
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getordercount")
    public void getOrderCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderApi.getOrderCount(request, response);
    }
}
