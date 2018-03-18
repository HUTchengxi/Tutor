package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 用户密保控制器
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/usersecret_con")
public class UserSecret {

    @Autowired
    private UserSCService userSCService;

    /**
     * 获取当前用户的密保数据
     *
     * @param request
     * @param response
     */
    @RequestMapping("/getsecretinfo")
    public void getSecretInfo(String username, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;

        if (username == null) {
            username = (String) session.getAttribute("username");
            if (username == null) {
                res = "{\"status\": \"invalid\"}";
                writer.print(new JsonParser().parse(res).getAsJsonObject());
                writer.flush();
                writer.close();
                return;
            }
        }
        List<main.java.org.framework.tutor.domain.UserSecret> userSecretList = userSCService.getSecretInfoByUsername(username);
        if (userSecretList.size() == 0) {
            res = "{\"status\": \"valid\"}";
        } else {
            res = "{";
            int i = 1;
            for (main.java.org.framework.tutor.domain.UserSecret userSecret : userSecretList) {
                res += "\"" + i + "\": ";
                String temp = "{\"question\": \"" + userSecret.getQuestion() + "\", " +
                        "\"answer\": \"" + userSecret.getAnswer() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 删除指定用户的密保数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/delusersecret")
    public void delUserSecret(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            //删除当前用户的所有密保数据
            userSCService.delUserSecret(username);
            res = "{\"status\": \"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 为指定用户添加密保数据
     *
     * @param question
     * @param answer
     * @param response
     * @param request
     */
    @RequestMapping("/addusersecret")
    public void addUserSecret(String question, String answer, HttpServletResponse response, HttpServletRequest request) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            Integer row = userSCService.addUserSecret(question, answer, username);
            if (row <= 0) {
                res = "{\"status\": \"mysqlerr\"}";
            } else {
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

}
