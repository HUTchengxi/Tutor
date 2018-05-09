package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserSecretApi {

    /**
     * 获取当前用户的密保数据
     *
     * @param request
     * @param response
     */
    public void getSecretInfo(String username, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 删除指定用户的密保数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void delUserSecret(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 为指定用户添加密保数据
     *
     * @param question
     * @param answer
     * @param response
     * @param request
     */
    public void addUserSecret(String question, String answer, HttpServletResponse response, HttpServletRequest request) throws IOException;
}
