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

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.BbsCardAnswerCommandApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yinjimin
 * @Description: 帖子回答的评论控制类
 * @date 2018年04月10日
 */
@RestController
@RequestMapping("/bbscardanswercommand_con")
public class BbsCardAnswerCommandController {

    @Autowired
    private BbsCardAnswerCommandApi bbsCardAnswerCommandApi;

    /**
     *
     * @Description 每次获取五条评论数据
     * @param [aid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @RequestMapping("/getcommandlistbyaid")
    public void getCommandListByAid(@RequestParam Integer startpos, @RequestParam Integer aid, HttpServletResponse response) throws IOException {

        bbsCardAnswerCommandApi.getCommandListByAid(startpos, aid, response);
    }


    /**
     *
     * @Description 发表评论
     * @param [cardid, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/publishcommand")
    public void publishCommand(@RequestParam Integer cardid, @RequestParam Integer aid, @RequestParam String answer, Integer repfloor, HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardAnswerCommandApi.publishCommand(cardid, aid, answer, repfloor, request, response);
    }

    @RequireAuth(ident = "user", type = "api")
    @PostMapping("getmycommandcount")
    public void getMyCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardAnswerCommandApi.getMyCommandCount(request, response);
    }

    /**
     *
     * @Description 获取当前用户的评论数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/14
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/getmycommandinfo")
    public void getMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardAnswerCommandApi.getMyCommandInfo(request, response);
    }
}
