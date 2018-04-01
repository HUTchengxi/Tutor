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
import org.framework.tutor.domain.CommonImgsrc;
import org.framework.tutor.service.CommonImgsrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yinjimin
 * @Description: 通用图片数据控制类
 * @date 2018年04月01日
 */
@RestController
@RequestMapping("/commonimgsrc_con")
public class CommonImgsrcController {

    @Autowired
    private CommonImgsrcService commonImgsrcService;


    /**
     *
     * @Description 获取所有图片数据
     * @param [response]
     * @return void
     * @author yinjimin
     * @date 2018/4/1
     */
    @RequestMapping("/getAll")
    public void getAll(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<CommonImgsrc> commonImgsrcList = commonImgsrcService.getAll();

        int i = 0;
        res = "{";
        for (CommonImgsrc commonImgsrc: commonImgsrcList) {
            res += "\"" + (i++) + "\": ";
            String temp = "{\"imgsrc\": \"" + commonImgsrc.getImgsrc() +
                    "\",\"title\": \"" + commonImgsrc.getTitle() + "\"}, ";
            res += temp;
        }
        res = res.substring(0, res.length() - 2);
        res += "}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
