package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
