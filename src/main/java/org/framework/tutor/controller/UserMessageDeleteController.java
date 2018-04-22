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
import org.framework.tutor.service.UserMessageDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description:
 * @date 2018年04月22日
 */
@RestController
@RequestMapping("/usermessagedelete_con")
public class UserMessageDeleteController {

    @Autowired
    private UserMessageDeleteService userMessageDeleteService;

    /**
     *
     * @Description 删除通知
     * @param [did, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/22
     */
    @RequestMapping("/deletemymessage")
    public void deleteMyMessage(@RequestParam Integer did, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        Integer row = userMessageDeleteService.addDelete(did, username);
        if(row == 1){
            resultMap.put("status", "valid");
        }else{
            resultMap.put("status", "invalid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
