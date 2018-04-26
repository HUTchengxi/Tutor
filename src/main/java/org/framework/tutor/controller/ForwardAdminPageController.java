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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yinjimin
 * @Description: 管理员登录之后才能访问到的所有api
 * @date 2018年04月25日
 */
@Controller
@RequestMapping("/adminpage_con")
public class ForwardAdminPageController {

    /**
     * 进入管理后台
     * @param request
     * @return
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/gosystutormain")
    public String goSysconfig(){
        return "/sysconfig/tutor/sysmain";
    }

    /**
     *
     * @Description 管理员登录页面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/19
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/adminloginpage")
    public String adminLoginPage(){

        return "/sysconfig/admin/adminlogin";
    }

    /**
     *
     * @Description 进入管理后台界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/19
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/gosysadminmain")
    public String goSysadminMain(){

        return "/sysconfig/admin/main";
    }

    /**
     *
     * @Description 订单异常管理界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/19
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/gosysordermanage")
    public String goSysOrderManage(){

        return "/sysconfig/admin/ordermanage";
    }

    /**
     *
     * @Description 发送邮件界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/sendmailpage")
    public String goSendMailPage(){

        return "sysconfig/admin/sendmail";
    }

    /**
     *
     * @Description 发送邮件界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/sendmessagepage")
    public String goSendMessagePage(){

        return "sysconfig/admin/sendmessage";
    }

    /**
     *
     * @Description 邮件管理界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/gosysadminemailmanage")
    public String goEmailManagePage(){

        return "sysconfig/admin/emailmanage";
    }

    /**
     *
     * @Description 进入课程下线申请管理界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/21
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/gosyscoursedeletemanage")
    public String goCourseDeleteManage(){

        return "sysconfig/admin/coursedeletemanage";
    }

    /**
     *
     * @Description进入管理员消息管理界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/22
     */
    @RequireAuth(ident = "admin")
    @RequestMapping("/gosysadminmessagemanage")
    public String goAdminMessageManage(){

        return "sysconfig/admin/messagemanage";
    }
}
