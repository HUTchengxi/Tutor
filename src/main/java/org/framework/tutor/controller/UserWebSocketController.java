package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.UserWebsocketApi;
import org.framework.tutor.entity.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yinjimin
 * @Description: 用户建立会话
 * @date 2018年05月09日
 */
@RestController
@RequestMapping("/userwebsocket_con")
public class UserWebSocketController {

    @Autowired
    private UserWebsocketApi userWebsocketApi;

    /**  
     * @Description 获取历史消息
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/loadmysocketlist.json")
    public String loadMySocketList(@RequestParam String reader){
        return userWebsocketApi.loadMySocketList(reader);
    }

    /**
     * @Description 保存发送的消息
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/savewebsocket.json")
    public String saveWebsocket(@RequestBody ParamMap paramMap){
        return userWebsocketApi.saveWebSocket(paramMap);
    }
}
