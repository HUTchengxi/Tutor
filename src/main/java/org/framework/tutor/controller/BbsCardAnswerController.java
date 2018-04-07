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
import org.framework.tutor.domain.BbsCardAnswer;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
    private BbsCardAnswerService bbsCardAnswerService;

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
    @PostMapping("/getcardanswerbycardid")
    public void getCardAnswerByCardid(@RequestParam Integer cardId, HttpServletResponse response) throws IOException {

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
}
