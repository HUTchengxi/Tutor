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
import org.framework.tutor.api.CourseCollectApi;
import org.framework.tutor.domain.CourseCollect;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseCollectService;
import org.framework.tutor.service.CourseMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseCollectionApiImpl implements CourseCollectApi {

    @Autowired
    private CourseCollectService courseCollectService;

    @Autowired
    private CourseMainService courseMainService;

    @Autowired
    private StringRedisTemplate redis;

    /**
     * 获取我的课程收藏记录
     *
     * @param request
     * @param response
     * @param startpos
     * @throws IOException
     */
    //TODO: 后续可以考虑加入redis
    @Override
    public void getMyCollect(HttpServletRequest request, HttpServletResponse response, Integer startpos) throws IOException {

        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<CourseCollect> courseCollects = courseCollectService.getMyCollect(username, startpos);
        if (courseCollects.size() == 0) {
            resultMap.put("status", "valid");
            resultMap.put("length", 0);
        } else {
            SimpleDateFormat ysdf = new SimpleDateFormat("yyyy年");
            SimpleDateFormat osdf = new SimpleDateFormat("MM月dd日");
            for (org.framework.tutor.domain.CourseCollect courseCollect : courseCollects) {
                CourseMain courseMain = courseMainService.getCourseById(courseCollect.getId());
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
    //TODO: 使用了Redis   保存[username].[cid].checkusercollect
    @Override
    public void checkUserCollect(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append("."+cid).append(".checkusercollect");
        if(redis.hasKey(keyTemp.toString())){
            resultMap.put("status", redis.opsForValue().get(keyTemp.toString()));
        }else {
            CourseCollect courseCollect = courseCollectService.getCollect(cid, username);
            if (courseCollect == null) {
                resultMap.put("status", "uncollect");
                redis.opsForValue().set(keyTemp.toString(), "uncollect");
            } else {
                resultMap.put("status", "collect");
                redis.opsForValue().set(keyTemp.toString(), "collect");
            }
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
    //TODO：使用了redis    更新[username].[cid].checkusercollect    tutor.[username].collectcount
    @Override
    public void modUserCollect(Integer cid, String mod, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append("."+cid).append(".checkusercollect");
        StringBuffer keyTemp2 = new StringBuffer("tutor");

        //获取对应的课程家教用户名
        CourseMain courseMain = courseMainService.getCourseById(cid);
        String tutor = courseMain.getUsername();
        keyTemp2.append("."+tutor).append(".collectcount");

        //收藏
        if ("collect".equals(mod)) {
            if (courseCollectService.Collect(cid, username, descript)) {
                resultMap.put("status", "valid");

                //更新[username].[cid].checkusercollect
                redis.opsForValue().set(keyTemp.toString(), "collect");

                if(redis.hasKey(keyTemp2.toString())){
                    Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp2.toString())) + 1;
                    redis.opsForValue().set(keyTemp2.toString(), count.toString());
                }
            } else {
                resultMap.put("status", "mysqlerr");
            }
        }
        //取消收藏
        else {
            if (courseCollectService.unCollect(cid, username)) {
                resultMap.put("status", "valid");

                //更新[username].[cid].checkusercollect
                redis.opsForValue().set(keyTemp.toString(), "uncollect");

                if(redis.hasKey(keyTemp2.toString())){
                    Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp2.toString())) - 1;
                    redis.opsForValue().set(keyTemp2.toString(), count.toString());
                }
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
    //TODO: 使用了redis    保存tutor.[username].collectcount
    @Override
    public void getCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        StringBuffer keyTemp = new StringBuffer("tutor");
        keyTemp.append("."+username).append(".collectcount");
        if(redis.hasKey(keyTemp.toString())){
            resultMap.put("count", redis.opsForValue().get(keyTemp.toString()));
        }else {
            Integer count = courseCollectService.getCollectCountNow(username, now);
            resultMap.put("count", count);
            redis.opsForValue().set(keyTemp.toString(), count.toString());
            redis.expire(keyTemp.toString(), 1800, TimeUnit.SECONDS);
        }
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
