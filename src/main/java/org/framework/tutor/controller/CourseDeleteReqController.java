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
package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseDeleteReq;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseDeleteReqService;
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

/**
 * @author yinjimin
 * @Description: 课程下线申请
 * @date 2018年04月15日
 */
@RestController
@RequestMapping("/coursedeletereq_con")
public class CourseDeleteReqController {

    @Autowired
    private CourseDeleteReqService courseDeleteReqService;

    @Autowired
    private CourseMService courseMService;

    /**
     *
     * @Description 提交课程下线申请
     * @param [cid, descript, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/setmycoursedeletereq")
    public void setMyCourseDeleteReq(@RequestParam Integer cid, @RequestParam String descript, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断课程是否为当前登录用户的
        CourseMain courseMain = courseMService.getCourseById(cid);
        if(courseMain == null || !courseMain.getUsername().equals(username)){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            //判断该课程是否已经提交
            CourseDeleteReq courseDeleteReq = courseDeleteReqService.getByCid(cid);
            if(courseDeleteReq == null){
                courseDeleteReqService.addCourseDeleteReq(cid, username, descript);
            }
            else{
                courseDeleteReqService.updateCourseDeleteReq(cid, descript);
            }
            res = "{\"status\": \"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
