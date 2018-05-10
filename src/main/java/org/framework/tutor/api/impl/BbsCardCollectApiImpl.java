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
import org.framework.tutor.api.BbsCardCollectApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardCollect;
import org.framework.tutor.service.BbsCardCollectService;
import org.framework.tutor.service.BbsCardService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private StringRedisTemplate redis;

    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前用户的收藏总数
     * @author yinjimin
     * @date 2018/4/1
     */
    //TODO：使用了Redis    保存[username].collectcount
    @Override
    public void getMyCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession sessions = request.getSession();
        String username = (String) sessions.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        Integer count = null;
        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append(".collectcount");
        if (redis.hasKey(keyTemp.toString())) {
            count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString()));
        } else {
            count = bbsCardCollectService.getMyCollectCount(username);
            redis.opsForValue().set(keyTemp.toString(), count.toString());
        }
        resultMap.put("count", count);

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [cardId, request, response]
     * @return void
     * @Description 判断当前用户是否已收藏
     * @author yinjimin
     * @date 2018/4/8
     */
    //TODO：使用了Redis    保存[username].[cardid].collectstatus
    @Override
    public void checkCollectStatus(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            resultMap.put("status", "none");
        } else {
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append("."+cardId).append(".collectstatus");
            if (redis.hasKey(keyTemp.toString())) {
                resultMap.put("status", redis.opsForValue().get(keyTemp.toString()));
            } else if (bbsCardCollectService.checkCollectStatus(cardId, username) != null) {
                resultMap.put("status", "col");
                redis.opsForValue().set(keyTemp.toString(), "col");
            } else {
                resultMap.put("status", "uncol");
                redis.opsForValue().set(keyTemp.toString(), "uncol");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * @param [cardId, request, response]
     * @return void
     * @Description 收藏问题
     * @author yinjimin
     * @date 2018/4/8
     */
    //TODO：使用了Redis    更新 [username].collectcount 和  [username].[cardid].collectstatus
    @Override
    public void collectCard(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已收藏
        if (bbsCardCollectService.checkCollectStatus(cardId, username) != null) {
            resultMap.put("status", "none");
        } else {
            bbsCardCollectService.collectCard(cardId, username);
            bbsCardService.addColCountByCardId(cardId);
            resultMap.put("status", "col");

            //更新[username].collectcount缓存数据
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append(".collectcount");
            if(redis.hasKey(keyTemp.toString())){
                Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) + 1;
                redis.opsForValue().set(keyTemp.toString(), count.toString());
            }

            //更新[username].[cardid].collectstatus缓存数据
            keyTemp = new StringBuffer(username);
            keyTemp.append("."+cardId).append(".collectstatus");
            if(redis.hasKey(keyTemp.toString())){
                redis.opsForValue().set(keyTemp.toString(), "col");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [cardId, request, response]
     * @return void
     * @Description 取消收藏问题
     * @author yinjimin
     * @date 2018/4/8
     */
    //TODO：使用了Redis    更新 [username].collectcount 和  [username].[cardid].collectstatus
    @Override
    public void uncollectCard(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已收藏
        if (bbsCardCollectService.checkCollectStatus(cardId, username) == null) {
            resultMap.put("status", "none");
        } else {
            bbsCardCollectService.uncollectCard(cardId, username);
            bbsCardService.delColCountByCardId(cardId);
            resultMap.put("status", "uncol");

            //更新[username].collectcount缓存数据
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append(".collectcount");
            if(redis.hasKey(keyTemp.toString())){
                Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) - 1;
                redis.opsForValue().set(keyTemp.toString(), count.toString());
            }

            //更新[username].[cardid].collectstatus缓存数据
            keyTemp = new StringBuffer(username);
            keyTemp.append("."+cardId).append(".collectstatus");
            if(redis.hasKey(keyTemp.toString())){
                redis.opsForValue().set(keyTemp.toString(), "uncol");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前用户收藏的帖子数据
     * @author yinjimin
     * @date 2018/4/13
     */
    //TODO：后续可以考虑使用redis，目前基于值的复杂性暂时不考虑
    @Override
    public void getMyCollectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> resultList = new ArrayList<>();

        List<BbsCardCollect> bbsCardList = bbsCardCollectService.getMyCollectInfo(username);
        if (bbsCardList.size() == 0) {
            resultMap.put("status", "none");
        } else {
            SimpleDateFormat ysdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardCollect bbsCardCollect : bbsCardList) {
                BbsCard bbsCard = bbsCardService.getCardById(bbsCardCollect.getCardid());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("id", bbsCard.getId());
                rowMap.put("crtime", ysdf.format(bbsCard.getCrttime()));
                rowMap.put("coltime", ysdf.format(bbsCardCollect.getColtime()));
                rowMap.put("title", bbsCard.getTitle());
                rowMap.put("comcount", bbsCard.getComcount());
                rowMap.put("viscount", bbsCard.getViscount());
                rowMap.put("colcount", bbsCard.getColcount());
                rowMap.put("descript", bbsCard.getDescript());
                resultList.add(rowMap);
            }
            resultMap.put("list", resultList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
