package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserMSService;
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
 * 用户通知控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/usermessage_con")
public class UserMessage {

    @Autowired
    private UserMSService userMSService;

    /**
     * 获取我的未读通知的数量
     * @param request
     * @param response
     */
    @RequestMapping("/getmymessagecount")
    public void getMyMessageCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;
        String username = (String) session.getAttribute("username");

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        }
        else{
            Integer count = userMSService.getMyMessageCount(username);
            res = "{\"count\": \""+count+"\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取我的通知数据(简单数据)
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getmymessage")
    public void getMyMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            //首先获取所有的我的通知数据
            List<org.framework.tutor.domain.UserMessage> userMessageList = userMSService.getMyMessage(username);
            if(userMessageList.size() == 0){
                res = "{\"status\": \"valid\"}";
            }
            else {
                res = "{";
                int i = 1;
                for (org.framework.tutor.domain.UserMessage userMessage: userMessageList) {
                    //获取指定发送通知的管理员的指定用户的未读通知总数据
                    Integer nocount = userMSService.getNoMessageCount(userMessage.getSuser(), username);
                    res += "\"" + i + "\": ";
                    String temp = "{\"suser\": \"" + userMessage.getSuser() + "\", " +
                            "\"nocount\": \"" + nocount + "\", " +
                            "\"imgsrc\": \"/images/user/face/bg.png\", " +
                            "\"title\": \"" + userMessage.getTitle() + "\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length() - 2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定管理员发送的我的通知数据
     * @param suser
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getmessagebysuser")
    public void getMessageByUser(String suser,HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            //首先获取所有的我的通知数据
            List<org.framework.tutor.domain.UserMessage> userMessageList = userMSService.getMessageBySuser(suser, username);
            if(userMessageList.size() == 0){
                res = "{\"status\": \"valid\"}";
            }
            else {
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserMessage userMessage: userMessageList) {
                    res += "\"" + i + "\": ";
                    String temp = "{\"status\": \"" + userMessage.getStatus() + "\", " +
                            "\"stime\": \"" + simpleDateFormat.format(userMessage.getStime()) + "\", " +
                            "\"id\": \"" + userMessage.getId() + "\", " +
                            "\"imgsrc\": \"/images/user/face/bg.png\", " +
                            "\"descript\": \"" + userMessage.getDescript() + "\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length() - 2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 通知阅读完毕之后的读状态的更改
     * @param suser
     * @param request
     * @param response
     */
    @RequestMapping("/setmessagestatus")
    public void setMessageStatus(String suser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer =response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"stauts\": \"invalid\"}";
        }
        else{
            Integer row = userMSService.setMessageStatus(suser, username);
            if(row > 0){
                res = "{\"status\": \"ok\"}";
            }
            else{
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取未读/已读消息
     * @param suser
     * @param status
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getmessagebystatus")
    public void getMessageByStatus(String suser, String status, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            Integer sta = "ed".equals(status)?1: 0;
            List<org.framework.tutor.domain.UserMessage> userMessages = userMSService.getMessageByStatus(suser, username, sta);
            if(userMessages.size() == 0){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserMessage userMessage: userMessages) {
                    res += "\"" + i + "\": ";
                    String temp = "{\"status\": \"" + userMessage.getStatus() + "\", " +
                            "\"stime\": \"" + simpleDateFormat.format(userMessage.getStime()) + "\", " +
                            "\"id\": \"" + userMessage.getId() + "\", " +
                            "\"imgsrc\": \"/images/user/face/bg.png\", " +
                            "\"descript\": \"" + userMessage.getDescript() + "\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length() - 2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 删除所选通知数据
     * @param did
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/delmymessage")
    public void delMyMessage(Integer did, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            Integer row = userMSService.delMyMessage(did);
            if(row == 1){
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
     * 标记全部为已读
     * @param request
     * @param response
     */
    @RequestMapping("/setallstatus")
    public void setAllStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;
        String username = (String) session.getAttribute("username");

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            Integer row = userMSService.setAllStatus(username);
            res = "{\"status\": \"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
