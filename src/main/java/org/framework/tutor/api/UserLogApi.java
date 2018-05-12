package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserLogApi {

    /**
     * 保存用户登录记录
     * @param logcity 登录城市
     * @param ip 登录ip
     * @param logsystem 登录的操作系统
     */
    public String loginLog(String logcity, String ip, String logsystem) throws IOException;

    /**
     * 获取用户的登录记录
     */
    public String getUserlog() throws IOException;
}
