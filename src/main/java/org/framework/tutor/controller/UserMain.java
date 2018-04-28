package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserMainApi;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserSCService;
import org.framework.tutor.util.CommonUtil;
import org.framework.tutor.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户个人信息控制类
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/usermain_con")
public class UserMain {

    @Autowired
    private UserMainApi userMainApi;

    /**
     * 获取我的个人头像
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getimgsrc")
    public void getImgsrc(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.getImgsrc(request, response);
    }

    /**
     * 获取我的个人信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getuserinfo")
    public void getUserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.getUserinfo(request, response);
    }

    /**
     * 我帮你换修改我的头像
     *
     * @param request
     * @param response
     * @param imgsrc
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/modimgsrc")
    public void modImgsrc(HttpServletRequest request, HttpServletResponse response, String imgsrc) throws IOException {

        userMainApi.modImgsrc(request, response, imgsrc);
    }

    /**
     * 手动上传修改我的头像
     *
     * @param request
     * @param response
     * @param imgfile
     * @param oimgsrc
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/modimgfile")
    public void modImgfile(HttpServletRequest request, HttpServletResponse response,
                           MultipartFile imgfile, String oimgsrc) throws IOException {

        userMainApi.modImgfile(request, response, imgfile, oimgsrc);
    }

    /**
     * 修改我的个人信息
     *
     * @param username
     * @param nickname
     * @param sex
     * @param age
     * @param info
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/moduserinfo")
    public void modUserinfo(String username, String nickname, Integer sex, Integer age, String info,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.modUserinfo(username, nickname, sex, age, info, request, response);
    }

    /**
     * 获取当前用户的绑定数据
     *
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getbindinfo")
    public void getBindInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.getBindInfo(request, response);
    }

    /**
     * 通过发送邮件找回密码
     *
     * @param email
     * @param username
     * @param response
     * @throws IOException
     */
    @RequestMapping("/forget_sendmail")
    public void sendMail(String email, String username, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        userMainApi.sendMail(email, username, request, response);
    }

    /**
     * 根据手机号/邮箱来重设密码
     *
     * @param username
     * @param email
     * @param phone
     * @param valicode
     * @param newpass
     * @param repass
     * @param request
     * @param response
     */
    @RequestMapping("/forget_modpass")
    public void modPass(String username, String email, String phone, String valicode, String newpass, String repass,
                        HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {

        userMainApi.modPass(username, email, phone, valicode, newpass, repass, request, response);
    }

    /**
     * 通过密保的方式进行找回密码
     *
     * @param queone
     * @param ansone
     * @param quetwo
     * @param anstwo
     * @param quethree
     * @param ansthree
     * @param password
     * @param username
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/forget_modpassbysecret")
    public void modPassBySecret(@RequestParam("queone") String queone, @RequestParam("ansone") String ansone,
                                String quetwo, String anstwo, String quethree, String ansthree, String password,
                                String username, HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {

        userMainApi.modPassBySecret(queone, ansone, quetwo, anstwo, quethree, ansthree, password, username, request, response);
    }

    /**
     * 解除绑定
     *
     * @param type
     * @param valicode
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/unbind_valicode")
    public void userUnbind(String type, String valicode, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.userUnbind(type, valicode, request, response);
    }

    /**
     * 发送绑定的验证短码
     *
     * @param type
     * @param email
     * @param phone
     * @param request
     * @param response
     */
    @RequestMapping("/sendbindcode")
    public void sendBindCode(@RequestParam("type") String type, String email, String phone, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.sendBindCode(type, email, phone, request, response);
    }

    /**
     * 进行邮箱/手机号码的绑定
     *
     * @param type
     * @param email
     * @param valicode
     * @param response
     * @param request
     */
    @RequestMapping("/userbind")
    public void userBind(@RequestParam("type") String type, String email, String valicode, HttpServletResponse response, HttpServletRequest request) throws IOException {

        userMainApi.userBind(type, email, valicode, response, request);
    }

    /**
     * 发送解除手机绑定的验证码
     *
     * @param phone
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/sendunbindphone")
    public void sendUnbindPhone(@RequestParam("phone") String phone, String username, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.sendUnbindPhone(phone, username, request, response);
    }

    /**
     * 发送手机注册验证码
     *
     * @param phone
     * @param request
     * @param response
     */
    @RequestMapping("/register_sendbindcode")
    public void sendRegisterBindCode(@RequestParam("phone") String phone, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMainApi.sendRegisterBindCode(phone, request, response);
    }
}
