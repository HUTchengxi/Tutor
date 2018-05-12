package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface LoginApi {

    /**
     * 用户进行登陆
     * @param username
     * @param password
     * @param remember 是否记住密码
     */
    public String login(String username, String password, Integer remember) throws IOException, NoSuchAlgorithmException;

    /**
     *  登录时判断是否已经记住密码并获取相关信息
     */
    public String getRememberUser() throws IOException ;

    /**
     * 返回当前用户登录状态信息
     */
    public String loginStatusCheck() throws IOException ;

    /**
     * 退出登录
     */
    public String loginOff() throws IOException ;
}
