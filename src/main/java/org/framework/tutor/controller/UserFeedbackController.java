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
import org.framework.tutor.api.UserFeedbackApi;
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
@RequestMapping("/userfeedback_con")
public class UserFeedbackController {

    @Autowired
    private UserFeedbackApi userFeedbackApi;

    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmyfeedback.json")
    public String getMyFeedback(HttpServletRequest request){
        return userFeedbackApi.getMyFeedback(request);
    }

    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/removemyfeedback.json")
    public String removeMyFeedback(@RequestParam Integer id, HttpServletRequest request){
        return userFeedbackApi.removeMyFeedback(id, request);
    }

    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/savemyfeedback.json")
    public String saveMyFeedback(@RequestParam String info, HttpServletRequest request){
        return userFeedbackApi.saveMyFeedback(info, request);
    }

    @RequireAuth(ident = "admin", type = "api")
    @RequestMapping("/getuserfeedback.json")
    public String getUserFeedback(@RequestBody ParamMap paramMap){
        return userFeedbackApi.getUserFeedback(paramMap);
    }

    @RequireAuth(ident = "admin", type = "api")
    @RequestMapping("/removeuserfeedback.json")
    public String removeUserFeedback(@RequestParam Integer id){
        return userFeedbackApi.removeUserFeedback(id);
    }

    @RequireAuth(ident = "admin", type = "api")
    @RequestMapping("/moduserfeedbackstatus.json")
    public String modUserFeedbackStatus(@RequestBody ParamMap paramMap){
        return userFeedbackApi.modUserFeedbackStatus(paramMap);
    }
}
