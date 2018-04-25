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
import org.framework.tutor.api.CourseDeleteRespApi;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.service.CourseDeleteRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class CourseDeleteRespApiImpl implements CourseDeleteRespApi {

    @Autowired
    private CourseDeleteRespService courseDeleteRespService;

    /**
     *
     * @Description 更新状态
     * @param [reqid, status, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    @Override
    public void modReqStatus(Integer id, Integer status, String respDesc, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        CourseDeleteResp courseDeleteResp = courseDeleteRespService.getByRid(id);
        if(courseDeleteResp == null){
            courseDeleteRespService.insertResp(id, status, respDesc);
        }else{
            courseDeleteRespService.updateResp(id, status, respDesc);
        }
        resultMap.put("status", "valid");

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
