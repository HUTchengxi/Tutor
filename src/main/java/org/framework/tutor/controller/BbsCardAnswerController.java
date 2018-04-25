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
import org.framework.tutor.api.BbsCardAnswerApi;
import org.framework.tutor.domain.BbsCardAnswer;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMService;
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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yinjimin
 * @Description: 帖子答案控制类
 * @date 2018年04月05日
 */
@RestController
@RequestMapping("/bbscardanswer_con")
public class BbsCardAnswerController {

    @Autowired
    private BbsCardAnswerApi bbsCardAnswerApi;

    /**  
     *    
     * @Description 获取对应的帖子答案数据
     * @param [cardId, response]
     * @return void
     * @author yinjimin  
     * @date 2018/4/7
     */  
    @PostMapping("/getcardanswerbycardid")
    public void getCardAnswerByCardid(@RequestParam Integer cardId, HttpServletResponse response) throws IOException {

        bbsCardAnswerApi.getCardAnswerByCardid(cardId, response);
    }

    /**
     *
     * @Description 判断当前用户是否已回答问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    @PostMapping("/checkusercommand")
    public void checkUserCommand(@RequestParam Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardAnswerApi.checkUserCommand(cardId, request, response);
    }

    /**
     *
     * @Description 添加回答
     * @param [cardId, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    @PostMapping("/addanswer")
    public void addAnswer(@RequestParam Integer cardId, @RequestParam String answer, HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardAnswerApi.addAnswer(cardId, answer, request, response);
    }

    /**
     *
     * @Description 获取用户回答总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/12
     */
    @PostMapping("/getmyanswercount")
    public void getMyAnswerCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardAnswerApi.getMyAnswerCount(request, response);
    }


    @PostMapping("/getmyanswerinfo")
    public void getMyAnswerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardAnswerApi.getMyAnswerInfo(request, response);
    }
}
