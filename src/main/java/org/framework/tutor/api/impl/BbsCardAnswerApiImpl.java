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
    @Override
    public void getCardAnswerByCardid(Integer cardId, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        try{
            List<BbsCardAnswer> bbsCardAnswerList = bbsCardAnswerService.getByCardid(cardId);
            if(bbsCardAnswerList.size() == 0){
                resultMap.put("status", "none");
            }
            else{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for(BbsCardAnswer bbsCardAnswer: bbsCardAnswerList){
                    Map<String, Object> rowMap = new HashMap<>(16);
                    UserMain userMain = userMService.getByUser(bbsCardAnswer.getUsername());
                    rowMap.put("id", bbsCardAnswer.getId());
                    rowMap.put("imgsrc", userMain.getImgsrc());
                    rowMap.put("nickname", userMain.getNickname());
                    rowMap.put("username", userMain.getUsername());
                    rowMap.put("answer", bbsCardAnswer.getAnswer());
                    rowMap.put("gcount", bbsCardAnswer.getGcount());
                    rowMap.put("bcount", bbsCardAnswer.getBcount());
                    rowMap.put("comcount", bbsCardAnswer.getComcount());
                    rowMap.put("crttime", simpleDateFormat.format(bbsCardAnswer.getCrtime()));
                    rowList.add(rowMap);
                }
                resultMap.put("list", rowList);
        }
        }catch (Exception e){
            resultMap.put("status", "sysexception");
            e.printStackTrace();
        } finally {
            writer.print(gson.toJson(resultMap));
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
    @Override
    public void checkUserCommand(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if(bbsCardAnswerService.checkIsExistAnswer(cardId, username) == null){
            resultMap.put("status", "un");
        }
        else{
            resultMap.put("status", "ed");
        }

        writer.print(gson.toJson(resultMap));
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
    @Override
    public void addAnswer(Integer cardId, String answer, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已经有了回答
        if(bbsCardAnswerService.checkIsExistAnswer(cardId, username) != null){
            resultMap.put("status", "invalid");
        }
        else{
            bbsCardAnswerService.addAnswer(cardId, username, answer);
            bbsCardService.addComCountByCardId(cardId);
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
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
    @Override
    public void getMyAnswerCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer count = bbsCardAnswerService.getMyAnswerCount(username);
        resultMap.put("count", count);

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    @Override
    public void getMyAnswerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        try{
            List<BbsCardAnswer> bbsCardAnswerList = bbsCardAnswerService.getmyAnswerInfo(username);
            if(bbsCardAnswerList.size() == 0){
                resultMap.put("status", "none");
            }
            else{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for(BbsCardAnswer bbsCardAnswer: bbsCardAnswerList){
                    Map<String, Object> rowMap = new HashMap<>(16);
                    UserMain userMain = userMService.getByUser(bbsCardAnswer.getUsername());
                    rowMap.put("aid", bbsCardAnswer.getId());
                    rowMap.put("imgsrc", userMain.getImgsrc());
                    rowMap.put("cid", bbsCardAnswer.getCardid());
                    rowMap.put("nickname", userMain.getNickname());
                    rowMap.put("answer", bbsCardAnswer.getAnswer());
                    rowMap.put("gcount", bbsCardAnswer.getGcount());
                    rowMap.put("bcount", bbsCardAnswer.getBcount());
                    rowMap.put("comcount", bbsCardAnswer.getComcount());
                    rowMap.put("crttime", simpleDateFormat.format(bbsCardAnswer.getCrtime()));
                    rowList.add(rowMap);
                }
                resultMap.put("list", rowList);
            }
        }catch (Exception e){
            resultMap.put("status", "sysexception");
            e.printStackTrace();
        } finally {
            writer.print(gson.toJson(resultMap));
            writer.flush();
            writer.close();
        }
    }
}
