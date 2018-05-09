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
import org.framework.tutor.api.CourseLogAPi;
import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseLogService;
import org.framework.tutor.service.CourseMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseLogApiImpl implements CourseLogAPi {

    @Autowired
    private CourseLogService courseLogService;

    @Autowired
    private CourseMainService courseMainService;

    /**
     * 获取我的课程记录
     *
     * @param request
     * @param response
     */
    @Override
    public void getLog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        //获取课程记录
        List<CourseLog> courseLogs = courseLogService.getUserlog(username);
        if (courseLogs.size() == 0) {
            resultMap.put("status", "ok");
            resultMap.put("len", 0);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseLog courseLog : courseLogs) {
                CourseMain courseMain = courseMainService.getCourseById(courseLog.getCid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("logtime", simpleDateFormat.format(courseLog.getLogtime()));
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("id", courseLog.getId());
                rowMap.put("cid", courseLog.getCid());
                rowMap.put("cname", courseMain.getName());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * 删除指定的课程记录
     *
     * @param id
     * @param request
     * @param response
     */
    @Override
    public void delLog(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (!courseLogService.delLog(id)) {
            resultMap.put("status", "mysqlerr");
        } else {
            resultMap.put("status", "ok");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
