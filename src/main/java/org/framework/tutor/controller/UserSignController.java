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
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmysign")
    public String getMySign() throws IOException {

        return userSignApi.getMySign();
    }


    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/addusersign")
    public String addUsersign() throws IOException {

        return userSignApi.addUsersign();
    }
}
