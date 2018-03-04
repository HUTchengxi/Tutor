package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * 处理注册的一系列请求
 * @author chengxi
 */
@RestController
@RequestMapping("/register_con")
public class Register {

    @Autowired
    private UserMService userMService;

    /**
     * 校验注册的用户名是否已经存在CSDN
     *
     * @param response
     * @param username
     */
    @RequestMapping("/check_exist_user")
    public void checkExistUser(HttpServletResponse response, String username) throws IOException {

        response.setCharacterEncoding("utf-8");;

        PrintWriter writer = response.getWriter();
        String res = null;

        if(userMService.userExist(username)){
            res = "{\"status\":\"invalid\"}";
        }
        else{
            res = "{\"status\":\"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 咱不验证的方式进行注册
     * @param response
     * @param username
     * @param password
     */
    @RequestMapping("/register_nocheck")
    public void registerNoCheck(HttpServletResponse response, String username, String password) throws IOException {

        response.setCharacterEncoding("utf-8");;

        PrintWriter writer = response.getWriter();
        String res = null;

        String nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-","")
                .substring(0,8);

        Integer identity = -2;

        //验证nickname的唯一性
        while(userMService == null){
            nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-","")
                    .substring(0,8);
        }

        //进行游客注册
        if(userMService.registerNoCheck(identity, username, password, nickname)){
            res = "{\"status\": \"valid\", \"url\": \"/forward_con/gologin\"}";
        }
        else{
            res = "{\"status\": \"invalid\", \"url\": \"#\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
