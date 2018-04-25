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
import org.framework.tutor.api.CourseSummaryApi;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.service.CourseSummaryService;
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
 * @author yinjimin
 * @Description: 课程概述
 * @date 2018年04月15日
 */
@RestController
@RequestMapping("/coursesummary_con")
public class CourseSummaryController {

    @Autowired
    private CourseSummaryApi courseSummaryApi;


    /**
     *
     * @Description 获取指定课程的课程概述
     * @param [cid, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/getcoursesummaryinfo")
    public void getCourseSummaryInfo(@RequestParam Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseSummaryApi.getCourseSummaryInfo(cid, request, response);
    }


    /**
     *
     * @Description 更新课程概述
     * @param [id, title, descript, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @PostMapping("/updatecoursesummary")
    public void updateCourseSummary(@RequestParam Integer id, String title, String descript, HttpServletResponse response) throws IOException {

        courseSummaryApi.updateCourseSummary(id, title, descript, response);
    }

}
