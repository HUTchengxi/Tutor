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
import org.framework.tutor.api.CourseChapterApi;
import org.framework.tutor.domain.CourseChapter;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseChService;
import org.framework.tutor.service.CourseMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseChapterApiImpl implements CourseChapterApi {

    @Autowired
    private CourseChService courseChService;

    @Autowired
    private CourseMService courseMService;

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @param response
     */
    @Override
    public void getCourseChapter(Integer cid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<CourseChapter> courseChapters = courseChService.getCourseChapter(cid);
        if(courseChapters.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else{
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseChapter courseChapter: courseChapters) {
                res += "\""+i+"\": ";
                String temp = "{\"title\": \""+courseChapter.getTitle()+"\", " +
                        "\"id\": \""+courseChapter.getId()+"\", " +
                        "\"ord\": \""+courseChapter.getOrd()+"\", " +
                        "\"descript\": \""+courseChapter.getDescript()+"\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 删除指定目录
     * @param [id, response, request]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @Override
    public void deleteChapter(Integer id, HttpServletResponse response, HttpServletRequest request) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;

        HttpSession session  = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断待删除的目录是否为当前登录用户的目录
        org.framework.tutor.domain.CourseChapter courseChapter = courseChService.getById(id);
        if(courseChapter == null){
            res = "{\"status\": \"invalid\"}";
        }
        else {
            CourseMain courseMain = courseMService.getCourseById(courseChapter.getCid());
            String realUser = courseMain.getUsername();
            if (!realUser.equals(username)) {
                res = "{\"status\": \"invalid\"}";
            }
            else{
                courseChService.deleteChapter(id);
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 更新目录
     * @param [id, title, descript, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @Override
    public void modChapter(Integer id, Integer cid, String title, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断更新的目录是否为当前登录用户的目录
        org.framework.tutor.domain.CourseChapter courseChapter = courseChService.getById(cid);
        if(courseChapter == null){
            res = "{\"status\": \"invalid\"}";
        }
        else {
            CourseMain courseMain = courseMService.getCourseById(courseChapter.getCid());
            String realUser = courseMain.getUsername();
            if (!realUser.equals(username)) {
                res = "{\"status\": \"invalid\"}";
            }
            else{
                //新增
                if(id == null){
                    //获取最大的ord
                    Integer ord = courseChService.getLastOrd(cid)+1;
                    courseChService.addChapter(cid, ord, title, descript);
                }
                //修改
                else{
                    courseChService.modChapter(id, title, descript);
                }
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
