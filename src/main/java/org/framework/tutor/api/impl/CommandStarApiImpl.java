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
import org.framework.tutor.api.CommandStarApi;
import org.framework.tutor.domain.CommandStar;
import org.framework.tutor.service.CommandStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CommandStarApiImpl implements CommandStarApi {

    @Autowired
    private CommandStarService commandStarService;

    /**
     * 获取指定用户的指定课程评论的点赞数据
     *
     * @param cmid
     * @param request
     * @param response
     */
    @Override
    public void getMyCommandStar(Integer cmid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(8);

        //获取当前用户的评论数据
        CommandStar commandStar = commandStarService.getByUserAndCmid(username, cmid);
        if (commandStar == null) {
            resultMap.put("status", "uns");
        } else {
            resultMap.put("status", commandStar.getScore());
        }

        //获取当前评论的所有点赞值
        Integer score = 1;
        List<CommandStar> commandStarList = commandStarService.getCountByCmid(cmid, score);
        score = -1;
        List<CommandStar> commandStarList1 = commandStarService.getCountByCmid(cmid, score);
        resultMap.put("gcount", commandStarList.size());
        resultMap.put("bcount", commandStarList1.size());

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 实现评论的点赞与踩
     *
     * @param status
     * @param cmid
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void addMyStar(Integer score, Integer cmid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已经打分
        CommandStar commandStar = commandStarService.getByUserAndCmid(username, cmid);
        if (commandStar != null) {
            resultMap.put("status", "stared");
        } else {
            Integer row = commandStarService.addMyStar(username, cmid, score);
            if (row == 1) {
                resultMap.put("status", "success");
            } else {
                resultMap.put("status", "mysqlerr");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
