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
import org.framework.tutor.api.CourseCollectApi;
import org.framework.tutor.domain.CourseCollect;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseCService;
import org.framework.tutor.service.CourseMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class CourseCollectionApiImpl implements CourseCollectApi {

    @Autowired
    private CourseCService courseCService;

    @Autowired
    private CourseMService courseMService;

    /**
     * 获取我的课程收藏记录
     * @param request
     * @param response
     * @param startpos
     * @throws IOException
     */
    @Override
    public void getMyCollect(HttpServletRequest request, HttpServletResponse response, Integer startpos) throws IOException {

        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            List<CourseCollect> courseCollects = courseCService.getMyCollect(username, startpos);
            if(courseCollects.size() == 0){
                res = "{\"status\": \"valid\", \"length\": \"0\"}";
            }
            else{
                res = "{";
                int i = 1;
                SimpleDateFormat ysdf = new SimpleDateFormat("yyyy年");
                SimpleDateFormat osdf = new SimpleDateFormat("MM月dd日");
                for (org.framework.tutor.domain.CourseCollect courseCollect: courseCollects) {
                    CourseMain courseMain = courseMService.getCourseById(courseCollect.getId());
                    res += "\""+i+"\": ";
                    String temp = "{\"cyear\": \""+ysdf.format(courseCollect.getColtime())+"\", " +
                            "\"cday\": \""+osdf.format(courseCollect.getColtime())+"\", " +
                            "\"cimgsrc\": \""+courseMain.getImgsrc()+"\", " +
                            "\"cname\": \""+courseMain.getName()+"\", " +
                            "\"cid\": \""+courseCollect.getCid()+"\", " +
                            "\"descript\": \""+courseCollect.getDescript()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 判断当前用户是否收藏了指定的课程
     * @param cid
     * @param request
     * @param response
     */
    @Override
    public void checkUserCollect(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;
        String username = (String) session.getAttribute("username");

        org.framework.tutor.domain.CourseCollect courseCollect = courseCService.getCollect(cid, username);
        if(courseCollect == null){
            res = "{\"status\": \"uncollect\"}";
        }
        else{
            res = "{\"status\": \"collect\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 收藏/取消收藏
     * @param cid
     * @param mod
     * @param descript
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void modUserCollect(Integer cid, String mod, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        //收藏
        if("collect".equals(mod)){
            if(courseCService.Collect(cid, username, descript)){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{\"status\": \"mysqlerr\"}";
            }
        }
        //取消收藏
        else{
            if(courseCService.unCollect(cid, username)){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取家教的今日课程收藏数量
     * @param request
     * @param response
     */
    @Override
    public void getCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        res = "{\"count\": \""+courseCService.getCollectCountNow(username, now)+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
