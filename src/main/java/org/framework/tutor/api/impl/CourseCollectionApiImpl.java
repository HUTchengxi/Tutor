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

import com.google.gson.Gson;
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
import java.util.*;

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
     *
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
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<CourseCollect> courseCollects = courseCService.getMyCollect(username, startpos);
        if (courseCollects.size() == 0) {
            resultMap.put("status", "valid");
            resultMap.put("length", 0);
        } else {
            SimpleDateFormat ysdf = new SimpleDateFormat("yyyy年");
            SimpleDateFormat osdf = new SimpleDateFormat("MM月dd日");
            for (org.framework.tutor.domain.CourseCollect courseCollect : courseCollects) {
                CourseMain courseMain = courseMService.getCourseById(courseCollect.getId());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("cyear", ysdf.format(courseCollect.getColtime()));
                rowMap.put("cday", osdf.format(courseCollect.getColtime()));
                rowMap.put("cimgsrc", courseMain.getImgsrc());
                rowMap.put("cname", courseMain.getName());
                rowMap.put("cid", courseCollect.getCid());
                rowMap.put("descript", courseCollect.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 判断当前用户是否收藏了指定的课程
     *
     * @param cid
     * @param request
     * @param response
     */
    @Override
    public void checkUserCollect(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        org.framework.tutor.domain.CourseCollect courseCollect = courseCService.getCollect(cid, username);
        if (courseCollect == null) {
            resultMap.put("status", "uncollect");
        } else {
            resultMap.put("status", "collect");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 收藏/取消收藏
     *
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
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //收藏
        if ("collect".equals(mod)) {
            if (courseCService.Collect(cid, username, descript)) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("status", "mysqlerr");
            }
        }
        //取消收藏
        else {
            if (courseCService.unCollect(cid, username)) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("status", "mysqlerr");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取家教的今日课程收藏数量
     *
     * @param request
     * @param response
     */
    @Override
    public void getCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        resultMap.put("count", courseCService.getCollectCountNow(username, now));

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
