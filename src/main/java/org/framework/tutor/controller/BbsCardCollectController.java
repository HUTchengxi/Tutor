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
import org.framework.tutor.api.BbsCardCollectApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardCollect;
import org.framework.tutor.service.BbsCardCollectService;
import org.framework.tutor.service.BbsCardService;
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
 * @Description: 用户帖子收藏控制类
 * @date 2018年04月01日
 */
@RestController
@RequestMapping("/bbscardcollect_con")
public class BbsCardCollectController {

    @Autowired
    private BbsCardCollectApi bbsCardCollectApi;
    
    /**  
     *    
     * @Description 获取当前用户的收藏总数
     * @param [request, response]    
     * @return void
     * @author yinjimin  
     * @date 2018/4/1
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycollectcount")
    public void getMyCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardCollectApi.getMyCollectCount(request, response);
    }

    /**
     *
     * @Description 判断当前用户是否已收藏
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/checkcollectstatus")
    public void checkCollectStatus(@RequestParam Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardCollectApi.checkCollectStatus(cardId, request, response);
    }


    /**
     *
     * @Description 收藏问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/collectcard")
    public void collectCard(@RequestParam Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardCollectApi.collectCard(cardId, request, response);
    }

    /**
     *
     * @Description 取消收藏问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/uncollectcard")
    public void uncollectCard(@RequestParam Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardCollectApi.uncollectCard(cardId, request, response);
    }


    /**
     *
     * @Description 获取当前用户收藏的帖子数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/13
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/getmycollectinfo")
    public void getMyCollectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        bbsCardCollectApi.getMyCollectInfo(request, response);
    }
}
