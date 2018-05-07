package org.framework.tutor.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseMainApi;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程表控制类
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/coursemain_con")
public class CourseMainController {

    @Autowired
    private CourseMainApi courseMainApi;

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

        courseMainApi.getCourseList(stype, ctype, sort, startpos, status, keyword, request, response);
    }

    /**
     * @param [response]
     * @return void
     * @Description 获取所有科目类别
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/getallcoursetype")
    public void getAllCourseType(HttpServletResponse response) throws IOException {

        courseMainApi.getAllCourseType(response);
    }

    /**
     * 获取指定主类别的所有科目类别
     *
     * @param stype
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursetype")
    public void getCourseType(String stype, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseMainApi.getCourseType(stype, request, response);
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

        courseMainApi.courseSearch(keyword, request, response);
    }

    /**
     * 获取指定的课程的数据
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursebyid")
    public void getCourseById(Integer id, HttpServletResponse response) throws IOException {

        courseMainApi.getCourseById(id, response);
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
    @RequestMapping("/getcoursecount")
    public void getCourseCount(Integer stype, String ctype, Integer status, String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseMainApi.getCourseCount(stype, ctype, status, keyword, request, response);
    }

    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前家教的所有发布数据
     * @author yinjimin
     * @date 2018/4/14
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getmypublish")
    public void getMyPublish(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseMainApi.getMyPublish(request, response);
    }

    /**
     * @param [name, stype, ctype, imgsrc, total, jcount, price, sumTitle1, sumTitle2, sumTitle3,
     *               sumDescript1, sumDescript2, sumDescript3, chapTitle, chaDescript, request, response]
     * @return void
     * @Description 发布课程
     * @author yinjimin
     * @date 2018/4/15
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/publishnewcourse")
    public void publishNewCourse(@RequestParam String name, @RequestParam Integer stype, @RequestParam String ctype, @RequestParam String descript,
                                 MultipartFile imgsrc, @RequestParam Integer total, @RequestParam Integer jcount,
                                 @RequestParam Double price, @RequestParam String sumTitle1, @RequestParam String sumTitle2,
                                 @RequestParam String sumTitle3, @RequestParam String sumDescript1, @RequestParam String sumDescript2,
                                 @RequestParam String sumDescript3, @RequestParam String chapTitle, @RequestParam String chapDescript,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseMainApi.publishNewCourse(name, stype, ctype, descript, imgsrc, total, jcount, price, sumTitle1, sumTitle2, sumTitle3, sumDescript1, sumDescript2, sumDescript3, chapTitle, chapDescript, request, response);
    }


    /**
     *
     * @Description 获取课程概述
     * @param
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/25
     */
    @PostMapping("/getcoursesummary")
    public @ResponseBody String getCourseSummary(@RequestParam Integer cid) {

        return courseMainApi.getCourseSummary(cid);
    }
}
