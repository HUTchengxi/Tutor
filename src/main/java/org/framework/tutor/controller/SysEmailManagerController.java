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
     * @Description 发送邮件
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/sendemail")
    public String sendEmail(@RequestBody EmailParam emailParam) throws IOException, MessagingException {

        return sysEmailManagerApi.sendEmail(emailParam);
    }

    /**
     * @Description 保存邮件
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/saveemail")
    public String saveEmail(@RequestBody EmailParam emailParam) throws IOException, MessagingException {

        return sysEmailManagerApi.saveEmail(emailParam);
    }

    /**
     * @Description 获取邮箱列表
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getemaillist")
    public String getEmailList(@RequestBody EmailParam emailParam) throws IOException {

        return sysEmailManagerApi.getEmailList(emailParam);
    }

    /**
     * @Description 获取对应的邮件详情
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getemaildetail")
    public String getEmailDetail(@RequestParam Integer id) throws IOException {

        return sysEmailManagerApi.getEmailDetail(id);
    }

    /**
     * @Description 删除指定邮件
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/deleteemail")
    public String deleteEmail(@RequestParam Integer id) throws IOException {

        return sysEmailManagerApi.deleteEmail(id);
    }

    /**  
     * @Description 获取指定修改的邮件数据
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getmodinfobyid")
    public String getModinfoById(@RequestParam Integer id) throws IOException {

        return sysEmailManagerApi.getModinfoById(id);
    }
}
