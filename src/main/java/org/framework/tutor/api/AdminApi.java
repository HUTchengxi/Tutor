package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface AdminApi {

    /**
     *
     * @Description 管理员登录
     * @param [username, password, remember, request]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    public void Login(String username, String password, HttpServletResponse response, HttpServletRequest request) throws IOException, NoSuchAlgorithmException;
}
