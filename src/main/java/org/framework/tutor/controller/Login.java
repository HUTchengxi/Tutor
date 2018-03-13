package org.framework.tutor.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理登录的一系列请求
 * @author chengxi
 */
@RestController
@RequestMapping("/login_con")
public class Login {

    @Autowired
    private UserMService userMService;

    /**
     * 用户进行登陆
     * @param request
     * @param response
     * @param username
     * @param password
     * @param remember
     * @throws IOException
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request,HttpServletResponse response,
                      String username, String password, Integer remember) throws IOException {


        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        Gson gson = new Gson();
        String res = "";

        //用户名不存在
        if(!userMService.userExist(username)){
            res = "{status: \"nouser\",url: \"#\"}";
        }
        //密码错误
        else if(!userMService.passCheck(username, password)){
            res = "{status: \"passerr\",url: \"#\"}";
        }
        //登陆成功
        else{
            //保存当前登陆状态（以后考虑使用shiro）
            HttpSession session = request.getSession();
            session.setAttribute("username",username);
            //保存昵称和当前用户身份
            UserMain userMain = userMService.getByUser(username);
            session.setAttribute("nickname",userMain.getNickname());
            session.setAttribute("identity",userMain.getIdentity());
            res = "{status: \"ok\",url: \"/forward_con/welcome\"}";

            //记住密码
            if(remember == 1){
                Cookie usercookie = new Cookie("username", username);
                Cookie passcookie = new Cookie("password", password);
                usercookie.setMaxAge(2*60*60*24);
                passcookie.setMaxAge(2*60*60*24);
                usercookie.setPath("/");
                passcookie.setPath("/");
                response.addCookie(usercookie);
                response.addCookie(passcookie);
            }
            //清空之前记住的密码
            else{
                Cookie[] cookies = request.getCookies();
                for(Cookie cookie: cookies){
                    if("username".equals(cookie.getName())){
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                    if("password".equals(cookie.getName())){
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     *  登录时判断是否已经记住密码并获取相关信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getrememberuser")
    public void getRememberUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();
        PrintWriter writer = response.getWriter();
        String res = null;

        String user = null;
        String pass = null;
        for(int i=0; i<cookies.length; i++){
            if("username".equals(cookies[i].getName())){
                user = cookies[i].getValue();
            }
            if("password".equals(cookies[i].getName())){
                pass = cookies[i].getValue();
            }
        }

        if(user == null){
            res = "{\"status\": \"none\"}";
        }
        else{
            res = "{\"username\": \""+user+"\", \"password\": \""+pass+"\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 返回当前用户登录状态信息
     * @param response
     */
    @RequestMapping("/login_statuscheck")
    public void loginStatusCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String nickname = (String) session.getAttribute("nickname");

        if(nickname == null){
            res = "{\"status\": \"nologin\", \"nick\": \"null\", \"ident\": \"null\"}";
        }
        else{
            Integer ident = (Integer) session.getAttribute("identity");
            res = "{\"status\": \"login\", \"nick\": \""+nickname+"\", \"ident\": \""+ident+"\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/login_logoff")
    public void loginOff(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String nickname = (String) session.getAttribute("nickname");

        //非法获取api侵入
        if(nickname == null){
            res = "{\"status\": \"invalid url\"}";
        }
        else {
            //清楚session里的所有信息，并使session失效
            session.invalidate();
            res = "{\"status\": \"logoff\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
