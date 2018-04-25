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
import org.framework.tutor.api.TutorBtnsApi;
import org.framework.tutor.domain.TutorBtns;
import org.framework.tutor.domain.TutorsysBtns;
import org.framework.tutor.service.TutorBtnsService;
import org.framework.tutor.service.TutorsysBtnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 *@date 2018年04月25日
 */
@Component
public class TutorBtnsApiImpl implements TutorBtnsApi {

    @Autowired
    private TutorBtnsService tutorBtnsService;

    @Autowired
    private TutorsysBtnsService tutorSysBtnsService;

    /**
     * 获取当前家教的所有常用链接数据
     * @param request
     * @param response
     */
    @Override
    public void getBtnsList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        List<TutorBtns> tutorBtnsList = tutorBtnsService.getBtnsList(username);

        if(tutorBtnsList.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else{
            res = "{";
            int i = 1;
            for (TutorBtns tutorBtns: tutorBtnsList) {
                //根据id获取对应的链接数据
                TutorsysBtns tutorsysBtns = tutorSysBtnsService.getById(tutorBtns.getBid());
                res += "\""+i+"\": ";
                String temp = "{\"id\": \""+tutorBtns.getId()+"\", " +
                        "\"name\": \""+tutorsysBtns.getName()+"\", "+
                        "\"url\": \""+tutorsysBtns.getUrl()+"\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
