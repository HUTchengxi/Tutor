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
import org.framework.tutor.api.BbsCardCollectApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardCollect;
import org.framework.tutor.service.BbsCardCollectService;
import org.framework.tutor.service.BbsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class BbsCardCollectApiImpl implements BbsCardCollectApi {

    @Autowired
    private BbsCardCollectService bbsCardCollectService;

    @Autowired
    private BbsCardService bbsCardService;

    /**
     *
     * @Description 获取当前用户的收藏总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/1
     */
    @Override
    public void getMyCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession sessions = request.getSession();
        String username = (String) sessions.getAttribute("username");
        String res = null;

        Integer count = bbsCardCollectService.getMyCollectCount(username);

        res = "{\"count\": \""+count+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 判断当前用户是否已收藏
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @Override
    public void checkCollectStatus(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;
        String username = (String) session.getAttribute("username");

        if(username == null){
            res = "{\"status\": \"none\"}";
        }
        else if(bbsCardCollectService.checkCollectStatus(cardId, username) != null){
            res = "{\"status\": \"col\"}";
        }
        else{
            res = "{\"status\": \"uncol\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


    /**
     *
     * @Description 收藏问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @Override
    public void collectCard(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;
        String username = (String) session.getAttribute("username");

        //判断是否已收藏
        if(bbsCardCollectService.checkCollectStatus(cardId, username) != null){
            res = "{\"status\": \"none\"}";
        }
        else{
            bbsCardCollectService.collectCard(cardId, username);
            bbsCardService.addColCountByCardId(cardId);
            res = "{\"status\": \"col\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 取消收藏问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @Override
    public void uncollectCard(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;
        String username = (String) session.getAttribute("username");

        //判断是否已收藏
        if(bbsCardCollectService.checkCollectStatus(cardId, username) == null){
            res = "{\"status\": \"none\"}";
        }
        else{
            bbsCardCollectService.uncollectCard(cardId, username);
            bbsCardService.delColCountByCardId(cardId);
            res = "{\"status\": \"uncol\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


    /**
     *
     * @Description 获取当前用户收藏的帖子数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/13
     */
    @Override
    public void getMyCollectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        List<BbsCardCollect> bbsCardList = bbsCardCollectService.getMyCollectInfo(username);
        if(bbsCardList.size() == 0){
            res = "{\"status\": \"none\"}";
        }else{
            res = "{";
            int i = 1;
            SimpleDateFormat ysdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardCollect bbsCardCollect: bbsCardList) {
                BbsCard bbsCard = bbsCardService.getCardById(bbsCardCollect.getCardid());
                res += "\""+i+"\": ";
                String temp = "{\"id\": \""+bbsCard.getId()+"\", " +
                        "\"crtime\": \""+ysdf.format(bbsCard.getCrttime())+"\", " +
                        "\"coltime\": \""+ysdf.format(bbsCardCollect.getColtime())+"\", " +
                        "\"title\": \""+bbsCard.getTitle()+"\", " +
                        "\"comcount\": \""+bbsCard.getComcount()+"\", " +
                        "\"viscount\": \""+bbsCard.getViscount()+"\", " +
                        "\"colcount\": \""+bbsCard.getColcount()+"\", " +
                        "\"descript\": \""+bbsCard.getDescript()+"\"}, ";
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
