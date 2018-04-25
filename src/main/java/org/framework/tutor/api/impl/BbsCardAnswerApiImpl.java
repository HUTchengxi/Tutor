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
import org.framework.tutor.api.BbsCardAnswerApi;
import org.framework.tutor.domain.BbsCardAnswer;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
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
public class BbsCardAnswerApiImpl implements BbsCardAnswerApi {

    @Autowired
    private BbsCardAnswerService bbsCardAnswerService;

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private UserMService userMService;

    /**
     *
     * @Description 获取对应的帖子答案数据
     * @param [cardId, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/7
     */
    public void getCardAnswerByCardid(Integer cardId, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        try{
            List<BbsCardAnswer> bbsCardAnswerList = bbsCardAnswerService.getByCardid(cardId);
            if(bbsCardAnswerList.size() == 0){
                res = "{\"status\": \"none\"}";
            }
            else{
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for(BbsCardAnswer bbsCardAnswer: bbsCardAnswerList){
                    UserMain userMain = userMService.getByUser(bbsCardAnswer.getUsername());
                    res += "\""+i+"\": ";
                    String temp = "{\"crttime\": \""+simpleDateFormat.format(bbsCardAnswer.getCrtime())+"\", " +
                            "\"nickname\": \""+userMain.getNickname()+"\", " +
                            "\"username\": \""+userMain.getUsername()+"\", " +
                            "\"imgsrc\": \""+userMain.getImgsrc()+"\", " +
                            "\"answer\": \""+bbsCardAnswer.getAnswer()+"\", " +
                            "\"id\": \""+bbsCardAnswer.getId()+"\", " +
                            "\"gcount\": \""+bbsCardAnswer.getGcount()+"\", " +
                            "\"bcount\": \""+bbsCardAnswer.getBcount()+"\", " +
                            "\"comcount\": \""+bbsCardAnswer.getComcount()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }catch (Exception e){
            res = "{\"status\": \"sysexception\"}";
            e.printStackTrace();
        } finally {
            writer.print(new JsonParser().parse(res).getAsJsonObject());
            writer.flush();
            writer.close();
        }
    }

    /**
     *
     * @Description 判断当前用户是否已回答问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    public void checkUserCommand(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"none\"}";
        }
        else if(bbsCardAnswerService.checkIsExistAnswer(cardId, username) == null){
            res = "{\"status\": \"un\"}";
        }
        else{
            res = "{\"status\": \"ed\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 添加回答
     * @param [cardId, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    public void addAnswer(Integer cardId, String answer, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断是否已经有了回答
        if(bbsCardAnswerService.checkIsExistAnswer(cardId, username) != null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            bbsCardAnswerService.addAnswer(cardId, username, answer);
            bbsCardService.addComCountByCardId(cardId);
            res = "{\"status\": \"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 获取用户回答总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/12
     */
    public void getMyAnswerCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        Integer count = bbsCardAnswerService.getMyAnswerCount(username);
        res = "{\"count\": \""+count+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


    public void getMyAnswerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        try{
            List<BbsCardAnswer> bbsCardAnswerList = bbsCardAnswerService.getmyAnswerInfo(username);
            if(bbsCardAnswerList.size() == 0){
                res = "{\"status\": \"none\"}";
            }
            else{
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for(BbsCardAnswer bbsCardAnswer: bbsCardAnswerList){
                    UserMain userMain = userMService.getByUser(bbsCardAnswer.getUsername());
                    res += "\""+i+"\": ";
                    String temp = "{\"crttime\": \""+simpleDateFormat.format(bbsCardAnswer.getCrtime())+"\", " +
                            "\"nickname\": \""+userMain.getNickname()+"\", " +
                            "\"imgsrc\": \""+userMain.getImgsrc()+"\", " +
                            "\"answer\": \""+bbsCardAnswer.getAnswer()+"\", " +
                            "\"cid\": \""+bbsCardAnswer.getCardid()+"\", " +
                            "\"aid\": \""+bbsCardAnswer.getId()+"\", " +
                            "\"gcount\": \""+bbsCardAnswer.getGcount()+"\", " +
                            "\"bcount\": \""+bbsCardAnswer.getBcount()+"\", " +
                            "\"comcount\": \""+bbsCardAnswer.getComcount()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }catch (Exception e){
            res = "{\"status\": \"sysexception\"}";
            e.printStackTrace();
        } finally {
            writer.print(new JsonParser().parse(res).getAsJsonObject());
            writer.flush();
            writer.close();
        }
    }
}
