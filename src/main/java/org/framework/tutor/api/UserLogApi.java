package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserLog;
import org.framework.tutor.service.UserLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public interface UserLogApi {

    /**
     * 保存用户登录记录
     * @param logcity
     * @param ip
     * @param logsystem
     * @param request
     * @param response
     */
    public void loginLog(String logcity, String ip, String logsystem, HttpServletRequest request,
                         HttpServletResponse response) throws IOException;

    /**
     * 获取用户的登录记录
     * @param request
     * @param response
     */
    public void getUserlog(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
