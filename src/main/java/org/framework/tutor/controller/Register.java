package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserVService;
import org.framework.tutor.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
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

    @Autowired
    private UserVService userVService;

    /**
     * 校验注册的用户名是否已经存在
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
     * 进行注册
     * @param response
     * @param username
     * @param password
     * @param checktype
     * @param telephone
     * @param email
     */
    @RequestMapping("/register_main")
    public void registerNoCheck(HttpServletResponse response, String username, String password, String checktype, String telephone, String email) throws IOException, MessagingException {

        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;

        //非法调用api
        if(checktype == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            String nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-","")
                    .substring(0,8);
            //验证nickname的唯一性
            while(userMService.NickExist(nickname)){
                nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-","")
                        .substring(0,8);
            }
            //游客身份注册
            if("none".equals(checktype)) {

                Integer identity = -2;
                //进行游客注册
                if (userMService.registerNoCheck(identity, username, password, nickname)) {
                    res = "{\"status\": \"valid\", \"url\": \"/forward_con/gologin\"}";
                } else {
                    res = "{\"status\": \"invalid\", \"url\": \"#\"}";
                }
            }
            //手机号码注册
            else if("telephone".equals(checktype)){

            }
            //邮箱注册
            else if("email".equals(checktype)){
                //邮箱已被注册
                if(userMService.emailExist(email)){
                    res = "{\"status\": \"exist\"}";
                }
                else{
                    Integer identity = -1;

                    if(userMService.registerByEmail(identity, username, password, nickname, email)){

                        //发送邮箱验证码
                        String valicode = EmailUtil.sendValiEmail(email, username);

                        //保存邮箱验证码
                        Integer status = 0;
                        userVService.addUserVali(username, valicode, status);

                        res = "{\"status\": \"valid\", \"url\": \"/forward_con/regwaiting\"}";
                    }
                    else{
                        res = "{\"status\": \"mysqlerr\"}";
                    }
                }
            }
            //非法调用api
            else{
                res = "{\"status\": \"invalid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    
}
