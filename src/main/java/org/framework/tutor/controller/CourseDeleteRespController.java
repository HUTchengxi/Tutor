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

import com.google.gson.Gson;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseDeleteRespApi;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.service.CourseDeleteRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月21日
 */
@RestController
@RequestMapping("/coursedeleteresp_con")
public class CourseDeleteRespController {

    @Autowired
    private CourseDeleteRespApi courseDeleteRespApi;

    /**
     *
     * @Description 更新状态
     * @param [reqid, status, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/modreqstatus")
    public void modReqStatus(@RequestParam Integer id, @RequestParam Integer status, @RequestParam String respDesc, HttpServletResponse response) throws IOException {

        courseDeleteRespApi.modReqStatus(id, status, respDesc, response);
    }
}
