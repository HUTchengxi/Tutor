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
import org.framework.tutor.api.SysEmailManagerApi;
import org.framework.tutor.entity.EmailParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月20日
 */
@RestController
@RequestMapping("/sysemailmanage_con")
public class SysEmailManagerController {

    @Autowired
    private SysEmailManagerApi sysEmailManagerApi;

    /**
     * @param [emailParam, request, response]
     * @return void
     * @Description 发送邮件
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/sendemail")
    public void sendEmail(@RequestBody EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        sysEmailManagerApi.sendEmail(emailParam, request, response);
    }

    /**
     * @param [emailParam, request, response]
     * @return void
     * @Description 保存邮件
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/saveemail")
    public void saveEmail(@RequestBody EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        sysEmailManagerApi.saveEmail(emailParam, request, response);
    }

    /**
     *
     * @Description 获取邮箱列表
     * @param [emailParam, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getemaillist")
    public void getEmailList(@RequestBody EmailParam emailParam, HttpServletResponse response) throws IOException {

        sysEmailManagerApi.getEmailList(emailParam, response);
    }

    /**
     *
     * @Description 获取对应的邮件详情
     * @param [id, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getemaildetail")
    public void getEmailDetail(@RequestParam Integer id, HttpServletResponse response) throws IOException {

        sysEmailManagerApi.getEmailDetail(id, response);
    }

    /**
     *
     * @Description 删除指定邮件
     * @param [id, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/deleteemail")
    public void deleteEmail(@RequestParam Integer id, HttpServletResponse response) throws IOException {

        sysEmailManagerApi.deleteEmail(id, response);
    }

    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getmodinfobyid")
    public void getModinfoById(@RequestParam Integer id, HttpServletResponse response) throws IOException {

        sysEmailManagerApi.getModinfoById(id, response);
    }
}
