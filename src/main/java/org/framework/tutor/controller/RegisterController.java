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
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/register_con")
public class RegisterController {

    @Autowired
    private RegisterApi registerApi;

    /**
     * 校验注册的用户名是否已经存在
     *
     * @param response
     * @param username
     */
    @RequestMapping("/check_exist_user")
    public void checkExistUser(HttpServletResponse response, String username) throws IOException {
        registerApi.checkExistUser(response, username);
    }

    /**
     * 进行注册
     *
     * @param request
     * @param response
     * @param username
     * @param password
     * @param checktype
     * @param telephone
     * @param phonecode
     * @param email
     */
    @RequestMapping("/register_main")
    public void registerNoCheck(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username,
                                @RequestParam("password") String password, @RequestParam("checktype") String checktype,
                                String telephone, String email, String phonecode) throws IOException, MessagingException, NoSuchAlgorithmException {
        registerApi.registerNoCheck(request, response, username, password, checktype, telephone, email, phonecode);
    }

    /**
     * 重发邮件
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/register_resendemail")
    public void emailResend(HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {
        registerApi.emailResend(request, response);
    }
}
