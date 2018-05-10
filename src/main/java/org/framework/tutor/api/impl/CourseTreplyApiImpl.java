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
import org.framework.tutor.api.CourseTreplyApi;
import org.framework.tutor.service.CourseTreplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CourseTreplyApiImpl implements CourseTreplyApi {

    @Autowired
    private CourseTreplyService courseTreplyService;

    /**
     * 获取对应用户的指定课程的家教回复数据
     * @param cid
     * @param id
     * @param response
     * @throws IOException
     */
    //TODO：后续考虑使用redis
    @Override
    public void getTreply(Integer cid, Integer cmid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        org.framework.tutor.domain.CourseTreply courseTreply = courseTreplyService.getCourseTreply(cid, cmid);

        if(courseTreply == null){
            resultMap.put("info", "null");
        }
        else {
            resultMap.put("info", courseTreply.getInfo());
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
