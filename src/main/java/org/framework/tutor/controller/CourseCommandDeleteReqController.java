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
import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.service.CourseCMService;
import org.framework.tutor.service.CourseCommandDeleteReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description: 评论删除待审
 * @date 2018年04月18日
 */
@RestController
@RequestMapping("/coursecommanddeletereq_con")
public class CourseCommandDeleteReqController {

    @Autowired
    private CourseCommandDeleteReqService courseCommandDeleteReqService;

    @Autowired
    private CourseCMService courseCMService;

    /**
     *
     * @Description 提交评论删除申请
     * @param [cid, info, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    @PostMapping("/addcommanddeletereq")
    @Transactional
    public void addCommandDeleteReq(@RequestParam Integer cid, @RequestParam String info, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断评论的课程username是否所属相同
        CourseCommand courseCommand = courseCMService.getCommandById(cid);
        if(courseCommand == null || !courseCommand.getUsername().equals(username)){
            resultMap.put("status", "invalid");
        }else{
            //提交申请，不管有没有都是添加，不会update
            courseCommandDeleteReqService.addCommandDeleteReq(username, cid, info);

            //对应的评论状态更新
            Integer status = 1;
            courseCMService.updateCommandStatus(cid,status);
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
