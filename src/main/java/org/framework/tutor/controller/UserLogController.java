package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserLogApi;
import org.framework.tutor.service.UserLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 用户登录记录控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/userlog_con")
public class UserLogController {

    @Autowired
    private UserLogApi userLogApi;

    /**
     * 保存用户登录记录
     * @param logcity
     * @param ip
     * @param logsystem
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/loginlog")
    public void loginLog(String logcity, String ip, String logsystem, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        userLogApi.loginLog(logcity, ip, logsystem, request, response);
    }

    /**
     * 获取用户的登录记录
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getuserlog")
    public void getUserlog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userLogApi.getUserlog(request, response);
    }
}
