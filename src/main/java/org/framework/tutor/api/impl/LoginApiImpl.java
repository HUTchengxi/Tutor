package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.LoginApi;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginApiImpl implements LoginApi{

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    @Override
    public String login(String username, String password, Integer remember) throws IOException, NoSuchAlgorithmException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        //用户名不存在
        if(!userMainService.userExist(username)){
            resultMap.put("status", "nouser");
            resultMap.put("url", "#");
        }
        //密码错误
        else {
            //获取密码盐
            Integer salt = userMainService.getByUser(username).getSalt();
            //明文传来的密码加盐判断
            String MD5Pass = CommonUtil.getMd5Pass(password, salt);

            if (!userMainService.passCheck(username, MD5Pass)) {

                resultMap.put("status", "passerr");
                resultMap.put("url", "#");
            }
            //登陆成功
            else {
                //保存当前登陆状态（以后考虑使用shiro）
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                //保存昵称和当前用户身份
                UserMain userMain = userMainService.getByUser(username);
                session.setAttribute("nickname", userMain.getNickname());
                session.setAttribute("identity", userMain.getIdentity());
                resultMap.put("status", "ok");
                resultMap.put("url", "/forward_con/welcome");

                //记住密码
                if (remember == 1) {
                    Cookie usercookie = new Cookie("username", username);
                    Cookie passcookie = new Cookie("password", password);
                    usercookie.setMaxAge(2 * 60 * 60 * 24);
                    passcookie.setMaxAge(2 * 60 * 60 * 24);
                    usercookie.setPath("/");
                    passcookie.setPath("/");
                    response.addCookie(usercookie);
                    response.addCookie(passcookie);
                }
                //清空之前记住的密码
                else {
                    Cookie[] cookies = request.getCookies();
                    for (Cookie cookie : cookies) {
                        if ("username".equals(cookie.getName())) {
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                        if ("password".equals(cookie.getName())) {
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getRememberUser() throws IOException {

        Cookie[] cookies = request.getCookies();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        String user = null;
        String pass = null;
        if(cookies == null || cookies.length <= 0){
            resultMap.put("status", "none");
        }
        else {
            for (int i = 0; i < cookies.length; i++) {
                if ("username".equals(cookies[i].getName())) {
                    user = cookies[i].getValue();
                }
                if ("password".equals(cookies[i].getName())) {
                    pass = cookies[i].getValue();
                }
            }

            if (user == null) {
                resultMap.put("status", "none");
            } else {
                resultMap.put("username", user);
                resultMap.put("password", pass);
            }
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String loginStatusCheck() throws IOException {

        HttpSession session = request.getSession();
        String nickname = (String) session.getAttribute("nickname");
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(8);

        if(nickname == null){
            resultMap.put("status", "nologin");
            resultMap.put("nick", "null");
            resultMap.put("ident", "null");
        }
        else{
            Integer ident = (Integer) session.getAttribute("identity");
            resultMap.put("status", "login");
            resultMap.put("nick", nickname);
            resultMap.put("username", username);
            resultMap.put("ident", ident);
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String loginOff() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String nickname = (String) session.getAttribute("nickname");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        //非法获取api侵入
        if(nickname == null){
            resultMap.put("status", "invalid url");
        }
        else {
            //清楚session里的所有信息，并使session失效
            session.removeAttribute("identity");
            session.removeAttribute("username");
            session.removeAttribute("nickname");
            resultMap.put("status", "logoff");
        }

        return gson.toJson(resultMap);
    }
}
