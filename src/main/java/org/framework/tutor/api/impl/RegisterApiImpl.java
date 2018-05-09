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
import org.framework.tutor.api.RegisterApi;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.service.UserValiService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class RegisterApiImpl implements RegisterApi {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private UserValiService userValiService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 校验注册的用户名是否已经存在
     *
     * @param response
     * @param username
     */
    @Override
    public void checkExistUser(HttpServletResponse response, String username) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (userMainService.userExist(username)) {
            resultMap.put("status", "invalid");
        } else {
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 进行注册
     *
     * @param request
     * @param response
     * @param username
     * @param password
     * @param checktype
     * @param telephone
     * @param phonecode
     * @param email
     */
    @Override
    public void registerNoCheck(HttpServletRequest request, HttpServletResponse response, String username, String password,
                                String checktype, String telephone, String email, String phonecode) throws IOException, MessagingException, NoSuchAlgorithmException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        //判断用户名是否已经被注册
        UserMain userMain = userMainService.getByUser(username);
        //非法调用api
        if(userMain != null){
            resultMap.put("status", "invalid");
        }
        else {
            String nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-", "")
                    .substring(0, 8);
            //验证nickname的唯一性
            while (userMainService.NickExist(nickname)) {
                nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-", "")
                        .substring(0, 8);
            }

            //随机获取加密盐
            Integer salt = CommonUtil.getMd5Salt();

            //密码加盐
            password = CommonUtil.getMd5Pass(password, salt);

            //游客身份注册
            if ("none".equals(checktype)) {
                Integer identity = -2;
                //进行游客注册
                if (userMainService.registerNoCheck(identity, username, password, nickname, salt)) {
                    resultMap.put("status", "valid");
                    resultMap.put("url", "/forward_con/gologin");
                } else {
                    resultMap.put("status", "invalid");
                    resultMap.put("url", "#");
                }
            }
            //手机号码注册
            else if ("telephone".equals(checktype)) {

                //判断手机号码和验证短码是否对应
                String realcode = (String) session.getAttribute("bindcode");
                String realphone = (String) session.getAttribute("bindphone");
                if(realcode != null && realcode.equals(phonecode) && realphone != null & realphone.equals(telephone)){

                    //进行注册
                    Integer identity = 0;
                    if(userMainService.registerByPhone(identity, username, password, nickname, telephone)){
                        resultMap.put("status", "valid");
                        resultMap.put("url", "/forward_con/gologin");
                    }
                    else{
                        resultMap.put("status", "mysqlerr");
                    }
                }
                else{
                    resultMap.put("status", "codeerr");
                }

            }
            //邮箱注册
            else if ("email".equals(checktype)) {
                //邮箱已被注册
                if (userMainService.emailExist(email)) {
                    resultMap.put("status", "exist");
                } else {
                    Integer identity = -2;

                    if (userMainService.registerByEmail(identity, username, password, nickname, email)) {

                        //发送邮箱验证码
                        String valicode = CommonUtil.getUUID();
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setFrom(from);
                        message.setTo(email);
                        message.setSubject("勤成家教网---邮箱验证链接");
                        message.setText("点击链接<a href='http://localhost:8080/forward_con/register_check?username=" + username + "&valicode=" + valicode + "'>http://localhost:8080/register_con/register_check</a>进行验证");
                        javaMailSender.send(message);

                        //保存邮箱验证码
                        Integer status = 0;
                        userValiService.addUserVali(username, valicode, status);

                        //保存用户邮箱以便后续刷新状态
                        request.getSession().setAttribute("email", email + " " + username);

                        resultMap.put("status", "valid");
                        resultMap.put("url", "forward_con/register_info");
                    } else {
                        resultMap.put("status", "mysqlerr");
                    }
                }
            }
            //非法调用api
            else {
                resultMap.put("status", "invalid");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 重发邮件
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void emailResend(HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (email == null) {
            resultMap.put("status", "invalid");
        } else {
            String realemail = email.split(" ")[0];
            String realusername = email.split(" ")[1];
            //发送邮箱验证码
            String valicode = CommonUtil.getUUID();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(realemail);
            message.setSubject("勤成家教网---邮箱验证链接");
            message.setText("点击链接<a href='http://localhost:8080/forward_con/register_check?username=" + realusername + "&valicode=" + valicode + "'>http://localhost:8080/register_con/register_check</a>进行验证");
            javaMailSender.send(message);

            //更新邮箱注册码
            userValiService.updateEmailCode(realusername, valicode);
            resultMap.put("status", "sendok");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
