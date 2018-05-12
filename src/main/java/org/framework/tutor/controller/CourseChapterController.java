package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseChapterApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 课程章节目录控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursechapter_con")
public class CourseChapterController {

    @Autowired
    private CourseChapterApi courseChapterApi;

    /**
     * @Description 获取指定课程的章节目录数据
     */
    @RequestMapping("/getcoursechapter")
    public String getCourseChapter(Integer cid) throws IOException {

        return courseChapterApi.getCourseChapter(cid);
    }

    /**
     * @Description 删除指定目录
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/deletechapter")
    public String deleteChapter(Integer id) throws IOException {

        return courseChapterApi.deleteChapter(id);
    }

    /**
     * @Description 更新目录
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/modchapter")
    public String modChapter(Integer id, @RequestParam Integer cid, @RequestParam String title, @RequestParam String descript) throws IOException {

        return courseChapterApi.modChapter(id, cid, title, descript);
    }
}
