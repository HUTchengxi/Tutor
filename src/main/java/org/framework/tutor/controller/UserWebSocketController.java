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
import org.framework.tutor.api.UserWebsocketApi;
import org.framework.tutor.entity.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年05月09日
 */
@RestController
@RequestMapping("/userwebsocket_con")
public class UserWebSocketController {

    @Autowired
    private UserWebsocketApi userWebsocketApi;

    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/loadmysocketlist.json")
    public String loadMySocketList(@RequestParam String reader, HttpServletRequest request){
        return userWebsocketApi.loadMySocketList(reader, request);
    }

    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/savewebsocket.json")
    public String saveWebsocket(@RequestBody ParamMap paramMap){
        return userWebsocketApi.saveWebSocket(paramMap);
    }
}
