package org.framework.tutor.api;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface RegisterApi {

    /**
     * 校验注册的用户名是否已经存在
     *
     * @param response
     * @param username
     */
    public void checkExistUser(HttpServletResponse response, String username) throws IOException;

    /**
     * 进行注册
     *
     * @param request
     * @param response
     * @param username
     * @param password
     * @param checktype
     * @param telephone
     * @param phonecode
     * @param email
     */
    public void registerNoCheck(HttpServletRequest request, HttpServletResponse response, String username, String password,String checktype,
                                String telephone, String email, String phonecode) throws IOException, MessagingException, NoSuchAlgorithmException;

    /**
     * 重发邮件
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void emailResend(HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException;
}
