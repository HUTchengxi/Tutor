package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.RegisterApi;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.service.UserValiService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class RegisterApiImpl implements RegisterApi {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private UserValiService userValiService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String checkExistUser(String username) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (userMainService.userExist(username)) {
            resultMap.put("status", "invalid");
        } else {
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String registerNoCheck(String username, String password, String checktype, String telephone, String email,
                                  String phonecode) throws IOException, MessagingException, NoSuchAlgorithmException {

        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        //判断用户名是否已经被注册
        UserMain userMain = userMainService.getByUser(username);
        //非法调用api
        if(userMain != null){
            resultMap.put("status", "invalid");
        }
        else {
            String nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-", "")
                    .substring(0, 8);
            //验证nickname的唯一性
            while (userMainService.NickExist(nickname)) {
                nickname = "勤成游客" + UUID.randomUUID().toString().replaceAll("-", "")
                        .substring(0, 8);
            }

            //随机获取加密盐
            Integer salt = CommonUtil.getMd5Salt();

            //密码加盐
            password = CommonUtil.getMd5Pass(password, salt);

            //游客身份注册
            if ("none".equals(checktype)) {
                Integer identity = -2;
                //进行游客注册
                if (userMainService.registerNoCheck(identity, username, password, nickname, salt)) {
                    resultMap.put("status", "valid");
                    resultMap.put("url", "/forward_con/gologin");
                } else {
                    resultMap.put("status", "invalid");
                    resultMap.put("url", "#");
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
                    if(userMainService.registerByPhone(identity, username, password, nickname, telephone)){
                        resultMap.put("status", "valid");
                        resultMap.put("url", "/forward_con/gologin");
                    }
                    else{
                        resultMap.put("status", "mysqlerr");
                    }
                }
                else{
                    resultMap.put("status", "codeerr");
                }

            }
            //邮箱注册
            else if ("email".equals(checktype)) {
                //邮箱已被注册
                if (userMainService.emailExist(email)) {
                    resultMap.put("status", "exist");
                } else {
                    Integer identity = -2;

                    if (userMainService.registerByEmail(identity, username, password, nickname, email)) {

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
                        userValiService.addUserVali(username, valicode, status);

                        //保存用户邮箱以便后续刷新状态
                        request.getSession().setAttribute("email", email + " " + username);

                        resultMap.put("status", "valid");
                        resultMap.put("url", "forward_con/register_info");
                    } else {
                        resultMap.put("status", "mysqlerr");
                    }
                }
            }
            //非法调用api
            else {
                resultMap.put("status", "invalid");
            }
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String emailResend() throws IOException, MessagingException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (email == null) {
            resultMap.put("status", "invalid");
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
            userValiService.updateEmailCode(realusername, valicode);
            resultMap.put("status", "sendok");
        }

        return gson.toJson(resultMap);
    }
}
