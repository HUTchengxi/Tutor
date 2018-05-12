package org.framework.tutor.controller;

import org.framework.tutor.api.RegisterApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 处理注册的一系列请求
 * @author chengxi
 */
@RestController
@RequestMapping("/register_con")
public class RegisterController {

    @Autowired
    private RegisterApi registerApi;


    /**
     * 校验注册的用户名是否已经存在
     */
    @RequestMapping("/check_exist_user")
    public String checkExistUser(String username) throws IOException {

        return registerApi.checkExistUser(username);
    }

    /**
     * 进行注册
     */
    @RequestMapping("/register_main")
    public String registerNoCheck(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("checktype") String checktype,
                                String telephone, String email, String phonecode) throws IOException, MessagingException, NoSuchAlgorithmException {

        return registerApi.registerNoCheck(username, password, checktype, telephone, email, phonecode);
    }

    /**
     * 重发邮件
     */
    @RequestMapping("/register_resendemail")
    public String emailResend() throws IOException, MessagingException {

        return registerApi.emailResend();
    }
}
