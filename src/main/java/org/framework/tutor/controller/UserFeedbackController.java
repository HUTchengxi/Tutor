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
 * @Description: 用户反馈
 * @date 2018年05月09日
 */
@RestController
@RequestMapping("/userfeedback_con")
public class UserFeedbackController {

    @Autowired
    private UserFeedbackApi userFeedbackApi;

    /**
     * @Description 获取用户所有反馈数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmyfeedback.json")
    public String getMyFeedback(){
        return userFeedbackApi.getMyFeedback();
    }

    /**
     * @Description 删除当前用户的指定反馈数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/removemyfeedback.json")
    public String removeMyFeedback(@RequestParam Integer id){
        return userFeedbackApi.removeMyFeedback(id);
    }

    /**
     * @Description 新增用户反馈
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/savemyfeedback.json")
    public String saveMyFeedback(@RequestParam String info){
        return userFeedbackApi.saveMyFeedback(info);
    }

    /**  
     * @Description 获取所有用户的反馈数据（admin)
     */
    @RequireAuth(ident = "admin", type = "api")
    @RequestMapping("/getuserfeedback.json")
    public String getUserFeedback(@RequestBody ParamMap paramMap){
        return userFeedbackApi.getUserFeedback(paramMap);
    }

    /**
     * @Description 删除用户的反馈数据
     */
    @RequireAuth(ident = "admin", type = "api")
    @RequestMapping("/removeuserfeedback.json")
    public String removeUserFeedback(@RequestParam Integer id){
        return userFeedbackApi.removeUserFeedback(id);
    }

    /**
     * @Description 更新用户反馈的处理状态
     */
    @RequireAuth(ident = "admin", type = "api")
    @RequestMapping("/moduserfeedbackstatus.json")
    public String modUserFeedbackStatus(@RequestBody ParamMap paramMap){
        return userFeedbackApi.modUserFeedbackStatus(paramMap);
    }
}
