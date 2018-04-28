package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserVService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

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
