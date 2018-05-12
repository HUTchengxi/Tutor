package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserLogApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/loginlog")
    public String loginLog(String logcity, String ip, String logsystem) throws IOException {

        return userLogApi.loginLog(logcity, ip, logsystem);
    }

    /**
     * 获取用户的登录记录
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getuserlog")
    public String getUserlog() throws IOException {

        return userLogApi.getUserlog();
    }
}
