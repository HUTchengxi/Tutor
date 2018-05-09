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
import org.framework.tutor.api.RankApi;
import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class RankApiImpl implements RankApi {

    @Autowired
    private UserSignService userSignService;

    @Autowired
    private UserMainService userMainService;

    /**
     * 获取rank榜数据
     * @param type
     * @param mark
     * @param startpos
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void rankSelect(String type, String mark, Integer startpos, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        //最勤打卡榜
        if("sign".equals(type)){
            //日榜
            if("day".equals(mark)){
                //获取今天的日期
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("-MM-dd");
                String daystr = sdf.format(now);

                List<UserSign> userSigns = userSignService.rankSignDay(daystr, startpos);
                if(userSigns.size() == 0){
                    res = "{\"count\": \"0\"}";
                }
                else{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    res = "{";
                    int i = 1;
                    for (UserSign userSign: userSigns) {
                        UserMain userMain = userMainService.getByUser(userSign.getUsername());
                        res += "\""+i+"\": ";
                        String temp = "{\"nickname\": \""+userMain.getNickname()+"\", " +
                                "\"stime\": \""+simpleDateFormat.format(userSign.getStime())+"\"}, ";
                        res += temp;
                        i++;
                    }
                    res = res.substring(0, res.length()-2);
                    res += "}";
                }
            }
            //总榜
            else if("total".equals(mark)){

                System.out.println("total");
                List<RankTemp> userSigns = userSignService.rankSignTotal(startpos);
                if(userSigns.size() == 0){
                    res = "{\"count\": \"0\"}";
                }
                else{
                    res = "{";
                    int i = 1;
                    for (RankTemp userSign: userSigns) {
                        UserMain userMain = userMainService.getByUser(userSign.getUsername());
                        res += "\"" + i + "\": ";
                        String temp = "{\"nickname\": \"" + userMain.getNickname() + "\", " +
                                "\"stime\": \"" +userSign.getTotal()+ "\"}, ";
                        res += temp;
                        i++;
                    }
                    res = res.substring(0, res.length()-2);
                    res += "}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
