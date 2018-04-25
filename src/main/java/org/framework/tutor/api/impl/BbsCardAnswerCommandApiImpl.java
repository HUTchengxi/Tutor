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
import org.framework.tutor.api.BbsCardAnswerCommandApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardAnswerCommand;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardAnswerCommandService;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMService;
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
public class BbsCardAnswerCommandApiImpl implements BbsCardAnswerCommandApi {

    @Autowired
    private BbsCardAnswerCommandService bbsCardAnswerCommandService;

    @Autowired
    private BbsCardAnswerService bbsCardAnswerService;

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private UserMService userMService;

    /**
     *
     * @Description 每次获取五条评论数据
     * @param [aid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    public void getCommandListByAid(Integer startpos, Integer aid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        startpos *= 5;
        List<BbsCardAnswerCommand> bbsCardAnswerCommands = bbsCardAnswerCommandService.getCommandListByAid(aid, startpos);

        int count = bbsCardAnswerCommands.size();
        if(count != 0){
            res = "{";
            int i = 1;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (BbsCardAnswerCommand bbsCardAnswerCommand: bbsCardAnswerCommands) {
                UserMain userMain = userMService.getByUser(bbsCardAnswerCommand.getUsername());
                res += "\""+i+"\": ";
                String temp = "{\"comtime\": \""+simpleDateFormat.format(bbsCardAnswerCommand.getComtime())+"\", " +
                        "\"nickname\": \""+userMain.getNickname()+"\", " +
                        "\"imgsrc\": \""+userMain.getImgsrc()+"\", " +
                        "\"cardid\": \""+bbsCardAnswerCommand.getCardid()+"\", " +
                        "\"floor\": \""+bbsCardAnswerCommand.getFloor()+"\", " +
                        "\"repfloor\": \""+bbsCardAnswerCommand.getRepfloor()+"\", " +
                        "\"id\": \""+bbsCardAnswerCommand.getId()+"\", " +
                        "\"descript\": \""+bbsCardAnswerCommand.getComment()+"\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }
        else{
            res = "{\"status\": \"none\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    public void publishCommand(Integer cardid, Integer aid, String answer, Integer repfloor, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        Integer floor = bbsCardAnswerCommandService.getCurrentFloor(cardid, aid);
        if(floor == null){
            floor = 0;
        }
        floor += 1;
        bbsCardAnswerCommandService.publishCommand(username, cardid, aid, answer, floor, repfloor);
        bbsCardAnswerService.addComcount(aid);
        res = "{\"status\": \"valid\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    public void getMyCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        Integer count = bbsCardAnswerCommandService.getComcountByUser(username);
        res = "{\"count\": \""+count+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    public void getMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        List<BbsCardAnswerCommand> bbsCardAnswerCommands = bbsCardAnswerCommandService.getMyCommandInfo(username);

        if(bbsCardAnswerCommands.size() == 0){
            res = "{\"status\": \"none\"}";
        }
        else {
            res = "{";
            int i = 1;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardAnswerCommand bbsCardAnswerCommand: bbsCardAnswerCommands) {
                BbsCard bbsCard = bbsCardService.getCardById(bbsCardAnswerCommand.getCardid());
                res += "\"" + i + "\": ";
                String temp = "{\"comtime\": \"" + simpleDateFormat.format(bbsCardAnswerCommand.getComtime()) + "\", " +
                        "\"title\": \"" + bbsCard.getTitle() + "\", " +
                        "\"cid\": \"" + bbsCard.getId() + "\", " +
                        "\"floor\": \"" + bbsCardAnswerCommand.getFloor() + "\", " +
                        "\"repfloor\": \"" + bbsCardAnswerCommand.getRepfloor() + "\", " +
                        "\"comment\": \"" + bbsCardAnswerCommand.getComment() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
