/*
 * Copyright (C) 2011-2013 ShenZhen iBoxpay Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of iBoxPay Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with iBoxpay inc.
 *
 *
 */
package org.framework.tutor.util;

import org.framework.tutor.api.UserWebsocketApi;
import org.framework.tutor.api.impl.UserWebsocketApiImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年05月10日
 */
@Component
@ServerEndpoint(value = "/openwebsocket")
public class WebsocketUtil {

    /**
     *
     * @Description 该实例无法自动注入，需要手动使用ApplicationContext来注入
     */
    private UserWebsocketApi userWebsocketApi;

    /**
     * @Description 与客户端建立的会话，需要通过他来发送数据给客户端
     */
    private Session session;

    /**
     *
     * @Description 启动类set注入，用于获取自动注入的实例
     */
    private static ConfigurableApplicationContext applicationContext;

    /**
     * @Description 保存所有连接的用户会话
     */
    private static List<Session> sessionList = new ArrayList();

    public static void setApplicationContext(ConfigurableApplicationContext applicationContext){
        applicationContext = applicationContext;
    }

    /**
     *
     * @Description 连接成功时调用的方法，客户端初次连接时触发
     * @param [session]
     * @return void
     * @author yinjimin
     * @date 2018/5/10
     */
    @OnOpen
    public void onOpen(Session session){

        this.session = session;
        if(!sessionList.contains(session)) {
            sessionList.add(session);
        }
        System.out.println("open  当前连接数" + sessionList.size());
    }

    /**
     *
     * @Description 接收客户端发送的消息数据
     * @param [message]
     * @return void
     * @author yinjimin
     * @date 2018/5/10
     */
    @OnMessage
    public void onMessage(String message) throws IOException {

        System.out.println("接收消息：" + message);
        for(Session session: sessionList){
            session.getBasicRemote().sendText(message);
        }
        //保存消息数据
//        userWebsocketApi.saveMessage(message);
    }
    
    /**
     *
     * @Description 连接关闭时调用
     * @param []
     * @return void
     * @author yinjimin
     * @date 2018/5/10
     */
    @OnClose
    public void onClose(){
        sessionList.remove(session);
        session = null;
        System.out.println("close  当前连接数" + sessionList.size());
    }
}
