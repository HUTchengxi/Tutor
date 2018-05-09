package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserSignApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户签到控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/usersign_con")
public class UserSignController {

    @Autowired
    private UserSignApi userSignApi;

    /**
     * 获取用户的签到数据
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmysign")
    public void getMySign(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userSignApi.getMySign(request, response);
    }


    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/addusersign")
    public void addUsersign(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userSignApi.addUsersign(request, response);
    }
}
