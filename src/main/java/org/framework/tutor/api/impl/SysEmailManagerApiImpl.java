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
package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.SysEmailManagerApi;
import org.framework.tutor.domain.SysEmailManage;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.service.SysEmailManageService;
import org.framework.tutor.service.UserMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class SysEmailManagerApiImpl implements SysEmailManagerApi {

    @Autowired
    private SysEmailManageService sysEmailManageService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * @param [emailParam, request, response]
     * @return void
     * @Description 发送邮件
     * @author yinjimin
     * @date 2018/4/20
     */
    @Override
    public void sendEmail(EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        //获取对应用户的address
        UserMain userMain = userMainService.getByUser(emailParam.getSend());
        Map<String, Object> resultMap = new HashMap<>(1);
        if (userMain == null) {
            resultMap.put("status", "noaddress");
        } else {
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

    /**
     * @param [emailParam, request, response]
     * @return void
     * @Description 发送邮件
     * @author yinjimin
     * @date 2018/4/20
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveEmail(EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        //获取对应用户的address
        UserMain userMain = userMainService.getByUser(emailParam.getSend());
        Map<String, Object> resultMap = new HashMap<>(1);
        if (userMain == null) {
            resultMap.put("status", "noaddress");
        } else {
            //判断是否存在id
            Integer emailId = emailParam.getId();
            SysEmailManage sysEmailManageById = null;
            if (emailId != null) {
                sysEmailManageById = sysEmailManageService.getById(emailId);
            }
            //新增
            if (sysEmailManageById == null) {
                Integer status = 0;
                String address = userMain.getEmail();
                //获取最新保存的id
                sysEmailManageService.sendEmail(emailParam.getSend(), address, emailParam.getTheme(), emailParam.getEmail(), status);
                Integer lastId = sysEmailManageService.getLastId();
                resultMap.put("emailId",lastId);
            }
            //更新
            else{
                sysEmailManageService.updateEmail(emailId, emailParam.getTheme(), emailParam.getEmail());
            }
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 获取邮箱列表
     * @param [emailParam, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    @Override
    public void getEmailList(EmailParam emailParam, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        Integer emailStatus = emailParam.getEmailStatus();
        Integer pageNo = emailParam.getPageNo();
        Integer pageSize = emailParam.getPageSize();
        Integer offset = pageNo * pageSize;
        List<Object> rowList = new ArrayList<>(pageSize);
        Map<String, Object> resultMap = new HashMap<>(2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取所有的email
        List<SysEmailManage> sysEmailManageList = null;
        Integer total = 0;
        if(emailStatus == -1){
            sysEmailManageList = sysEmailManageService.getAllEmailListLimit(offset, pageSize);
            total = sysEmailManageService.getAllEmailCount();
        }else{
            sysEmailManageList = sysEmailManageService.getEmailListByStatusLimit(emailStatus, offset, pageSize);
            total = sysEmailManageService.getEmailCountByStatus(emailStatus);
        }
        if(sysEmailManageList == null || sysEmailManageList.size() == 0){
            resultMap.put("rows", rowList);
            resultMap.put("total", 0);
        }else{
            for(SysEmailManage sysEmailManage: sysEmailManageList){
                Map<String, Object> rowMap = new HashMap<>(4);
                rowMap.put("emailTheme", sysEmailManage.getTheme());
                rowMap.put("emailStatus", sysEmailManage.getStatus());
                rowMap.put("updateTime", simpleDateFormat.format(sysEmailManage.getSendtime()));
                rowMap.put("emailId", sysEmailManage.getId());
                rowList.add(rowMap);
            }
            resultMap.put("rows", rowList);
            resultMap.put("total", total);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 获取对应的邮件详情
     * @param [id, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    @Override
    public void getEmailDetail(Integer id, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        PrintWriter writer = response.getWriter();

        SysEmailManage sysEmailManage = sysEmailManageService.getById(id);
        if(sysEmailManage == null){
            resultMap.put("status", "none");
        }else{
            String status = sysEmailManage.getStatus() == 1?"已发送":"草稿";
            resultMap.put("sendto", sysEmailManage.getSendto());
            resultMap.put("theme", sysEmailManage.getTheme());
            resultMap.put("email", sysEmailManage.getEmail());
            resultMap.put("status", status);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 删除指定邮件
     * @param [id, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    @Override
    public void deleteEmail(Integer id, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        SysEmailManage sysEmailManage = sysEmailManageService.getById(id);
        if(sysEmailManage == null){
            resultMap.put("status", "invalid");
        }else {
            sysEmailManageService.deleteEmial(id);
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    @Override
    public void getModinfoById(Integer id, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        PrintWriter writer = response.getWriter();

        SysEmailManage sysEmailManage = sysEmailManageService.getById(id);
        if(sysEmailManage == null){
            resultMap.put("status", "invalid");
        }else{
            resultMap.put("status", "valid");
            resultMap.put("username", sysEmailManage.getSendto());
            resultMap.put("theme", sysEmailManage.getTheme());
            resultMap.put("email", sysEmailManage.getEmail());
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
