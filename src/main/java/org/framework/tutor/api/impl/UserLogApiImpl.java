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

import com.google.gson.JsonParser;
import org.framework.tutor.api.UserLogApi;
import org.framework.tutor.domain.UserLog;
import org.framework.tutor.service.UserLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class UserLogApiImpl implements UserLogApi {

    @Autowired
    private UserLService userLService;

    /**
     * 保存用户登录记录
     * @param logcity
     * @param ip
     * @param logsystem
     * @param request
     * @param response
     */
    @Override
    public void loginLog(String logcity, String ip, String logsystem, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;
        String username = (String) session.getAttribute("username");

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            if(userLService.saveUserlog(username, logcity, ip, logsystem)){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取用户的登录记录
     * @param request
     * @param response
     */
    @Override
    public void getUserlog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"forward_con/welcome\"}";
        }
        else{
            //获取登录记录
            List<UserLog> userLogs  = userLService.getUserlog(username);
            if(userLogs.size() == 0){
                res = "{\"status\": \"ok\", \"len\": \"0\"}";
            }
            else {
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserLog userLog : userLogs) {
                    res += "\""+i+"\": ";
                    String temp = "{\"logtime\": \""+simpleDateFormat.format(userLog.getLogtime())+"\", " +
                            "\"logcity\": \""+userLog.getLogcity()+"\", " +
                            "\"logip\": \""+userLog.getLogip()+"\", " +
                            "\"logsystem\": \""+userLog.getLogsys()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
