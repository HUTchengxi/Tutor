package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;

public interface UserWebsocketApi {

    String loadMySocketList(String reader, HttpServletRequest request);

    void saveMessage(String message);

    String saveWebSocket(ParamMap paramMap);
}
