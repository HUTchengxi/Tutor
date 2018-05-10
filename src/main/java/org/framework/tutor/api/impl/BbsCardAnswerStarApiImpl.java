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
import org.framework.tutor.api.BbsCardAnswerStarApi;
import org.framework.tutor.domain.BbsCardAnswerStar;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardAnswerStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class BbsCardAnswerStarApiImpl implements BbsCardAnswerStarApi {

    @Autowired
    private BbsCardAnswerStarService bbsCardAnswerStarService;

    @Autowired
    private BbsCardAnswerService bbsCardAnswerService;

    @Autowired
    private StringRedisTemplate redis;

    /**
     * @param [cardId, request, response]
     * @return void
     * @Description 判断当前用户是否star指定回答
     * @author yinjimin
     * @date 2018/4/10
     */
    //TODO: 使用了Redis   保存[username].[aid].checkuserstar
    @Override
    public void checkUserStar(Integer aid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append("." + aid).append(".checkuserstar");
        if (redis.hasKey(keyTemp.toString())) {
            resultMap.put("status", redis.opsForValue().get(keyTemp.toString()));
        } else {
            BbsCardAnswerStar bbsCardAnswerStar = bbsCardAnswerStarService.checkUserStar(aid, username);
            if (bbsCardAnswerStar == null) {
                resultMap.put("status", "none");
                redis.opsForValue().set(keyTemp.toString(), "none");
            } else {
                Integer score = bbsCardAnswerStar.getScore();
                if (score == 1) {
                    resultMap.put("status", "star");
                    redis.opsForValue().set(keyTemp.toString(), "star");
                } else {
                    resultMap.put("status", "unstar");
                    redis.opsForValue().set(keyTemp.toString(), "unstar");
                }
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * @param [aid, score, request, response]
     * @return void
     * @Description 用户star指定回答
     * @author yinjimin
     * @date 2018/4/10
     */
    //TODO：使用了Redis   更新[username].[aid].checkuserstar
    @Override
    public void addUserStar(Integer aid, Integer score, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已经star过
        if (bbsCardAnswerStarService.checkUserStar(aid, username) != null) {
            resultMap.put("status", "invalid");
        } else {
            bbsCardAnswerStarService.addUserStar(aid, username, score);
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append("." + aid).append(".checkuserstar");
            if (score == 1) {
                bbsCardAnswerService.addGcount(aid);
                redis.opsForValue().set(keyTemp.toString(), "star");
            } else {
                bbsCardAnswerService.addBcount(aid);
                redis.opsForValue().set(keyTemp.toString(), "unstar");
            }
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
