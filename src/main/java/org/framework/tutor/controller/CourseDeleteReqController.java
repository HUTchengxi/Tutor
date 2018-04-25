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

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.CourseDeleteReqApi;
import org.framework.tutor.domain.CourseDeleteReq;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrderManager;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseDeleteReqService;
import org.framework.tutor.service.CourseDeleteRespService;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description: 课程下线申请
 * @date 2018年04月15日
 */
@RestController
@RequestMapping("/coursedeletereq_con")
public class CourseDeleteReqController {

    @Autowired
    private CourseDeleteReqApi courseDeleteReqApi;

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

        courseDeleteReqApi.setMyCourseDeleteReq(cid, descript, request, response);
    }

    /**
     *
     * @Description 获取课程下线申请数据列表
     * @param [paramMap, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    @PostMapping("/getreqlist")
    public void getReqList(@RequestBody ParamMap paramMap, HttpServletResponse response) throws IOException {

        courseDeleteReqApi.getReqList(paramMap, response);
    }

    /**
     *
     * @Description 获取课程下线申请详情
     * @param [reqid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    @PostMapping("/getreqdetail")
    public void getReqDetail(@RequestParam Integer reqid, HttpServletResponse response) throws IOException {

        courseDeleteReqApi.getReqDetail(reqid, response);
    }
}
