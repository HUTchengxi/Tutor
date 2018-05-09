package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
                      String username, String password, Integer remember) throws IOException, NoSuchAlgorithmException;

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
