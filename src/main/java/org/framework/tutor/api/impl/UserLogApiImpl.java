package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.UserLogApi;
import org.framework.tutor.domain.UserLog;
import org.framework.tutor.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class UserLogApiImpl implements UserLogApi {

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String loginLog(String logcity, String ip, String logsystem) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        if (userLogService.saveUserlog(username, logcity, ip, logsystem)) {
            resultMap.put("status", "valid");
        } else {
            resultMap.put("status", "mysqlerr");
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getUserlog() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

            //获取登录记录
            List<UserLog> userLogs = userLogService.getUserlog(username);
            if (userLogs.size() == 0) {
                resultMap.put("status", "ok");
                resultMap.put("len", 0);
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.UserLog userLog : userLogs) {
                    Map<String, Object> rowMap = new HashMap<>(8);
                    rowMap.put("logtime", simpleDateFormat.format(userLog.getLogtime()));
                    rowMap.put("logcity", userLog.getLogcity());
                    rowMap.put("logip", userLog.getLogip());
                    rowMap.put("logsystem", userLog.getLogsys());
                    rowList.add(rowMap);
                }
                resultMap.put("list", rowList);
            }

        return gson.toJson(resultMap);
    }
}
