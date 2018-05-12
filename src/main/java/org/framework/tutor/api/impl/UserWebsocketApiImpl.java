package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.UserWebsocketApi;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.domain.UserWebsocket;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.service.UserWebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UserWebsocketApiImpl implements UserWebsocketApi {

    @Autowired
    private UserWebsocketService userWebsocketService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public String loadMySocketList(String reader) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(8);
        List<Object> rowList = null;
        resultMap.put("mine", username);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        resultMap.put("now", simpleDateFormat2.format(now));

        List<UserWebsocket> userWebsocketList = userWebsocketService.loadMyWebSocketList(username, reader);
        if(userWebsocketList.size() > 0){
            rowList = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(UserWebsocket userWebsocket: userWebsocketList){
                Map<String, Object> rowMap = new HashMap<>();
                UserMain writer = userMainService.getByUser(userWebsocket.getWriter());
                UserMain read = userMainService.getByUser(userWebsocket.getReader());
                rowMap.put("writer", writer.getUsername());
                rowMap.put("reader", read.getUsername());
                rowMap.put("wimgsrc", writer.getImgsrc());
                rowMap.put("rimgsrc", read.getImgsrc());
                rowMap.put("info", userWebsocket.getInfo());
                rowMap.put("ptime", simpleDateFormat.format(userWebsocket.getPtime()));
                rowList.add(rowMap);
            }
        }
        resultMap.put("list", rowList);
        return gson.toJson(resultMap);
    }

    @Override
    public void saveMessage(String message) {

        String strArr[] = message.split("\\{别学我}: ");
        for(String str: strArr){
        }
    }

    @Override
    public String saveWebSocket(ParamMap paramMap) {

        String writer = paramMap.getUsername();
        String reader = paramMap.getTutorName();
        String info = paramMap.getCourseName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ptime = simpleDateFormat.format(paramMap.getStartTime());
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = userWebsocketService.saveWebsocket(writer, reader, info, ptime);
        if(row == 1){
            resultMap.put("status", "valid");
        }else{
            resultMap.put("status", "sqlerr");
        }

        return gson.toJson(resultMap);
    }
}
