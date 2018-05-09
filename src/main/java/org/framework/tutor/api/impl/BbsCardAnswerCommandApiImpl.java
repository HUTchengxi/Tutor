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
import org.framework.tutor.api.BbsCardAnswerCommandApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardAnswerCommand;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardAnswerCommandService;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class BbsCardAnswerCommandApiImpl implements BbsCardAnswerCommandApi {

    @Autowired
    private BbsCardAnswerCommandService bbsCardAnswerCommandService;

    @Autowired
    private BbsCardAnswerService bbsCardAnswerService;

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private UserMainService userMainService;

    /**
     *
     * @Description 每次获取五条评论数据
     * @param [aid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @Override
    public void getCommandListByAid(Integer startpos, Integer aid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        startpos *= 5;
        List<BbsCardAnswerCommand> bbsCardAnswerCommands = bbsCardAnswerCommandService.getCommandListByAid(aid, startpos);

        int count = bbsCardAnswerCommands.size();
        if(count != 0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardAnswerCommand bbsCardAnswerCommand: bbsCardAnswerCommands) {
                UserMain userMain = userMainService.getByUser(bbsCardAnswerCommand.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("comtime", simpleDateFormat.format(bbsCardAnswerCommand.getComtime()));
                rowMap.put("nickname", userMain.getNickname());
                rowMap.put("imgsrc", userMain.getImgsrc());
                rowMap.put("cardid", bbsCardAnswerCommand.getCardid());
                rowMap.put("floor", bbsCardAnswerCommand.getFloor());
                rowMap.put("repfloor", bbsCardAnswerCommand.getRepfloor());
                rowMap.put("id", bbsCardAnswerCommand.getId());
                rowMap.put("descript", bbsCardAnswerCommand.getComment());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }
        else{
            resultMap.put("status", "none");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     *
     * @Description 发表评论
     * @param [cardid, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @Override
    public void publishCommand(Integer cardid, Integer aid, String answer, Integer repfloor, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer floor = bbsCardAnswerCommandService.getCurrentFloor(cardid, aid);
        if(floor == null){
            floor = 0;
        }
        floor += 1;
        bbsCardAnswerCommandService.publishCommand(username, cardid, aid, answer, floor, repfloor);
        bbsCardAnswerService.addComcount(aid);
        resultMap.put("status", "valid");

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    @Override
    public void getMyCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer count = bbsCardAnswerCommandService.getComcountByUser(username);
        resultMap.put("count", count);

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 获取当前用户的评论数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/14
     */
    @Override
    public void getMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<BbsCardAnswerCommand> bbsCardAnswerCommands = bbsCardAnswerCommandService.getMyCommandInfo(username);

        if(bbsCardAnswerCommands.size() == 0){
            resultMap.put("status", "none");
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardAnswerCommand bbsCardAnswerCommand: bbsCardAnswerCommands) {
                BbsCard bbsCard = bbsCardService.getCardById(bbsCardAnswerCommand.getCardid());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("comtime", simpleDateFormat.format(bbsCardAnswerCommand.getComtime()));
                rowMap.put("title", bbsCard.getTitle());
                rowMap.put("cid", bbsCard.getId());
                rowMap.put("floor", bbsCardAnswerCommand.getFloor());
                rowMap.put("repfloor", bbsCardAnswerCommand.getRepfloor());
                rowMap.put("comment", bbsCardAnswerCommand.getComment());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
