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

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseOrderManagerApi;
import org.framework.tutor.entity.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 已购订单管理表
 * @author yinjimin
 * @date 2018年04月18日
 */
@RestController
@RequestMapping("/courseordermanager_con")
public class CourseOrderManagerController {

    @Autowired
    private CourseOrderManagerApi courseOrderManagerApi;

    /**
     * @param [paramMap, request, response]
     * @return void
     * @Description 获取指定家教课程订单列表
     * @author yinjimin
     * @date 2018/4/18
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getcourseorderlist")
    public void getCourseOrderList(@RequestBody ParamMap paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderManagerApi.getCourseOrderList(paramMap, request, response);
    }

    /**
     *
     * @Description 获取订单详情数据
     * @param [code, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getorderdetail")
    public void getOrderDetail(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderManagerApi.getOrderDetail(code, request, response);
    }

    /**
     *
     * @Description 更新家教处理状态
     * @param [tutorStatus, tutorInfo, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/updatetutorstatus")
    public void updateTutorStatus(@RequestParam String code, @RequestParam Integer tutorStatus, @RequestParam String tutorInfo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseOrderManagerApi.updateTutorStatus(code, tutorStatus, tutorInfo, request, response);
    }

    /**
     *
     * @Description 获取异常订单数据
     * @param [paramMap, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/geterrororderlist")
    public void getErrorOrderList(@RequestBody ParamMap paramMap, HttpServletResponse response) throws IOException {

        courseOrderManagerApi.getErrorOrderList(paramMap, response);
    }

    /**
     *
     * @Description 查看指定异常订单详情数据
     * @param [code, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/geterrororderdetail")
    public void getErrorOrderDetail(@RequestParam String code, HttpServletResponse response) throws IOException {

        courseOrderManagerApi.getErrorOrderDetail(code, response);
    }
}
