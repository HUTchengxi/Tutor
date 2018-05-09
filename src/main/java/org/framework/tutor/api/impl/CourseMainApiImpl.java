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
import org.framework.tutor.api.CourseMainApi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
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
public class CourseMainApiImpl implements CourseMainApi {

    @Autowired
    private CourseMainService courseMainService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private CourseCommandService courseCommandService;

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private CourseChapterService courseChapterService;

    @Autowired
    private CourseSummaryService courseSummaryService;

    /**
     * 加载课程数据
     *
     * @param stype
     * @param ctype
     * @param sort
     * @param startpos
     * @param status
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getCourseList(Integer stype, String ctype, String sort, Integer startpos, Integer status, String keyword,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();
        List<CourseMain> courseMains = null;
        //默认：不限进行排序
        if (ctype.equals("all")) {
            //时间最新排序
            if (sort.equals("new")) {
                //未指定主类别
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListNew(startpos);
                    } else {
                        courseMains = courseMainService.getCourseListNewKW(keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListNew(stype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListNewKW(stype, keyword, startpos);
                    }
                }
            }
            //搜索最热排序
            else if (sort.equals("hot")) {
                //未指定主类别
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListHot(startpos);
                    } else {
                        courseMains = courseMainService.getCourseListHotKW(keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListHot(stype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListHotSKW(stype, keyword, startpos);
                    }
                }
            }
            //评论最多排序
            else {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListMore(startpos);
                    } else {
                        courseMains = courseMainService.getCourseListMoreKW(keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListMore(stype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListMoreSKW(stype, keyword, startpos);
                    }
                }
            }
        }
        //选择指定子类别的课程
        else {
            //时间最新排序
            if (sort.equals("new")) {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListNewAC(ctype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListNewACK(ctype, keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListNew(stype, ctype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListNewKW(stype, ctype, keyword, startpos);
                    }
                }
            }
            //搜索最热排序
            else if (sort.equals("hot")) {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListHotAC(ctype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListHotACK(ctype, keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListHot(stype, ctype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListHotKW(stype, ctype, keyword, startpos);
                    }
                }
            }
            //评论最多排序
            else {
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListMoreAC(ctype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListMoreACK(ctype, keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMainService.getCourseListMore(stype, ctype, startpos);
                    } else {
                        courseMains = courseMainService.getCourseListMoreKW(stype, ctype, keyword, startpos);
                    }
                }
            }
        }
        if (courseMains.size() == 0) {
            resultMap.put("status", "valid");
            resultMap.put("len", 0);
        } else {
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMainService.getByUser(courseMain.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("id", courseMain.getId());
                rowMap.put("name", courseMain.getName());
                rowMap.put("jcount", courseMain.getJcount());
                rowMap.put("price", courseMain.getPrice());
                rowMap.put("nickname", userMain.getNickname());
                rowMap.put("uimgsrc", userMain.getImgsrc());
                rowMap.put("descript", courseMain.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [response]
     * @return void
     * @Description 获取所有科目类别
     * @author yinjimin
     * @date 2018/4/15
     */
    @Override
    public void getAllCourseType(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMainService.getAllCourseType();
        if (courseMains.size() == 0) {
            resultMap.put("status", "valid");
            resultMap.put("len", 0);
        } else {
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                Map<String, Object> rowMap = new HashMap<>(2);
                rowMap.put("ctype", courseMain.getCtype());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定主类别的所有科目类别
     *
     * @param stype
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getCourseType(String stype, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMainService.getCourseType(stype);
        if (courseMains.size() == 0) {
            resultMap.put("status", "valid");
            resultMap.put("len", 0);
        } else {
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                Map<String, Object> rowMap = new HashMap<>(2);
                rowMap.put("ctype", courseMain.getCtype());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 关键字获取所有对应的课程数据
     *
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void courseSearch(String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMainService.courseSearch(keyword);
        if (courseMains.size() == 0) {
            resultMap.put("status", "valid");
            resultMap.put("len", 0);
        } else {
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMainService.getByUser(courseMain.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("id", courseMain.getId());
                rowMap.put("name", courseMain.getName());
                rowMap.put("jcount", courseMain.getJcount());
                rowMap.put("nickname", userMain.getNickname());
                rowMap.put("price", courseMain.getPrice());
                rowMap.put("uimgsrc", userMain.getImgsrc());
                rowMap.put("descript", courseMain.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定的课程的数据
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @Override
    public void getCourseById(Integer id, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(16);

        org.framework.tutor.domain.CourseMain courseMain = courseMainService.getCourseById(id);
        if (courseMain == null) {
            resultMap.put("status", "invalid");
        } else {
            UserMain userMain = userMainService.getByUser(courseMain.getUsername());
            resultMap.put("imgsrc", courseMain.getImgsrc());
            resultMap.put("id", courseMain.getId());
            resultMap.put("stype", courseMain.getStype());
            resultMap.put("ctype", courseMain.getCtype());
            resultMap.put("name", courseMain.getName());
            resultMap.put("jcount", courseMain.getJcount());
            resultMap.put("nickname", userMain.getNickname());
            resultMap.put("info", userMain.getInfo());
            resultMap.put("price", courseMain.getPrice());
            resultMap.put("uimgsrc", userMain.getImgsrc());
            resultMap.put("total", courseMain.getTotal());
            resultMap.put("descript", courseMain.getDescript());
        }
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取所搜索的课程数量，便于实现分页
     *
     * @param stype
     * @param ctype
     * @param status
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getCourseCount(Integer stype, String ctype, Integer status, String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer total = 0;
        //默认，不限课程类别进行排序
        if (ctype.equals("all")) {
            //未指定主类别
            if (stype == -1) {
                //不是通过用户搜素
                if (status == null || status == 0) {
                    total = courseMainService.getCourseCount();
                } else {
                    total = courseMainService.getCourseCountK(keyword);
                }
            } else {
                if (status == null || status == 0) {
                    total = courseMainService.getCourseCountS(stype);
                } else {
                    total = courseMainService.getCourseCountSK(stype, keyword);
                }
            }
        } else {
            if (stype == -1) {
                if (status == null || status == 0) {
                    total = courseMainService.getCourseCountC(ctype);
                } else {
                    total = courseMainService.getCourseCountCK(ctype, keyword);
                }
            } else {
                if (status == null || status == 0) {
                    total = courseMainService.getCourseCountCS(ctype, stype);
                } else {
                    total = courseMainService.getCourseCountCSK(ctype, stype, keyword);
                }
            }
        }

        resultMap.put("total", total);
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前家教的所有发布数据
     * @author yinjimin
     * @date 2018/4/14
     */
    @Override
    public void getMyPublish(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMainService.getMyPublish(username);
        if (courseMains.size() == 0) {
            resultMap.put("status", 0);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMainService.getByUser(courseMain.getUsername());
                Integer score = courseCommandService.getMyPublishAvg(courseMain.getId());
                Map<String, Object> rowMap = new HashMap<>(16);
                Integer buycount = courseOrderService.getMyCourseOrderCount(courseMain.getId()).size();
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("id", courseMain.getId());
                rowMap.put("name", courseMain.getName());
                rowMap.put("viscount", courseMain.getHcount());
                rowMap.put("ptime", simpleDateFormat2.format(courseMain.getPtime()));
                rowMap.put("regtime", simpleDateFormat.format(courseMain.getPtime()));
                rowMap.put("comcount", courseMain.getCcount());
                rowMap.put("buycount", courseMain.getCcount());
                rowMap.put("score", score);
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [name, stype, ctype, imgsrc, total, jcount, price, sumTitle1, sumTitle2, sumTitle3,
     *               sumDescript1, sumDescript2, sumDescript3, chapTitle, chaDescript, request, response]
     * @return void
     * @Description 发布课程
     * @author yinjimin
     * @date 2018/4/15
     */
    @Override
    @Transactional
    public void publishNewCourse(String name, Integer stype, String ctype, String descript,
                                 MultipartFile imgsrc, Integer total, Integer jcount,
                                 Double price, String sumTitle1, String sumTitle2,
                                 String sumTitle3, String sumDescript1, String sumDescript2,
                                 String sumDescript3, String chapTitle, String chapDescript,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Integer identity = (Integer) session.getAttribute("identity");
            //判断课程名称是否已存在
            org.framework.tutor.domain.CourseMain nameCourseMain = courseMainService.checkIsexistName(name);
            if (nameCourseMain != null) {
                resultMap.put("status", "courseexist");
            } else {
                //上传图片
                String webPath = session.getServletContext().getRealPath("/");
                System.out.println(webPath);
                String srcPath = webPath.substring(0, webPath.lastIndexOf("\\"));
                srcPath = srcPath.substring(0, srcPath.lastIndexOf("\\"));
                System.out.println(srcPath);
                File file = new File(srcPath + File.separator + "/resources/static/images/user/course/" + imgsrc.getOriginalFilename());
                if (file.exists()) {
                    resultMap.put("status", "fileexist");
                }else {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(imgsrc.getBytes());
                    fos.flush();
                    fos.close();
                    //保存课程基本信息
                    courseMainService.publishCourse(username, name, "/images/user/course/" + imgsrc.getOriginalFilename(), stype, ctype, jcount, descript, price, total);
                    org.framework.tutor.domain.CourseMain courseMain = courseMainService.getByName(username, name, stype, ctype);
                    //保存课程概述信息
                    courseSummaryService.addCourseSummary(username, courseMain.getId(), sumTitle1, sumDescript1);
                    courseSummaryService.addCourseSummary(username, courseMain.getId(), sumTitle2, sumDescript2);
                    courseSummaryService.addCourseSummary(username, courseMain.getId(), sumTitle3, sumDescript3);
                    //保存目录信息
                    String eq = "c03c1650fa940cd2f5de959bfbd6d8a6";
                    String[] titleArr = chapTitle.split(eq);
                    String[] descriptArr = chapDescript.split(eq);
                    int i = 1;
                    for (String title : titleArr) {
                        courseChapterService.addChapter(courseMain.getId(), i, title, descriptArr[--i]);
                        i += 2;
                    }
                    resultMap.put("status", "valid");
                }
            }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * @param
     * @return java.lang.String
     * @Description 获取课程概述
     * @author yinjimin
     * @date 2018/4/25
     */
    @Override
    public String getCourseSummary(Integer cid) {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(3);

        List<CourseSummary> courseSummarys = courseSummaryService.getCourseSummary(cid);

        if (courseSummarys.size() == 0) {
            resultMap.put("status", "none");
        } else {

            UserMain userMain = userMainService.getByUser(courseSummarys.get(0).getUsername());
            resultMap.put("nickname", userMain.getNickname());
            resultMap.put("imgsrc", userMain.getImgsrc());
            List<Object> rowList = new ArrayList<>(3);
            for (CourseSummary courseSummary : courseSummarys) {
                Map<String, Object> rowMap = new HashMap<>(2);
                rowMap.put("title", courseSummary.getTitle());
                rowMap.put("descript", courseSummary.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("rows", rowList);
        }

        return gson.toJson(resultMap);
    }
}
