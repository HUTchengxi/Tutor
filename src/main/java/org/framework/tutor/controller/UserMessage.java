package org.framework.tutor.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMessageDelete;
import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.UserMSService;
import org.framework.tutor.service.UserMessageDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户通知控制类
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/usermessage_con")
public class UserMessage {

    @Autowired
    private UserMSService userMSService;

    @Autowired
    private UserMessageDeleteService userMessageDeleteService;

    /**
     * 获取我的未读通知的数量
     *
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
        } else {
            Integer count = userMSService.getMyMessageCount(username);
            res = "{\"count\": \"" + count + "\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取我的通知数据(简单数据)
     *
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

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            //首先获取所有的我的通知数据
            List<org.framework.tutor.domain.UserMessage> userMessageList = userMSService.getMyMessage(username);
            if (userMessageList.size() == 0) {
                res = "{\"status\": \"valid\"}";
            } else {
                res = "{";
                int i = 1;
                for (org.framework.tutor.domain.UserMessage userMessage : userMessageList) {
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
                if (res.equals("{")) {
                    res = "{\"status\": \"valid\"}";
                } else {
                    res = res.substring(0, res.length() - 2);
                    res += "}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定管理员发送的我的通知数据
     *
     * @param suser
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getmessagebysuser")
    public void getMessageByUser(String suser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            //首先获取所有的我的通知数据
            List<org.framework.tutor.domain.UserMessage> userMessageList = userMSService.getMessageBySuser(suser, username);
            if (userMessageList.size() == 0) {
                res = "{\"status\": \"valid\"}";
            } else {
                System.out.println(userMessageList.size());
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserMessage userMessage : userMessageList) {
                    UserMessageDelete userMessageDelete = userMessageDeleteService.getStatus(userMessage.getId(), username);
                    Integer status = null;
                    if (userMessageDelete != null) {
                        status = userMessageDelete.getStatus();
                    }
                    status = status == null ? 0 : 1;
                    res += "\"" + i + "\": ";
                    String temp = "{\"status\": \"" + status + "\", " +
                            "\"stime\": \"" + simpleDateFormat.format(userMessage.getStime()) + "\", " +
                            "\"id\": \"" + userMessage.getId() + "\", " +
                            "\"imgsrc\": \"/images/user/face/bg.png\", " +
                            "\"descript\": \"" + userMessage.getDescript() + "\"}, ";
                    res += temp;
                    i++;
                }
                if (res.equals("{")) {
                    res = "{\"status\": \"valid\"}";
                } else {
                    res = res.substring(0, res.length() - 2);
                    res += "}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 通知阅读完毕之后的读状态的更改
     *
     * @param suser
     * @param request
     * @param response
     */
    @RequestMapping("/setmessagestatus")
    @Transactional
    public void setMessageStatus(String suser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"stauts\": \"invalid\"}";
        } else {
            //清空所有的suser和username相关的已读数据
            userMessageDeleteService.deleteRepeatRead(suser, username);
            Integer row = userMSService.setMessageRead(suser, username);
            if (row > 0) {
                res = "{\"status\": \"ok\"}";
            } else {
                res = "{\"status\": \"valid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取未读/已读消息
     *
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

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            Integer sta = "ed".equals(status) ? 1 : 0;
            List<org.framework.tutor.domain.UserMessage> userMessages = null;
            if (sta == 1) {
                userMessages = userMSService.getReadMessage(suser, username);
            } else {
                userMessages = userMSService.getUnreadMessage(suser, username);
            }
            if (userMessages == null || userMessages.size() == 0) {
                res = "{\"status\": \"valid\"}";
            } else {
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserMessage userMessage : userMessages) {
                    res += "\"" + i + "\": ";
                    String temp = "{\"status\": \"" + sta + "\", " +
                            "\"stime\": \"" + simpleDateFormat.format(userMessage.getStime()) + "\", " +
                            "\"id\": \"" + userMessage.getId() + "\", " +
                            "\"imgsrc\": \"/images/user/face/bg.png\", " +
                            "\"descript\": \"" + userMessage.getDescript() + "\"}, ";
                    res += temp;
                    i++;
                }
                if (res.equals("{")) {
                    res = "{\"status\": \"valid\"}";
                } else {
                    res = res.substring(0, res.length() - 2);
                    res += "}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 删除所选通知数据
     *
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

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            Integer row = userMSService.delMyMessage(did, username);
            if (row == 1) {
                res = "{\"status\": \"valid\"}";
            } else {
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 标记全部为已读
     *
     * @param request
     * @param response
     */
    @RequestMapping("/setallstatus")
    public void setAllStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;
        String username = (String) session.getAttribute("username");

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            Integer row = userMSService.setAllStatus(username);
            res = "{\"status\": \"valid\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * @param [paramMap, response]
     * @return void
     * @Description 管理员身份获取通知数据列表
     * @author yinjimin
     * @date 2018/4/22
     */
    @PostMapping("/getmessagelist")
    public void getMessageList(@RequestBody ParamMap paramMap, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        PrintWriter writer = response.getWriter();
        List<Object> rowList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Integer pageNo = paramMap.getPageNo();
        Integer pageSize = paramMap.getPageSize();
        Integer offset = pageNo * pageSize;
        Integer identity = 0;
        if (paramMap.getStatus() != null) {
            identity = paramMap.getStatus();
        }
        String startTime = "";
        if (paramMap.getStartTime() != null) {
            startTime = simpleDateFormat.format(paramMap.getStartTime());
        }
        String title = "";
        if (paramMap.getCourseName() != null) {
            title = paramMap.getCourseName();
        }

        //模糊查询所有的数据
        List<org.framework.tutor.domain.UserMessage> userMessages = userMSService.getMessageListLimit(identity, title, startTime, offset, pageSize);
        Integer count = userMSService.getMessageCountLimit(identity, title, startTime);
        if (userMessages.size() == 0) {
            resultMap.put("rows", rowList);
            resultMap.put("total", 0);
        } else {
            for (org.framework.tutor.domain.UserMessage userMessage : userMessages) {
                Map<String, Object> rowMap = new HashMap<>(1);
                rowMap.put("id", userMessage.getId());
                rowMap.put("title", userMessage.getTitle());
                rowMap.put("time", simpleDateFormat.format(userMessage.getStime()));
                String status = userMessage.getStatus() == 1 ? "已读" : "未读";
                rowMap.put("status", status);
                String ident = userMessage.getIdentity() == 1 ? "是" : "否";
                rowMap.put("username", ident.equals("是") ? userMessage.getUsername() : "所有人");
                rowMap.put("identity", ident);
                rowList.add(rowMap);
            }
            resultMap.put("rows", rowList);
            resultMap.put("total", count);
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [id, response]
     * @return void
     * @Description 管理员身份查看通知详情
     * @author yinjimin
     * @date 2018/4/22
     */
    @PostMapping("/getmessagedetail")
    public void getMessageDetail(@RequestParam Integer id, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(6);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        org.framework.tutor.domain.UserMessage userMessage = userMSService.getById(id);
        if (userMessage == null) {
            resultMap.put("status", "invalid");
        } else {
            resultMap.put("title", userMessage.getTitle());
            resultMap.put("message", userMessage.getDescript());
            String status = userMessage.getStatus() == 1 ? "已读" : "未读";
            resultMap.put("status", status);
            String ident = userMessage.getIdentity() == 1 ? "是" : "否";
            resultMap.put("identity", ident);
            resultMap.put("username", ident.equals("是") ? userMessage.getUsername() : "所有人");
            resultMap.put("time", simpleDateFormat.format(userMessage.getStime()));
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 发送通知
     * @param [emailParam, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/24
     */
    @PostMapping("/sendmessage")
    public void sendMessage(@RequestBody EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        HttpSession session = request.getSession();
        String suser = (String) session.getAttribute("username");

        try {
            Integer identity = emailParam.getId();
            String username = suser;
            if(emailParam.getSend()==null || emailParam.getSend().equals("")){
                username = emailParam.getSend();
            }
            String title = emailParam.getTheme();
            String message = emailParam.getEmail();

            userMSService.seneMessage(identity, suser, username, title, message);
            resultMap.put("status", "valid");
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status", "sqlerr");
        }

        //TODO: 使用try/catch之后的下面的语句还是可以执行的，就算是调用了e.printStackTrace();
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
