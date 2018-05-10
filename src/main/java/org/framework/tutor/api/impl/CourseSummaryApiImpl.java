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
package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.CourseSummaryApi;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.service.CourseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseSummaryApiImpl implements CourseSummaryApi {

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
    //TODO：后续考虑使用redis
    @Override
    public void getCourseSummaryInfo(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        List<CourseSummary> courseSummarys = courseSummaryService.getCourseSummaryInfo(cid);

        if(courseSummarys.size() == 0){
            resultMap.put("status", 0);
        }
        else{
            for (CourseSummary courseSummary: courseSummarys) {
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("title", courseSummary.getTitle());
                rowMap.put("id", courseSummary.getId());
                rowMap.put("descript", courseSummary.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
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
    //TODO：后续考虑使用redis
    @Override
    public void updateCourseSummary(Integer id, String title, String descript, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = courseSummaryService.updateCourseSummary(id, title, descript);
        if(row == 1){
            resultMap.put("status", "valid");
        }
        else{
            resultMap.put("status", "sqlerr");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
