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
import org.framework.tutor.api.CourseCommandApi;
import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseCMService;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class CourseCommandApiImpl implements CourseCommandApi {

    @Autowired
    private CourseCMService courseCMService;

    @Autowired
    private UserMService userMService;

    @Autowired
    private CourseOService courseOService;

    @Autowired
    private CourseMService courseMService;

    /**
     * 获取课程评论数据
     *
     * @param cid
     * @param response
     */
    @Override
    public void getCourseCommand(Integer cid, Integer startpos, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<CourseCommand> courseCommands = courseCMService.getCourseCommand(cid, startpos);
        int count = courseCommands.size();
        if (count == 0) {
            resultMap.put("count", courseCommands.size());
        } else {
            resultMap.put("count", courseCommands.size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                UserMain userMain = userMService.getByUser(courseCommand.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                rowMap.put("info", courseCommand.getInfo());
                rowMap.put("id", courseCommand.getId());
                rowMap.put("repid", courseCommand.getRepid());
                rowMap.put("uimgsrc", userMain.getImgsrc());
                rowMap.put("score", courseCommand.getScore());
                rowMap.put("username", userMain.getNickname());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取课程神评
     *
     * @param cid
     * @param response
     * @throws IOException
     */
    @Override
    public void getCourseCommandGod(Integer cid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getCourseCommandGod(cid);
        int count = courseCommands.size();
        if (count == 0) {
            resultMap.put("count", courseCommands.size());
        } else {
            resultMap.put("count", courseCommands.size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                UserMain userMain = userMService.getByUser(courseCommand.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                rowMap.put("info", courseCommand.getInfo());
                rowMap.put("id", courseCommand.getId());
                rowMap.put("repid", courseCommand.getRepid());
                rowMap.put("uimgsrc", userMain.getImgsrc());
                rowMap.put("username", userMain.getNickname());
                rowMap.put("score", courseCommand.getScore());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 查看当前用户对指定课程的评价
     *
     * @param cid
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void selMyCommand(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        //判断当前用户是否购买了该课程  cid  username start=1
        Integer state = 1;
        if (courseOService.getByUserAndState(cid, username, state) == null) {
            resultMap.put("status", "nobuy");
        } else {
            List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getMyCommand(username, cid);
            int count = courseCommands.size();
            if (count == 0) {
                resultMap.put("count", courseCommands.size());
            } else {
                resultMap.put("count", courseCommands.size());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                    UserMain userMain = userMService.getByUser(courseCommand.getUsername());
                    Map<String, Object> rowMap = new HashMap<>(16);
                    rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                    rowMap.put("info", courseCommand.getInfo());
                    rowMap.put("id", courseCommand.getId());
                    rowMap.put("repid", courseCommand.getRepid());
                    rowMap.put("uimgsrc", userMain.getImgsrc());
                    rowMap.put("score", courseCommand.getScore());
                    rowMap.put("username", userMain.getNickname());
                    rowList.add(rowMap);
                }
                resultMap.put("list", rowList);
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 发表用户评价
     *
     * @param cid
     * @param command
     * @param score
     * @param request
     * @param response
     */
    @Override
    public void subMyCommand(Integer cid, String command, Integer score, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            resultMap.put("status", "invalid");
        } else {
            Integer row = courseCMService.subMyCommand(cid, command, score, username);
            if (row == 1) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("status","mysqlerr");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前登录家教的课程评论总数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        resultMap.put("count", courseCMService.getCommandCountNow(username, now));

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前登录家教的课程评分平均值
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getScoreAvg(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());
        resultMap.put("count", courseCMService.getScoreAvgNow(username, now));

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前用户的课程评论数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void loadMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.loadMyCommandInfo(username);
        if (courseCommands.size() == 0) {
            resultMap.put("count", 0);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                CourseMain courseMain = courseMService.getCourseById(courseCommand.getCid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                rowMap.put("info", courseCommand.getInfo());
                rowMap.put("cid", courseCommand.getCid());
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("score", courseCommand.getScore());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [paramMap, request, response]
     * @return void
     * @Description 获取课程评论列表
     * @author yinjimin
     * @date 2018/4/18
     */
    @Override
    public void getCommandList(ParamMap paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> rowMap = new HashMap<>(1);

        Integer pageNo = paramMap.getPageNo();
        Integer pageSize = paramMap.getPageSize();
        String courseName = paramMap.getCourseName();

        if (pageNo == null || pageSize == null) {
            rowMap.put("status", "paramMiss");
            rowMap.put("rows", rowList);
        } else {

            if (courseName == null || "".equals(courseName)) {

                List<CourseMain> courseMains = courseMService.getMyCourseList(username);
                if (courseMains.size() == 0) {
                    rowMap.put("status", "courseNone");
                    rowMap.put("rows", rowList);
                } else {
                    StringBuffer courseIds = new StringBuffer("");
                    for (CourseMain courseMain : courseMains) {
                        courseIds.append(courseMain.getId());
                        courseIds.append(",");
                    }
                    String courseId = courseIds.substring(0, courseIds.length() - 1).toString();
                    List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getMyCommandList(courseId, pageNo * pageSize, pageSize);
                    if (courseCommands.size() == 0) {
                        rowMap.put("status", "commandNone");
                        rowMap.put("rows", rowList);
                    } else {
                        //获取所有数据总数
                        Integer count = courseCMService.getCommandCountByIdlist(courseId);
                        rowMap.put("total", count);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                            resultMap = new HashMap<>(1);
                            CourseMain courseMain = courseMService.getCourseById(courseCommand.getCid());
                            Integer stype = courseMain.getStype();
                            StringBuffer courseStype = new StringBuffer("");
                            if (stype == 1) {
                                courseStype.append("小学-");
                            } else if (stype == 2) {
                                courseStype.append("初中-");
                            } else if (stype == 3) {
                                courseStype.append("高中-");
                            } else {
                                courseStype.append("其他-");
                            }
                            resultMap.put("commandId", courseCommand.getId());
                            resultMap.put("courseName", courseMain.getName());
                            resultMap.put("courseType", courseStype + courseMain.getCtype());
                            resultMap.put("commandUser", courseCommand.getUsername());
                            resultMap.put("commandDesc", courseCommand.getInfo());
                            resultMap.put("commandTime", simpleDateFormat.format(courseCommand.getCtime()));
                            resultMap.put("godStatus", courseCommand.getGod());
                            resultMap.put("commandStatus", courseCommand.getStatus());
                            rowList.add(resultMap);
                        }
                        rowMap.put("rows", rowList);
                    }
                }
            } else {
                List<CourseMain> courseMains = courseMService.getByCoursename(courseName);
                if (courseMains.size() == 0) {
                    rowMap.put("status", "courseNone");
                    rowMap.put("rows", rowList);
                } else {
                    StringBuffer courseIds = new StringBuffer("");
                    for (CourseMain courseMain : courseMains) {
                        courseIds.append(courseMain.getId());
                        courseIds.append(",");
                    }
                    String courseId = courseIds.substring(0, courseIds.length() - 1).toString();
                    List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getMyCommandList(courseId, pageNo * pageSize, pageSize);
                    if (courseCommands.size() == 0) {
                        rowMap.put("status", "commandNone");
                        rowMap.put("rows", rowList);
                    } else {
                        //获取所有数据总数
                        Integer count = courseCMService.getCommandCountByIdlist(courseId);
                        rowMap.put("total", count);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                            resultMap = new HashMap<>(1);
                            CourseMain courseMain = courseMService.getCourseById(courseCommand.getCid());
                            Integer stype = courseMain.getStype();
                            StringBuffer courseStype = new StringBuffer("");
                            if (stype == 1) {
                                courseStype.append("小学-");
                            } else if (stype == 2) {
                                courseStype.append("初中-");
                            } else if (stype == 3) {
                                courseStype.append("高中-");
                            } else {
                                courseStype.append("其他-");
                            }
                            resultMap.put("commandId", courseCommand.getId());
                            resultMap.put("courseName", courseMain.getName());
                            resultMap.put("courseType", courseStype + courseMain.getCtype());
                            resultMap.put("commandUser", courseCommand.getUsername());
                            resultMap.put("commandDesc", courseCommand.getInfo());
                            resultMap.put("commandTime", simpleDateFormat.format(courseCommand.getCtime()));
                            resultMap.put("godStatus", courseCommand.getGod());
                            resultMap.put("commandStatus", courseCommand.getStatus());
                            rowList.add(resultMap);
                        }
                        rowMap.put("rows", rowList);
                    }
                }
            }
        }

        writer.print(gson.toJson(rowMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [id, request, response]
     * @return void
     * @Description 指定评论为神评
     * @author yinjimin
     * @date 2018/4/18
     */
    @Override
    public void setCommandGodstate(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        //获取评论对应的课程所属username
        org.framework.tutor.domain.CourseCommand courseCommand = courseCMService.getCommandById(id);
        if (courseCommand == null || !courseCommand.getUsername().equals(username)) {
            resultMap.put("status", "invalid");
        } else {

            //判断当前课程神评是否已经为三个了
            Integer count = courseCMService.getGodCountById(courseCommand.getCid());
            if (count >= 3) {
                resultMap.put("status", "full");
            } else {
                //设置神评
                courseCMService.setCommandGodstate(id);
                resultMap.put("status", "valid");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
