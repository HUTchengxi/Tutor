package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 课程表控制类
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/coursemain_con")
public class CourseMain {

    @Autowired
    private CourseMService courseMService;

    @Autowired
    private UserMService userMService;

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
    @RequestMapping("/getcourselist")
    public void getCourseList(Integer stype, String ctype, String sort, Integer startpos, Integer status, String keyword,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;
        List<org.framework.tutor.domain.CourseMain> courseMains = null;
        //默认：不限进行排序
        if (ctype.equals("all")) {
            //时间最新排序
            if (sort.equals("new")) {
                //未指定主类别
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListNew(startpos);
                    } else {
                        courseMains = courseMService.getCourseListNewKW(keyword, startpos);
                    }
                } else {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListNew(stype, startpos);
                    } else {
                        courseMains = courseMService.getCourseListNewKW(stype, keyword, startpos);
                    }
                }
            }
            //搜索最热排序
            else if (sort.equals("hot")) {
                //未指定主类别
                if (stype == -1) {
                    if (status == null || status == 0) {
                        courseMains = courseMService.getCourseListHot(startpos);
                    } else {
                        courseMains = courseMService.getCourseListHotKW(keyword, startpos);
                    }
                } else {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListHot(stype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListHotSKW(stype, keyword, startpos);
                    }
                }
            }
            //评论最多排序
            else {
                if (stype == -1) {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListMore(startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListMoreKW(keyword, startpos);
                    }
                } else {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListMore(stype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListMoreSKW(stype, keyword, startpos);
                    }
                }
            }
        }
        //选择指定子类别的课程
        else {
            //时间最新排序
            if (sort.equals("new")) {
                if (stype == -1) {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListNewAC(ctype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListNewACK(ctype, keyword, startpos);
                    }
                } else {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListNew(stype, ctype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListNewKW(stype, ctype, keyword, startpos);
                    }
                }
            }
            //搜索最热排序
            else if (sort.equals("hot")) {
                if (stype == -1) {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListHotAC(ctype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListHotACK(ctype, keyword, startpos);
                    }
                } else {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListHot(stype, ctype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListHotKW(stype, ctype, keyword, startpos);
                    }
                }
            }
            //评论最多排序
            else {
                if (stype == -1) {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListMoreAC(ctype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListMoreACK(ctype, keyword, startpos);
                    }
                } else {
                    if(status == null || status == 0) {
                        courseMains = courseMService.getCourseListMore(stype, ctype, startpos);
                    }
                    else{
                        courseMains = courseMService.getCourseListMoreKW(stype, ctype, keyword, startpos);
                    }
                }
            }
        }
        if (courseMains.size() == 0) {
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        } else {
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMService.getByUser(courseMain.getUsername());
                res += "\"" + i + "\": ";
                String temp = "{\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                        "\"id\": \"" + courseMain.getId() + "\", " +
                        "\"name\": \"" + courseMain.getName() + "\", " +
                        "\"jcount\": \"" + courseMain.getJcount() + "\", " +
                        "\"nickname\": \"" + userMain.getNickname() + "\", " +
                        "\"price\": \"" + courseMain.getPrice() + "\", " +
                        "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                        "\"descript\": \"" + courseMain.getDescript() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }
        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取所有科目类别
     *
     * @param stype
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursetype")
    public void getCourseType(String stype, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.getCourseType(stype);
        if (courseMains.size() == 0) {
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        } else {
            res = "{";
            int i = 0;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                String temp = "\"ctype" + i + "\": \"" + courseMain.getCtype() + "\", ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/coursesearch")
    public void courseSearch(String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.courseSearch(keyword);
        if (courseMains.size() == 0) {
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        } else {
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseMain courseMain : courseMains) {
                UserMain userMain = userMService.getByUser(courseMain.getUsername());
                res += "\"" + i + "\": ";
                String temp = "{\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                        "\"id\": \"" + courseMain.getId() + "\", " +
                        "\"name\": \"" + courseMain.getName() + "\", " +
                        "\"jcount\": \"" + courseMain.getJcount() + "\", " +
                        "\"nickname\": \"" + userMain.getNickname() + "\", " +
                        "\"price\": \"" + courseMain.getPrice() + "\", " +
                        "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                        "\"descript\": \"" + courseMain.getDescript() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定的课程的数据
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursebyid")
    public void getCourseById(Integer id, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        org.framework.tutor.domain.CourseMain courseMain = courseMService.getCourseById(id);
        UserMain userMain = userMService.getByUser(courseMain.getUsername());
        res = "{\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                "\"id\": \"" + courseMain.getId() + "\", " +
                "\"stype\": \"" + courseMain.getStype() + "\", " +
                "\"ctype\": \"" + courseMain.getCtype() + "\", " +
                "\"name\": \"" + courseMain.getName() + "\", " +
                "\"jcount\": \"" + courseMain.getJcount() + "\", " +
                "\"nickname\": \"" + userMain.getNickname() + "\", " +
                "\"info\": \"" + userMain.getInfo() + "\", " +
                "\"price\": \"" + courseMain.getPrice() + "\", " +
                "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                "\"total\": \"" + courseMain.getTotal() + "\", " +
                "\"descript\": \"" + courseMain.getDescript() + "\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取所搜索的课程数量，便于实现分页
     * @param stype
     * @param ctype
     * @param status
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursecount")
    public void getCourseCount(Integer stype, String ctype, Integer status, String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        Integer total = 0;
        //默认，不限课程类别进行排序
        if(ctype.equals("all")){
            //未指定主类别
            if(stype == -1){
                //不是通过用户搜素
                if(status == null || status == 0){
                    total = courseMService.getCourseCount();
                }
                else{
                    total = courseMService.getCourseCountK(keyword);
                }
            }
            else{
                if(status == null || status == 0){
                    total = courseMService.getCourseCountS(stype);
                }
                else{
                    total = courseMService.getCourseCountSK(stype, keyword);
                }
            }
        }
        else{
            if(stype == -1){
                if(status == null || status == 0){
                    total = courseMService.getCourseCountC(ctype);
                }
                else{
                    total = courseMService.getCourseCountCK(ctype, keyword);
                }
            }
            else{
                if(status == null || status == 0){
                    total = courseMService.getCourseCountCS(ctype, stype);
                }
                else{
                    total = courseMService.getCourseCountCSK(ctype, stype, keyword);
                }
            }
        }

        res = "{\"total\": \""+total+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
