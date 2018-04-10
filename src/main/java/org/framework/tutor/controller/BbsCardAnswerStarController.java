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
package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.BbsCardAnswerStar;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardAnswerStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yinjimin
 * @Description: 帖子答案star控制类
 * @date 2018年04月10日
 */
@RestController
@RequestMapping("/bbscardanswerstar_con")
public class BbsCardAnswerStarController {

    @Autowired
    private BbsCardAnswerStarService bbsCardAnswerStarService;

    @Autowired
    private BbsCardAnswerService bbsCardAnswerService;

    /**
     *
     * @Description 判断当前用户是否star指定回答
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @RequestMapping("/checkuserstar")
    public void checkUserStar(@RequestParam Integer aid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"nologin\"}";
        }
        else{
            BbsCardAnswerStar bbsCardAnswerStar = bbsCardAnswerStarService.checkUserStar(aid, username);
            if(bbsCardAnswerStar == null){
                res = "{\"status\": \"none\"}";
            }
            else{
                Integer score = bbsCardAnswerStar.getScore();
                if(score == 1){
                    res = "{\"status\": \"star\"}";
                }
                else{
                    res = "{\"status\": \"unstar\"}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


    /**
     *
     * @Description 用户star指定回答
     * @param [aid, score, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @PostMapping("/adduserstar")
    public void addUserStar(@RequestParam Integer aid, @RequestParam Integer score, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        //判断是否已经star过
        if(bbsCardAnswerStarService.checkUserStar(aid, username) != null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            bbsCardAnswerStarService.addUserStar(aid, username, score);
            if(score == 1){
                bbsCardAnswerService.addGcount(aid);
            }
            else{
                bbsCardAnswerService.addBcount(aid);
            }
            res = "{\"status\": \"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
