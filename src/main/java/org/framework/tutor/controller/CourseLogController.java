package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseLogAPi;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseLService;
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
import java.util.List;

/**
 * 课程记录控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/courselog_con")
public class CourseLogController {

    @Autowired
    private CourseLogAPi courseLogAPi;

    /**
     * 获取我的课程记录
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getlog")
    public void getLog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseLogAPi.getLog(request, response);
    }


    /**
     * 删除指定的课程记录
     * @param id
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/dellog")
    public void delLog(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseLogAPi.delLog(id, request, response);
    }
}
