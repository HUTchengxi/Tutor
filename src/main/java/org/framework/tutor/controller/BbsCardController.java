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
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.domain.UserMain;
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
 * @Description: 论坛帖子控制类
 * @author yinjimin
 * @date 2018年03月31日
 */
@RestController
@RequestMapping("/bbscard_con")
public class BbsCardController {

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private UserMService userMService;


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

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;
        PrintWriter writer = response.getWriter();

        Integer count = bbsCardService.getMyCardCount(username);

        res = "{\"count\": \""+count+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
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

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;
        String username = (String) session.getAttribute("username");

        //判断标题是否已经被占用了
        if(bbsCardService.getByTitle(title) != null){
            res = "{\"status\": \"texist\"}";
        }
        else{
            bbsCardService.publishCard(username, title, imgsrc, descript);
            res = "{\"status\": \"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
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

        response.setCharacterEncoding("utf-8");
        PrintWriter  writer = response.getWriter();
        String res = null;

        //获取课程记录
        List<BbsCard> bbsCards = bbsCardService.searchCard(keyword);
        if(bbsCards.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else {
            res = "{";
            int i = 1;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (BbsCard bbsCard: bbsCards) {
                UserMain userMain = userMService.getByUser(bbsCard.getUsername());
                res += "\""+i+"\": ";
                String temp = "{\"crttime\": \""+simpleDateFormat.format(bbsCard.getCrttime())+"\", " +
                        "\"nickname\": \""+userMain.getNickname()+"\", " +
                        "\"imgsrc\": \""+userMain.getImgsrc()+"\", " +
                        "\"bimgsrc\": \""+bbsCard.getImgsrc()+"\", " +
                        "\"id\": \""+bbsCard.getId()+"\", " +
                        "\"descript\": \""+bbsCard.getDescript()+"\", " +
                        "\"title\": \""+bbsCard.getTitle()+"\"}, ";
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

        response.setCharacterEncoding("utf-8");
        PrintWriter  writer = response.getWriter();
        String res = null;

        //获取课程记录
        List<BbsCard> bbsCards = bbsCardService.loadHotCard();
        if(bbsCards.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else {
            res = "{";
            int i = 1;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (BbsCard bbsCard: bbsCards) {
                UserMain userMain = userMService.getByUser(bbsCard.getUsername());
                res += "\""+i+"\": ";
                String temp = "{\"crttime\": \""+simpleDateFormat.format(bbsCard.getCrttime())+"\", " +
                        "\"nickname\": \""+userMain.getNickname()+"\", " +
                        "\"imgsrc\": \""+userMain.getImgsrc()+"\", " +
                        "\"bimgsrc\": \""+bbsCard.getImgsrc()+"\", " +
                        "\"id\": \""+bbsCard.getId()+"\", " +
                        "\"descript\": \""+bbsCard.getDescript()+"\", " +
                        "\"title\": \""+bbsCard.getTitle()+"\"}, ";
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

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        try{
            Integer id = Integer.parseInt(cardId);
            BbsCard bbsCard = bbsCardService.getCardById(id);
            if(bbsCard == null){
                res = "{\"status\": \"none\"}";
            }
            else{
                UserMain userMain = userMService.getByUser(bbsCard.getUsername());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                res = "{\"crttime\": \""+simpleDateFormat.format(bbsCard.getCrttime())+"\", " +
                        "\"username\": \""+bbsCard.getUsername()+"\", " +
                        "\"viscount\": \""+bbsCard.getViscount()+"\", " +
                        "\"colcount\": \""+bbsCard.getColcount()+"\", " +
                        "\"comcount\": \""+bbsCard.getComcount()+"\", " +
                        "\"nickname\": \""+userMain.getNickname()+"\", " +
                        "\"imgsrc\": \""+userMain.getImgsrc()+"\", " +
                        "\"id\": \""+bbsCard.getId()+"\", " +
                        "\"descript\": \""+bbsCard.getDescript()+"\", " +
                        "\"title\": \""+bbsCard.getTitle()+"\"}";
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
