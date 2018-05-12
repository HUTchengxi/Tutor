package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserSignApi {

    /**
     * 获取用户的签到数据
     */
    public String getMySign() throws IOException;

    /**
     * @Description 用户签到
     */
    public String addUsersign() throws IOException;
}
