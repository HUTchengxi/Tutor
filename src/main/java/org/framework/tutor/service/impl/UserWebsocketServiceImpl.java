package org.framework.tutor.service.impl;

import org.framework.tutor.domain.UserWebsocket;
import org.framework.tutor.mapper.UserWebsocketMapper;
import org.framework.tutor.service.UserWebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年05月09日
 */
@Component
public class UserWebsocketServiceImpl implements UserWebsocketService {

    @Autowired
    private UserWebsocketMapper userWebsocketMapper;

    @Override
    public List<UserWebsocket> loadMyWebSocketList(String username, String reader) {
        return userWebsocketMapper.loadMyWebSocketList(username, reader);
    }

    @Override
    public Integer saveWebsocket(String writer, String reader, String info, String ptime) {
        return userWebsocketMapper.saveWebsocket(writer, reader, info, ptime);
    }
}
