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
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.BbsCardAnswerStarApi;
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
    private BbsCardAnswerStarApi bbsCardAnswerStarApi;

    /**
     * @Description 判断当前用户是否star指定回答
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/checkuserstar")
    public String checkUserStar(@RequestParam Integer aid) throws IOException {

        return bbsCardAnswerStarApi.checkUserStar(aid);
    }


    /**
     * @Description 用户star指定回答
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/adduserstar")
    public String addUserStar(@RequestParam Integer aid, @RequestParam Integer score) throws IOException {

        return bbsCardAnswerStarApi.addUserStar(aid, score);
    }
}
