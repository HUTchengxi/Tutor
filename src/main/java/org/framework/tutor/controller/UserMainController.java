package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserMainApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 * 用户个人信息控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/usermain_con")
public class UserMainController {

    @Autowired
    private UserMainApi userMainApi;

    /**
     * 获取我的个人头像
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getimgsrc")
    public String getImgsrc() throws IOException {

        return userMainApi.getImgsrc();
    }

    /**
     * 获取我的个人信息
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getuserinfo")
    public String getUserinfo() throws IOException {

        return userMainApi.getUserinfo();
    }

    /**
     * 我帮你换修改我的头像
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/modimgsrc")
    public String modImgsrc(String imgsrc) throws IOException {

        return userMainApi.modImgsrc(imgsrc);
    }

    /**
     * 手动上传修改我的头像
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/modimgfile")
    public String modImgfile(MultipartFile imgfile, String oimgsrc) throws IOException {

        return userMainApi.modImgfile(imgfile, oimgsrc);
    }

    /**
     * 修改我的个人信息
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/moduserinfo")
    public String modUserinfo(String username, String nickname, Integer sex, Integer age, String info) throws IOException {

        return userMainApi.modUserinfo(username, nickname, sex, age, info);
    }

    /**
     * 获取当前用户的绑定数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getbindinfo")
    public String getBindInfo() throws IOException {

        return userMainApi.getBindInfo();
    }

    /**
     * 通过发送邮件找回密码
     */
    @RequestMapping("/forget_sendmail")
    public String sendMail(String email, String username) throws IOException, MessagingException {

        return userMainApi.sendMail(email, username);
    }

    /**
     * 根据手机号/邮箱来重设密码
     */
    @RequestMapping("/forget_modpass")
    public String modPass(String username, String email, String phone, String valicode, String newpass, String repass) throws IOException, NoSuchAlgorithmException {

        return userMainApi.modPass(username, email, phone, valicode, newpass, repass);
    }

    /**
     * 通过密保的方式进行找回密码
     */
    @RequestMapping("/forget_modpassbysecret")
    public String modPassBySecret(@RequestParam("queone") String queone, @RequestParam("ansone") String ansone,
                                String quetwo, String anstwo, String quethree, String ansthree, String password,
                                String username) throws IOException, NoSuchAlgorithmException {

        return userMainApi.modPassBySecret(queone, ansone, quetwo, anstwo, quethree, ansthree, password, username);
    }

    /**
     * 解除绑定
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/unbind_valicode")
    public String userUnbind(String type, String valicode) throws IOException {

        return userMainApi.userUnbind(type, valicode);
    }

    /**
     * 发送绑定的验证短码
     */
    @RequestMapping("/sendbindcode")
    public String sendBindCode(@RequestParam("type") String type, String email, String phone) throws IOException {

        return userMainApi.sendBindCode(type, email, phone);
    }

    /**
     * 进行邮箱/手机号码的绑定
     */
    @RequestMapping("/userbind")
    public String userBind(@RequestParam("type") String type, String email, String valicode) throws IOException {

        return userMainApi.userBind(type, email, valicode);
    }

    /**
     * 发送解除手机绑定的验证码
     */
    @RequestMapping("/sendunbindphone")
    public String sendUnbindPhone(@RequestParam("phone") String phone, String username) throws IOException {

        return userMainApi.sendUnbindPhone(phone, username);
    }

    /**
     * 发送手机注册验证码
     */
    @RequestMapping("/register_sendbindcode")
    public String sendRegisterBindCode(@RequestParam("phone") String phone) throws IOException {

        return userMainApi.sendRegisterBindCode(phone);
    }
}
