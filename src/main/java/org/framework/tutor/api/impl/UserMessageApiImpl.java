package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.UserMessageApi;
import org.framework.tutor.domain.UserMessage;
import org.framework.tutor.domain.UserMessageDelete;
import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.UserMessageService;
import org.framework.tutor.service.UserMessageDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserMessageApiImpl implements UserMessageApi {

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserMessageDeleteService userMessageDeleteService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String getMyMessageCount() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer count = userMessageService.getMyMessageCount(username);
        resultMap.put("count", count);

        return gson.toJson(resultMap);
    }


    @Override
    public String getMyMessage() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        //首先获取所有的我的通知数据
        List<UserMessage> userMessageList = userMessageService.getMyMessage(username);
        if (userMessageList.size() == 0) {
            resultMap.put("status", "valid");
        } else {
            for (org.framework.tutor.domain.UserMessage userMessage : userMessageList) {
                //获取指定发送通知的管理员的指定用户的未读通知总数据
                Integer nocount = userMessageService.getNoMessageCount(userMessage.getSuser(), username);
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("suser", userMessage.getSuser());
                rowMap.put("nocount", nocount);
                rowMap.put("imgsrc", "/images/user/face/bg.png");
                rowMap.put("title", userMessage.getTitle());
                rowList.add(rowMap);
            }
            if (rowList.size() == 0) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("list", rowList);
            }
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getMessageByUser(String suser) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        //首先获取所有的我的通知数据
        List<org.framework.tutor.domain.UserMessage> userMessageList = userMessageService.getMessageBySuser(suser, username);
        if (userMessageList.size() == 0) {
            resultMap.put("status", "valid");
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.UserMessage userMessage : userMessageList) {
                UserMessageDelete userMessageDelete = userMessageDeleteService.getStatus(userMessage.getId(), username);
                Integer status = null;
                if (userMessageDelete != null) {
                    status = userMessageDelete.getStatus();
                }
                status = status == null ? 0 : 1;
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("status", status);
                rowMap.put("stime", simpleDateFormat.format(userMessage.getStime()));
                rowMap.put("id", userMessage.getId());
                rowMap.put("imgsrc", "/images/user/face/bg.png");
                rowMap.put("descript", userMessage.getDescript());
                rowList.add(rowMap);
            }
            if (rowList.size() == 0) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("list", rowList);
            }
        }

        return gson.toJson(resultMap);
    }


    @Override
    @Transactional
    public String setMessageStatus(String suser) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //清空所有的suser和username相关的已读数据
        userMessageDeleteService.deleteRepeatRead(suser, username);
        Integer row = userMessageService.setMessageRead(suser, username);
        if (row > 0) {
            resultMap.put("status", "ok");
        } else {
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getMessageByStatus(String suser, String status) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        Integer sta = "ed".equals(status) ? 1 : 0;
        List<org.framework.tutor.domain.UserMessage> userMessages = null;
        if (sta == 1) {
            userMessages = userMessageService.getReadMessage(suser, username);
        } else {
            userMessages = userMessageService.getUnreadMessage(suser, username);
        }
        if (userMessages == null || userMessages.size() == 0) {
            resultMap.put("status", "valid");
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.UserMessage userMessage : userMessages) {
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("status", sta);
                rowMap.put("stime", simpleDateFormat.format(userMessage.getStime()));
                rowMap.put("id", userMessage.getId());
                rowMap.put("imgsrc", "/images/user/face/bg.png");
                rowMap.put("descript", userMessage.getDescript());
                rowList.add(rowMap);
            }
            if (rowList.size() == 0) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("list", rowList);
            }
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String delMyMessage(Integer did) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = userMessageService.delMyMessage(did, username);
        if (row == 1) {
            resultMap.put("status", "valid");
        } else {
            resultMap.put("status", "mysqlerr");
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String setAllStatus() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = userMessageService.setAllStatus(username);
        resultMap.put("status", "valid");

        return gson.toJson(resultMap);
    }


    @Override
    public String getMessageList(ParamMap paramMap) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
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
        List<org.framework.tutor.domain.UserMessage> userMessages = userMessageService.getMessageListLimit(identity, title, startTime, offset, pageSize);
        Integer count = userMessageService.getMessageCountLimit(identity, title, startTime);
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

        return gson.toJson(resultMap);
    }


    @Override
    public String getMessageDetail(Integer id) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(6);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        org.framework.tutor.domain.UserMessage userMessage = userMessageService.getById(id);
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

        return gson.toJson(resultMap);
    }


    @Override
    public String sendMessage(EmailParam emailParam) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        HttpSession session = request.getSession();
        String suser = (String) session.getAttribute("username");

        try {
            Integer identity = emailParam.getId();
            String username = suser;
            if (!(emailParam.getSend() == null || emailParam.getSend().equals(""))) {
                username = emailParam.getSend();
            }
            String title = emailParam.getTheme();
            String message = emailParam.getEmail();

            userMessageService.seneMessage(identity, suser, username, title, message);
            resultMap.put("status", "valid");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", "sqlerr");
        }

        //TODO: 使用try/catch之后的下面的语句还是可以执行的，就算是调用了e.printStackTrace();
        return gson.toJson(resultMap);
    }
}
