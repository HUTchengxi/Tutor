package org.framework.tutor.controller;

import org.framework.tutor.annotation.OneLogin;
import org.framework.tutor.api.LoginApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 处理登录的一系列请求
 * @author chengxi
 */
@RestController
@RequestMapping("/login_con")
public class Login {

    @Autowired
    private LoginApi loginApi;

    /**
     * 用户进行登陆
     * @param request
     * @param response
     * @param username
     * @param password
     * @param remember
     * @throws IOException
     */
    @OneLogin
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam String username, @RequestParam String password, Integer remember) throws IOException, NoSuchAlgorithmException {
        loginApi.login(request, response, username, password, remember);
    }

    /**
     *  登录时判断是否已经记住密码并获取相关信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getrememberuser")
    public void getRememberUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginApi.getRememberUser(request, response);
    }

    /**
     * 返回当前用户登录状态信息
     * @param response
     */
    @RequestMapping("/login_statuscheck")
    public void loginStatusCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginApi.loginStatusCheck(request, response);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/login_logoff")
    public void loginOff(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginApi.loginOff(request, response);
    }
}
