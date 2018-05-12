package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserMessageApi;
import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.entity.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户通知控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/usermessage_con")
public class UserMessageController {

    @Autowired
    private UserMessageApi userMessageApi;

    /**
     * 获取我的未读通知的数量
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmymessagecount")
    public String getMyMessageCount() throws IOException {

        return userMessageApi.getMyMessageCount();
    }

    /**
     * 获取我的通知数据(简单数据)
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmymessage")
    public String getMyMessage() throws IOException {

        return userMessageApi.getMyMessage();
    }

    /**
     * 获取指定管理员发送的我的通知数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmessagebysuser")
    public String getMessageByUser(String suser) throws IOException {

        return userMessageApi.getMessageByUser(suser);
    }

    /**
     * 通知阅读完毕之后的读状态的更改
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/setmessagestatus")
    public String setMessageStatus(String suser) throws IOException {

        return userMessageApi.setMessageStatus(suser);
    }

    /**
     * 获取未读/已读消息
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmessagebystatus")
    public String getMessageByStatus(String suser, String status) throws IOException {

        return userMessageApi.getMessageByStatus(suser, status);
    }

    /**
     * 删除所选通知数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/delmymessage")
    public String delMyMessage(Integer did) throws IOException {

        return userMessageApi.delMyMessage(did);
    }

    /**
     * 标记全部为已读
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/setallstatus")
    public String setAllStatus() throws IOException {

        return userMessageApi.setAllStatus();
    }

    /**
     * @Description 管理员身份获取通知数据列表
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/getmessagelist")
    public String getMessageList(@RequestBody ParamMap paramMap) throws IOException {

        return userMessageApi.getMessageList(paramMap);
    }

    /**
     * @Description 管理员身份查看通知详情
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/getmessagedetail")
    public String getMessageDetail(@RequestParam Integer id) throws IOException {

        return userMessageApi.getMessageDetail(id);
    }

    /**
     * @Description 发送通知
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/sendmessage")
    public String sendMessage(@RequestBody EmailParam emailParam) throws IOException {

        return userMessageApi.sendMessage(emailParam);
    }
}
