package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserSecretApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户密保控制器
 * @author chengxi
 */
@RestController
@RequestMapping("/usersecret_con")
public class UserSecretController {

    @Autowired
    private UserSecretApi userSecretApi;

    /**
     * 获取当前用户的密保数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getsecretinfo")
    public String getSecretInfo(String username) throws IOException {

        return userSecretApi.getSecretInfo(username);
    }

    /**
     * 删除指定用户的密保数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/delusersecret")
    public String delUserSecret() throws IOException {

        return userSecretApi.delUserSecret();
    }

    /**
     * 为指定用户添加密保数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/addusersecret")
    public String addUserSecret(String question, String answer) throws IOException {

        return userSecretApi.addUserSecret(question, answer);
    }

}
