package org.framework.tutor.api;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public interface LoginApi {

    /**
     * 用户进行登陆
     * @param request
     * @param response
     * @param username
     * @param password
     * @param remember
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response,
                      String username, String password, Integer remember) throws IOException ;

    /**
     *  登录时判断是否已经记住密码并获取相关信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void getRememberUser(HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 返回当前用户登录状态信息
     * @param response
     */
    public void loginStatusCheck(HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    public void loginOff(HttpServletRequest request, HttpServletResponse response) throws IOException ;
}
