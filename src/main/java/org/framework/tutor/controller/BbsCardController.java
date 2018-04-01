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
import org.framework.tutor.service.BbsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 论坛帖子控制类
 * @author yinjimin
 * @date 2018年03月31日
 */
@RestController
@RequestMapping("/bbscard_con")
public class BbsCardController {

    @Autowired
    private BbsCardService bbsCardService;


    /**
     *
     * @Description 获取当前登录用户的帖子发表总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/3/31
     */
    @RequestMapping("/getmycardcount")
    public void getMyCardCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;
        PrintWriter writer = response.getWriter();

        Integer count = bbsCardService.getMyCardCount(username);

        res = "{\"count\": \""+count+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
