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
public class UserLogApiImpl implements UserLogApi {

    @Autowired
    private UserLService userLService;

    /**
     * 保存用户登录记录
     *
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
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        if (userLService.saveUserlog(username, logcity, ip, logsystem)) {
            resultMap.put("status", "valid");
        } else {
            resultMap.put("status", "mysqlerr");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取用户的登录记录
     *
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
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

            //获取登录记录
            List<UserLog> userLogs = userLService.getUserlog(username);
            if (userLogs.size() == 0) {
                resultMap.put("status", "ok");
                resultMap.put("len", 0);
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserLog userLog : userLogs) {
                    Map<String, Object> rowMap = new HashMap<>(8);
                    rowMap.put("logtime", simpleDateFormat.format(userLog.getLogtime()));
                    rowMap.put("logcity", userLog.getLogcity());
                    rowMap.put("logip", userLog.getLogip());
                    rowMap.put("logsystem", userLog.getLogsys());
                    rowList.add(rowMap);
                }
                resultMap.put("list", rowList);
            }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
