package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserSecretApi {

    /**
     * 获取当前用户的密保数据
     * @param username
     */
    public String getSecretInfo(String username) throws IOException;

    /**
     * 删除指定用户的密保数据
     */
    public String delUserSecret() throws IOException;

    /**
     * 为指定用户添加密保数据
     * @param question 密保问题
     * @param answer 密保答案
     */
    public String addUserSecret(String question, String answer) throws IOException;
}
