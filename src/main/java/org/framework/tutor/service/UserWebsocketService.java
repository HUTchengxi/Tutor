package org.framework.tutor.service;

import org.framework.tutor.domain.UserWebsocket;

import java.util.List;

public interface UserWebsocketService {

    List<UserWebsocket> loadMyWebSocketList(String username, String reader);

    Integer saveWebsocket(String writer, String reader, String info, String ptime);
}
