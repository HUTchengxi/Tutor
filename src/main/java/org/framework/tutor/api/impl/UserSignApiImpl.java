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
import org.framework.tutor.api.UserSignApi;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.service.UserSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class UserSignApiImpl implements UserSignApi {

    @Autowired
    private UserSService userSService;

    /**
     * 获取用户的签到数据
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMySign(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;
        String username = (String) session.getAttribute("username");

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            //获取本月份
            Date now = new Date();
            Integer monI = now.getMonth()+1;
            StringBuffer temp = new StringBuffer(monI.toString());
            String monthstr = temp.length()==1?"-0"+temp.toString()+"-": "-"+temp.toString()+"-";

            List<UserSign> userSigns = userSService.getMySignNow(username, monthstr);
            if(userSigns.size() == 0){
                res = "{\"status\": \"valid\"}";
            }
            else{
                StringBuffer date = new StringBuffer(",");
                for (org.framework.tutor.domain.UserSign userSign: userSigns) {
                    date.append(userSign.getStime().getDate() + ",");
                }
                res = new StringBuffer("{\"date\": \"").append(date).append("\"}")
                        .toString();
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


    public void addUsersign(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            Integer row = userSService.addUsersign(username);
            if(row != 1){
                res = "{\"status\": \"mysqlerr\"}";
            }
            else{
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
