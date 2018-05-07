package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserSecretApi;
import org.framework.tutor.service.UserSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 用户密保控制器
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/usersecret_con")
public class UserSecretController {

    @Autowired
    private UserSecretApi userSecretApi;

    /**
     * 获取当前用户的密保数据
     *
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getsecretinfo")
    public void getSecretInfo(String username, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userSecretApi.getSecretInfo(username, request, response);
    }

    /**
     * 删除指定用户的密保数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/delusersecret")
    public void delUserSecret(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userSecretApi.delUserSecret(request, response);
    }

    /**
     * 为指定用户添加密保数据
     *
     * @param question
     * @param answer
     * @param response
     * @param request
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/addusersecret")
    public void addUserSecret(String question, String answer, HttpServletResponse response, HttpServletRequest request) throws IOException {

        userSecretApi.addUserSecret(question, answer, response, request);
    }

}
