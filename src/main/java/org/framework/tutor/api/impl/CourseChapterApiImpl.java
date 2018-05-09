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
import org.framework.tutor.api.CourseChapterApi;
import org.framework.tutor.domain.CourseChapter;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseChapterService;
import org.framework.tutor.service.CourseMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
public class CourseChapterApiImpl implements CourseChapterApi {

    @Autowired
    private CourseChapterService courseChapterService;

    @Autowired
    private CourseMainService courseMainService;

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @param response
     */
    @Override
    public void getCourseChapter(Integer cid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<CourseChapter> courseChapters = courseChapterService.getCourseChapter(cid);
        if(courseChapters.size() == 0){
            resultMap.put("count", 0);
        }
        else{
            for (org.framework.tutor.domain.CourseChapter courseChapter: courseChapters) {
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("title", courseChapter.getTitle());
                rowMap.put("id", courseChapter.getId());
                rowMap.put("ord", courseChapter.getOrd());
                rowMap.put("descript", courseChapter.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
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
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        HttpSession session  = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断待删除的目录是否为当前登录用户的目录
        org.framework.tutor.domain.CourseChapter courseChapter = courseChapterService.getById(id);
        if(courseChapter == null){
            resultMap.put("status", "invalid");
        }
        else {
            CourseMain courseMain = courseMainService.getCourseById(courseChapter.getCid());
            String realUser = courseMain.getUsername();
            if (!realUser.equals(username)) {
                resultMap.put("status", "invalid");
            }
            else{
                courseChapterService.deleteChapter(id);
                resultMap.put("status", "valid");
            }
        }

        writer.print(gson.toJson(resultMap));
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
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断更新的目录是否为当前登录用户的目录
        org.framework.tutor.domain.CourseChapter courseChapter = courseChapterService.getById(cid);
        if(courseChapter == null){
            resultMap.put("status", "invalid");
        }
        else {
            CourseMain courseMain = courseMainService.getCourseById(courseChapter.getCid());
            String realUser = courseMain.getUsername();
            if (!realUser.equals(username)) {
                resultMap.put("status", "invalid");
            }
            else{
                //新增
                if(id == null){
                    //获取最大的ord
                    Integer ord = courseChapterService.getLastOrd(cid)+1;
                    courseChapterService.addChapter(cid, ord, title, descript);
                }
                //修改
                else{
                    courseChapterService.modChapter(id, title, descript);
                }
                resultMap.put("status", "valid");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
