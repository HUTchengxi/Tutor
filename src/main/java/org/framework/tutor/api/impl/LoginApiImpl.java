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
import com.google.gson.JsonParser;
import org.framework.tutor.api.LoginApi;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.util.CommonUtil;
import org.framework.tutor.util.LoginQueueUtil;
import org.framework.tutor.util.ScheduledUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class LoginApiImpl implements LoginApi{

    @Autowired
    private UserMService userMService;

    /**
     * 用户进行登陆
     * @param request
     * @param response
     * @param username
     * @param password
     * @param remember
     * @throws IOException
     */
    @Override
    public void login(HttpServletRequest request, HttpServletResponse response,
                      String username, String password, Integer remember) throws IOException, NoSuchAlgorithmException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        //用户名不存在
        if(!userMService.userExist(username)){
            resultMap.put("status", "nouser");
            resultMap.put("url", "#");
        }
        //密码错误
        else {
            //获取密码盐
            Integer salt = userMService.getByUser(username).getSalt();
            //明文传来的密码加盐判断
            String MD5Pass = CommonUtil.getMd5Pass(password, salt);

            if (!userMService.passCheck(username, MD5Pass)) {

                resultMap.put("status", "passerr");
                resultMap.put("url", "#");
            }
            //登陆成功
            else {
                //保存当前登陆状态（以后考虑使用shiro）
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                //保存昵称和当前用户身份
                UserMain userMain = userMService.getByUser(username);
                session.setAttribute("nickname", userMain.getNickname());
                session.setAttribute("identity", userMain.getIdentity());
                resultMap.put("status", "ok");
                resultMap.put("url", "/forward_con/welcome");

                //记住密码
                if (remember == 1) {
                    Cookie usercookie = new Cookie("username", username);
                    Cookie passcookie = new Cookie("password", password);
                    usercookie.setMaxAge(2 * 60 * 60 * 24);
                    passcookie.setMaxAge(2 * 60 * 60 * 24);
                    usercookie.setPath("/");
                    passcookie.setPath("/");
                    response.addCookie(usercookie);
                    response.addCookie(passcookie);
                }
                //清空之前记住的密码
                else {
                    Cookie[] cookies = request.getCookies();
                    for (Cookie cookie : cookies) {
                        if ("username".equals(cookie.getName())) {
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                        if ("password".equals(cookie.getName())) {
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                }

                //加入用户队列中
                LoginQueueUtil.addUser(username);

                //设置定时任务，进行轮询判断是否被挤下线
                Runnable oneLogin = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("测试我是否一直在执行");
                        //判断对应的outer队列是否有自己，有的话就退出登录
                        if(LoginQueueUtil.checkOuterExist(username)){
                            session.invalidate();
                            LoginQueueUtil.removeOuter(username);
                        }
                    }
                };
                ScheduledUtil.addTask(oneLogin);
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *  登录时判断是否已经记住密码并获取相关信息
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getRememberUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        String user = null;
        String pass = null;
        if(cookies == null || cookies.length <= 0){
            resultMap.put("status", "none");
        }
        else {
            for (int i = 0; i < cookies.length; i++) {
                if ("username".equals(cookies[i].getName())) {
                    user = cookies[i].getValue();
                }
                if ("password".equals(cookies[i].getName())) {
                    pass = cookies[i].getValue();
                }
            }

            if (user == null) {
                resultMap.put("status", "none");
            } else {
                resultMap.put("username", user);
                resultMap.put("password", pass);
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 返回当前用户登录状态信息
     * @param response
     */
    @Override
    public void loginStatusCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String nickname = (String) session.getAttribute("nickname");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(8);

        if(nickname == null){
            resultMap.put("status", "nologin");
            resultMap.put("nick", "null");
            resultMap.put("ident", "null");
        }
        else{
            Integer ident = (Integer) session.getAttribute("identity");
            resultMap.put("status", "login");
            resultMap.put("nick", nickname);
            resultMap.put("ident", ident);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void loginOff(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String nickname = (String) session.getAttribute("nickname");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        //非法获取api侵入
        if(nickname == null){
            resultMap.put("status", "invalid url");
        }
        else {
            //清楚session里的所有信息，并使session失效
            session.invalidate();
            resultMap.put("status", "logoff");

            //清除登录队列
            LoginQueueUtil.removeUser(username);
            LoginQueueUtil.removeOuter(username);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
