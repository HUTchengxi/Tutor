/*
 * Copyright (C) 2011-2013 ShenZhen iBoxpay Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of iBoxPay Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with iBoxpay inc.
 *
 *
 */
package org.framework.tutor.api.impl;

import com.google.gson.JsonParser;
import org.framework.tutor.api.CourseOrderApi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseOrderApiImpl implements CourseOrderApi {

    @Autowired
    private CourseOService courseOService;

    @Autowired
    private CourseMService courseMService;

    /**
     * 获取课程订购数据
     * @param cid
     * @param request
     * @param response
     */
    public void getOrderInfo(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        //获取课程订购人数
        Integer ccount = courseOService.getOrderCount(cid);

        res = "{\"count\": \""+ccount+"\", ";

        if(username == null){
            res += "\"state\": \"invalid\"}";
        }
        else{
            org.framework.tutor.domain.CourseOrder courseOrder = courseOService.getUserOrder(username, cid);
            if(courseOrder == null){
                res += "\"state\": \"valid\"}";
            }
            else{
                Integer state = courseOrder.getState();
                if(state == 0) {
                    res += "\"state\": \"cart\"}";
                }
                else{
                    res += "\"state\": \"buy\"}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 加入购物车
     * @param cid
     * @param request
     * @param response
     */
    public void addCart(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else {
            Integer state = 0;
            if(!(courseOService.addUserOrder(username, cid, state))){
                res = "{\"status\": \"mysqlerr\"}";
            }
            else{
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取用户购物车数据
     * @param request
     * @param response
     */
    public void getMyCart(Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else {
            Integer status = 0;
            List<CourseOrder> courseOrders = courseOService.getMyOrder(username, status, startpos);
            if(courseOrders.size() == 0){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{";
                int i = 1;
                for (org.framework.tutor.domain.CourseOrder courseOrder : courseOrders) {
                    CourseMain courseMain = courseMService.getCourseById(courseOrder.getCid());
                    res += "\""+i+"\": ";
                    String temp = "{\"imgsrc\": \""+courseMain.getImgsrc()+"\", " +
                            "\"name\": \""+courseMain.getName()+"\", " +
                            "\"id\": \""+courseOrder.getId()+"\", " +
                            "\"cid\": \""+courseOrder.getCid()+"\", " +
                            "\"price\": \""+courseMain.getPrice()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }

        System.out.println(res);

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 删除指定用户的购物车物品
     * @param id
     * @param request
     * @param response
     */
    public void delMyCart(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            int len = courseOService.delMyCart(id, username);
            if(len == 0){
                res = "{\"status\": \"invalid\"}";
            }
            else{
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.close();
    }

    /**
     * 获取指定用户的购物车物品总数
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMyCartCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;
        String username = (String) session.getAttribute("username");

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            res = "{\"count\": \""+courseOService.getMyCartCount(username)+"\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取用户已支付/未支付/已失效订单数据
     * @param status
     * @param startpos
     * @param request
     * @param response
     */
    public void getMyOrder( String status, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else {
            Integer state = 1;
            if(status.equals("ned")){
                state = 3;
            }
            else if(status.equals("edn")){
                state = 2;
            }
            List<org.framework.tutor.domain.CourseOrder> courseOrders = courseOService.getMyOrder(username, state, startpos);
            if(courseOrders.size() == 0){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{";
                int i = 1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (org.framework.tutor.domain.CourseOrder courseOrder : courseOrders) {
                    CourseMain courseMain = courseMService.getCourseById(courseOrder.getCid());
                    res += "\""+i+"\": ";
                    String temp = "{\"name\": \""+courseMain.getName()+"\", " +
                            "\"id\": \""+courseOrder.getId()+"\", " +
                            "\"cid\": \""+courseOrder.getCid()+"\", " +
                            "\"otime\": \""+sdf.format(courseOrder.getOtime())+"\", " +
                            "\"price\": \""+courseMain.getPrice()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }

        System.out.println(res);

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 将指定订单放入回收站
     * @param oid
     * @param request
     * @param response
     */
    public void setInCycle(Integer oid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{

            //判断用户名和订单是否对应，防止api非法调用
            org.framework.tutor.domain.CourseOrder courseOrder = courseOService.getByIdAndUser(username, oid);
            if(courseOrder == null){
                res = "{\"status\": \"invalid\"}";
            }
            else {
                Integer row = courseOService.setInCycle(oid);
                if (row == 1) {
                    res = "{\"status\": \"valid\"}";
                } else {
                    res = "{\"status\": \"mysqlerr\"}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取家教的课程订单总数
     * @param request
     * @param response
     * @throws IOException
     */
    public void getOrderCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        res = "{\"count\": \""+courseOService.getOrderCountNow(username, now)+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
