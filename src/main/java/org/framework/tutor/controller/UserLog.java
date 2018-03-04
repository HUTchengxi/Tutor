package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 用户登录记录控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/userlog_con")
public class UserLog {

    @Autowired
    private UserLService userLService;

    /**
     * 保存用户登录记录
     * @param logcity
     * @param ip
     * @param logsystem
     * @param request
     * @param response
     */
    @RequestMapping("/loginlog")
    public void loginLog(String logcity, String ip, String logsystem, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;
        String username = (String) session.getAttribute("username");

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            if(userLService.saveUserlog(username, logcity, ip, logsystem)){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取用户的登录记录
     * @param request
     * @param response
     */
    @RequestMapping("/getuserlog")
    public void getUserlog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"forward_con/welcome\"}";
        }
        else{
            //获取登录记录
            List<org.framework.tutor.domain.UserLog> userLogs  = userLService.getUserlog(username);
            if(userLogs.size() == 0){
                res = "{\"status\": \"ok\", \"len\": \"0\"}";
            }
            else {
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserLog userLog : userLogs) {
                    res += "\""+i+"\": ";
                    String temp = "{\"logtime\": \""+simpleDateFormat.format(userLog.getLogtime())+"\", " +
                            "\"logcity\": \""+userLog.getLogcity()+"\", " +
                            "\"logip\": \""+userLog.getLogip()+"\", " +
                            "\"logsystem\": \""+userLog.getLogsys()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
