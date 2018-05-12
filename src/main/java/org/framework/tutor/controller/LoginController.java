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
public class LoginController {

    @Autowired
    private LoginApi loginApi;

    /**
     * 用户进行登陆
     */
    @OneLogin
    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Integer remember) throws IOException, NoSuchAlgorithmException {

        return loginApi.login(username, password, remember);
    }

    /**
     *  登录时判断是否已经记住密码并获取相关信息
     */
    @RequestMapping("/getrememberuser")
    public String getRememberUser() throws IOException {

        return loginApi.getRememberUser();
    }

    /**
     * 返回当前用户登录状态信息
     */
    @RequestMapping("/login_statuscheck")
    public String loginStatusCheck() throws IOException {

        return loginApi.loginStatusCheck();
    }

    /**
     * 退出登录
     */
    @RequestMapping("/login_logoff")
    public String loginOff() throws IOException {

        return loginApi.loginOff();
    }
}
