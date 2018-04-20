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
import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.service.SysEmailManageService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月20日
 */
@RestController
@RequestMapping("/sysemailmanage_con")
public class SysEmailManagerController {

    @Autowired
    private SysEmailManageService sysEmailManageService;

    @Autowired
    private UserMService userMService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     *
     * @Description 发送邮件
     * @param [emailParam, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    @RequestMapping("/sendemail")
    public void sendEmail(@RequestBody EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        //获取对应用户的address
        UserMain userMain = userMService.getByUser(emailParam.getSend());
        Map<String, Object> resultMap = new HashMap<>(1);
        if(userMain == null){
            resultMap.put("status", "noaddress");
        }
        else {
            Integer status = 1;
            String address = userMain.getEmail();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(address);
            messageHelper.setSubject(emailParam.getTheme());
            messageHelper.setText(emailParam.getFormatEmail(), true);
            javaMailSender.send(mimeMessage);
            sysEmailManageService.sendEmail(emailParam.getSend(), address, emailParam.getTheme(), emailParam.getEmail(), status);
            resultMap.put("status", "sendok");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
