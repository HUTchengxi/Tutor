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
import org.framework.tutor.service.BbsCardCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yinjimin
 * @Description: 用户帖子收藏控制类
 * @date 2018年04月01日
 */
@RestController
@RequestMapping("/bbscardcollect_con")
public class BbsCardCollectController {

    @Autowired
    private BbsCardCollectService bbsCardCollectService;

    
    /**  
     *    
     * @Description 获取当前用户的收藏总数
     * @param [request, response]    
     * @return void
     * @author yinjimin  
     * @date 2018/4/1
     */  
    @RequestMapping("/getmycollectcount")
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
}
