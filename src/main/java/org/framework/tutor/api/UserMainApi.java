package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
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
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public interface UserMainApi {

    /**
     * 获取我的个人头像
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getImgsrc(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取我的个人信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getUserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 我帮你换修改我的头像
     *
     * @param request
     * @param response
     * @param imgsrc
     */
    public void modImgsrc(HttpServletRequest request, HttpServletResponse response, String imgsrc) throws IOException;

    /**
     * 手动上传修改我的头像
     *
     * @param request
     * @param response
     * @param imgfile
     * @param oimgsrc
     */
    public void modImgfile(HttpServletRequest request, HttpServletResponse response,
                           MultipartFile imgfile, String oimgsrc) throws IOException ;

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
    public void modUserinfo(String username, String nickname, Integer sex, Integer age, String info,
                            HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取当前用户的绑定数据
     *
     * @param request
     * @param response
     */
    public void getBindInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 通过发送邮件找回密码
     *
     * @param email
     * @param username
     * @param response
     * @throws IOException
     */
    public void sendMail(String email, String username, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException;

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
    public void modPass(String username, String email, String phone, String valicode, String newpass, String repass,
                        HttpServletRequest request, HttpServletResponse response) throws IOException;

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
    public void modPassBySecret(String queone, String ansone,
                                String quetwo, String anstwo, String quethree, String ansthree, String password,
                                String username, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 解除绑定
     *
     * @param type
     * @param valicode
     * @param request
     * @param response
     */
    public void userUnbind(String type, String valicode, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 发送绑定的验证短码
     *
     * @param type
     * @param email
     * @param phone
     * @param request
     * @param response
     */
    public void sendBindCode(String type, String email, String phone, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 进行邮箱/手机号码的绑定
     *
     * @param type
     * @param email
     * @param valicode
     * @param response
     * @param request
     */
    public void userBind(String type, String email, String valicode, HttpServletResponse response, HttpServletRequest request) throws IOException;

    /**
     * 发送解除手机绑定的验证码
     *
     * @param phone
     * @param request
     * @param response
     * @throws IOException
     */
    public void sendUnbindPhone(String phone, String username, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 发送手机注册验证码
     *
     * @param phone
     * @param request
     * @param response
     */
    public void sendRegisterBindCode(String phone, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
