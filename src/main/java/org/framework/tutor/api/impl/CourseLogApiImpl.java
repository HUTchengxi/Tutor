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
import org.framework.tutor.api.CourseLogAPi;
import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseLService;
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
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseLogApiImpl implements CourseLogAPi {

    @Autowired
    private CourseLService courseLService;

    @Autowired
    private CourseMService courseMService;

    /**
     * 获取我的课程记录
     * @param request
     * @param response
     */
    @Override
    public void getLog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            //获取课程记录
            List<CourseLog> courseLogs  = courseLService.getUserlog(username);
            if(courseLogs.size() == 0){
                res = "{\"status\": \"ok\", \"len\": \"0\"}";
            }
            else {
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.CourseLog courseLog : courseLogs) {
                    CourseMain courseMain = courseMService.getCourseById(courseLog.getCid());
                    res += "\""+i+"\": ";
                    String temp = "{\"logtime\": \""+simpleDateFormat.format(courseLog.getLogtime())+"\", " +
                            "\"imgsrc\": \""+courseMain.getImgsrc()+"\", " +
                            "\"id\": \""+courseLog.getId()+"\", " +
                            "\"cid\": \""+courseLog.getCid()+"\", " +
                            "\"cname\": \""+courseMain.getName()+"\"}, ";
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
     * 删除指定的课程记录
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
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            if(!courseLService.delLog(id)){
                res = "{\"status\": \"mysqlerr\", \"msg\": \"I'm sorry\"}";
            }
            else{
                res = "{\"status\": \"ok\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
