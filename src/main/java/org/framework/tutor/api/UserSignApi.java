package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserSignApi {

    /**
     * 获取用户的签到数据
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMySign(HttpServletRequest request, HttpServletResponse response) throws IOException;


    public void addUsersign(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
