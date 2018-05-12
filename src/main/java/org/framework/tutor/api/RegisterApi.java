package org.framework.tutor.api;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface RegisterApi {

    /**
     * 校验注册的用户名是否已经存在
     * @param username
     */
    public String checkExistUser(String username) throws IOException;

    /**
     * 进行注册(三种方式都用这一个注册：手机/邮箱/暂不验证)
     * @param username
     * @param password
     * @param checktype 注册的方式，三种之一
     * @param telephone
     * @param phonecode
     * @param email
     */
    public String registerNoCheck(String username, String password,String checktype, String telephone, String email,
                                  String phonecode) throws IOException, MessagingException, NoSuchAlgorithmException;

    /**
     * 重发邮件
     */
    public String emailResend() throws IOException, MessagingException;
}
