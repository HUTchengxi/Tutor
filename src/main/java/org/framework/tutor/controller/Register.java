package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserVService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * 处理注册的一系列请求
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/register_con")
public class Register {

    @Autowired
    private UserMService userMService;

    @Autowired
    private UserVService userVService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 校验注册的用户名是否已经存在
     *
     * @param response
     * @param username
     */
    @RequestMapping("/check_exist_user")
    public void checkExistUser(HttpServletResponse response, String username) throws IOException {

        response.setCharacterEncoding("utf-8");
        ;

        PrintWriter writer = response.getWriter();
        String res = null;

        if (userMService.userExist(username)) {
            res = "{\"status\":\"invalid\"}";
        } else {
            res = "{\"status\":\"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
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
                                String telephone, String email, String phonecode) throws IOException, MessagingException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;

        //判断用户名是否已经被注册
        UserMain userMain = userMService.getByUser(username);
        //非法调用api
        if(userMain != null){
            res = "{\"status\": \"invalid\"}";
        }
        else {
            String nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-", "")
                    .substring(0, 8);
            //验证nickname的唯一性
            while (userMService.NickExist(nickname)) {
                nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-", "")
                        .substring(0, 8);
            }
            //游客身份注册
            if ("none".equals(checktype)) {
                Integer identity = -2;
                //进行游客注册
                if (userMService.registerNoCheck(identity, username, password, nickname)) {
                    res = "{\"status\": \"valid\", \"url\": \"/forward_con/gologin\"}";
                } else {
                    res = "{\"status\": \"invalid\", \"url\": \"#\"}";
                }
            }
            //手机号码注册
            else if ("telephone".equals(checktype)) {

                //判断手机号码和验证短码是否对应
                String realcode = (String) session.getAttribute("bindcode");
                String realphone = (String) session.getAttribute("bindphone");
                if(realcode != null && realcode.equals(phonecode) && realphone != null & realphone.equals(telephone)){

                    //进行注册
                    Integer identity = 0;
                    if(userMService.registerByPhone(identity, username, password, nickname, telephone)){
                        res = "{\"status\": \"valid\", \"url\": \"/forward_con/gologin\"}";
                    }
                    else{
                        res = "{\"status\": \"mysqlerr\"}";
                    }
                }
                else{
                    res = "{\"status\": \"codeerr\"}";
                }

            }
            //邮箱注册
            else if ("email".equals(checktype)) {
                //邮箱已被注册
                if (userMService.emailExist(email)) {
                    res = "{\"status\": \"exist\"}";
                } else {
                    Integer identity = -2;

                    if (userMService.registerByEmail(identity, username, password, nickname, email)) {

                        //发送邮箱验证码
                        String valicode = CommonUtil.getUUID();
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setFrom(from);
                        message.setTo(email);
                        message.setSubject("勤成家教网---邮箱验证链接");
                        message.setText("点击链接<a href='http://localhost:8080/forward_con/register_check?username=" + username + "&valicode=" + valicode + "'>http://localhost:8080/register_con/register_check</a>进行验证");
                        javaMailSender.send(message);

                        //保存邮箱验证码
                        Integer status = 0;
                        userVService.addUserVali(username, valicode, status);

                        //保存用户邮箱以便后续刷新状态
                        request.getSession().setAttribute("email", email + " " + username);

                        res = "{\"status\": \"valid\", \"url\": \"/forward_con/register_info\"}";
                    } else {
                        res = "{\"status\": \"mysqlerr\"}";
                    }
                }
            }
            //非法调用api
            else {
                res = "{\"status\": \"invalid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
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

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String res = null;

        if (email == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            String realemail = email.split(" ")[0];
            String realusername = email.split(" ")[1];
            //发送邮箱验证码
            String valicode = CommonUtil.getUUID();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(realemail);
            message.setSubject("勤成家教网---邮箱验证链接");
            message.setText("点击链接<a href='http://localhost:8080/forward_con/register_check?username=" + realusername + "&valicode=" + valicode + "'>http://localhost:8080/register_con/register_check</a>进行验证");
            javaMailSender.send(message);

            //更新邮箱注册码
            userVService.updateEmailCode(realusername, valicode);
            res = "{\"status\": \"sendok\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


}
