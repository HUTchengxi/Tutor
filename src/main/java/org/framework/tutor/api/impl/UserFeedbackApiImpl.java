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
import org.framework.tutor.api.UserFeedbackApi;
import org.framework.tutor.domain.UserFeedback;
import org.framework.tutor.service.UserFeedbackService;
import org.framework.tutor.service.UserMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年05月09日
 */
@Component
public class UserFeedbackApiImpl implements UserFeedbackApi {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @Autowired
    private UserMSService userMSService;

    @Override
    public String getMyFeedback(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = null;

        List<UserFeedback> userFeedbacks = userFeedbackService.getMyFeedback(username);
        if(userFeedbacks.size() > 0){
            rowList = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(UserFeedback userFeedback: userFeedbacks){
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("id", userFeedback.getId());
                rowMap.put("info", userFeedback.getInfo());
                rowMap.put("ptime", simpleDateFormat.format(userFeedback.getPtime()));
                rowList.add(rowMap);
            }
        }
        resultMap.put("rows", rowList);
        return gson.toJson(resultMap);
    }

    @Override
    public String removeMyFeedback(Integer id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //首先判断是否是该该用户的
        UserFeedback userFeedback = userFeedbackService.getByUserAndId(id, username);
        if(userFeedback == null){
            resultMap.put("status", "invalid");
        }else{
            //设置status
            Integer status = -1;
            Integer row = userFeedbackService.modMyFeedbackStatus(id, status);
            if(row == 1){
                resultMap.put("status", "valid");
            }else{
                resultMap.put("status", "sqlerr");
                status = userFeedback.getStatus();
                userFeedbackService.modMyFeedbackStatus(id, status);
            }
        }
        return gson.toJson(resultMap);
    }

    @Override
    public String saveMyFeedback(String info, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = userFeedbackService.saveMyFeedback(username, info);
        if(row == 1){
            resultMap.put("status", "valid");

            //发送通知
            Integer identity = 1;
            String suser= "chengxi";
            String title = "反馈成功通知";
            String message = "谢谢您的反馈，稍后我们会第一时间通知您反馈进度";
            userMSService.seneMessage(identity, suser, username, title, message);
        }else{
            resultMap.put("status", "sqlerr");
        }
        return gson.toJson(resultMap);
    }
}
