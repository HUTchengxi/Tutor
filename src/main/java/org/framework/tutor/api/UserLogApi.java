package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
