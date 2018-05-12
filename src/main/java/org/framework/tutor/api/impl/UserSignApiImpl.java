package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.UserSignApi;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
public class UserSignApiImpl implements UserSignApi {

    @Autowired
    private UserSignService userSignService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String getMySign() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        //获取本月份
        Date now = new Date();
        Integer monI = now.getMonth() + 1;
        StringBuffer temp = new StringBuffer(monI.toString());
        String monthstr = temp.length() == 1 ? "-0" + temp.toString() + "-" : "-" + temp.toString() + "-";

        List<UserSign> userSigns = userSignService.getMySignNow(username, monthstr);
        if (userSigns.size() == 0) {
            resultMap.put("status", "valid");
        } else {
            StringBuffer date = new StringBuffer(",");
            for (org.framework.tutor.domain.UserSign userSign : userSigns) {
                date.append(userSign.getStime().getDate() + ",");
            }
            resultMap.put("date", date.toString());
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String addUsersign() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            resultMap.put("status", "invalid");
        } else {
            Integer row = userSignService.addUsersign(username);
            if (row != 1) {
                resultMap.put("status", "mysqlerr");
            } else {
                resultMap.put("status", "valid");
            }
        }

        return gson.toJson(resultMap);
    }
}
