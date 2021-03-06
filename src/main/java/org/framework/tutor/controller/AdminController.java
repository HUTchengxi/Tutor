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
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMService;
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
import java.util.HashMap;
import java.util.Map;


/**
 * @author yinjimin
 * @Description: 管理控制类
 * @date 2018年04月19日
 */
@RestController
@RequestMapping("/admin_con")
public class AdminController {

    @Autowired
    private UserMService userMService;

    /**
     *
     * @Description 管理员登录
     * @param [username, password, remember, request]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @PostMapping("login.json")
    public void Login(@RequestParam String username, @RequestParam String password, HttpServletResponse response, HttpServletRequest request) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        HttpSession session = request.getSession();

        UserMain userMain = userMService.checkAdminLogin(username, password);
        if(userMain == null){
            resultMap.put("status", "error");
        }else{
            session.setAttribute("username", userMain.getUsername());
            session.setAttribute("nickname", userMain.getNickname());
            session.setAttribute("identity", userMain.getIdentity());
            resultMap.put("status", "success");
            resultMap.put("url", "/forward_con/gosysadminmain");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
