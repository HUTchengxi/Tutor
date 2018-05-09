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
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/usermessage_con")
public class UserMessageController {

    @Autowired
    private UserMessageApi userMessageApi;

    /**
     * 获取我的未读通知的数量
     *
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmymessagecount")
    public void getMyMessageCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.getMyMessageCount(request, response);
    }

    /**
     * 获取我的通知数据(简单数据)
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmymessage")
    public void getMyMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.getMyMessage(request, response);
    }

    /**
     * 获取指定管理员发送的我的通知数据
     *
     * @param suser
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmessagebysuser")
    public void getMessageByUser(String suser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.getMessageByUser(suser, request, response);
    }

    /**
     * 通知阅读完毕之后的读状态的更改
     *
     * @param suser
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/setmessagestatus")
    public void setMessageStatus(String suser, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.setMessageStatus(suser, request, response);
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
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmessagebystatus")
    public void getMessageByStatus(String suser, String status, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.getMessageByStatus(suser, status, request, response);
    }

    /**
     * 删除所选通知数据
     *
     * @param did
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/delmymessage")
    public void delMyMessage(Integer did, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.delMyMessage(did, request, response);
    }

    /**
     * 标记全部为已读
     *
     * @param request
     * @param response
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/setallstatus")
    public void setAllStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.setAllStatus(request, response);
    }

    /**
     * @param [paramMap, response]
     * @return void
     * @Description 管理员身份获取通知数据列表
     * @author yinjimin
     * @date 2018/4/22
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/getmessagelist")
    public void getMessageList(@RequestBody ParamMap paramMap, HttpServletResponse response) throws IOException {

        userMessageApi.getMessageList(paramMap, response);
    }

    /**
     * @param [id, response]
     * @return void
     * @Description 管理员身份查看通知详情
     * @author yinjimin
     * @date 2018/4/22
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/getmessagedetail")
    public void getMessageDetail(@RequestParam Integer id, HttpServletResponse response) throws IOException {

        userMessageApi.getMessageDetail(id, response);
    }

    /**
     *
     * @Description 发送通知
     * @param [emailParam, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/24
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/sendmessage")
    public void sendMessage(@RequestBody EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userMessageApi.sendMessage(emailParam, request, response);
    }
}
