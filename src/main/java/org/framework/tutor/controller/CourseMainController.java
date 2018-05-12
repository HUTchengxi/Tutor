package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseMainApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 课程表控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursemain_con")
public class CourseMainController {

    @Autowired
    private CourseMainApi courseMainApi;

    /**
     * 加载课程数据
     */
    @RequestMapping("/getcourselist")
    public String getCourseList(Integer stype, String ctype, String sort, Integer startpos, Integer status, String keyword) throws IOException {

        return courseMainApi.getCourseList(stype, ctype, sort, startpos, status, keyword);
    }

    /**
     * @Description 获取所有科目类别
     */
    @PostMapping("/getallcoursetype")
    public String getAllCourseType() throws IOException {

        return courseMainApi.getAllCourseType();
    }

    /**
     * 获取指定主类别的所有科目类别
     */
    @RequestMapping("/getcoursetype")
    public String getCourseType(String stype) throws IOException {

        return courseMainApi.getCourseType(stype);
    }

    /**
     * 关键字获取所有对应的课程数据
     */
    @RequestMapping("/coursesearch")
    public String courseSearch(String keyword) throws IOException {

        return courseMainApi.courseSearch(keyword);
    }

    /**
     * 获取指定的课程的数据
     */
    @RequestMapping("/getcoursebyid")
    public String getCourseById(Integer id) throws IOException {

        return courseMainApi.getCourseById(id);
    }

    /**
     * 获取所搜索的课程数量，便于实现分页
     */
    @RequestMapping("/getcoursecount")
    public String getCourseCount(Integer stype, String ctype, Integer status, String keyword) throws IOException {

        return courseMainApi.getCourseCount(stype, ctype, status, keyword);
    }

    /**
     * @Description 获取当前家教的所有发布数据
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getmypublish")
    public String getMyPublish() throws IOException {

        return courseMainApi.getMyPublish();
    }

    /**
     * @Description 发布课程
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/publishnewcourse")
    public String publishNewCourse(@RequestParam String name, @RequestParam Integer stype, @RequestParam String ctype, @RequestParam String descript,
                                 MultipartFile imgsrc, @RequestParam Integer total, @RequestParam Integer jcount, @RequestParam Double price,
                                   @RequestParam String sumTitle1, @RequestParam String sumTitle2, @RequestParam String sumTitle3, @RequestParam String sumDescript1,
                                   @RequestParam String sumDescript2, @RequestParam String sumDescript3, @RequestParam String chapTitle,
                                   @RequestParam String chapDescript) throws IOException {

        return courseMainApi.publishNewCourse(name, stype, ctype, descript, imgsrc, total, jcount, price, sumTitle1, sumTitle2, sumTitle3, sumDescript1, sumDescript2, sumDescript3, chapTitle, chapDescript);
    }


    /**
     *
     * @Description 获取课程概述
     */
    @PostMapping("/getcoursesummary")
    public String getCourseSummary(@RequestParam Integer cid) {

        return courseMainApi.getCourseSummary(cid);
    }
}
