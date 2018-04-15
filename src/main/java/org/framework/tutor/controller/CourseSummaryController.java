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
    private CourseSummaryService courseSummaryService;


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

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        List<CourseSummary> courseSummarys = courseSummaryService.getCourseSummaryInfo(cid);

        if(courseSummarys.size() == 0){
            res = "{\"status\": \"0\"}";
        }
        else{
            res = "{";
            int i = 1;
            for (CourseSummary courseSummary: courseSummarys) {
                res += "\"" + i + "\": ";
                String temp = "{\"title\": \"" + courseSummary.getTitle() + "\", " +
                        "\"id\": \"" + courseSummary.getId() + "\", " +
                        "\"descript\": \"" + courseSummary.getDescript() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
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

        PrintWriter writer = response.getWriter();
        String res = null;

        Integer row = courseSummaryService.updateCourseSummary(id, title, descript);
        if(row == 1){
            res = "{\"status\": \"valid\"}";
        }
        else{
            res = "{\"status\": \"sqlerr\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

}
