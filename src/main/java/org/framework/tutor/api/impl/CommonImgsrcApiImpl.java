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
import org.framework.tutor.api.CommonImgsrcApi;
import org.framework.tutor.domain.CommonImgsrc;
import org.framework.tutor.service.CommonImgsrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
public class CommonImgsrcApiImpl implements CommonImgsrcApi {

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
    //TODO：后续可以考虑加入redis缓存
    @Override
    public void getAll(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<CommonImgsrc> commonImgsrcList = commonImgsrcService.getAll();

        for (CommonImgsrc commonImgsrc: commonImgsrcList) {
            Map<String, Object> rowMap = new HashMap<>(4);
            rowMap.put("imgsrc", commonImgsrc.getImgsrc());
            rowMap.put("title", commonImgsrc.getTitle());
            rowList.add(rowMap);
        }
        resultMap.put("list", rowList);

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
