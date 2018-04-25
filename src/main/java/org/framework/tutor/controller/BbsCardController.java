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
import org.framework.tutor.api.BbsCardApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.util.CommonUtil;
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
 * @Description: 论坛帖子控制类
 * @author yinjimin
 * @date 2018年03月31日
 */
@RestController
@RequestMapping("/bbscard_con")
public class BbsCardController {

    @Autowired
    private BbsCardApi bbsCardApi;


    /**
     *
     * @Description 获取当前登录用户的帖子发表总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/3/31
     */
    @RequestMapping("/getmycardcount")
    public void getMyCardCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardApi.getMyCardCount(request, response);
    }


    /**  
     *    
     * @Description 指定用户发表讨论
     * @param [title, imgsrc, descript, request, response]    
     * @return void
     * @author yinjimin  
     * @date 2018/4/1
     */  
    @RequestMapping("/publishCard")
    public void publishCard(String title, String imgsrc, String descript, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        bbsCardApi.publishCard(title, imgsrc, descript, request, response);
    }


    /**
     *
     * @Description 获取指定关键字的帖子数据
     * @param [keyword, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/3
     */
    @PostMapping("/searchCard")
    public void searchCard(String keyword, HttpServletResponse response) throws IOException {

        bbsCardApi.searchCard(keyword, response);
    }


    /**  
     *    
     * @Description 加载最新五条热门帖子
     * @param [response]
     * @return void
     * @author yinjimin  
     * @date 2018/4/3
     */  
    @PostMapping("/loadhotcard")
    public void loadHotCard(HttpServletResponse response) throws IOException {

        bbsCardApi.loadHotCard(response);
    }

    /**  
     *    
     * @Description 获取对应问题数据
     * @param [cardId, response]    
     * @return void
     * @author yinjimin  
     * @date 2018/4/6
     */  
    @PostMapping("/getcardbyid")
    public void getCardById(@RequestParam String cardId, HttpServletResponse response) throws IOException {

        bbsCardApi.getCardById(cardId, response);
    }

    @PostMapping("/addviscount")
    public void addViscount(@RequestParam Integer cardid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardApi.addViscount(cardid, request, response);
    }

    /**
     *
     * @Description 获取当前用户发表的帖子数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/13
     */
    @PostMapping("/getmycardinfo")
    public void getMyCardInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardApi.getMyCardInfo(request, response);
    }
}
