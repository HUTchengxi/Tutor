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
import org.framework.tutor.api.AdminApi;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class AdminApiImpl implements AdminApi {

    @Autowired
    private UserMainService userMainService;

    /**
     *
     * @Description 管理员登录
     * @param [username, password, remember, request]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @Override
    public void Login(String username, String password, HttpServletResponse response, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        HttpSession session = request.getSession();

        //获取密码盐
        Integer salt = userMainService.getByUser(username).getSalt();
        //明文传来的密码加盐判断
        String MD5Pass = CommonUtil.getMd5Pass(password, salt);
        UserMain userMain = userMainService.checkAdminLogin(username, MD5Pass);
        if(userMain == null){
            resultMap.put("status", "error");
        }else{
            session.setAttribute("username", userMain.getUsername());
            session.setAttribute("nickname", userMain.getNickname());
            session.setAttribute("identity", userMain.getIdentity());
            resultMap.put("status", "success");
            resultMap.put("url", "/adminpage_con/gosysadminmain");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
