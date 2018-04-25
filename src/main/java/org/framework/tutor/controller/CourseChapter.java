package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.api.CourseChapterApi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseChService;
import org.framework.tutor.service.CourseMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 课程章节目录控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursechapter_con")
public class CourseChapter {

    @Autowired
    private CourseChapterApi courseChapterApi;

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @param response
     */
    @RequestMapping("/getcoursechapter")
    public void getCourseChapter(Integer cid, HttpServletResponse response) throws IOException {

        courseChapterApi.getCourseChapter(cid, response);
    }

    /**
     *
     * @Description 删除指定目录
     * @param [id, response, request]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/deletechapter")
    public void deleteChapter(Integer id, HttpServletResponse response, HttpServletRequest request) throws IOException {

        courseChapterApi.deleteChapter(id, response, request);
    }

    /**
     *
     * @Description 更新目录
     * @param [id, title, descript, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/modchapter")
    public void modChapter(Integer id, @RequestParam Integer cid, @RequestParam String title, @RequestParam String descript, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseChapterApi.modChapter(id, cid, title, descript, request, response);
    }
}
