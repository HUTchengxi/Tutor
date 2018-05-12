package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.UserSecretApi;
import org.framework.tutor.service.UserSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserSecretApiImpl implements UserSecretApi {

    @Autowired
    private UserSecretService userSecretService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String getSecretInfo(String username) throws IOException {

        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<main.java.org.framework.tutor.domain.UserSecret> userSecretList = userSecretService.getSecretInfoByUsername(username);
        if (userSecretList.size() == 0) {
            resultMap.put("status", "valid");
        } else {
            for (main.java.org.framework.tutor.domain.UserSecret userSecret : userSecretList) {
                Map<String, Object> rowMap = new HashMap<>(4);
                rowMap.put("question", userSecret.getQuestion());
                rowMap.put("answer", userSecret.getAnswer());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String delUserSecret() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //删除当前用户的所有密保数据
        userSecretService.delUserSecret(username);
        resultMap.put("status", "valid");

        return gson.toJson(resultMap);
    }


    @Override
    public String addUserSecret(String question, String answer) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = userSecretService.addUserSecret(question, answer, username);
        if (row <= 0) {
            resultMap.put("status", "mysqlerr");
        } else {
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }
}
