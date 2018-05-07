package org.framework.tutor.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseCollectApi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseCService;
import org.framework.tutor.service.CourseMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 课程收藏控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursecollect_con")
public class CourseCollectController {

    @Autowired
    private CourseCollectApi courseCollectApi;

    /**
     * 获取我的课程收藏记录
     * @param request
     * @param response
     * @param startpos
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycollect")
    public void getMyCollect(HttpServletRequest request, HttpServletResponse response, Integer startpos) throws IOException {

        courseCollectApi.getMyCollect(request, response, startpos);
    }

    /**
     * 判断当前用户是否收藏了指定的课程
     * @param cid
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/checkusercollect")
    public void checkUserCollect(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCollectApi.checkUserCollect(cid, request, response);
    }

    /**
     * 收藏/取消收藏
     * @param cid
     * @param mod
     * @param descript
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/modusercollect")
    public void modUserCollect(Integer cid, String mod, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCollectApi.modUserCollect(cid, mod, descript, request, response);
    }

    /**
     * 获取家教的今日课程收藏数量
     * @param request
     * @param response
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getcollectcount")
    public void getCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCollectApi.getCollectCount(request, response);
    }
}
